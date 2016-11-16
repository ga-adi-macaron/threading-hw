package com.scottlindley.farmgroceryapp.FarmActivity;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scottlindley.farmgroceryapp.CustomObjects.Farm;
import com.scottlindley.farmgroceryapp.Database.MySQLiteHelper;
import com.scottlindley.farmgroceryapp.R;

/**
 * Created by Scott Lindley on 11/2/2016.
 */

public class FarmInfoFragment extends Fragment {
    private MySQLiteHelper mHelper;
    private Farm mSelectedFarm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_farm_info,container,false);
    }

    public static Fragment newInstance(Bundle bundle){
        Fragment infoFragment = new FarmInfoFragment();
        infoFragment.setArguments(bundle);
        return infoFragment;
    }

    //required empty constructor
    public FarmInfoFragment() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();

        int farmID = bundle.getInt(FarmPagerAdapter.PAGER_ADAPTER_FARM_ID);

        mHelper = MySQLiteHelper.getInstance(view.getContext());

        new ThreadGetFarmByID().execute(new Params(view, farmID));

    }

    private class Params {
        public View mView;
        public int mFarmID;

        public Params (View view, int farmID){
            mView = view;
            mFarmID = farmID;
        }
    }

    public class ThreadGetFarmByID extends AsyncTask<Params, Void, Farm>{
        private View view;

        @Override
        protected Farm doInBackground(Params... params) {
            view = params[0].mView;
            return mHelper.getFarmByID(params[0].mFarmID);
        }

        @Override
        protected void onPostExecute(Farm farm) {
            super.onPostExecute(farm);
            mSelectedFarm = farm;
            ImageView photo = (ImageView) view.findViewById(R.id.farm_photo_fragment);
            TextView story = (TextView) view.findViewById(R.id.farm_story);
            TextView state = (TextView) view.findViewById(R.id.farm_state);
            TextView specialty = (TextView) view.findViewById(R.id.farm_specialty);
            RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.info_fragment_layout);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                layout.setPadding(150, 0, 150, 0);
            }

        /*
        States in the database are all lowercase to make searching easier this line
        of code capitalizes the first letter of each word
        */

            String[] statePieces = mSelectedFarm.getState().split(" ");
            String upperCaseState;

            statePieces[0] = Character.toString(statePieces[0].charAt(0)).toUpperCase() +
                    statePieces[0].substring(1);
            if (statePieces.length > 1) {
                statePieces[1] = " " + Character.toString(statePieces[1].charAt(0)).toUpperCase() +
                        statePieces[1].substring(1);
                upperCaseState = statePieces[0] + statePieces[1];
            } else {
                upperCaseState = statePieces[0];
            }

            photo.setImageResource(mSelectedFarm.getPhotoID());
            story.setText(mSelectedFarm.getStory());
            state.setText("State: " + upperCaseState);
            specialty.setText("Specialty: " + mSelectedFarm.getSpecialty());
        }
    }
}
