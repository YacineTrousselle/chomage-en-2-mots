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
        if (offre.lieuTravail.codePostal != null && offre.lieuTravail.codePostal.length() >= 2) {
            this.departement = offre.lieuTravail.codePostal.substring(0, 2);
        }
    }

    public Offre favoriteToFakeOffre() {
        Offre offre = new Offre();
        offre.entreprise = new Offre.Entreprise();
        offre.lieuTravail = new Offre.LieuTravail();
        offre.origineOffre = new Offre.OrigineOffre();
        offre.id = this.id;
        offre.intitule = this.intitule;
        offre.description = this.description;
        offre.entreprise.logo = this.logoEntreprise;
        offre.entreprise.nom = this.nomEntreprise;
        offre.typeContrat = this.typeContrat;
        offre.dureeTravailLibelle = this.dureeTravailLibelle;
        offre.origineOffre.urlOrigine = this.url;
        offre.qualificationCode = this.qualificationCode;
        offre.qualificationLibelle = this.qualificationLibelle;
        offre.lieuTravail.codePostal = this.departement;
        return offre;
    }

    public String getInfo() {
        String info = intitule;
        if (typeContrat != null) {
            info += "\nType Contrat: " + typeContrat;
        }
        if (qualificationLibelle != null) {
            info += "\nQualification: " + qualificationLibelle;
        }
        if (departement != null) {
            info += "\nDepartement: " + departement;
        }

        return info;
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
