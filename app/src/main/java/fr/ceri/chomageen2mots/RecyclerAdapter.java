package fr.ceri.chomageen2mots;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        String infoStr = searchResult.getOffres().get(position).nomEntreprise + ", " + searchResult.getOffres().get(position).commune +"\n" + searchResult.getOffres().get(position).typeContrat + " " + searchResult.getOffres().get(position).dureeTravailLibelle;
        holder.id = searchResult.getOffres().get(position).id;
        holder.itemTitle.setText(searchResult.getOffres().get(position).intitule);
        holder.itemDetail.setText(infoStr);
        String imgUrl = searchResult.getOffres().get(position).logoEntreprise;
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        String id;
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

        }
    }

}
