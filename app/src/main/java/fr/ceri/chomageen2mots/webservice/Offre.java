package fr.ceri.chomageen2mots.webservice;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonClass;

import java.util.ArrayList;
import java.util.List;
public class Offre {
    @PrimaryKey
    @NonNull
    @Json(name = "id")
    public String id;
    @Json(name = "intitule")
    public String intitule;
    @Json(name = "description")
    public String description;
    @Json(name = "entreprise.logo")

    public String logoEntreprise;
    @Json(name = "entreprise.nom")
    public String nomEntreprise;
}