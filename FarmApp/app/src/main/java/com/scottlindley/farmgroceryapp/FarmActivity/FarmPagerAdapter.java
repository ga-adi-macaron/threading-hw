package com.scottlindley.farmgroceryapp.FarmActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Scott Lindley on 11/2/2016.
 */

public class FarmPagerAdapter extends FragmentPagerAdapter {
    private int mFarmID;
    private Bundle mBundle;

    public static final String PAGER_ADAPTER_FARM_ID = "pager_farm_id";
    public FarmPagerAdapter(FragmentManager fm, int farmID) {
        super(fm);
        mFarmID = farmID;
        mBundle = new Bundle();
        mBundle.putInt(PAGER_ADAPTER_FARM_ID, mFarmID);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return FarmInfoFragment.newInstance(mBundle);
            case 1: return FarmProduceFragment.newInstance(mBundle);
            case 2: return FarmLikesFragment.newInstance(mBundle);
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Info";
            case 1: return "Produce";
            case 2: return "Likes";
            default: return null;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        if(object instanceof FarmActivity.OnLikeButtonListener){
            ((FarmActivity.OnLikeButtonListener)object).onLikeButtonClicked(mFarmID);
        }
        if(object instanceof FarmProduceFragment){
            ((FarmProduceFragment)object).getAdapter().refreshData(mFarmID);
        }
        return super.getItemPosition(object);
    }


}
