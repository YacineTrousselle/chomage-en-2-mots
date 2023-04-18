package fr.ceri.chomageen2mots.webservice;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class PoleEmploiApi {
    public static final String GRANT_TYPE = "client_credentials";
    public static final String CLIENT_ID = "PAR_chomageen2mots_4f49b2ffbfa6de5e7742eac2926419b1001c4fb553df6e4fbed8f87d554e7e1b";
    public static final String CLIENT_SECRET = "77d670a35e3b6ef3308a7031a47bfa9ef7a4e5aa13b457f02b593a0a685af580";
    public static final String SCOPE = "api_offresdemploiv2 o2dsoffre";
    private static final String ACCESS_TOKEN_REQUEST_URL = "https://entreprise.pole-emploi.fr/connexion/oauth2/access_token?realm=partenaire";

    private final PEInterface api;
    private final MutableLiveData<AccessToken> accessToken = new MutableLiveData<>();
    private final MutableLiveData<Long> requestedAt = new MutableLiveData<>((long) 0);

    public PoleEmploiApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pole-emploi.io")
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        api = retrofit.create(PEInterface.class);
    }

    public void callApi(CallbackInterface callback) {
        Log.d("jean", "callApi: " + accessToken + " " + requestedAt);
        if (accessToken.getValue() != null && accessToken.getValue().isValid(requestedAt.getValue())) {
            Log.d("jean", "there is a token " + accessToken.getValue());
            callback.onTokenReceived(accessToken.getValue().getToken());
        } else {
            api.getAccessToken(ACCESS_TOKEN_REQUEST_URL, GRANT_TYPE, CLIENT_ID, CLIENT_SECRET, SCOPE)
                    .enqueue(
                            new Callback<AccessToken>() {
                                @Override
                                public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {
                                    accessToken.postValue(response.body());
                                    requestedAt.postValue(new Date().getTime() / 1000);
                                    callback.onTokenReceived(response.body().getToken());
                                    Log.d("jean", "onResponse: " + response.body());
                                }

                                @Override
                                public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {
                                    throw new RuntimeException(t);
                                }
                            }
                    );
        }
    }

    public void search(Context context, String keyword, int pagination) {
        int nbOffre = ConfigurationParams.getNbOffreParPage(context);
        String range = (nbOffre * pagination) + "-" + (nbOffre * (pagination + 1) - 1);
        callApi(token ->
            api.search("Bearer " + token, ConfigurationParams.getConfig(context), keyword, range).enqueue(
                    new Callback<SearchResult>() {
                        @Override
                        public void onResponse(@NonNull Call<SearchResult> call, @NonNull Response<SearchResult> response) {
                            Log.d("jean", String.valueOf(call.request().url()));
                            if (response.code() == 200 || response.code() == 206) {
                                assert response.body() != null;
                                Log.d("jean", "onResponse: " + Arrays.toString(response.body().resultats.stream().map(offre -> offre.id).toArray()));
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<SearchResult> call, @NonNull Throwable t) {
                            throw new RuntimeException(t);
                        }
                    }
            )
        );
    }
}

