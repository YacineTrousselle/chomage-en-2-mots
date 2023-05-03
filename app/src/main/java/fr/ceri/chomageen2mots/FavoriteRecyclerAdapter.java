package fr.ceri.chomageen2mots;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.ceri.chomageen2mots.database.Favorite;

public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder> {
    private List<Favorite> favorites;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (favorites.get(position).logoEntreprise != null) {
            Picasso.get().load(favorites.get(position).logoEntreprise).into(holder.img);
        } else {
            Picasso.get().load(R.drawable.c).into(holder.img);
        }
        holder.title.setText(favorites.get(position).intitule);
    }

    @Override
    public int getItemCount() {
        if (null == favorites) {
            return 0;
        }
        return favorites.size();
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.compagny_img);
            title = itemView.findViewById(R.id.title);
        }
    }
}
