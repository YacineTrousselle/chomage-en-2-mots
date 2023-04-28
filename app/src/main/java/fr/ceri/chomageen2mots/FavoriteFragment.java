package fr.ceri.chomageen2mots;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

public class FavoriteFragment extends Fragment {
    FavoriteViewModel favoriteViewModel;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FavoriteRecyclerAdapter favoriteRecyclerAdapter;

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
