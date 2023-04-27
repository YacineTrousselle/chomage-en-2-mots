package fr.ceri.chomageen2mots;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import fr.ceri.chomageen2mots.databinding.FragmentJobResultBinding;
import fr.ceri.chomageen2mots.webservice.PoleEmploiApi;
import fr.ceri.chomageen2mots.webservice.SearchResult;

public class JobResultFragment extends Fragment {

    private FragmentJobResultBinding binding;
    private String keyword;
    private final LiveData<Integer> pagination = new MutableLiveData<>(0);
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentJobResultBinding.inflate(inflater, container, false);
        keyword = JobResultFragmentArgs.fromBundle(requireArguments()).getKeyword();

/*
        viewModel = new ViewModelProvider(this).get(ListViewModel.class);
        setListener();
*/

        Log.d("jean", "Keyword in Result fragment = " + keyword);



        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.job_list);
        adapter = new RecyclerAdapter();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        PoleEmploiApi poleEmploiApi = new PoleEmploiApi();
        recyclerView.setAdapter(adapter);


        adapter.resultLiveData = poleEmploiApi.search(getContext(), keyword, pagination.getValue());

        adapter.resultLiveData.observe(getViewLifecycleOwner(),
                update -> {
                    Log.d("MANULEBOSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS", "UPDATE LICEDATA" );
                    adapter.notifyItemRangeInserted(0, adapter.resultLiveData.getValue().resultats.size());
                }
            );




        binding.buttonSecond.setOnClickListener(
            view1 ->
                NavHostFragment.findNavController(JobResultFragment.this)
                    .navigate(R.id.action_ResultFragment_to_SearchFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

/*
    public void setListener(){
        recyclerView = getView().findViewById(R.id.job_list);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setListViewModel(viewModel);

    }

*/
}
