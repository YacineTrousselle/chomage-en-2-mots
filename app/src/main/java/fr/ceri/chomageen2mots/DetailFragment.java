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
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailFragment extends Fragment {
    String id;
    private TextView jobInfo, jobDescription, jobTitle;
    private ImageView imageView;
    private MenuItem favToogleMenuItem;
    private DetailViewModel detailViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString("id");
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        favToogleMenuItem = menu.findItem(R.id.fav_toogle);
        favToogleMenuItem.setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_fragment, container, false);
        detailViewModel = new ViewModelProvider(this,
                new DetailViewModelFactory(requireActivity().getApplication(), id)).get(DetailViewModel.class);

        jobInfo = rootView.findViewById(R.id.jobInfo);
        jobDescription = rootView.findViewById(R.id.jobDescription);
        jobTitle = rootView.findViewById(R.id.job_title);
        imageView = rootView.findViewById(R.id.imageView);

        if (id == null) {
            jobTitle.setText("Une erreur est survenue");
            return rootView;
        }
        detailViewModel.getOffreMutableLiveData().observe(getViewLifecycleOwner(),
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
                        detailViewModel.getIsFavorite().observe(getViewLifecycleOwner(),
                                isFav -> {
                                    if (isFav) {
                                        favToogleMenuItem.setTitle("Supprimer");
                                        favToogleMenuItem.setOnMenuItemClickListener(item -> {
                                            detailViewModel.deleteFavorite();
                                            return true;
                                        });
                                    } else {
                                        favToogleMenuItem.setTitle("Ajouter");
                                        favToogleMenuItem.setOnMenuItemClickListener(item -> {
                                            detailViewModel.addFavorite(offre);
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
