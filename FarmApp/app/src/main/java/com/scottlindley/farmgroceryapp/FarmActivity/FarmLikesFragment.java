package com.scottlindley.farmgroceryapp.FarmActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scottlindley.farmgroceryapp.CustomObjects.Farm;
import com.scottlindley.farmgroceryapp.CustomObjects.Like;
import com.scottlindley.farmgroceryapp.Database.MySQLiteHelper;
import com.scottlindley.farmgroceryapp.R;

import java.util.List;

/**
 * Created by Scott Lindley on 11/4/2016.
 */

public class FarmLikesFragment extends Fragment implements FarmActivity.OnLikeButtonListener{
    private Farm mSelectedFarm;
    private int mFarmID;
    private List<Like> mLikes;
    private RecyclerView mRecyclerView;
    private FarmLikesRecyclerAdapter mAdapter;
    private Context mContext;


    //when the toolbar's like button is clicked, refresh the adapter and scroll to the newly added like
    @Override
    public void onLikeButtonClicked(int farmID) {
        new ThreadGetLikes().execute(new Params(null,farmID));
        mAdapter.replaceData(mLikes);
        mRecyclerView.smoothScrollToPosition(mLikes.size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = container.getContext();
        return inflater.inflate(R.layout.fragment_likes,container,false);
    }

    public static Fragment newInstance(Bundle bundle){
        Fragment likeFragment = new FarmLikesFragment();
        likeFragment.setArguments(bundle);
        return likeFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        mFarmID = bundle.getInt(FarmPagerAdapter.PAGER_ADAPTER_FARM_ID);

        Params params = new Params(view, mFarmID);
        new ThreadGetFarmByID().execute(params);
        new ThreadGetLikes().execute(params);

    }

    private class Params {
        public View mView;
        public int mFarmID;

        public Params(View view, int farmID) {
            mView = view;
            mFarmID = farmID;
        }
    }

    public class ThreadGetLikes extends AsyncTask<Params, Void, List<Like>>{
        private View view;

        @Override
        protected List<Like> doInBackground(Params... params) {
            view = params[0].mView;
            return MySQLiteHelper.getInstance(mContext).getLikes(params[0].mFarmID);

        }

        @Override
        protected void onPostExecute(List<Like> likes) {
            super.onPostExecute(likes);
            mLikes = likes;
            mRecyclerView = (RecyclerView) view.findViewById(R.id.likes_recycler);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
            }
            if(mLikes!=null) {
                mAdapter = new FarmLikesRecyclerAdapter(mContext, mLikes);
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    }

    public class ThreadGetFarmByID extends AsyncTask<Params, Void, Farm>{
        private View view;

        @Override
        protected Farm doInBackground(Params... params) {
            view = params[0].mView;
            return MySQLiteHelper.getInstance(mContext).getFarmByID(params[0].mFarmID);
        }

        @Override
        protected void onPostExecute(Farm farm) {
            super.onPostExecute(farm);
            mSelectedFarm = farm;
        }
    }
}
