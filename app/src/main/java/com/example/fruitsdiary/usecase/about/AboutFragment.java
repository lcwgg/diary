package com.example.fruitsdiary.usecase.about;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.fruitsdiary.usecase.home.HomeAbstractFragment;
import com.example.fruitsdiary.R;
import com.example.fruitsdiary.databinding.FragmentAboutBinding;

/**
 * A placeholder fragment containing a simple view.
 */
public class AboutFragment extends HomeAbstractFragment {

    public static final int FRAGMENT_POSITION = 1;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String FRAGMENT_NAME = "About";

    private WebView mWebView;

    public AboutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAboutBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false);
        View rootView = binding.getRoot();
        mWebView = binding.aboutWebview;
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.getSettings().setJavaScriptEnabled(false);
        mWebView.loadUrl("https://www.themobilelife.com/about/");
    }

    @Override
    public void setFabAction(FloatingActionButton fab) {
        fab.setImageResource(android.R.drawable.ic_dialog_email);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_EMAIL, getString(R.string.about_email));
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }


}
