package fr.ceri.chomageen2mots.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import fr.ceri.chomageen2mots.webservice.Offre;

@Entity(tableName = "favorites")
public class Favorite {
    @PrimaryKey
    @NonNull
    public String id;
    public String intitule;
    public String description;
    public String logoEntreprise;
    public String nomEntreprise;
    public String typeContrat;
    public String dureeTravailLibelle;
    public String url;

    public Favorite(
            @NonNull String id,
            String intitule,
            String description,
            String logoEntreprise,
            String nomEntreprise,
            String typeContrat,
            String dureeTravailLibelle,
            String url
    ) {
        this.id = id;
        this.intitule = intitule;
        this.description = description;
        this.logoEntreprise = logoEntreprise;
        this.nomEntreprise = nomEntreprise;
        this.typeContrat = typeContrat;
        this.dureeTravailLibelle = dureeTravailLibelle;
        this.url = url;
    }

    public Favorite(Offre offre) {
        this.id = offre.id;
        this.intitule = offre.intitule;
        this.description = offre.description;
        this.logoEntreprise = offre.entreprise.logo;
        this.nomEntreprise = offre.entreprise.nom;
        this.typeContrat = offre.typeContrat;
        this.dureeTravailLibelle = offre.dureeTravailLibelle;
        this.url = offre.origineOffre.urlOrigine;
    }
}
