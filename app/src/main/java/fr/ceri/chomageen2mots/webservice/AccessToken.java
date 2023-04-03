package fr.ceri.chomageen2mots.webservice;

import androidx.annotation.NonNull;

import com.squareup.moshi.Json;

import java.util.Date;

public class AccessToken {
    @Json(name = "access_token")
    private String token;
    @Json(name = "expires_in")
    private int expiresIn;

    public AccessToken(String token, int expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public boolean isValid(long requestedAt) {
        return (new Date().getTime()) / 1000 > requestedAt + expiresIn;
    }

    @NonNull
    @Override
    public String toString() {
        return "AccessToken{" +
                "token='" + token + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}
