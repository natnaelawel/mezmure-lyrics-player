package com.example.mediaplayer;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HintShowFragment extends Fragment {

    View rootView;
    WebView webView;
    TextView hintShowTextView;
    public HintShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_hint_show, container, false);
        webView = rootView.findViewById(R.id.help_show);
        hintShowTextView = rootView.findViewById(R.id.hint_show);

        String hint = getArguments().getString("hint","");
        String help = getArguments().getString("help","");
        if (!hint.isEmpty()){
            hintShowTextView.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            hintShowTextView.setText(hint);
        }else if (!help.isEmpty()){
            hintShowTextView.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            webView.loadDataWithBaseURL(null, help, "text/html", "utf-8", null);

        }

       return rootView;
    }

}
