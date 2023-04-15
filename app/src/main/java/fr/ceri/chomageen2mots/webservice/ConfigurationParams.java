package fr.ceri.chomageen2mots.webservice;

import java.io.Serializable;
import java.util.Objects;

public class ConfigurationParams implements Serializable {
    private int departement;
    private boolean tempsPlein;
    private String experience;
    private String niveauFormation;

    public ConfigurationParams() {
        this.departement = 0;
        this.tempsPlein = true;
        this.experience = null;
        this.niveauFormation = null;
    }

    public int getDepartement() {
        return departement;
    }

    public void setDepartement(int departement) {
        this.departement = departement;
    }

    public boolean isTempsPlein() {
        return tempsPlein;
    }

    public void setTempsPlein(boolean tempsPlein) {
        this.tempsPlein = tempsPlein;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        if (!Objects.equals(experience, "1") && !Objects.equals(experience, "2") && !Objects.equals(experience, "3")) {
            return ;
        }
        this.experience = experience;
    }

    public String getNiveauFormation() {
        return niveauFormation;
    }

    public void setNiveauFormation(String niveauFormation) {
        this.niveauFormation = niveauFormation;
    }

    @Override
    public String toString() {
        return "ConfigurationParams{" +
                "departement=" + departement +
                ", tempsPlein=" + tempsPlein +
                ", experience='" + experience + '\'' +
                ", niveauFormation='" + niveauFormation + '\'' +
                '}';
    }
}
