package com.sleepfuriously.digitalturbinechallenge.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sleepfuriously.digitalturbinechallenge.R;
import com.sleepfuriously.digitalturbinechallenge.model.dtXmlData.DTXmlDataAd;
import com.squareup.picasso.Picasso;


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
    static final String DATA_ITEM_KEY = "item_id";


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

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);

        if (appBarLayout != null) {
            appBarLayout.setTitle(mAdData.productName);
            mTwoPane = false;
        }
        else {
            mTwoPane = true;
        }
    }

    @SuppressLint("SetTextI18n")    // prevents locality number warnings
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.item_detail, container, false);
        Context ctx = rootView.getContext();

        // Show the dummy content as text in a TextView.
        if (mAdData != null) {
            if (mAdData.appId != null)
                ((TextView) rootView.findViewById(R.id.app_id_tv)).setText(mAdData.appId);

            if (mAdData.productName != null)
                ((TextView) rootView.findViewById(R.id.product_name_tv)).setText(mAdData.productName);

            if (mAdData.productId != null)
                ((TextView) rootView.findViewById(R.id.product_id_tv)).setText(Integer.toString(mAdData.productId));

            if (mAdData.productDescription != null)
                ((TextView) rootView.findViewById(R.id.product_desc_tv)).setText(mAdData.productDescription);

            if (mAdData.rating != null)
                ((TextView) rootView.findViewById(R.id.rating_tv)).setText(Float.toString(mAdData.rating));

            if (mAdData.averageRatingImageURL != null) {
                ((TextView) rootView.findViewById(R.id.average_rating_url_tv)).setText(mAdData.averageRatingImageURL);
                ImageView imageView = rootView.findViewById(R.id.average_rating_url_iv);

                // doing the hard way to properly handle errors (yes, this url has out-dated certificate
                Picasso.Builder builder = new Picasso.Builder(ctx);
                builder.listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        exception.printStackTrace();
                        TextView errLabel = rootView.findViewById(R.id.average_rating_err_label_tv);
                        errLabel.setVisibility(View.VISIBLE);

                        TextView errTv = rootView.findViewById(R.id.average_rating_url_err_tv);
                        errTv.setVisibility(View.VISIBLE);
                        errTv.setText(exception.getMessage());
                    }
                });
                builder.build().load(mAdData.averageRatingImageURL).into(imageView);
            }

            if (mAdData.bidRate != null)
                ((TextView) rootView.findViewById(R.id.bid_rate_tv)).setText(Float.toString(mAdData.bidRate));

            if (mAdData.billingTypeId != null)
                ((TextView) rootView.findViewById(R.id.billing_type_id_tv)).setText(Integer.toString(mAdData.billingTypeId));

            if (mAdData.callToAction != null)
                ((TextView) rootView.findViewById(R.id.call_to_action_tv)).setText(mAdData.callToAction);

            if (mAdData.campaignId != null)
                ((TextView) rootView.findViewById(R.id.campaign_id_tv)).setText(Integer.toString(mAdData.campaignId));

            if (mAdData.campaignDisplayOrder != null)
                ((TextView) rootView.findViewById(R.id.campaign_display_order_tv)).setText(Integer.toString(mAdData.campaignDisplayOrder));

            if (mAdData.campaignTypeId != null)
                ((TextView) rootView.findViewById(R.id.campaign_type_id_tv)).setText(Integer.toString(mAdData.campaignTypeId));

            if (mAdData.categoryName != null)
                ((TextView) rootView.findViewById(R.id.category_name_tv)).setText(mAdData.categoryName);

            if (mAdData.clickProxyURL != null)
                ((TextView) rootView.findViewById(R.id.click_proxy_url_tv)).setText(mAdData.clickProxyURL);

            if (mAdData.creativeId != null)
                ((TextView) rootView.findViewById(R.id.creative_id_tv)).setText(Integer.toString(mAdData.creativeId));

            if (mAdData.externalMetadata != null)
                ((TextView) rootView.findViewById(R.id.external_metadata_tv)).setText(mAdData.externalMetadata.toString());

            if (mAdData.homeScreen != null)
                ((TextView) rootView.findViewById(R.id.home_screen_tv)).setText(Boolean.toString(mAdData.homeScreen));

            if (mAdData.impressionTrackingURL != null)
                ((TextView) rootView.findViewById(R.id.impression_tracking_url_tv)).setText(mAdData.impressionTrackingURL);

            if (mAdData.isRandomPick != null)
                ((TextView) rootView.findViewById(R.id.random_pick_tv)).setText(Boolean.toString(mAdData.isRandomPick));

            if (mAdData.numberOfRatings != null)
                ((TextView) rootView.findViewById(R.id.num_ratings_tv)).setText(mAdData.numberOfRatings);

            if (mAdData.minOSVersion != null)
                ((TextView) rootView.findViewById(R.id.min_os_ver_tv)).setText(mAdData.minOSVersion);

            if (mAdData.productThumbnail != null) {
                ((TextView) rootView.findViewById(R.id.product_thumb_tv)).setText(mAdData.productThumbnail);
                ImageView thumbIv = rootView.findViewById(R.id.product_thumb_iv);
                Picasso.with(ctx).load(mAdData.productThumbnail).into(thumbIv);
            }

            if (mAdData.numberOfDownloads != null)
                ((TextView) rootView.findViewById(R.id.num_downloads_tv)).setText(mAdData.numberOfDownloads);

            if (mAdData.tstiEligible != null)
                ((TextView) rootView.findViewById(R.id.tsti_eligible_tv)).setText(Boolean.toString(mAdData.tstiEligible));

            if (mAdData.stiEnabled != null)
                ((TextView) rootView.findViewById(R.id.sti_enabled_tv)).setText(Boolean.toString(mAdData.stiEnabled));

            if (mAdData.postInstallActions != null)
                ((TextView) rootView.findViewById(R.id.post_install_actions_tv)).setText(mAdData.postInstallActions.toString());

            if (mAdData.appPrivacyPolicyUrl != null)
                ((TextView) rootView.findViewById(R.id.privacy_policy_tv)).setText(mAdData.appPrivacyPolicyUrl);
        }

        return rootView;
    }
}
