package edu.temple.project8;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * Created by Sean Gibson on 4/9/2018.
 */

public class WebViewFragmentPager extends FragmentStatePagerAdapter {


    private Context context;
    private int numItems = 1;
    WebViewFragmentPager(FragmentManager fm, Context context) {
        super(fm);

        this.context = context;
    }

    /**
     * This method is called when the user request a new tab. It calls getItem to create
     * a new item and place it beyond the last index.
     */
    public void addNewPage() {
        getItem(numItems);
        numItems++;
        notifyDataSetChanged();

    }

    @Override
    public Fragment getItem(int position) {
        Log.d("debugUrl",""+position);
        WebViewFragment fragment = new WebViewFragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return numItems;
    }
}
