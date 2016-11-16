package com.scottlindley.farmgroceryapp.CartActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scottlindley.farmgroceryapp.CustomObjects.Cart;
import com.scottlindley.farmgroceryapp.CustomObjects.Food;
import com.scottlindley.farmgroceryapp.R;

import java.util.List;

/**
 * Created by Scott Lindley on 11/5/2016.
 */

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.CartViewHolder>{
    private List<Food> mItems;
    private QuantityButtonListener mListener;

    public CartRecyclerAdapter(QuantityButtonListener listener, List<Food> items) {
//        mItems = consolidateDuplicates(items);
        mItems = items;
        mListener = listener;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_recycler_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartViewHolder holder, final int position) {
        holder.mFoodImage.setImageResource(mItems.get(position).getImageID());
        holder.mFoodName.setText(mItems.get(position).getFarmName()+"\n"+mItems.get(position).getName());
        holder.mFoodPrice.setText("$"+mItems.get(position).getPrice());
        holder.mQuantity.setText(String.valueOf(mItems.get(position).getQuantity()));

        holder.mQuantityUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cart.getInstance().getItems().get(holder.getAdapterPosition()).incrementQuantity();
                replaceData();
                notifyItemChanged(holder.getAdapterPosition());
                mListener.handleIncrement();
            }
        });

        holder.mQuantityDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cart.getInstance().getItems().get(holder.getAdapterPosition()).decrementQuantity();
                replaceData();
                notifyItemChanged(holder.getAdapterPosition());
                mListener.handleIncrement();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /*
    This method takes in a list of food items and looks for any duplicates with the same food
    name AND the same farm name. It returns a list of food items with adjusted quantities and
    instead of duplicates.
     */
    //TODO: TEST
    public List<Food> consolidateDuplicates(List<Food> items) {
        for (int i = 0; i < items.size(); i++) {
            String itemName = items.get(i).getName();
            String farmName = items.get(i).getFarmName();
            for (int j = 0; j < items.size(); j++) {
                if (items.get(j).getName().equals(itemName)
                        && items.get(j).getFarmName().equals(farmName)
                        && i != j) {
                    items.remove(j);
                }
            }
        }
        return items;
    }


    public void replaceData(){
        mItems = consolidateDuplicates(Cart.getInstance().getItems());
        notifyDataSetChanged();
    }

    public interface QuantityButtonListener{
        void handleIncrement();
    }


    public class CartViewHolder extends RecyclerView.ViewHolder{
        public ImageView mQuantityUp, mQuantityDown, mFoodImage;
        public TextView mQuantity, mFoodName, mFoodPrice;
        public CartViewHolder(View itemView) {
            super(itemView);
            mQuantityUp = (ImageView)itemView.findViewById(R.id.quantity_up);
            mQuantityDown = (ImageView)itemView.findViewById(R.id.quantity_down);
            mQuantity = (TextView)itemView.findViewById(R.id.quantity);
            mFoodImage = (ImageView)itemView.findViewById(R.id.food_image);
            mFoodName = (TextView)itemView.findViewById(R.id.food_name);
            mFoodPrice = (TextView)itemView.findViewById(R.id.food_price);
        }
    }
}
