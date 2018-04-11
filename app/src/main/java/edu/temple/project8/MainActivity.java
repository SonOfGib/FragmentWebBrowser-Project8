package edu.temple.project8;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements WebViewFragment.OnTabSwitchListener {

    private WebViewFragmentPager mAdapter;
    private ViewPager mPager;
    private TextView urlBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urlBar = this.findViewById(R.id.url_bar);
        Button goButton = this.findViewById(R.id.go_button);
        mPager = this.findViewById(R.id.webView_pager);
        mAdapter = new WebViewFragmentPager(this.getSupportFragmentManager(), this);
        final WebViewFragment.OnTabSwitchListener context = this;
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageScrollStateChanged(int state) {}
            @Override
            public void onPageSelected(int position) {
                //ensures that when we swipe we don't create a new webview.
                WebViewFragment f = (WebViewFragment) mAdapter.instantiateItem(mPager, mPager.getCurrentItem());
                context.onTabSwitch(f.getCurrentUrl());
            }


        });
        mPager.setAdapter(mAdapter);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewFragment fragment = (WebViewFragment) mPager.getAdapter().instantiateItem(
                        mPager, mPager.getCurrentItem());
                fragment.changeWebsite(urlBar.getText().toString());


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.web_browser_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int current = mPager.getCurrentItem();
        WebViewFragmentPager adapter = (WebViewFragmentPager) mPager.getAdapter();
        switch (item.getItemId()) {
            case R.id.back_arrow:
                // User chose the back button.
                if(current -1 > -1) {
                    mPager.setCurrentItem(current - 1);
                }
                return true;

            case R.id.new_page:
                // User chose the new page button.
                adapter.addNewPage();
                mPager.setCurrentItem(adapter.getCount() -1);

                return true;
            case R.id.forward_arrow:
                //User chose the forward button.
                if(current + 1 < adapter.getCount()) {
                    mPager.setCurrentItem(current + 1);
                }
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onTabSwitch(String url) {
        urlBar.setText(url);

    }
}
