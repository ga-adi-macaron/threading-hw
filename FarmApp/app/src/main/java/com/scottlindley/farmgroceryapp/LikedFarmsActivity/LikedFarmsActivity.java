package com.scottlindley.farmgroceryapp.LikedFarmsActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.scottlindley.farmgroceryapp.CartActivity.CartActivity;
import com.scottlindley.farmgroceryapp.CustomObjects.Like;
import com.scottlindley.farmgroceryapp.Database.MySQLiteHelper;
import com.scottlindley.farmgroceryapp.FarmListActivity.FarmListActivity;
import com.scottlindley.farmgroceryapp.OrderHistoryActivity.OrderHistoryActivity;
import com.scottlindley.farmgroceryapp.R;
import com.scottlindley.farmgroceryapp.SettingsActivity.SettingsActivity;

import java.util.List;

public class LikedFarmsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MySQLiteHelper mHelper;
    private int mUserID;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_farms);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mHelper = MySQLiteHelper.getInstance(LikedFarmsActivity.this);

        SharedPreferences preferences = getSharedPreferences(FarmListActivity.PREFERENCES_KEY, MODE_PRIVATE);
        mUserID = preferences.getInt(FarmListActivity.DEVICE_USER_ID_KEY, -1);
        if(mUserID ==-1){finish();}

        setUpNavBar();

        setUpRecyclerView();

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

        } else if (id == R.id.nav_cart) {
            startActivity(new Intent(LikedFarmsActivity.this, CartActivity.class));
        } else if (id == R.id.nav_order_history) {
            startActivity(new Intent(this, OrderHistoryActivity.class));
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
                this, drawer,mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView navUserName = (TextView)headerView.findViewById(R.id.nav_user_name);
        TextView navUserState = (TextView)headerView.findViewById(R.id.nav_user_state);

        if(mUserID!=-1) {
            navUserName.setText(
                    mHelper.getUserByID(mUserID).getName());
            navUserState.setText(
                    mHelper.getUserByID(mUserID).getState());
        }
    }


    public void setUpRecyclerView(){
        List<Like> likes = mHelper.getUserLikes(mUserID);

        TextView noFarmsLikedText = (TextView)findViewById(R.id.no_liked_farms_text);

        if(likes.isEmpty()){
            noFarmsLikedText.setVisibility(View.VISIBLE);
        }else{
            noFarmsLikedText.setVisibility(View.INVISIBLE);
        }

        mRecyclerView = (RecyclerView)findViewById(R.id.likes_activity_recycler);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(LikedFarmsActivity.this));
        }else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            mRecyclerView.setLayoutManager(new GridLayoutManager(LikedFarmsActivity.this, 2));
        }
        mRecyclerView.setAdapter(new LikedFarmsRecyclerAdapter(likes, LikedFarmsActivity.this, mUserID));
    }
}
