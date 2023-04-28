package fr.ceri.chomageen2mots;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.ceri.chomageen2mots.database.Favorite;
import fr.ceri.chomageen2mots.database.FavoriteRepository;

public class FavoriteViewModel extends AndroidViewModel {
    private final FavoriteRepository favoriteRepository;
    private LiveData<List<Favorite>> favorites;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        favoriteRepository = new FavoriteRepository(application);
        favorites = favoriteRepository.getAllFavorites();
    }

    public LiveData<List<Favorite>> getAllFavorites() {
        return favoriteRepository.getAllFavorites();
    }
}
