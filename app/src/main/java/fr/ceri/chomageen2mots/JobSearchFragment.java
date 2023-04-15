package fr.ceri.chomageen2mots;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Objects;

import fr.ceri.chomageen2mots.JobSearchFragmentDirections;
import fr.ceri.chomageen2mots.databinding.FragmentSearchBinding;

public class JobSearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    private SearchView searchView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        searchView = binding.sheachBar;
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && !query.isEmpty()) {
                    Log.d("MANULEBOSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS", "QUERY ==> " + query);
                    JobSearchFragmentDirections.ActionSearchFragmentToResultFragment action = JobSearchFragmentDirections.actionSearchFragmentToResultFragment();
                    action.setKeyword(query);
                    NavHostFragment.findNavController(JobSearchFragment.this)
                            .navigate(action);
                    return true;
                } else {
                    Log.e("MANULEBOSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS", "query is null or empty");
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
