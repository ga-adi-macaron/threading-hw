package com.scottlindley.farmgroceryapp.SignUpActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.scottlindley.farmgroceryapp.CustomObjects.User;
import com.scottlindley.farmgroceryapp.Database.MySQLiteHelper;
import com.scottlindley.farmgroceryapp.FarmListActivity.FarmListActivity;
import com.scottlindley.farmgroceryapp.R;

public class SignUpActivity extends AppCompatActivity {
    private TextView mButtonText;
    private TextView mSignUpText;
    private EditText mNameEdit, mStateEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setUpViews();

    }

    public void setUpViews(){
        mSignUpText = (TextView)findViewById(R.id.sign_up_text);
        mNameEdit = (EditText)findViewById(R.id.user_name_edit);
        mStateEdit = (EditText)findViewById(R.id.user_state_edit);
        mButtonText = (TextView) findViewById(R.id.button_text);

        mButtonText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignUpText.setVisibility(View.INVISIBLE);
                mNameEdit.setVisibility(View.VISIBLE);
                mStateEdit.setVisibility(View.VISIBLE);
                mButtonText.setText("Okay");
                mButtonText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Add the new user to the database and save userID into SharedPreferences
                        if (!mNameEdit.getText().toString().equals("")
                                && !mStateEdit.getText().toString().equals("")) {
                            User user = new User(
                                    mNameEdit.getText().toString(),
                                    mStateEdit.getText().toString());
                            MySQLiteHelper.getInstance(SignUpActivity.this).insertUser(user);
                            SharedPreferences preferences = getSharedPreferences(
                                    FarmListActivity.PREFERENCES_KEY, MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt(FarmListActivity.DEVICE_USER_ID_KEY,
                                    MySQLiteHelper.getInstance(SignUpActivity.this).getLastUser().getID());
                            editor.commit();

                            startActivity(new Intent(SignUpActivity.this, FarmListActivity.class));
                            finish();
                        }else{
                            if(mNameEdit.getText().toString().equals("")
                                    && !mStateEdit.getText().toString().equals("")){
                                mNameEdit.setError("Name cannot be blank");
                            } else if (!mNameEdit.getText().toString().equals("")
                                    && mStateEdit.getText().toString().equals("")){
                                mStateEdit.setError("State cannot be blank");
                            }
                        }
                    }
                });
            }
        });
    }
}
