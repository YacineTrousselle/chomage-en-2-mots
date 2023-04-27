package fr.ceri.chomageen2mots.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.squareup.moshi.JsonClass;

import java.util.ArrayList;
import java.util.List;

@JsonClass(generateAdapter = false)
@Entity(tableName = "favorites")
public class Offre {
    @PrimaryKey(autoGenerate = false)
    public String id;
    public String intitule;
    public String description;
    public String dateCreation;
    public String dateActualisation;
    public String romeCode;
    public String romeLibelle;
    public String appellationlibelle;
    public String typeContrat;
    public String typeContratLibelle;
    public String natureContrat;
    public String experienceExige;
    public String experienceLibelle;
    public String experienceCommentaire;
    public String dureeTravailLibelle;
    public String dureeTravailLibelleConverti;
    public String complementExercice;
    public String conditionExercice;
    public boolean alternance;
    public float nombrePostes;
    public boolean accessibleTH;
    public String deplacementCode;
    public String deplacementLibelle;
    public String qualificationCode;
    public String qualificationLibelle;
    public String secteurActivite;
    public String secteurActiviteLibelle;
    public String trancheEffectifEtab;
    public LieuTravail lieuTravail;
    public Entreprise entreprise;
    public List<Object> formations = new ArrayList<>();
    public List<Object> langues = new ArrayList<>();
    public List<Object> permis = new ArrayList<>();
    public List<Object> outilsBureautiques = new ArrayList<>();
    public List<Object> competences = new ArrayList<>();
    public Salaire salaire;
    public Contact contact;
    public Agence agence;
    public List<Object> qualitesProfessionnelles = new ArrayList<>();
    public OrigineOffre origineOffre;

    public static class OrigineOffre {
        public String origine;
        public String urlOrigine;
        List<Object> partenaires = new ArrayList<>();
    }

    public static class Agence {
        public String telephone;
        public String courriel;
    }

    public static class Contact {
        public String nom;
        public String coordonnees1;
        public String coordonnees2;
        public String coordonnees3;
        public String telephone;
        public String courriel;
        public String commentaire;
        public String urlRecruteur;
        public String urlPostulation;
    }

    public static class Salaire {
        public String libelle;
        public String commentaire;
        public String complement1;
        public String complement2;
    }

    public static class Entreprise {
        public String nom;
        public String description;
        public String logo;
        public String url;
    }

    public static class LieuTravail {
        public String libelle;
        public float latitude;
        public float longitude;
        public String codePostal;
        public String commune;
    }
}
