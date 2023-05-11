package fr.ceri.chomageen2mots.webservice;

public class Offre {
    public String id;
    public String intitule;
    public String description;
    public String typeContrat;
    public String dureeTravailLibelle;
    public String qualificationCode;
    public String qualificationLibelle;

    public Entreprise entreprise;
    public OrigineOffre origineOffre;
    public LieuTravail lieuTravail;

    public static class Entreprise {
        public String nom;
        public String logo;
    }

    public static class OrigineOffre {
        public String urlOrigine;
    }

    public static class LieuTravail {
        public String codePostal;
    }

    public String getInfo() {
        String infoStr = "";
        if (entreprise.nom != null) {
            infoStr = entreprise.nom + "\n";
        }
        if (entreprise.nom != null) {
            infoStr +=  typeContrat + " ";
        }
        if (dureeTravailLibelle != null) {
            infoStr += dureeTravailLibelle;
        }
        return infoStr;
    }
}
