package com.sleepfuriously.digitalturbinechallenge.view;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sleepfuriously.digitalturbinechallenge.R;
import com.sleepfuriously.digitalturbinechallenge.model.DummyContent;
import com.sleepfuriously.digitalturbinechallenge.model.dtXmlData.DTXmlDataAd;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link MainActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 *
 * NOTE: when instantiating this fragment the caller (which will
 * always be the MainActivity) must specify which data item this
 * Fragment must display.
 */
public class ItemDetailFragment extends Fragment {

    //-------------------------------
    //  constants
    //-------------------------------

    /**
     * The key that tells this fragment which Ad data to get
     * from MainActivity.
     */
    public static final String DATA_ITEM_KEY = "item_id";


    //-------------------------------
    //  data
    //-------------------------------

    /** Primary data for this Fragment */
    private DTXmlDataAd mAdData;


    /** When TRUE, this is in a 2-pane environment */
    private boolean mTwoPane;

    //-------------------------------
    //  methods
    //-------------------------------

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(DATA_ITEM_KEY)) {
            Toast.makeText(getContext(), "Error creating Fragment--no data arguments, aborting!",
                    Toast.LENGTH_LONG).show();
            return;
        }

        Bundle bundle = getArguments();
        mAdData = (DTXmlDataAd) bundle.getSerializable(DATA_ITEM_KEY);

//        int adPosition = getArguments().getInt(DATA_ITEM_KEY);
        Activity activity = this.getActivity();
//        mAdData = activity.getData(adPosition);

        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mAdData.productName);
            mTwoPane = false;
        }
        else {
            mTwoPane = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mAdData != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mAdData.productDescription);
        }

        return rootView;
    }
}
