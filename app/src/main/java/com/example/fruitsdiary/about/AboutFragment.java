package com.example.fruitsdiary.about;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fruitsdiary.FruitsDiaryAbstractFragment;
import com.example.fruitsdiary.R;
import com.example.fruitsdiary.databinding.FragmentAboutBinding;

/**
 * A placeholder fragment containing a simple view.
 */
public class AboutFragment extends FruitsDiaryAbstractFragment {

    public static final int FRAGMENT_POSITION = 1;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String FRAGMENT_NAME = "About";

    public AboutFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AboutFragment newInstance(int sectionNumber) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAboutBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false);
        View rootView = binding.getRoot();
        TextView textView = binding.sectionLabel;
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }

    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }
}
