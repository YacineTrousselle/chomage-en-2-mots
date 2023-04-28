package fr.ceri.chomageen2mots.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.ceri.chomageen2mots.webservice.Offre;

@Database(entities = {Favorite.class}, version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {
    public abstract FavoriteDao favoriteDao();

    private static volatile FavoriteDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static FavoriteDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FavoriteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), FavoriteDatabase.class, "offre_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                FavoriteDao dao = INSTANCE.favoriteDao();
                dao.deleteAll();
                Favorite[] favoris = {
                        new Favorite("jean", "jean", "jean", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/24/LEGO_logo.svg/2048px-LEGO_logo.svg.png", "jean"),
                        new Favorite("jean1", "41", "jean", "https://www.adobe.com/fr/express/create/media_127a4cd0c28c2753638768caf8967503d38d01e4c.jpeg?width=400&format=jpeg&optimize=medium", "jean"),
                        new Favorite("jeangrg", "jean", "jean", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/24/LEGO_logo.svg/2048px-LEGO_logo.svg.png", "jean"),
                        new Favorite("jezrgan1", "41", "jean", "https://www.adobe.com/fr/express/create/media_127a4cd0c28c2753638768caf8967503d38d01e4c.jpeg?width=400&format=jpeg&optimize=medium", "jean"),
                        new Favorite("ggzrgjean", "jean", "jean", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/24/LEGO_logo.svg/2048px-LEGO_logo.svg.png", "jean"),
                        new Favorite("jegrzgan1", "41", "jean", "https://www.adobe.com/fr/express/create/media_127a4cd0c28c2753638768caf8967503d38d01e4c.jpeg?width=400&format=jpeg&optimize=medium", "jean"),
                        new Favorite("jejuyjzrgrzan", "jean", "jean", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/24/LEGO_logo.svg/2048px-LEGO_logo.svg.png", "jean"),
                        new Favorite("jehyan1", "41", "jean", "https://www.adobe.com/fr/express/create/media_127a4cd0c28c2753638768caf8967503d38d01e4c.jpeg?width=400&format=jpeg&optimize=medium", "jean"),
                        new Favorite("jezrgzran", "jean", "jean", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/24/LEGO_logo.svg/2048px-LEGO_logo.svg.png", "jean"),
                        new Favorite("jetvan1", "41", "jean", "https://www.adobe.com/fr/express/create/media_127a4cd0c28c2753638768caf8967503d38d01e4c.jpeg?width=400&format=jpeg&optimize=medium", "jean"),
                        new Favorite("jeagzrgn", "jean", "jean", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/24/LEGO_logo.svg/2048px-LEGO_logo.svg.png", "jean"),
                        new Favorite("jeaeuyzn2", "moilr", "jean", "https://cdn.pixabay.com/photo/2017/03/16/21/18/logo-2150297_640.png", "jean"),
                };
                for (Favorite favorite : favoris) {
                    dao.insert(favorite);
                    Log.d("jean", "favori: " + favorite) ;
                }
            });
        }
    };
}
