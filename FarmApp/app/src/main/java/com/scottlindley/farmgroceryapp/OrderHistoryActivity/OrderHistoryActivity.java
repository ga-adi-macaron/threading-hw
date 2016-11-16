package com.scottlindley.farmgroceryapp.OrderHistoryActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.scottlindley.farmgroceryapp.CartActivity.CartActivity;
import com.scottlindley.farmgroceryapp.CustomObjects.Order;
import com.scottlindley.farmgroceryapp.Database.MySQLiteHelper;
import com.scottlindley.farmgroceryapp.FarmListActivity.FarmListActivity;
import com.scottlindley.farmgroceryapp.LikedFarmsActivity.LikedFarmsActivity;
import com.scottlindley.farmgroceryapp.R;
import com.scottlindley.farmgroceryapp.SettingsActivity.SettingsActivity;

import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView mRecyclerView;
    private OrderHistoryRecyclerAdapter mAdapter;
    private Toolbar mToolbar;
    private List<Order> mOrders;
    private int mUserID;
    private boolean mNewOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        SharedPreferences preferences = getSharedPreferences(FarmListActivity.PREFERENCES_KEY, MODE_PRIVATE);
        mUserID = preferences.getInt(FarmListActivity.DEVICE_USER_ID_KEY, -1);
        if(mUserID ==-1){finish();}

        setUpNavBar();

        setUpRecycler();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_like) {
            startActivity(new Intent(this, LikedFarmsActivity.class));
        } else if (id == R.id.nav_cart) {
            startActivity(new Intent(OrderHistoryActivity.this, CartActivity.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.nav_farm_list){
            startActivity(new Intent(this, FarmListActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setUpNavBar(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences preferences = getSharedPreferences(FarmListActivity.PREFERENCES_KEY, MODE_PRIVATE);
        int DeviceUserID = preferences.getInt(FarmListActivity.DEVICE_USER_ID_KEY, -1);

        View headerView = navigationView.getHeaderView(0);
        TextView navUserName = (TextView)headerView.findViewById(R.id.nav_user_name);
        TextView navUserState = (TextView)headerView.findViewById(R.id.nav_user_state);

        if(DeviceUserID!=-1) {
            navUserName.setText(
                    MySQLiteHelper.getInstance(OrderHistoryActivity.this).getUserByID(DeviceUserID).getName());
            navUserState.setText(
                    MySQLiteHelper.getInstance(OrderHistoryActivity.this).getUserByID(DeviceUserID).getState());
        }
    }

    public void setUpRecycler(){
        mOrders = MySQLiteHelper.getInstance(OrderHistoryActivity.this).getOrdersByUserID(mUserID);

        Intent receivedIntent = getIntent();
        int orderPlaced = receivedIntent.getIntExtra(CartActivity.ORDER_PLACED_KEY, 0);

        if(orderPlaced==1){
            mNewOrder = true;
        }else{
            mNewOrder = false;
        }

        mRecyclerView = (RecyclerView)findViewById(R.id.order_history_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(OrderHistoryActivity.this,LinearLayoutManager.VERTICAL, true));
        mAdapter = new OrderHistoryRecyclerAdapter(mOrders, mNewOrder);
        mRecyclerView.setAdapter(mAdapter);
        if(mNewOrder){mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount()-1);}
    }
}
