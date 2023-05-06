package fr.ceri.chomageen2mots;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class JobResultViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
    private Application application;
    private String keyword;

    public JobResultViewModelFactory(@NonNull Application application, String keyword) {
        super(application);
        this.application = application;
        this.keyword = keyword;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new JobResultViewModel(application, keyword);
    }
}
