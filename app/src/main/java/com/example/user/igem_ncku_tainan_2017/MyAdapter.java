package com.example.user.igem_ncku_tainan_2017;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by user on 04/09/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private String[] cardViewTitles;
    private int[] imagesIds;
    private Listener listener;

    public interface Listener {
        void onClick(int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//Create ViewHolder
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.card_view_icon_image);
        Drawable drawable = cardView.getResources().getDrawable(imagesIds[position], null);
        imageView.setImageDrawable(drawable);
        TextView textView = (TextView) cardView.findViewById(R.id.card_view_title);
        textView.setText(cardViewTitles[position]);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardViewTitles.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {//Define ViewHolder
        private CardView cardView;

        public ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }

    public MyAdapter(String[] cardViewTitles, int[] imagesIds) {//Construct MyAdapter
        this.cardViewTitles = cardViewTitles;
        this.imagesIds = imagesIds;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
