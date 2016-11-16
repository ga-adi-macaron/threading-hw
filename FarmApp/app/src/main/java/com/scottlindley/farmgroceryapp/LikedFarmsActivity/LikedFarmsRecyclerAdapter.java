package com.scottlindley.farmgroceryapp.LikedFarmsActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scottlindley.farmgroceryapp.CustomObjects.Farm;
import com.scottlindley.farmgroceryapp.CustomObjects.Like;
import com.scottlindley.farmgroceryapp.Database.MySQLiteHelper;
import com.scottlindley.farmgroceryapp.FarmActivity.FarmActivity;
import com.scottlindley.farmgroceryapp.FarmListActivity.FarmListRecyclerAdapter;
import com.scottlindley.farmgroceryapp.R;

import java.util.List;

/**
 * Created by Scott Lindley on 11/7/2016.
 */

public class LikedFarmsRecyclerAdapter extends RecyclerView.Adapter<LikedFarmsRecyclerAdapter.LikedFarmsViewHolder>{
    private List<Like> mLikes;
    private Context mContext;
    private int mUserID;

    public LikedFarmsRecyclerAdapter(List<Like> likes, Context context, int userID) {
        mLikes = likes;
        mContext = context;
        mUserID = userID;
    }

    @Override
    public LikedFarmsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.likes_activity_recycler_items, parent, false);
        return new LikedFarmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LikedFarmsViewHolder holder, int position) {
        final Farm likedFarm = MySQLiteHelper.getInstance(mContext)
                .getFarmByID(mLikes.get(position).getFarmID());
        holder.mFarmPhoto.setImageResource(likedFarm.getPhotoID());
        holder.mFarmName.setText(likedFarm.getName());
         /*
        States in the database are all lowercase to make searching easier this line
        of code capitalizes the first letters of each word
        */
        String[] statePieces = MySQLiteHelper.getInstance(mContext)
                .getUserByID(mLikes.get(position).getUserID()).getState().split(" ");
        String upperCaseState;
        statePieces[0] = Character.toString(statePieces[0].charAt(0)).toUpperCase()+
                statePieces[0].substring(1);
        if(statePieces.length>1) {
            statePieces[1] = " "+Character.toString(statePieces[1].charAt(0)).toUpperCase()+
                    statePieces[1].substring(1);
            upperCaseState = statePieces[0] + statePieces[1];
        }else{
            upperCaseState = statePieces[0];
        }
        holder.mFarmState.setText(upperCaseState);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, FarmActivity.class);
                intent.putExtra(FarmListRecyclerAdapter.FARM_ID_INTENT_KEY, likedFarm.getID());
                mContext.startActivity(intent);
            }
        });

        //Toggles 'like' on a Farm and updates the Like in the database
        holder.mHeartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isLiked = false;
                List<Like> dbLikes = MySQLiteHelper.getInstance(mContext).getUserLikes(mUserID);
                for (Like like : dbLikes){
                    if(like.getFarmID()==likedFarm.getID()){
                        isLiked = true;
                    }
                }
                if(isLiked){
                    holder.mHeartButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                    holder.mHeartButton.setColorFilter(Color.rgb(120,120,120));
                    holder.mHeartButton.setAlpha(0.25f);
                    MySQLiteHelper.getInstance(mContext).deleteLike(mLikes.get(holder.getAdapterPosition()));
                }else{
                    holder.mHeartButton.setImageResource(R.drawable.ic_favorite_white_24dp);
                    holder.mHeartButton.setColorFilter(Color.rgb(183,28,28));
                    holder.mHeartButton.setAlpha(1f);
                    MySQLiteHelper.getInstance(mContext).insertLike(new Like(likedFarm.getID(),mUserID));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLikes.size();
    }

    public class LikedFarmsViewHolder extends RecyclerView.ViewHolder{
        public ImageView mFarmPhoto, mHeartButton;
        public TextView mFarmName, mFarmState;
        public LikedFarmsViewHolder(View itemView) {
            super(itemView);
            mFarmName = (TextView)itemView.findViewById(R.id.like_farm_name);
            mFarmState = (TextView)itemView.findViewById(R.id.like_farm_state);
            mFarmPhoto = (ImageView)itemView.findViewById(R.id.like_farm_photo);
            mHeartButton = (ImageView)itemView.findViewById(R.id.like_heart);
        }
    }
}
