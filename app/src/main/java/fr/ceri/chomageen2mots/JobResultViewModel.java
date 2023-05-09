package fr.ceri.chomageen2mots;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import fr.ceri.chomageen2mots.database.Favorite;
import fr.ceri.chomageen2mots.database.FavoriteRepository;
import fr.ceri.chomageen2mots.webservice.Offre;
import fr.ceri.chomageen2mots.webservice.PoleEmploiApi;
import fr.ceri.chomageen2mots.webservice.SearchResult;

public class JobResultViewModel extends AndroidViewModel {
    private final PoleEmploiApi poleEmploiApi;

    private final String keyword;
    private final MutableLiveData<SearchResult> searchResult;
    private MutableLiveData<SearchResult> lastSearchResult;
    private MediatorLiveData<SearchResult> resultMediatorLiveData = new MediatorLiveData<>();
    private int pagination = 0;
    private FavoriteRepository favoriteRepository;

    public JobResultViewModel(@NonNull Application application, String keyword) {
        super(application);
        poleEmploiApi = PoleEmploiApi.getInstance(getApplication());
        this.keyword = keyword;
        lastSearchResult = poleEmploiApi.search(getApplication().getApplicationContext(), keyword, pagination);
        searchResult = lastSearchResult;
        resultMediatorLiveData.addSource(searchResult, searchResultValue -> {
            if (!searchResultValue.getOffres().isEmpty()) {
                resultMediatorLiveData.postValue(searchResultValue);
            }
        });
        favoriteRepository = new FavoriteRepository(application);
    }

    public void nextPage() {
        if (pagination > 0) {
            loadData(false);
        }
    }

    public void previousPage() {
        if (resultMediatorLiveData.getValue() != null && resultMediatorLiveData.getValue().getCode() == 206) {
            loadData(true);
        }
    }

    public MediatorLiveData<SearchResult> getResultMediatorLiveData() {
        return resultMediatorLiveData;
    }

    private void loadData(boolean isNextPage) {
        int paginationTemp = isNextPage ? pagination + 1 : pagination - 1;
        lastSearchResult = poleEmploiApi.search(getApplication().getApplicationContext(), keyword, paginationTemp);
        lastSearchResult.observeForever(searchResultValue -> {
            if (searchResultValue.getCode() != 204 && searchResultValue.getCode() != 0) {
                searchResult.setValue(searchResultValue);
                pagination = paginationTemp;
            }
        });
    }

    public void addNewFav(Offre offre) {
        Favorite favorite = new Favorite(offre);
        favoriteRepository.insertFavorite(favorite);
    }

    public void deleteFav(Favorite favorite) {
        favoriteRepository.deleteFavorite(favorite);
    }

    public Favorite getFavoriteById(String id) {
        return favoriteRepository.getFavorite(id);
    }
}
