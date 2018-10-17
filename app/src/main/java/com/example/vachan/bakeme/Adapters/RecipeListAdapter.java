package com.example.vachan.bakeme.Adapters;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vachan.bakeme.IngredientsWidget;
import com.example.vachan.bakeme.Model.Recipe;
import com.example.vachan.bakeme.R;
import com.example.vachan.bakeme.Views.RecipeDetailsActivity;

import java.util.ArrayList;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.MyViewHolder> {

    private Context mContext ;
    private ArrayList<Recipe> mData ;

    public interface ListActivityInterface {
        public void sendInfo(Recipe recipe);
    }

    private ListActivityInterface mCallback;

    public RecipeListAdapter(Context mContext, ArrayList<Recipe> mData) {
        this.mCallback = (ListActivityInterface) mContext;
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.recipeitemview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeListAdapter.MyViewHolder holder, final int position) {

        holder.nameView.setText(mData.get(position).getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.sendInfo(mData.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

         TextView nameView;
         CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.name);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }

}
