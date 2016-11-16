package com.scottlindley.farmgroceryapp;

import com.scottlindley.farmgroceryapp.CartActivity.CartActivity;
import com.scottlindley.farmgroceryapp.CustomObjects.Cart;
import com.scottlindley.farmgroceryapp.CustomObjects.Farm;
import com.scottlindley.farmgroceryapp.CustomObjects.Food;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Created by Scott Lindley on 11/10/2016.
 */

public class CartActivityTest {

    @Test
    public void roundNumbersTest() throws Exception{
        CartActivity cartActivity = new CartActivity();
        Cart cart = Cart.getInstance();
        Farm farm = new Farm(1, "myFarm", "story", "specialty", "state");
        cart.getItems().add(new Food("apple", 4.353232356, farm));
        cart.getItems().add(new Food("banana", 1.02335432, farm));

        cartActivity.roundNumbers();
        assertEquals(cartActivity.getRoundedSubTotal(), 5.38, 0);
        assertEquals(cartActivity.getRoundedTax(), 0.48, 0);
        assertEquals(cartActivity.getRoundedTotal(), 5.85, 0);
    }
}
