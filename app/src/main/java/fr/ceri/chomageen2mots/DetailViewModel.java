package fr.ceri.chomageen2mots;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import fr.ceri.chomageen2mots.database.Favorite;
import fr.ceri.chomageen2mots.database.FavoriteRepository;
import fr.ceri.chomageen2mots.webservice.Offre;
import fr.ceri.chomageen2mots.webservice.PoleEmploiApi;

public class DetailViewModel extends AndroidViewModel {
    private final String id;
    private final MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();
    private final MutableLiveData<Offre> offreMutableLiveData;
    private final FavoriteRepository favoriteRepository;

    public DetailViewModel(@NonNull Application application, String id) {
        super(application);
        this.id = id;
        PoleEmploiApi poleEmploiApi = PoleEmploiApi.getInstance(application);
        favoriteRepository = new FavoriteRepository(application);
        isFavorite.setValue(favoriteRepository.getFavorite(id) != null);
        if (Boolean.TRUE.equals(isFavorite.getValue())) {
            offreMutableLiveData = new MutableLiveData<>(favoriteRepository.getFavorite(id).favoriteToFakeOffre());
        } else {
            offreMutableLiveData = poleEmploiApi.getOffre(id);
        }
    }

    public MutableLiveData<Offre> getOffreMutableLiveData() {
        return offreMutableLiveData;
    }

    public MutableLiveData<Boolean> getIsFavorite() {
        return isFavorite;
    }

    public void deleteFavorite() {
        favoriteRepository.deleteFavorite(favoriteRepository.getFavorite(id));
        isFavorite.setValue(false);
    }

    public void addFavorite(Offre offre) {
        favoriteRepository.insertFavorite(new Favorite(offre));
        isFavorite.setValue(true);
    }
}
