package fr.ceri.chomageen2mots;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteFragment extends Fragment {
    FavoriteViewModel favoriteViewModel;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FavoriteRecyclerAdapter favoriteRecyclerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem filterMenuItem = menu.findItem(R.id.filtrer);
        filterMenuItem.setVisible(true);
        filterMenuItem.setOnMenuItemClickListener(item -> {
            FilterDialogFragment filterDialogFragment = new FilterDialogFragment(favoriteViewModel);
            filterDialogFragment.show(getParentFragmentManager(), "FilterDialogFragment");
            return true;
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);

        recyclerView = view.findViewById(R.id.favoriteList);
        layoutManager = new GridLayoutManager(requireActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        favoriteRecyclerAdapter = new FavoriteRecyclerAdapter();
        recyclerView.setAdapter(favoriteRecyclerAdapter);

        favoriteViewModel.getAllFavorites().observe(getViewLifecycleOwner(),
                favorites -> {
                    favoriteRecyclerAdapter.setFavorites(favorites);
                    favoriteRecyclerAdapter.notifyDataSetChanged();
                }
        );
        favoriteViewModel.getFavoriteFilters().observe(getViewLifecycleOwner(),
                favoriteFilters -> {
                    favoriteRecyclerAdapter.setFavorites(favoriteViewModel.getFilteredFavorites(favoriteFilters));
                    favoriteRecyclerAdapter.notifyDataSetChanged();
                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
