package edu.temple.project8;

import android.annotation.SuppressLint;
import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import java.net.MalformedURLException;
import java.net.URL;

/**
 * Activities that contain this fragment must implement the
 * {@link WebViewFragment.OnTabSwitchListener} interface
 * to handle interaction events.
 */
public class WebViewFragment extends Fragment {

    private OnTabSwitchListener mListener;
    private String currentUrl = "";
    public static String CURRENT_URL = "current_url";
    private View rootView;
    public WebViewFragment() {
        // Required empty public constructor
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       rootView = inflater.inflate(R.layout.fragment_webview, container, false);
       WebView webView = (WebView) rootView.findViewById(R.id.webBrowser_view);
       WebSettings webSettings = webView.getSettings();
       webSettings.setJavaScriptEnabled(true);
       if(currentUrl.equals("") && savedInstanceState != null)
            currentUrl = savedInstanceState.getString(CURRENT_URL);
       webView.setWebViewClient(new WebViewClient() {
           @Override
           public void onPageFinished(WebView view, String url){
               mListener.onTabSwitch(url);
               currentUrl = view.getUrl();
           }
       });
       webView.loadUrl(currentUrl);
       mListener.onTabSwitch(currentUrl);
        return rootView;
    }

    /**
     * Method that changes the url loaded by the webview to be the url passed to it.
     * @param website The url that we should change to.
     */
    public void changeWebsite(String website){
        website = parseURL(website);
        WebView webView = rootView.findViewById(R.id.webBrowser_view);
        if(website !=null) {
            webView.loadUrl(website);
        }
        else{
            Toast.makeText(getContext(), "Invalid URL, page not loaded.", Toast.LENGTH_LONG).show();
        }
    }

    private String parseURL(String website){
        if (!website.startsWith("http://") && !website.startsWith("https://")) {
            Log.e("websiteerror",website);
            website = "http://" + website;
        }
        URL url =null;
        try {
            url = new URL(website);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        return url.toString();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("debugUrl","saving state: " + currentUrl);
        outState.putString(CURRENT_URL, currentUrl);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTabSwitchListener) {
            mListener = (OnTabSwitchListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTabSwitchListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public String getCurrentUrl(){
        return currentUrl;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     */
    public interface OnTabSwitchListener {
        void onTabSwitch(String url);
    }
}
