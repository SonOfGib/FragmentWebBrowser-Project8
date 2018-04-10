package edu.temple.project8;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Activities that contain this fragment must implement the
 * {@link WebViewFragment.OnTabSwitchListener} interface
 * to handle interaction events.
 */
public class WebViewFragment extends Fragment {

    private OnTabSwitchListener mListener;
    private String currentUrl = null;
    public static String CURRENT_URL = "current_url";
    public WebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.fragment_webview, container, false);
       WebView webView = (WebView) rootView.findViewById(R.id.webBrowser_view);
       if(currentUrl == null) {
           currentUrl = (String) getArguments().get(CURRENT_URL);
           if(currentUrl == null) {
               Log.e("WebViewFragment","both current url and arguments were null ...");
               return rootView;
           }
       }
       webView.loadUrl(currentUrl);
       currentUrl = webView.getUrl();
        try {
            mListener.onTabSwitch(new URL(currentUrl));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return rootView;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     */
    public interface OnTabSwitchListener {
        void onTabSwitch(URL url);
    }
}
