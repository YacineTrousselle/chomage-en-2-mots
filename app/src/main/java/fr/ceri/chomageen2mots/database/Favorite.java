package fr.ceri.chomageen2mots.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import fr.ceri.chomageen2mots.webservice.Offre;

@Entity(tableName = "favorites")
public class Favorite extends Offre {
    @PrimaryKey
    @NonNull
    public String id;

    public Favorite(String id, String intitule, String description, String logoEntreprise, String nomEntreprise) {
        this.id = id;
        this.intitule = intitule;
        this.description = description;
        this.logoEntreprise = logoEntreprise;
        this.nomEntreprise = nomEntreprise;
    }
}
