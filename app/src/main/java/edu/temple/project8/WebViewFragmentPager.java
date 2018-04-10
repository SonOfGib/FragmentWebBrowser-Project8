package edu.temple.project8;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * Created by Sean Gibson on 4/9/2018.
 */

public class WebViewFragmentPager extends FragmentStatePagerAdapter {

    private ArrayList<WebViewFragment> fragmentArrayList;
    private Context context;
    private FragmentManager fragmentManager;
    WebViewFragmentPager(FragmentManager fm, Context context) {
        super(fm);
        fragmentArrayList = new ArrayList<WebViewFragment>();
        fragmentManager = fm;
        this.context = context;
    }

    public void addNewPage(String url) {
        //url was passed from the user, it could be malformed.
        url = parseURL(url);
        Bundle args = new Bundle();
        args.putString(WebViewFragment.CURRENT_URL, url);
        WebViewFragment webViewFragment = new WebViewFragment();
        webViewFragment.setArguments(args);
        fragmentArrayList.add(webViewFragment);
    }

    public void changePage(String url){
        url = parseURL(url);
        
    }

    private String parseURL(String website){
        if (!website.startsWith("http://") || !website.startsWith("https://"))
            website = "http://" + website;
        URL url =null;
        try {
            url = new URL(website);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to load url because it was malformed.",Toast.LENGTH_LONG).show();
            return null;
        }
        return url.toString();
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
}
