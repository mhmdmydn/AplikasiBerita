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
import com.bumptech.glide.Glide;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import android.content.Intent;
import com.del.dnews.activity.ViewContentActivity;
import android.graphics.Typeface;

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
        final ModelNews mn = list.get(position);
        
		CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(con);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();
		Glide.with(con).load(mn.getUrlToImage()).placeholder(circularProgressDrawable).into(holder.imgUrl);
        
        holder.txtTitle.setText(mn.getTitle());
        holder.txtTitle.setTypeface(Typeface.createFromAsset(con.getAssets(),"fonts/sans_bold.ttf"), 0);
        holder.txtAuthor.setText(mn.getAuthor());
        holder.txtAuthor.setTypeface(Typeface.createFromAsset(con.getAssets(),"fonts/sans_medium.ttf"), 0);
        holder.txtDate.setText(mn.getPublishedAt());
        holder.txtDate.setTypeface(Typeface.createFromAsset(con.getAssets(),"fonts/sans_regular.ttf"), 0);
        
        holder.cardNew.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent goWeb = new Intent();
                    goWeb.setAction(Intent.ACTION_VIEW);
                    goWeb.setClass(con, ViewContentActivity.class);
                    goWeb.putExtra("title", mn.getTitle());
                    goWeb.putExtra("author", mn.getAuthor());
                    goWeb.putExtra("url", mn.getUrl());
                    con.startActivity(goWeb);
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardNew;
        ImageView imgUrl;
        TextView txtTitle, txtDate, txtAuthor;

        public ViewHolder(View itemView) {
            super(itemView);
            cardNew = itemView.findViewById(R.id.card_news);
            imgUrl = itemView.findViewById(R.id.img_url);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtDate = itemView.findViewById(R.id.txt_time);
            txtAuthor = itemView.findViewById(R.id.txt_author);
        }
    }


}
