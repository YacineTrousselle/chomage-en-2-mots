package fr.ceri.chomageen2mots.webservice;

public class Offre {
    public String id;
    public String intitule;
    public String description;
    public Entreprise entreprise;

    @Override
    public String toString() {
        return "Offre{" +
                "id='" + id + '\'' +
                ", intitule='" + intitule + '\'' +
                ", description='" + description + '\'' +
                ", entreprise=" + entreprise +
                '}';
    }

    public static class Entreprise {
        public String nom;
        public String logo;

        @Override
        public String toString() {
            return "Entreprise{" +
                    "nom='" + nom + '\'' +
                    ", logo='" + logo + '\'' +
                    '}';
        }
    }
}
