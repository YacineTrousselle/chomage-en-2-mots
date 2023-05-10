package fr.ceri.chomageen2mots;

import android.app.Application;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.ceri.chomageen2mots.database.Favorite;
import fr.ceri.chomageen2mots.database.FavoriteRepository;
import fr.ceri.chomageen2mots.webservice.Offre;
import fr.ceri.chomageen2mots.webservice.SearchResult;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private SearchResult searchResult;
    private ViewGroup parent;
    private Button button_retry;

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
        Log.d("MANU", "searchResult ====> " + searchResult);
        if(searchResult != null){
            button_retry.setVisibility(View.INVISIBLE);
        }
        String infoStr = "";
        if (searchResult.getOffres().get(position).entreprise.nom != null) {
            infoStr = searchResult.getOffres().get(position).entreprise.nom;
        }
        if (searchResult.getOffres().get(position).entreprise.nom != null) {
            infoStr += "\n" + searchResult.getOffres().get(position).typeContrat + " ";
        }
        if (searchResult.getOffres().get(position).dureeTravailLibelle != null) {
            infoStr += searchResult.getOffres().get(position).dureeTravailLibelle;
        }
        holder.offre = searchResult.getOffres().get(position);
        holder.jobInfo = infoStr;
        holder.itemTitle.setText(searchResult.getOffres().get(position).intitule);
        holder.itemDetail.setText(infoStr);
        String imgUrl = searchResult.getOffres().get(position).entreprise.logo;
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

    public void setSearchResult(SearchResult searchResult) {
        Log.d("MANU", "searchResult ====> " + searchResult);
        this.searchResult = searchResult;
    }

    public void setBtnRetry(Button btn){
        Log.d("MANU", "searchResult ====> " + searchResult);
        button_retry = btn;

    }
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        Offre offre;
        String jobInfo;
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
            itemView.setOnClickListener(v -> {
                JobResultFragmentDirections.ActionResultFragmentToDetailsFragment action = JobResultFragmentDirections.actionResultFragmentToDetailsFragment();
                //  action.setImgUrl();
                action.setTitre(offre.intitule);
                action.setDescription(offre.description);
                if (offre.entreprise.logo != null) {
                    action.setImgUrl(offre.entreprise.logo);
                } else {
                    action.setImgUrl("");
                }

                action.setJobInfo(jobInfo);
                Navigation.findNavController(v).navigate(action);
            });
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            FavoriteRepository favoriteRepository = new FavoriteRepository(new Application());
            Favorite favorite = favoriteRepository.getFavorite(offre.id);
            if (favorite == null) {
                contextMenu.add(0, 0, 0, "Ajouter aux favoris");
                contextMenu.findItem(0).setOnMenuItemClickListener(item -> {
                    favoriteRepository.insertFavorite(new Favorite(offre));
                    return true;
                });
            } else {
                contextMenu.add(0, 0, 0, "Supprimer des favoris");
                contextMenu.findItem(0).setOnMenuItemClickListener(item -> {
                    favoriteRepository.deleteFavorite(favorite);
                    return true;
                });
            }
        }
    }
}
