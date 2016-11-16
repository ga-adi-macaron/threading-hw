package com.scottlindley.farmgroceryapp;

import com.scottlindley.farmgroceryapp.CustomObjects.Cart;
import com.scottlindley.farmgroceryapp.CustomObjects.Farm;
import com.scottlindley.farmgroceryapp.CustomObjects.Food;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Scott Lindley on 11/9/2016.
 */

public class CartTest {
    @Test
    public void removeZeroQuantitiesTest() throws Exception{
        Cart cart = Cart.getInstance();
        Farm farm = new Farm(1,"myFarm","story","specialty","state");
        cart.getItems().add(new Food("apple", 3.00, farm, 0));

        int expectedCartSize = 0;

        cart.removeZeroQuantities();

        assertEquals(cart.getItems().size(), expectedCartSize);
    }

    @Test
    public void clearCartTest() throws Exception{
        Cart cart = Cart.getInstance();
        Farm farm = new Farm(1,"myFarm","story","specialty","state");
        cart.getItems().add(new Food("apple", 3.00, farm, 15));

        int expectedCartSize = 0;

        cart.clearCart();

        assertEquals(cart.getItems().size(), expectedCartSize);
    }


}
