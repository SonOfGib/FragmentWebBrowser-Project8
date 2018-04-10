package edu.temple.project8;

import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements WebViewFragment.OnTabSwitchListener {

    private WebViewFragmentPager mAdapter;
    private TextView urlBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlBar = this.findViewById(R.id.url_bar);
        setContentView(R.layout.activity_main);
        ViewPager pager = this.findViewById(R.id.webView_pager);
        mAdapter = new WebViewFragmentPager(this.getSupportFragmentManager(), this)
        pager.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.web_browser_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back_arrow:
                // User chose the back button.

                return true;

            case R.id.new_page:
                // User chose the new page button.
                mAdapter.addNewPage(urlBar.getText().toString());
                return true;
            case R.id.forward_arrow:
                //User chose the forward button.

                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onTabSwitch(URL url) {
        urlBar.setText(url.toString());

    }
}
