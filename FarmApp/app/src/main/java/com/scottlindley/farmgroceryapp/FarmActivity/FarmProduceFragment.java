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
import com.scottlindley.farmgroceryapp.CustomObjects.Food;
import com.scottlindley.farmgroceryapp.Database.MySQLiteHelper;
import com.scottlindley.farmgroceryapp.R;

import java.util.List;

/**
 * Created by Scott Lindley on 11/4/2016.
 */

public class FarmProduceFragment extends Fragment {
    private Farm mSelectedFarm;
    private RecyclerView mRecyclerView;
    private FarmProduceRecyclerAdapter mAdapter;
    private Context mContext;
    private List<Food> mFoods;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = container.getContext();
        return inflater.inflate(R.layout.fragment_farm_produce, container, false);
    }

    public static Fragment newInstance(Bundle bundle){
        Fragment productFragment = new FarmProduceFragment();
        productFragment.setArguments(bundle);
        return productFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();

        int farmID = bundle.getInt(FarmPagerAdapter.PAGER_ADAPTER_FARM_ID);

        ThreadGetFarmByID task = new ThreadGetFarmByID();
        task.execute(new Params(view, farmID));

    }

    private class Params {
        public View mView;
        public int mFarmID;

        public Params (View view, int farmID){
            mView = view;
            mFarmID = farmID;
        }
    }


    public FarmProduceRecyclerAdapter getAdapter(){
        return mAdapter;
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
            mRecyclerView = (RecyclerView) view.findViewById(R.id.produce_fragment_recycler);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
            }

            new ThreadGetFoodByFarm().execute(farm.getID());

        }
    }

    public class ThreadGetFoodByFarm extends AsyncTask<Integer, Void, List<Food>>{
        @Override
        protected List<Food> doInBackground(Integer... integers) {
            return MySQLiteHelper.getInstance(mContext).getFoodByFarm(integers[0]);

        }

        @Override
        protected void onPostExecute(List<Food> foods) {
            super.onPostExecute(foods);
            mFoods = foods;
            mAdapter = new FarmProduceRecyclerAdapter(mFoods, mContext);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
