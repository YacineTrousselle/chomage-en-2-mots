package fr.ceri.chomageen2mots.webservice;

import com.squareup.moshi.Json;

import java.util.ArrayList;
import java.util.List;

import fr.ceri.chomageen2mots.database.Offre;

public class SearchResult {
    @Json(name = "resultats")
    public List<Offre> resultats = new ArrayList<>();
}
