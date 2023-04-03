package fr.ceri.chomageen2mots.webservice;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
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
}
