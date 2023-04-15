package fr.ceri.chomageen2mots;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Objects;

import fr.ceri.chomageen2mots.databinding.FragmentJobResultBinding;

public class JobResultFragment extends Fragment {

    private FragmentJobResultBinding binding;
    private String keyword;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentJobResultBinding.inflate(inflater, container, false);
        keyword = JobResultFragmentArgs.fromBundle(requireArguments()).getKeyword();

        Log.d("MANULEBOSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS", "Keyword in Result fragment = " + keyword);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(JobResultFragment.this)
                        .navigate(R.id.action_ResultFragment_to_SearchFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}