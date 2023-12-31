package fr.ceri.chomageen2mots;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.ceri.chomageen2mots.database.Favorite;
import fr.ceri.chomageen2mots.database.FavoriteRepository;

public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder> {
    private List<Favorite> favorites;
    private FavoriteViewModel favoriteViewModel;

    public FavoriteRecyclerAdapter(FavoriteViewModel favoriteViewModel) {
        this.favoriteViewModel = favoriteViewModel;
    }

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
        holder.info.setText(favorites.get(position).getInfo());
        holder.id = favorites.get(position).id;
        holder.url = favorites.get(position).url;
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        String id;
        String url;
        ImageView img;
        TextView info;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.compagny_img);
            info = itemView.findViewById(R.id.info);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(v -> {
                FavoriteFragmentDirections.ActionFavoriteToDetails action = FavoriteFragmentDirections.actionFavoriteToDetails();
                action.setId(id);

                Navigation.findNavController(v).navigate(action);
            });
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            FavoriteRepository favoriteRepository = new FavoriteRepository(new Application());
            Favorite favorite = favoriteRepository.getFavorite(id);
            contextMenu.add(0, 0, 0, "Supprimer des favoris");
            contextMenu.findItem(0).setOnMenuItemClickListener(item -> {
                favoriteViewModel.deleteFav(favorite);
                setFavorites(favoriteViewModel.getFilteredFavorites(favoriteViewModel.getFavoriteFilters().getValue()));
                notifyDataSetChanged();
                return true;
            });
            contextMenu.add(1, 1, 1, "Copier le lien").setOnMenuItemClickListener(item -> {
                // Obtenez le gestionnaire de presse-papiers
                ClipboardManager clipboardManager = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);

                // Copier la chaîne dans le presse-papiers
                ClipData clip = ClipData.newPlainText("texte", favorite.url);
                clipboardManager.setPrimaryClip(clip);

                return true;
            });
        }
    }
}
