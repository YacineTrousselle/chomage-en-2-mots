package fr.ceri.chomageen2mots.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OffreDao {
    @Query("SELECT * FROM favorites")
    List<Offre> getAll();

    @Insert
    void insert(Offre offre);

    @Update
    void update(Offre offre);
}
