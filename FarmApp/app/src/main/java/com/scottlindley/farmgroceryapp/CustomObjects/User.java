package com.scottlindley.farmgroceryapp.CustomObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott Lindley on 11/4/2016.
 */

public class User {
    private String mName, mState;
    private int mID;
    private Cart mCart;
    private List<Integer> mLikedFarmsIDs;

    public User(String name, String state) {
        mName = name;
        mState = state;
        mCart = Cart.getInstance();
        mLikedFarmsIDs = new ArrayList<>();
    }

    public User(String name, String state, int ID) {
        mName = name;
        mState = state;
        mID = ID;
    }

    public String getName() {
        return mName;
    }

    public String getState() {
        return mState;
    }

    public int getID() {
        return mID;
    }

}
