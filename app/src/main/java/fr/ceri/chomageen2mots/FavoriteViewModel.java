package fr.ceri.chomageen2mots;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.ceri.chomageen2mots.database.Favorite;
import fr.ceri.chomageen2mots.database.FavoriteRepository;
import fr.ceri.chomageen2mots.webservice.Offre;

public class FavoriteViewModel extends AndroidViewModel {
    private final FavoriteRepository favoriteRepository;

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
}
