package fr.ceri.chomageen2mots;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FavoriteRecyclerAdapter favoriteRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.favoriteList);
        layoutManager = new GridLayoutManager(requireActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        favoriteRecyclerAdapter = new FavoriteRecyclerAdapter();
        recyclerView.setAdapter(favoriteRecyclerAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
