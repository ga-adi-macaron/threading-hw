package com.scottlindley.farmgroceryapp.Database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Scott Lindley on 11/2/2016.
 */

public class DBAssetsHelper extends SQLiteAssetHelper{
    public static final String DATA_BASE_NAME = "FarmApp";
    public static final int VERSION_NUMBER = 1;

    public DBAssetsHelper(Context context) {
        super(context,DATA_BASE_NAME, null,VERSION_NUMBER);
    }
}
