package fr.ceri.chomageen2mots.webservice;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    private List<Offre> offres;
    private boolean isLoading;
    private int code;

    public SearchResult(Resultats resultats, boolean isLoading, int code) {
        this.offres = resultats == null ? new ArrayList<>() : resultats.resultats;
        this.isLoading = isLoading;
        this.code = code;
    }

    public List<Offre> getOffres() {
        return offres;
    }

    public void setOffres(List<Offre> offres) {
        this.offres = offres;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
