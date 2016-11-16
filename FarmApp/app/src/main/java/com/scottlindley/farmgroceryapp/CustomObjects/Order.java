package com.scottlindley.farmgroceryapp.CustomObjects;

/**
 * Created by Scott Lindley on 11/8/2016.
 */

public class Order {
    private double mOrderPrice;
    private String mOrderDate;
    private int mUserID;

    public Order(double orderPrice, String orderDate, int userID) {
        mOrderPrice = orderPrice;
        mOrderDate = orderDate;
        mUserID = userID;
    }

    public double getOrderPrice() {
        return mOrderPrice;
    }

    public String getOrderDate() {
        return mOrderDate;
    }

    public int getUserID() {
        return mUserID;
    }
}
