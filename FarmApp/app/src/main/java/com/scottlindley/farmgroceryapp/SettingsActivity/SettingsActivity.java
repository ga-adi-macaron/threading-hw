package com.scottlindley.farmgroceryapp.SettingsActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.scottlindley.farmgroceryapp.CartActivity.CartActivity;
import com.scottlindley.farmgroceryapp.CustomObjects.User;
import com.scottlindley.farmgroceryapp.Database.MySQLiteHelper;
import com.scottlindley.farmgroceryapp.FarmListActivity.FarmListActivity;
import com.scottlindley.farmgroceryapp.LikedFarmsActivity.LikedFarmsActivity;
import com.scottlindley.farmgroceryapp.OrderHistoryActivity.OrderHistoryActivity;
import com.scottlindley.farmgroceryapp.R;


public class SettingsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar mToolbar;
    private EditText mNameEdit, mStateEdit;
    private TextView mButtonText;
    private int mUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        SharedPreferences preferences = getSharedPreferences(FarmListActivity.PREFERENCES_KEY, MODE_PRIVATE);
        mUserID = preferences.getInt(FarmListActivity.DEVICE_USER_ID_KEY, -1);
        if(mUserID ==-1){finish();}

        setUpNavBar();

        setUpViews();
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
        int id = item.getItemId();

        if (id == R.id.nav_like) {
            startActivity(new Intent(this, LikedFarmsActivity.class));
        } else if (id == R.id.nav_cart) {
            startActivity(new Intent(this, CartActivity.class));
        } else if (id == R.id.nav_order_history) {
            startActivity(new Intent(this, OrderHistoryActivity.class));
        }else if (id == R.id.nav_farm_list){
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
                    MySQLiteHelper.getInstance(SettingsActivity.this).getUserByID(DeviceUserID).getName());
            navUserState.setText(
                    MySQLiteHelper.getInstance(SettingsActivity.this).getUserByID(DeviceUserID).getState());
        }
    }

    public void setUpViews(){
        mNameEdit = (EditText)findViewById(R.id.user_name_edit);
        mStateEdit = (EditText)findViewById(R.id.user_state_edit);
        mButtonText = (TextView) findViewById(R.id.edit_profile_text);

        //Guide the user through the edit account process with simple textviews and edittexts
        mButtonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNameEdit.setVisibility(View.VISIBLE);
                mStateEdit.setVisibility(View.VISIBLE);
                mButtonText.setText("Okay");
                mButtonText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //update the user's info in the database and insert the userID into SharedPreferences
                        if (!mNameEdit.getText().toString().equals("")
                                && !mStateEdit.getText().toString().equals("")) {
                            User user = new User(
                                    mNameEdit.getText().toString(),
                                    mStateEdit.getText().toString());
                            MySQLiteHelper.getInstance(SettingsActivity.this).upDateUserInfo(mUserID, user);
                            SharedPreferences preferences = getSharedPreferences(
                                    FarmListActivity.PREFERENCES_KEY, MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt(FarmListActivity.DEVICE_USER_ID_KEY,
                                    MySQLiteHelper.getInstance(SettingsActivity.this).getLastUser().getID());
                            editor.commit();
                            Toast.makeText(SettingsActivity.this, "Changes Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            if (mNameEdit.getText().toString().equals("")
                                    && !mStateEdit.getText().toString().equals("")) {
                                mNameEdit.setError("Name cannot be blank");
                            } else if (!mNameEdit.getText().toString().equals("")
                                    && mStateEdit.getText().toString().equals("")) {
                                mStateEdit.setError("State cannot be blank");
                            }
                        }
                    }
                });
            }
        });
    }
}
