package fr.ceri.chomageen2mots;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment {
     private TextView jobInfo, jobDescription, jobTitle;
     private ImageView imageView;
     String description, titre, jobInfoText, imgUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_fragment, container, false);
        jobInfo = rootView.findViewById(R.id.jobInfo);
        jobDescription = rootView.findViewById(R.id.jobDescription);
        jobTitle = rootView.findViewById(R.id.job_title);
        imageView = rootView.findViewById(R.id.imageView);
        // Mise à jour des valeurs des TextView et de l'ImageView
        jobInfo.setText(jobInfoText);
        jobDescription.setText(description);
        jobTitle.setText(titre);
        // Utilisez une bibliothèque tierce pour charger l'image depuis l'URL
        if (imgUrl != "") {
            Picasso.get().load(imgUrl).into(imageView);
        } else {
            Picasso.get().load(R.drawable.c).into(imageView);
        }

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Récupération des arguments passés à ce fragment
        if (getArguments() != null) {
            description = getArguments().getString("description");
            titre = getArguments().getString("titre");
            jobInfoText = getArguments().getString("jobInfo");
            imgUrl = getArguments().getString("imgUrl");
        }
    }
}
