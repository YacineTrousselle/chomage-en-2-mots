package fr.ceri.chomageen2mots.database;

import static fr.ceri.chomageen2mots.database.FavoriteDatabase.databaseWriteExecutor;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class FavoriteRepository {
    private final LiveData<List<Favorite>> allFavorites;
    private final FavoriteDao favoriteDao;

    public FavoriteRepository(Application application) {
        FavoriteDatabase favoriteDatabase = FavoriteDatabase.getDatabase(application);
        favoriteDao = favoriteDatabase.favoriteDao();
        allFavorites = favoriteDao.getAll();
    }

    public LiveData<List<Favorite>> getAllFavorites() {
        return allFavorites;
    }

    public Favorite getFavorite(String id) {
        Future<Favorite> favoriteFuture = databaseWriteExecutor.submit(() -> favoriteDao.getById(id));
        try {
            return favoriteFuture.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertFavorite(Favorite favorite) {
        databaseWriteExecutor.execute(() -> favoriteDao.insert(favorite));
    }

    public void deleteFavorite(Favorite favorite) {
        databaseWriteExecutor.execute(() -> favoriteDao.deleteFavorite(favorite));
    }
}
