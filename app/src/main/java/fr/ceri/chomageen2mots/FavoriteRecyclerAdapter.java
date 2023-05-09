package fr.ceri.chomageen2mots;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.util.Log;
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

import fr.ceri.chomageen2mots.FavoriteFragmentDirections;
import fr.ceri.chomageen2mots.database.Favorite;
import fr.ceri.chomageen2mots.database.FavoriteRepository;
import fr.ceri.chomageen2mots.webservice.Offre;

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


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        String id;
        String url;
        ImageView img;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.compagny_img);
            title = itemView.findViewById(R.id.title);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(v -> {

                FavoriteFragmentDirections.ActionFavoriteToDetails action = FavoriteFragmentDirections.actionFavoriteToDetails();

                FavoriteRepository favoriteRepository = new FavoriteRepository(new Application());
                Favorite favorite = favoriteRepository.getFavorite(id);
                String infoStr = "";
                if (favorite.nomEntreprise != null) {
                    infoStr = favorite.nomEntreprise;
                }
                if (favorite.typeContrat != null) {
                    infoStr += "\n" + favorite.typeContrat + " ";
                }
                if (favorite.dureeTravailLibelle != null) {
                    infoStr += favorite.dureeTravailLibelle;
                }

                if (favorite.logoEntreprise != null) {
                    action.setImgUrl(favorite.logoEntreprise);
                } else {
                    action.setImgUrl("");
                }

                action.setTitre(favorite.intitule);
                action.setDescription(favorite.description);
                action.setJobInfo(infoStr);
                Navigation.findNavController(v).navigate(action);
            });
        }


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            FavoriteRepository favoriteRepository = new FavoriteRepository(new Application());
            Favorite favorite = favoriteRepository.getFavorite(id);
            contextMenu.add(0, 0, 0, "Supprimer des favoris");
            contextMenu.findItem(0).setOnMenuItemClickListener(item -> {
                favoriteRepository.deleteFavorite(favorite);
                return true;
            });
            contextMenu.add(1, 1, 1, "Copier le lien").setOnMenuItemClickListener(item -> {
                Log.d("MANULEBOSSSSSSSSSSSSSSSSSSSSSSS", "The url is : " + favorite.url);
                // Obtenez le gestionnaire de presse-papiers
                ClipboardManager clipboardManager = (ClipboardManager) view.getContext().getSystemService(view.getContext().CLIPBOARD_SERVICE);


                // Copier la chaîne dans le presse-papiers
                ClipData clip = ClipData.newPlainText("texte", favorite.url);
                clipboardManager.setPrimaryClip(clip);

                return true;
            });


        }
    }
}
