package fr.ceri.chomageen2mots;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fr.ceri.chomageen2mots.databinding.FragmentJobResultBinding;
import fr.ceri.chomageen2mots.webservice.SearchResult;

public class JobResultFragment extends Fragment {
    private FragmentJobResultBinding binding;
    private String keyword;
    private RecyclerAdapter adapter;
    private JobResultViewModel jobResultViewModel;

    private SearchResult searchResult;

    private Button button_retry;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentJobResultBinding.inflate(inflater, container, false);
        keyword = JobResultFragmentArgs.fromBundle(requireArguments()).getKeyword();

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        jobResultViewModel = new ViewModelProvider(this,
                new JobResultViewModelFactory(requireActivity().getApplication(), keyword)).get(JobResultViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.job_list);
        adapter = new RecyclerAdapter();
        adapter.setBtnRetry(view.findViewById(R.id.button_retry));
        jobResultViewModel.getResultMediatorLiveData().observe(getViewLifecycleOwner(),
                searchResult -> {
                        adapter.setSearchResult(searchResult);
                        adapter.notifyDataSetChanged();

                }
        );
        button_retry = view.findViewById(R.id.button_retry);
        button_retry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("MANU", "Retry bro");
                LiveData<SearchResult> retryLiveData = jobResultViewModel.retryResultMediatorLiveData();
                retryLiveData.removeObservers(getViewLifecycleOwner()); // Remove any existing observer
                retryLiveData.observe(getViewLifecycleOwner(), searchResult -> {
                    adapter.setSearchResult(searchResult);
                    adapter.notifyDataSetChanged();
                });
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        binding.buttonPrevious.setOnClickListener(v -> jobResultViewModel.nextPage());
        binding.buttonNext.setOnClickListener(v -> jobResultViewModel.previousPage());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
