package fr.ceri.chomageen2mots;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import fr.ceri.chomageen2mots.database.Favorite;
import fr.ceri.chomageen2mots.database.FavoriteRepository;
import fr.ceri.chomageen2mots.webservice.Offre;
import fr.ceri.chomageen2mots.webservice.PoleEmploiApi;

public class DetailFragment extends Fragment {
    private final MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();
    String id;
    MutableLiveData<Offre> offreMutableLiveData;
    private TextView jobInfo, jobDescription, jobTitle;
    private ImageView imageView;
    private FavoriteRepository favoriteRepository;
    private MenuItem favToogleMenuItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString("id");
        }
        favoriteRepository = new FavoriteRepository(requireActivity().getApplication());
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        favToogleMenuItem = menu.findItem(R.id.fav_toogle);
        favToogleMenuItem.setVisible(true);
        isFavorite.setValue(favoriteRepository.getFavorite(id) != null);
        favToogleMenuItem.setVisible(true);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_fragment, container, false);
        PoleEmploiApi poleEmploiApi = PoleEmploiApi.getInstance(requireActivity().getApplication());
        jobInfo = rootView.findViewById(R.id.jobInfo);
        jobDescription = rootView.findViewById(R.id.jobDescription);
        jobTitle = rootView.findViewById(R.id.job_title);
        imageView = rootView.findViewById(R.id.imageView);

        if (id == null) {
            jobTitle.setText("Une erreur est survenue");
            return rootView;
        }
        offreMutableLiveData = poleEmploiApi.getOffre(id);
        offreMutableLiveData.observe(getViewLifecycleOwner(),
                offre -> {
                    if (offre != null) {
                        jobInfo.setText(offre.getInfo());
                        jobDescription.setText(offre.description);
                        jobTitle.setText(offre.intitule);

                        if (offre.entreprise.logo != null && !Objects.equals(offre.entreprise.logo, "")) {
                            Picasso.get().load(offre.entreprise.logo).into(imageView);
                        } else {
                            Picasso.get().load(R.drawable.c).into(imageView);
                        }
                        isFavorite.observe(getViewLifecycleOwner(),
                                aBoolean -> {
                                    if (aBoolean) {
                                        favToogleMenuItem.setTitle("Supprimer");
                                        favToogleMenuItem.setOnMenuItemClickListener(item -> {
                                            favoriteRepository.deleteFavorite(favoriteRepository.getFavorite(id));
                                            isFavorite.setValue(false);
                                            return true;
                                        });
                                    } else {
                                        favToogleMenuItem.setTitle("Ajouter");
                                        favToogleMenuItem.setOnMenuItemClickListener(item -> {
                                            favoriteRepository.insertFavorite(new Favorite(offre));
                                            isFavorite.setValue(true);
                                            return true;
                                        });
                                    }
                                }
                        );
                    }
                }
        );

        return rootView;
    }
}
