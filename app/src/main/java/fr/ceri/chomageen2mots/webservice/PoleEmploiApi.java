package fr.ceri.chomageen2mots.webservice;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.Arrays;

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

    private static PoleEmploiApi INSTANCE = null;
    private static volatile AccessToken accessToken = null;
    private final PEInterface api;
    private final Application app;

    private PoleEmploiApi(Application app) {
        this.app = app;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.pole-emploi.io")
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        api = retrofit.create(PEInterface.class);
    }

    public static PoleEmploiApi getInstance(Application app) {
        if (INSTANCE == null) {
            INSTANCE = new PoleEmploiApi(app);
        }
        return INSTANCE;
    }

    public void callApi(CallbackInterface callback) {
        if (accessToken != null && accessToken.isValid()) {
            callback.onTokenReceived(accessToken.getToken());
        } else {
            Call<AccessToken> accessTokenCall = api.getAccessToken(ACCESS_TOKEN_REQUEST_URL, GRANT_TYPE, CLIENT_ID, CLIENT_SECRET, SCOPE);
            accessTokenCall.enqueue(
                    new Callback<AccessToken>() {
                        @Override
                        public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {
                            accessToken = response.body();
                            callback.onTokenReceived(accessToken.getToken());
                        }

                        @Override
                        public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {
                            Context context = app.getApplicationContext();
                            CharSequence text = "Pas de connexion";
                            int duration = Toast.LENGTH_LONG;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
            );

        }
    }

    public MutableLiveData<SearchResult> search(Context context, String keyword, int pagination) {
        int nbOffre = ConfigurationParams.getNbOffreParPage(context);
        String range = (nbOffre * pagination) + "-" + (nbOffre * (pagination + 1) - 1);

        MutableLiveData<SearchResult> searchResult = new MutableLiveData<>(new SearchResult(null, false, 0));
        callApi(token ->
                api.search("Bearer " + token, ConfigurationParams.getConfig(context), keyword, range).enqueue(
                        new Callback<Resultats>() {
                            @Override
                            public void onResponse(@NonNull Call<Resultats> call, @NonNull Response<Resultats> response) {
                                if (response.code() == 200 || response.code() == 206) {
                                    searchResult.postValue(new SearchResult(response.body(), false, response.code()));
                                    Log.d("MANU", "ANDROID CKROBI1");
                                    return;
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<Resultats> call, @NonNull Throwable t) {
                                Context context = app.getApplicationContext();
                                CharSequence text = "Pas de connexion";
                                int duration = Toast.LENGTH_LONG;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                        }
                )
        );
        return searchResult;
    }
}
