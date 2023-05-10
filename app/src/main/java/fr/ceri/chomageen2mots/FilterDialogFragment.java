package fr.ceri.chomageen2mots;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Arrays;
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
        List<String> allqualificationCode = favoriteViewModel.getAllqualification();
        List<String> allDepartement = favoriteViewModel.getAllDepartement();
        Log.d("jean", "onCreateDialog: " + Arrays.toString(allDepartement.toArray()));
        allTypeContrat.add(0, "Tout");
        allqualificationCode.add(0, "Tout");
        allDepartement.add(0, "Tout");

        typeContratSpinner.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item, allTypeContrat));
        qualificationCodeSpinner.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item, allqualificationCode));
        departementSpinner.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item, allDepartement));

        String defaultTypeContrat = getValueToDisplay(favoriteViewModel.getFavoriteFilters().getValue().typeContrat);
        String defaultQualificationCode = getValueToDisplay(favoriteViewModel.getFavoriteFilters().getValue().qualification);
        String defaultDepartement = getValueToDisplay(favoriteViewModel.getFavoriteFilters().getValue().departement);

        typeContratSpinner.setSelection(allTypeContrat.indexOf(defaultTypeContrat));
        qualificationCodeSpinner.setSelection(allqualificationCode.indexOf(defaultQualificationCode));
        departementSpinner.setSelection(allDepartement.indexOf(defaultDepartement));

        builder.setView(dialogView)
                .setTitle("Filtrer les favoris")
                .setPositiveButton("Filtrer", (dialogInterface, i) -> {
                    String typeContrat = typeContratSpinner.getSelectedItem().toString();
                    String qualificationCode = qualificationCodeSpinner.getSelectedItem().toString();
                    String departement = departementSpinner.getSelectedItem().toString();

                    FavoriteFilters favoriteFilters = new FavoriteFilters();
                    favoriteFilters.typeContrat = getValueToDisplay(typeContrat);
                    favoriteFilters.qualification = getValueToDisplay(qualificationCode);
                    favoriteFilters.departement = getValueToDisplay(departement);
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

    public String getValueToDisplay(String filter) {
        if (filter.equals("Tout")) {
            return "";
        }
        return filter;
    }
}
