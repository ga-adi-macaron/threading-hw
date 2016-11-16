package com.scottlindley.farmgroceryapp.FarmActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.scottlindley.farmgroceryapp.CustomObjects.Cart;
import com.scottlindley.farmgroceryapp.CustomObjects.Food;
import com.scottlindley.farmgroceryapp.Database.MySQLiteHelper;
import com.scottlindley.farmgroceryapp.R;

import java.util.List;

/**
 * Created by Scott Lindley on 11/4/2016.
 */

public class FarmProduceRecyclerAdapter extends RecyclerView.Adapter<FarmProduceRecyclerAdapter.ProduceViewHolder>{
    private List<Food> mFoods;
    private Context mContext;

    public FarmProduceRecyclerAdapter(List<Food> foods, Context context) {
        mFoods = foods;
        mContext = context;
    }

    @Override
    public ProduceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.produce_recycler_items, parent, false);
        return new ProduceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProduceViewHolder holder, final int position) {
        holder.mFoodPhoto.setImageResource(mFoods.get(position).getImageID());
        holder.mFoodName.setText(mFoods.get(position).getName());
        holder.mFoodPrice.setText("$"+Double.toString(mFoods.get(position).getPrice()));

        //Look to see if this food is currently in the cart
        boolean isInCart = false;
        for (Food f: Cart.getInstance().getItems()){
            if (f.getName().equals(mFoods.get(position).getName())
                && f.getFarmName().equals(mFoods.get(position).getFarmName())){
                isInCart = true;
            }
        }
        /*If it is then grey out the add to cart button and make it unclickable
        If it's not in the cart then add it to the cart with a quantity of '1' when clicked*/
        if(isInCart){
            holder.mAddToCartButton.setAlpha(0.35f);
            holder.mAddToCartButton.setClickable(false);
        }else {
            holder.mAddToCartButton.setAlpha(1.0f);
            holder.mAddToCartButton.setClickable(true);
            holder.mAddToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mFoods.get(position).getQuantity() == 0) {
                        mFoods.get(position).incrementQuantity();
                    }
                    if (!Cart.getInstance().getItems().contains(mFoods.get(position))) {
                        mFoods.get(position).setQuantity(1);
                        Cart.getInstance().getItems().add(mFoods.get(position));
                        Toast.makeText(view.getContext(), mFoods.get(position).getName() +
                                " added to cart", Toast.LENGTH_SHORT).show();
                        view.setAlpha(0.35f);
                        view.setClickable(false);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mFoods.size();
    }

    public class ProduceViewHolder extends RecyclerView.ViewHolder{
        public ImageView mFoodPhoto, mAddToCartButton;
        public TextView mFoodName, mFoodPrice;
        public ProduceViewHolder(View itemView) {
            super(itemView);
            mFoodName = (TextView) itemView.findViewById(R.id.food_name);
            mFoodPrice = (TextView) itemView.findViewById(R.id.food_price);
            mFoodPhoto = (ImageView) itemView.findViewById(R.id.food_image);
            mAddToCartButton = (ImageView) itemView.findViewById(R.id.add_to_cart_button);
        }
    }

    public void refreshData(int farmID){
        mFoods = MySQLiteHelper.getInstance(mContext).getFoodByFarm(farmID);
        notifyDataSetChanged();
    }
}
