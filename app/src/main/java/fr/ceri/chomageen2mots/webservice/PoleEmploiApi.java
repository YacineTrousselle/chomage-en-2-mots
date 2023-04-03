package fr.ceri.chomageen2mots.webservice;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

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
    private MutableLiveData<AccessToken> accessToken = new MutableLiveData<AccessToken>();
    private long requestedAt = 0;

    public PoleEmploiApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pole-emploi.io")
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        api = retrofit.create(PEInterface.class);
    }

    public MutableLiveData<AccessToken> getAccessToken() {
        if (accessToken.getValue() != null && accessToken.getValue().isValid(requestedAt)){
            return accessToken;
        }

        api.getAccessToken(ACCESS_TOKEN_REQUEST_URL, GRANT_TYPE, CLIENT_ID, CLIENT_SECRET, SCOPE)
            .enqueue(
                    new Callback<AccessToken>() {
                        @Override
                        public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {
                            accessToken.postValue(response.body());
                            requestedAt = (new Date().getTime()) / 1000;
                            Log.d("jean", "onResponse: " + response.body());
                        }

                        @Override
                        public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {
                            throw new RuntimeException(t);
                        }
                    }
            );
        return accessToken;
    }
}

