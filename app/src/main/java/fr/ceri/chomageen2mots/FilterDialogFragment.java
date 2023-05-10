package fr.ceri.chomageen2mots;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.List;

import fr.ceri.chomageen2mots.database.FavoriteFilters;

public class FilterDialogFragment extends DialogFragment {
    private Spinner typeContratSpinner, qualificationCodeSpinner, departementSpinner;
    private final FavoriteViewModel favoriteViewModel;

    public FilterDialogFragment(FavoriteViewModel favoriteViewModel) {
        this.favoriteViewModel = favoriteViewModel;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_filters, null);

        typeContratSpinner = dialogView.findViewById(R.id.spinner1);
        qualificationCodeSpinner = dialogView.findViewById(R.id.spinner2);
        departementSpinner = dialogView.findViewById(R.id.spinner3);

        List<String> allTypeContrat = favoriteViewModel.getAllTypeContrat();
        List<String> allqualificationCode = favoriteViewModel.getAllqualificationCode();
        List<String> allDepartement = favoriteViewModel.getAllDepartement();
        allTypeContrat.add(0, "");
        allqualificationCode.add(0, "");
        allDepartement.add(0, "");

        typeContratSpinner.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item, allTypeContrat));
        qualificationCodeSpinner.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item, allqualificationCode));
        departementSpinner.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item, allDepartement));

        String defaultTypeContrat = favoriteViewModel.getFavoriteFilters().getValue().typeContrat;
        String defaultQualificationCode = favoriteViewModel.getFavoriteFilters().getValue().qualificationCode;
        String defaultDepartement = favoriteViewModel.getFavoriteFilters().getValue().departement;

        typeContratSpinner.setSelection(allTypeContrat.indexOf(defaultTypeContrat));
        qualificationCodeSpinner.setSelection(allqualificationCode.indexOf(defaultQualificationCode));
        departementSpinner.setSelection(allDepartement.indexOf(defaultDepartement));

        builder.setView(dialogView)
                .setTitle("Sélectionnez les options")
                .setPositiveButton("Submit", (dialogInterface, i) -> {
                    String typeContrat = typeContratSpinner.getSelectedItem().toString();
                    String qualificationCode = qualificationCodeSpinner.getSelectedItem().toString();
                    String departement = departementSpinner.getSelectedItem().toString();
                    FavoriteFilters favoriteFilters = new FavoriteFilters();
                    favoriteFilters.typeContrat = typeContrat;
                    favoriteFilters.qualificationCode = qualificationCode;
                    favoriteFilters.departement = departement;
                    favoriteViewModel.setFavoriteFilters(favoriteFilters);
                })
                .setNegativeButton("Réinitialiser", (dialogInterface, i) -> {
                    typeContratSpinner.setSelection(0);
                    qualificationCodeSpinner.setSelection(0);
                    departementSpinner.setSelection(0);
                    favoriteViewModel.setFavoriteFilters(new FavoriteFilters());
                });

        return builder.create();
    }
}
