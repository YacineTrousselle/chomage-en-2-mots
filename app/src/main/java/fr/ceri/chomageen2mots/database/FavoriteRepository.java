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
        return favoriteDao.getAll();
    }

    public List<Favorite> getFilteredFavorites(FavoriteFilters filter) {
        Future<List<Favorite>> filteredFavoritesFuture = databaseWriteExecutor.submit(() -> favoriteDao.getFilteredFavorites(filter.typeContrat, filter.qualificationCode, filter.departement));
        try {
            return filteredFavoritesFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Favorite getFavorite(String id) {
        Future<Favorite> favoriteFuture = databaseWriteExecutor.submit(() -> favoriteDao.getById(id));
        try {
            return favoriteFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getAllTypeContrat() {
        Future<List<String>> allTypeContratFuture = databaseWriteExecutor.submit(favoriteDao::getAllTypeContrat);
        try {
            return allTypeContratFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getAllqualificationCode() {
        Future<List<String>> allqualificationCodeFuture = databaseWriteExecutor.submit(favoriteDao::getAllqualificationCode);
        try {
            return allqualificationCodeFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getAllDepartement() {
        Future<List<String>> allDepartementFuture = databaseWriteExecutor.submit(favoriteDao::getAllDepartement);
        try {
            return allDepartementFuture.get();
        } catch (ExecutionException | InterruptedException e) {
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
