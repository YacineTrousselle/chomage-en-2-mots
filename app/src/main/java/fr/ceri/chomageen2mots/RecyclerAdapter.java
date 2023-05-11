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

import fr.ceri.chomageen2mots.database.Favorite;
import fr.ceri.chomageen2mots.database.FavoriteRepository;
import fr.ceri.chomageen2mots.webservice.Offre;
import fr.ceri.chomageen2mots.webservice.SearchResult;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private SearchResult searchResult;
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
        if(searchResult != null){
            button_retry.setVisibility(View.INVISIBLE);
        }
        holder.offre = searchResult.getOffres().get(position);
        holder.itemTitle.setText(searchResult.getOffres().get(position).intitule);
        holder.itemDetail.setText(holder.offre.getInfo());
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
        this.searchResult = searchResult;
    }

    public void setBtnRetry(Button btn){
        button_retry = btn;

    }
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        Offre offre;
        ImageView itemImage;
        TextView itemTitle;
        TextView itemDetail;

        ViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.compagny_img);
            itemTitle = itemView.findViewById(R.id.job_title);
            itemDetail = itemView.findViewById(R.id.job_info);

            itemView.setOnClickListener(v -> {
                JobResultFragmentDirections.ActionResultFragmentToDetailsFragment action = JobResultFragmentDirections.actionResultFragmentToDetailsFragment();
                action.setId(offre.id);
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
