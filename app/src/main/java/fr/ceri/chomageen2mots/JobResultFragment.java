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
import androidx.navigation.fragment.NavHostFragment;

import fr.ceri.chomageen2mots.databinding.FragmentJobResultBinding;
import fr.ceri.chomageen2mots.webservice.PoleEmploiApi;

public class JobResultFragment extends Fragment {

    private FragmentJobResultBinding binding;
    private String keyword;
    private final LiveData<Integer> pagination = new MutableLiveData<>(0);

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentJobResultBinding.inflate(inflater, container, false);
        keyword = JobResultFragmentArgs.fromBundle(requireArguments()).getKeyword();

        Log.d("jean", "Keyword in Result fragment = " + keyword);
        PoleEmploiApi poleEmploiApi = new PoleEmploiApi();
        poleEmploiApi.search(getContext(), keyword, pagination.getValue());

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
}
