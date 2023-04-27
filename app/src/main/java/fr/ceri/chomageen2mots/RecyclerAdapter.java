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

import fr.ceri.chomageen2mots.webservice.PoleEmploiApi;
import fr.ceri.chomageen2mots.webservice.SearchResult;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
{
    private int currPos = 0;
    private PoleEmploiApi api = new PoleEmploiApi();
    public static LiveData<SearchResult> resultLiveData;
    ListViewModel viewModel;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        currPos++;
        if(currPos % 2 != 0) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_list_r_image, parent, false);
            return new ViewHolder(v);
        }
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_l_image, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Log.d("MANULEBOSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS", "ViewHolder : " + position);
        holder.itemTitle.setText(resultLiveData.getValue().resultats.get(position).intitule);
        holder.itemDetail.setText(resultLiveData.getValue().resultats.get(position).description);
        try
        {
            URL url = new URL(resultLiveData.getValue().resultats.get(position).EntrepriseObject.logo);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            holder.itemImage.setImageBitmap(BitmapFactory.decodeStream(input));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setResultLiveData(LiveData<SearchResult> inResultLiveData){
        resultLiveData =  inResultLiveData;
    }

    public void setListViewModel(ListViewModel viewModel){
        viewModel = viewModel;
    }
    @Override
    public int getItemCount() {
        return resultLiveData.getValue().resultats.size();
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
                @Override public void onClick(View v) {

                    int position = getAdapterPosition();
                    Log.d("MANULEBOSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS", "Details for ViewHolder : " + position);
                }
            });

        }
    }

}
