package fr.ceri.chomageen2mots;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DetailViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
    private final Application application;
    private final String id;

    public DetailViewModelFactory(@NonNull Application application, String id) {
        super(application);
        this.application = application;
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailViewModel(application, id);
    }
}
