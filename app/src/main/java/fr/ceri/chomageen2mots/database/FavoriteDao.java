package fr.ceri.chomageen2mots.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    LiveData<List<Favorite>> getAll();

    @Query("SELECT * FROM favorites WHERE id = :id")
    Favorite getById(String id);

    @Insert
    void insert(Favorite favorite);

    @Update
    void update(Favorite favorite);

    @Query("DELETE FROM favorites")
    void deleteAll();

    @Delete
    void deleteFavorite(Favorite favorite);
}
