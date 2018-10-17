package com.example.vachan.bakeme.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vachan.bakeme.Model.Steps;
import com.example.vachan.bakeme.R;
import com.example.vachan.bakeme.Views.StepsDetailsActivity;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.MyViewHolder> {

    private Context mContext ;
    private ArrayList<Steps> mData ;

    public StepsAdapter(Context mContext, ArrayList<Steps> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public StepsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.stepitemview,parent,false);
        return new StepsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StepsAdapter.MyViewHolder holder, final int position) {

        holder.stepsView.setText(mData.get(position).getStepInfo());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StepsDetailsActivity.class);
                /*
                intent.putExtra("Step", mData.get(position));
                intent.putExtra("total_steps", mData.size());
               */
                intent.putParcelableArrayListExtra("steps", mData);
                intent.putExtra("step_number", position);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView stepsView;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            stepsView = (TextView) itemView.findViewById(R.id.steps);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }
}
