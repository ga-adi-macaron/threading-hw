package com.scottlindley.farmgroceryapp.FarmActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scottlindley.farmgroceryapp.CustomObjects.Like;
import com.scottlindley.farmgroceryapp.Database.MySQLiteHelper;
import com.scottlindley.farmgroceryapp.R;

import java.util.List;

/**
 * Created by Scott Lindley on 11/4/2016.
 */

public class FarmLikesRecyclerAdapter extends RecyclerView.Adapter<FarmLikesRecyclerAdapter.LikesViewHolder>{
    private List<Like> mLikes;
    private Context mContext;

    public FarmLikesRecyclerAdapter(Context context, List<Like> likes) {
        mContext = context;
        mLikes = likes;
    }

    @Override
    public LikesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.likes_recycler_item,parent,false);
        return new LikesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LikesViewHolder holder, int position) {
        holder.mUserName.setText(MySQLiteHelper.getInstance(mContext).getUserByID(mLikes.get(position).getUserID()).getName());

        /*
        States in the database are all lowercase to make searching easier this bit
        of code capitalizes the first letters of each word before it is displayed
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
        holder.mUserState.setText(upperCaseState);

    }

    @Override
    public int getItemCount() {
        return mLikes.size();
    }

    public void replaceData(List<Like> likes){
        mLikes = likes;
        notifyDataSetChanged();
    }

    public class LikesViewHolder extends RecyclerView.ViewHolder{
        public TextView mUserName, mUserState;
        public LikesViewHolder(View itemView) {
            super(itemView);
            mUserName = (TextView)itemView.findViewById(R.id.user_name);
            mUserState = (TextView)itemView.findViewById(R.id.user_state);
        }
    }
}
