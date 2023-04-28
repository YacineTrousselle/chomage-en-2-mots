package fr.ceri.chomageen2mots.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;


public class FavoriteRepository {
    private LiveData<List<Favorite>> allFavorites;
    private FavoriteDao favoriteDao;

    public FavoriteRepository(Application application) {
        FavoriteDatabase favoriteDatabase = FavoriteDatabase.getDatabase(application);
        favoriteDao = favoriteDatabase.favoriteDao();
        allFavorites = favoriteDao.getAll();
    }

    public LiveData<List<Favorite>> getAllFavorites() {
        return allFavorites;
    }
}
