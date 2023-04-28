package fr.ceri.chomageen2mots;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import fr.ceri.chomageen2mots.webservice.SearchResult;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    public static LiveData<SearchResult> resultLiveData;
    private int currPos = 0;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        currPos++;
        if (currPos % 2 != 0) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_list_r_image, parent, false);
            return new ViewHolder(v);
        }
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_l_image, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MANULEBOSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS", "ViewHolder : " + position);
        holder.itemTitle.setText(resultLiveData.getValue().getOffres().get(position).intitule);
        holder.itemDetail.setText(resultLiveData.getValue().getOffres().get(position).description);
        if (resultLiveData.getValue().getOffres().get(position).logoEntreprise != null) {
            URL url = null;
            try {
                Log.d("LOGOOOOOOOOOOO", resultLiveData.getValue().getOffres().get(position).logoEntreprise);

                url = new URL(resultLiveData.getValue().getOffres().get(position).logoEntreprise);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                holder.itemImage.setImageBitmap(BitmapFactory.decodeStream(input));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            Log.d("LOGOOOOOOOOOOO", "No logo for " + position);
            holder.itemImage.setImageDrawable(holder.itemImage.getResources().getDrawable(R.drawable.c));
            //holder.itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.c));
        }
    }

    @Override
    public int getItemCount() {
        if (resultLiveData == null || resultLiveData.getValue() == null) {
            return 0;
        }
        return resultLiveData.getValue().getOffres().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView itemTitle;
        TextView itemDetail;

        ViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.compagny_img);
            itemTitle = itemView.findViewById(R.id.job_title);
            itemDetail = itemView.findViewById(R.id.job_info);

            int position = getAdapterPosition();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    Log.d("MANULEBOSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS", "Details for ViewHolder : " + position);
                }
            });

        }
    }

}
