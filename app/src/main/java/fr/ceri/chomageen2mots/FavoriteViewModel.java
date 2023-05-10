package fr.ceri.chomageen2mots;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import fr.ceri.chomageen2mots.database.Favorite;
import fr.ceri.chomageen2mots.database.FavoriteFilters;
import fr.ceri.chomageen2mots.database.FavoriteRepository;

public class FavoriteViewModel extends AndroidViewModel {
    private final FavoriteRepository favoriteRepository;
    private MutableLiveData<FavoriteFilters> favoriteFilters = new MutableLiveData<>(new FavoriteFilters());

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        favoriteRepository = new FavoriteRepository(application);
        LiveData<List<Favorite>> favorites = favoriteRepository.getAllFavorites();
    }

    public LiveData<List<Favorite>> getAllFavorites() {
        return favoriteRepository.getAllFavorites();
    }

    public void deleteFav(Favorite favorite) {
        favoriteRepository.deleteFavorite(favorite);
    }

    public Favorite getFavoriteById(String id) {
        return favoriteRepository.getFavorite(id);
    }

    public void setFavoriteFilters(FavoriteFilters favoriteFilters) {
        this.favoriteFilters.setValue(favoriteFilters);
    }

    public MutableLiveData<FavoriteFilters> getFavoriteFilters() {
        return favoriteFilters;
    }

    public List<Favorite> getFilteredFavorites(FavoriteFilters favoriteFilters) {
        return favoriteRepository.getFilteredFavorites(favoriteFilters);
    }

    public List<String> getAllTypeContrat() {
        return favoriteRepository.getAllTypeContrat();
    }
    public List<String> getAllqualificationCode() {
        return favoriteRepository.getAllqualificationCode();
    }
    public List<String> getAllDepartement() {
        return favoriteRepository.getAllDepartement();
    }
}
