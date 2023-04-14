package fr.ceri.chomageen2mots;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import fr.ceri.chomageen2mots.JobSearchFragmentDirections;
import fr.ceri.chomageen2mots.databinding.FragmentSearchBinding;

public class JobSearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //JobSearchFragmentDirections.
                //JobResultFragmentDirections.actionResultFragmentToSearchFragment();
                JobSearchFragmentDirections.ActionSearchFragmentToResultFragment action = JobSearchFragmentDirections.actionSearchFragmentToResultFragment();
                action.setKeyword(binding.textInputLayout.toString());
                NavHostFragment.findNavController(JobSearchFragment.this)
                        .navigate(R.id.action_SearchFragment_to_ResultFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}