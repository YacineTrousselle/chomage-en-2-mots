package fr.ceri.chomageen2mots.webservice;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationParams implements Serializable {
    private static final ArrayList<String> EXPERIENCE = new ArrayList<>(Arrays.asList(
            "Moins d'un an d'expérience",
            "De 1 à 3 ans d'expérience",
            "Plus de 3 ans d'expérience"
    ));

    public static Map<String, String> getConfig(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Map<String, String> params = new HashMap<>();
        String departement = sharedPreferences.getString("departement", "Tous les départements");
        if (!departement.equals("Tous les départements")) {
            String departementCode = departement.split("-")[0].trim();
            params.put("departement", departementCode);
        }
        String temps = sharedPreferences.getString("temps", "Tout");
        if (!temps.equals("Tout")) {
            boolean isTempsPlein = temps.equals("Temps plein");
            params.put("tempsPlein", String.valueOf(isTempsPlein));
        }
        String experience = sharedPreferences.getString("experience", "Tout");
        if (!experience.equals("Tout")) {
            int ind = EXPERIENCE.indexOf(experience);
            if (ind != -1) {
                params.put("experience", String.valueOf(ind));
            }
        }
        String niveauFormation = sharedPreferences.getString("niveauFormation", "Tous les niveaux de formation");
        if (!niveauFormation.equals("Tous les niveaux de formation")) {
            String niveauFormationValue = niveauFormation.split("-")[0].trim();
            params.put("niveauFormation", niveauFormationValue);
        }

        return params;
    }

    public static int getNbOffreParPage(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return Integer.parseInt(sharedPreferences.getString("range", "10"));
    }
}
