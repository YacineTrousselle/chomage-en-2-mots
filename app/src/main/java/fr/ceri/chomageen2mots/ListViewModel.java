package fr.ceri.chomageen2mots;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.ceri.chomageen2mots.webservice.SearchResult;

public class ListViewModel extends AndroidViewModel {
    LiveData<List<SearchResult>> searchResultLiveData;

    public ListViewModel(@NonNull Application application, String keyword) {
        super(application);
    }

    LiveData<List<SearchResult>> getAllResult() {
        return searchResultLiveData;
    }
}
