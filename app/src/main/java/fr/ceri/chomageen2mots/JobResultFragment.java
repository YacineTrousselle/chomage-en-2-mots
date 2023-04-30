package fr.ceri.chomageen2mots;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import fr.ceri.chomageen2mots.databinding.FragmentJobResultBinding;
import fr.ceri.chomageen2mots.webservice.PoleEmploiApi;
import fr.ceri.chomageen2mots.webservice.SearchResult;

public class JobResultFragment extends Fragment {
    private final PoleEmploiApi poleEmploiApi = PoleEmploiApi.getInstance();
    private FragmentJobResultBinding binding;
    private String keyword;
    private RecyclerAdapter adapter;
    private int pagination = 0;
    private MutableLiveData<SearchResult> searchResult = new MutableLiveData<>();
    private ProgressBar progress;

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

        RecyclerView recyclerView = view.findViewById(R.id.job_list);
        progress = view.findViewById(R.id.progress);
        adapter = new RecyclerAdapter();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        loadData();
        binding.buttonPrevious.setOnClickListener(v -> {
            if (pagination > 0) {
                pagination--;
                loadData();
            }
        });
        binding.buttonNext.setOnClickListener(v -> {
            if (searchResult.getValue().getCode() == 206) {
                pagination++;
                loadData();
            }
        });
    }

    private void loadData() {
        searchResult = poleEmploiApi.search(getContext(), keyword, pagination);

        searchResult.observe(getViewLifecycleOwner(),
                searchResultValue -> {
                    if (searchResultValue.isLoading()) {
                        progress.setVisibility(View.VISIBLE);
                    } else {
                        progress.setVisibility(View.GONE);
                        adapter.setSearchResult(searchResultValue);
                        adapter.notifyDataSetChanged();
                    }
                }
        );
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
