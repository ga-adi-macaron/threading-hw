package com.scottlindley.farmgroceryapp.FarmListActivity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scottlindley.farmgroceryapp.CustomObjects.Farm;
import com.scottlindley.farmgroceryapp.FarmActivity.FarmActivity;
import com.scottlindley.farmgroceryapp.R;

import java.util.List;

/**
 * Created by Scott Lindley on 11/2/2016.
 */

public class FarmListRecyclerAdapter extends RecyclerView.Adapter<FarmListRecyclerAdapter.FarmListViewHolder>{
    public static final String FARM_ID_INTENT_KEY = "farmID";
    private List<Farm> mFarms;

    public FarmListRecyclerAdapter(List<Farm> farms) {
        mFarms = farms;
    }

    @Override
    public FarmListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.farm_recycler_item,parent,false);
        return new FarmListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FarmListViewHolder holder, int position) {
        holder.mName.setText(mFarms.get(position).getName());
        holder.mPhoto.setImageResource(mFarms.get(position).getPhotoID());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FarmActivity.class);
                intent.putExtra(FARM_ID_INTENT_KEY, mFarms.get(holder.getAdapterPosition()).getID());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFarms.size();
    }

    public void replaceData(List<Farm> farms){
        mFarms = farms;
        notifyDataSetChanged();
    }


    public class FarmListViewHolder extends RecyclerView.ViewHolder{
        public ImageView mPhoto;
        public TextView mName;
        public FarmListViewHolder(View itemView) {
            super(itemView);
            mName = (TextView)itemView.findViewById(R.id.farm_name);
            mPhoto = (ImageView)itemView.findViewById(R.id.farm_photo);
        }
    }
}
