package com.scottlindley.farmgroceryapp.CustomObjects;

/**
 * Created by Scott Lindley on 11/2/2016.
 */

public class Like {
    private int mFarmID;
    private int mUserID;

    public Like(int farmID, int userID) {
        mFarmID = farmID;
        mUserID = userID;
    }

    public int getFarmID() {
        return mFarmID;
    }

    public int getUserID() {
        return mUserID;
    }
}
