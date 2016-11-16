package com.scottlindley.farmgroceryapp;

import com.scottlindley.farmgroceryapp.CartActivity.CartRecyclerAdapter;
import com.scottlindley.farmgroceryapp.CustomObjects.Farm;
import com.scottlindley.farmgroceryapp.CustomObjects.Food;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Scott Lindley on 11/10/2016.
 */

public class CartRecyclerAdapterTest {

    @Test
    public void consolidateDuplicatesTest() throws Exception{
        List<Food> foods = new ArrayList<>();
        Farm farm1 = new Farm(1,"farm1","story","specialty","state");
        Farm farm2 = new Farm(2,"farm2","story","specialty","state");
        foods.add(new Food("apples",4.5,farm1));
        foods.add(new Food("bananas",3.5,farm1));
        foods.add(new Food("apples",4.5,farm1));
        foods.add(new Food("bananas",7.0,farm2));
        foods.add(new Food("apples",2.0,farm2));
        foods.add(new Food("bananas",3.5,farm1));

        CartRecyclerAdapter.QuantityButtonListener listener = new CartRecyclerAdapter.QuantityButtonListener() {
            @Override
            public void handleIncrement() {

            }
        };
        CartRecyclerAdapter adapter = new CartRecyclerAdapter(listener, foods);

        foods = adapter.consolidateDuplicates(foods);
        assertEquals(4, foods.size());
    }
}
