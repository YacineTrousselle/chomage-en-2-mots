package fr.ceri.chomageen2mots.webservice;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface PEInterface {
    @FormUrlEncoded
    @POST
    Call<AccessToken> getAccessToken(
            @Url String accessTokenUrl,
            @Field("grant_type") String grantType,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("scope") String scope
    );

    @GET("/partenaire/offresdemploi/v2/offres/search")
    Call<Resultats> search(
            @Header("Authorization") String accessToken,
            @QueryMap Map<String, String> params,
            @Query("motsCles") String keyword,
            @Query("range") String range
    );
}
