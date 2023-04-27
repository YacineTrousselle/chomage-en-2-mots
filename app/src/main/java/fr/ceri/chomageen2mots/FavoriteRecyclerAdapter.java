package fr.ceri.chomageen2mots;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import fr.ceri.chomageen2mots.webservice.Offre;

public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder> {
    private List<Offre> favorites;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bitmap bitmap = getImgFromUrl(favorites.get(position).entreprise.logo);
        holder.img.setImageBitmap(bitmap);
        holder.title.setText(favorites.get(position).intitule);
    }

    @Override
    public int getItemCount() {
        if (null == favorites) {
            return 0;
        }
        return favorites.size();
    }

    public Bitmap getImgFromUrl(String imageUrl) {
        try
        {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
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
