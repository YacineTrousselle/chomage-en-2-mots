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
    public String qualificationCode;
    public String qualificationLibelle;
    public String departement;

    public Favorite(
            @NonNull String id,
            String intitule,
            String description,
            String logoEntreprise,
            String nomEntreprise,
            String typeContrat,
            String dureeTravailLibelle,
            String url,
            String qualificationCode,
            String qualificationLibelle,
            String departement
    ) {
        this.id = id;
        this.intitule = intitule;
        this.description = description;
        this.logoEntreprise = logoEntreprise;
        this.nomEntreprise = nomEntreprise;
        this.typeContrat = typeContrat;
        this.dureeTravailLibelle = dureeTravailLibelle;
        this.url = url;
        this.qualificationCode = qualificationCode;
        this.qualificationLibelle = qualificationLibelle;
        this.departement = departement;
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
        this.qualificationCode = offre.qualificationCode;
        this.qualificationLibelle = offre.qualificationLibelle;
        this.departement = offre.lieuTravail.codePostal.substring(0, 2);
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "id='" + id + '\'' +
                ", intitule='" + intitule + '\'' +
                ", description='" + description + '\'' +
                ", logoEntreprise='" + logoEntreprise + '\'' +
                ", nomEntreprise='" + nomEntreprise + '\'' +
                ", typeContrat='" + typeContrat + '\'' +
                ", dureeTravailLibelle='" + dureeTravailLibelle + '\'' +
                ", url='" + url + '\'' +
                ", qualificationCode='" + qualificationCode + '\'' +
                ", qualificationLibelle='" + qualificationLibelle + '\'' +
                ", departement='" + departement + '\'' +
                '}';
    }
}
