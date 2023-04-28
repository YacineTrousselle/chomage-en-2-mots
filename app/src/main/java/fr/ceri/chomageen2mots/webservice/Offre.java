package fr.ceri.chomageen2mots.webservice;

import androidx.annotation.NonNull;

import com.squareup.moshi.Json;

public class Offre {
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

    @NonNull
    @Override
    public String toString() {
        return "Offre{" +
                "id='" + id + '\'' +
                ", intitule='" + intitule + '\'' +
                ", description='" + description + '\'' +
                ", logoEntreprise='" + logoEntreprise + '\'' +
                ", nomEntreprise='" + nomEntreprise + '\'' +
                '}';
    }
}