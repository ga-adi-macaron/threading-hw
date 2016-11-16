package com.scottlindley.farmgroceryapp.CustomObjects;

import com.scottlindley.farmgroceryapp.R;

/**
 * Created by Scott Lindley on 11/2/2016.
 */

public class Food {
    private String mName;
    private int mImageID;
    private double mPrice;
    private String mFarmName;
    private int mQuantity;
    private double mPriceForOne;
    private int mFarmID;

    public Food(String name, double price, Farm farm) {
        mName = name;
        mPrice = price;
        mPriceForOne = price;
        mFarmName = farm.getName();
        mFarmID = farm.getID();
        mQuantity = 0;

        assignImages(name);
    }

    public Food(String name, double price, Farm farm, int quantity){
        this(name, price, farm);
        mQuantity = quantity;
    }




    public void assignImages(String name) {
        switch (name) {
            case "apples":
                mImageID = R.drawable.apple;
                break;
            case "basil":
                mImageID = R.drawable.basil;
                break;
            case "beets":
                mImageID = R.drawable.beets;
                break;
            case "blueberries":
                mImageID = R.drawable.blueberries;
                break;
            case "bread":
                mImageID = R.drawable.bread;
                break;
            case "cabbage":
                mImageID = R.drawable.cabbage;
                break;
            case "carrots":
                mImageID = R.drawable.carrots;
                break;
            case "celery":
                mImageID = R.drawable.celery;
                break;
            case "corn":
                mImageID = R.drawable.corn;
                break;
            case "eggs":
                mImageID = R.drawable.eggs;
                break;
            case "fennel":
                mImageID = R.drawable.fennel;
                break;
            case "honey":
                mImageID = R.drawable.honey;
                break;
            case "lettuce":
                mImageID = R.drawable.lettuce;
                break;
            case "milk":
                mImageID = R.drawable.milk;
                break;
            case "onions":
                mImageID = R.drawable.onion;
                break;
            case "peppers":
                mImageID = R.drawable.peppers;
                break;
            case "raddishes":
                mImageID = R.drawable.raddish;
                break;
            case "raspberries":
                mImageID = R.drawable.raspberries;
                break;
            case "strawberries":
                mImageID = R.drawable.strawberries;
                break;
            case "turnips":
                mImageID = R.drawable.turnips;
                break;
            case "potatoes":
                mImageID = R.drawable.potatoes;
                break;
            case "peanuts":
                mImageID = R.drawable.peanuts;
                break;
            case "sunflower seeds":
                mImageID = R.drawable.sunflower;
                break;
            case "kale":
                mImageID = R.drawable.kale;
                break;
            case "mint":
                mImageID = R.drawable.mint;
                break;
            case "brussel sprouts":
                mImageID = R.drawable.brussels;
                break;
        }
    }




    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity){
        mQuantity = quantity;
    }

    public void incrementQuantity(){
        mQuantity++;
        mPrice = roundPrice(mPriceForOne * mQuantity);
    }

    public void decrementQuantity(){
        if(mQuantity>0) {
            mQuantity--;
            mPrice = roundPrice(mPriceForOne * mQuantity);
        }
    }

    public String getFarmName() {
        return mFarmName;
    }

    public int getFarmID() {
        return mFarmID;
    }

    public String getName() {
        return mName;
    }

    public int getImageID() {
        return mImageID;
    }

    public double getPrice() {
        return mPrice;
    }

    public double roundPrice(double price){
        double roundedPrice = price*100;
        roundedPrice = Math.round(roundedPrice);
        roundedPrice = roundedPrice/100;

        return roundedPrice;
    }
}
