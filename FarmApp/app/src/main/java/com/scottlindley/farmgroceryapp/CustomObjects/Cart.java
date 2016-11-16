package com.scottlindley.farmgroceryapp.CustomObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott Lindley on 11/5/2016.
 */

public class Cart {
    private List<Food> mItems;
    private static Cart sCart;
    private double mTax, mSubTotal, mTotal;

    private Cart(){
        mItems = new ArrayList<>();
        mSubTotal = 0;
        mTax = 0;
        mTotal = 0;
    }

    /*
    This method is called when the cart activity calls onResume(); It goes through the cart and
    removes any item with a quantity of zero. This allows a user to decrement a cart item down
    to zero without the item disappearing. However, one the user leaves and then returns to the
    cart activity, the item will be gone.
     */
    public void removeZeroQuantities(){
        List<Food> revisedList = new ArrayList<>();
        for(Food food : mItems){
            if(food.getQuantity()!=0){
                revisedList.add(food);
            }
        }
        mItems = revisedList;
    }

    public void clearCart(){
        for (Food food : mItems){
            food.setQuantity(0);
        }
        removeZeroQuantities();
    }

    public double getTax() {
        setTax();
        return mTax;
    }

    public void setTax() {
        mTax = mSubTotal * 0.08875;
    }

    public double getSubTotal() {
        setSubTotal();
        return mSubTotal;
    }

    public void setSubTotal() {
        mSubTotal = 0;
        for (Food food : mItems){
            mSubTotal += food.getPrice();
        }
    }

    public double getTotal() {
        setTotal();
        return mTotal;
    }

    public void setTotal() {
        mTotal = mSubTotal + mTax;
    }

    public static Cart getInstance(){
        if(sCart==null){
            sCart = new Cart();
        }
        return sCart;
    }

    public List<Food> getItems() {
        return mItems;
    }
}
