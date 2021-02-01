package com.del.dnews.adapter;

import androidx.recyclerview.widget.RecyclerView;
import com.del.dnews.R;
import android.view.ViewGroup;
import android.view.View;
import androidx.cardview.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.content.Context;
import com.del.dnews.model.ModelNews;
import java.util.List;
import com.del.dnews.util.MainUtils;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;
//import com.bumptech.glide.Glide;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.ViewHolder> {

    private Context con;
    private List<ModelNews> list;

    public AdapterNews(Context con, List<ModelNews> list) {
        this.list = list;
        this.con = con;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_news, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelNews mn = list.get(position);
		
		//Glide.with(con).load(mn.getUrlToImage()).into(holder.imgUrl);
        holder.txtTitle.setText(mn.getTitle());
        holder.txtPublished.setText(mn.getPublishedAt());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardNew;
        ImageView imgUrl;
        TextView txtTitle, txtPublished;

        public ViewHolder(View itemView) {
            super(itemView);
            cardNew = itemView.findViewById(R.id.card_news);
            imgUrl = itemView.findViewById(R.id.img_url);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtPublished = itemView.findViewById(R.id.txt_author);

        }
    }


}
