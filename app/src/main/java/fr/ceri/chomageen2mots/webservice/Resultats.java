package fr.ceri.chomageen2mots.webservice;

import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

public class Resultats {
    @Json(name = "resultats")
    public List<Offre> resultats = new ArrayList<>();
}
