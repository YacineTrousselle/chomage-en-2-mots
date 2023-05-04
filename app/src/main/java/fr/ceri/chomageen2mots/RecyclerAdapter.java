package fr.ceri.chomageen2mots;

import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import fr.ceri.chomageen2mots.webservice.SearchResult;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private SearchResult searchResult;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(viewType == 0 ? R.layout.offre_right_img : R.layout.offre_left_img, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        String infoStr = searchResult.getOffres().get(position).entreprise.nom + "\n" + searchResult.getOffres().get(position).typeContrat + " " + searchResult.getOffres().get(position).dureeTravailLibelle;
        holder.jobInfo = infoStr;
        holder.jobDescription = searchResult.getOffres().get(position).description;
        holder.jobTitle = searchResult.getOffres().get(position).intitule;
        holder.id = searchResult.getOffres().get(position).id;
        holder.itemTitle.setText(searchResult.getOffres().get(position).intitule);
        holder.itemDetail.setText(infoStr);
        String imgUrl = searchResult.getOffres().get(position).entreprise.logo;
        holder.imgUrl = imgUrl;
        if (imgUrl != null) {
            Picasso.get().load(imgUrl).into(holder.itemImage);
        } else {
            Picasso.get().load(R.drawable.c).into(holder.itemImage);
        }
    }

    @Override
    public int getItemCount() {
        if (searchResult == null) {
            return 0;
        }
        return searchResult.getOffres().size();
    }

    public SearchResult getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(SearchResult searchResult) {
        this.searchResult = searchResult;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        String id, jobInfo, jobDescription, jobTitle, imgUrl;
        ImageView itemImage;
        TextView itemTitle;
        TextView itemDetail;

        ViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.compagny_img);
            itemTitle = itemView.findViewById(R.id.job_title);
            itemDetail = itemView.findViewById(R.id.job_info);

            itemView.setOnClickListener(v -> {
                Log.d("MANULEBOSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS", "Details for ViewHolder : " + getAdapterPosition());
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                    int position = getAdapterPosition();
                    JobResultFragmentDirections.ActionResultFragmentToDetailsFragment action = JobResultFragmentDirections.actionResultFragmentToDetailsFragment();
                  //  action.setImgUrl();
                    action.setTitre(jobTitle);
                    action.setDescription(jobDescription);
                    if (imgUrl != null) {
                        action.setImgUrl(imgUrl);
                    } else {
                        action.setImgUrl("");
                    }

                    action.setJobInfo(jobInfo);
                    Navigation.findNavController(v).navigate(action);
                }
            });

        }
    }

}
