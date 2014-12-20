package com.example.jean.formuiautomator;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import org.json.JSONObject;


public class MainActivity extends Activity implements DetailFragment.DetailFragmentListener {


    /**
     *  Debug Tag for logging debug output to LogCat
     */
    private final String LOG_TAG = getClass().getSimpleName();

    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransact;
    Fragment mDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDetailFragment = DetailFragment.newInstance("Test Form", AppConstants.TEST_FORM_DATA_JSON);
        fragmentTransact = fragmentManager.beginTransaction();
        if (savedInstanceState == null) {
            fragmentTransact.add(R.id.activity_main_container, mDetailFragment);
            fragmentTransact.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // This hides that Settings tab (a.k.a three dots tab)
        menu.findItem(R.id.action_settings).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendReply(JSONObject formData) {
        Log.v(LOG_TAG, "sendReply() called");
        Log.d(LOG_TAG, "formData = " + formData.toString());
        /**
         *  Send the JSON response wherever you want to
         */
    }

    @Override
    public void onDetailFragmentInteraction(JSONObject formData) {
        Log.v(LOG_TAG, "onDetailFragmentInteraction() called");
        sendReply(formData);

    }

}
