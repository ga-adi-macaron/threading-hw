package com.scottlindley.farmgroceryapp.CustomObjects;

import com.scottlindley.farmgroceryapp.R;

import java.util.List;

/**
 * Created by Scott Lindley on 11/1/2016.
 */

public class Farm {
    private String mName, mStory, mState, mSpecialty;
    private int mID;
    private int mPhotoID;
    private List<Food> mInventory;

    public Farm(int ID, String name, String story, String specialty, String state) {
        mID = ID;
        mName = name;
        mStory = story;
        mState = state;
        mSpecialty = specialty;

        switch (mName) {
            case "McDonald Farms":
                mPhotoID = (R.drawable.mcdonald_small);
                break;
            case "Stony Hill Farms":
                mPhotoID = (R.drawable.stony_hill_small);
                break;
            case "Sunny Side Farms":
                mPhotoID = (R.drawable.sunny_small);
                break;
            case "Seed Sowers":
                mPhotoID = (R.drawable.sowers_small);
                break;
            case "The Funny Farm":
                mPhotoID = (R.drawable.funny_small);
                break;
            case "Turnip the Beet":
                mPhotoID = (R.drawable.beet_small);
                break;
            case "Scare Crow Farms":
                mPhotoID = (R.drawable.scarecrow_small);
                break;
            case "Couch Potato Farms":
                mPhotoID = (R.drawable.couch_potato_small);
                break;
            case "Peter Piper Farms":
                mPhotoID = (R.drawable.pickled_peppers);
                break;
            case "Strawberry Fields":
                mPhotoID = (R.drawable.strawberry_fields_small);
                break;
            case "Buffalo Hill Gardens":
                mPhotoID = (R.drawable.buffalo_hills_small);
                break;
            case "Deer Cove Acres":
                mPhotoID = (R.drawable.deer_cove_small);
                break;
            case "Angry Beaver Nursery":
                mPhotoID = (R.drawable.angry_beaver_small);
                break;
            case "Big Bear Orchard":
                mPhotoID = (R.drawable.bear_orchard_small);
                break;
            case "Bumble Bee Lands":
                mPhotoID = (R.drawable.bee_land_small);
                break;
            case "Flower Hills Range":
                mPhotoID = (R.drawable.flower_hill);
                break;
        }
    }

    public String getName() {
        return mName;
    }

    public String getStory() {
        return mStory;
    }

    public String getState() {
        return mState;
    }

    public String getSpecialty() {
        return mSpecialty;
    }


    public int getID() {
        return mID;
    }

    public int getPhotoID(){
        return mPhotoID;
    }


    public void getInventory(List<Food> inventory) {
        mInventory = inventory;
    }
}
