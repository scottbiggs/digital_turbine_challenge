package com.sleepfuriously.digitalturbinechallenge.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sleepfuriously.digitalturbinechallenge.R;

/**
 * VH for the items in the Main List's recyclerview.
 *
 * Needs to hold:
 *      container (for button clicks)
 *      name
 *      rating
 *      thumbnail
 */
public class MainListViewHolder extends RecyclerView.ViewHolder {

    final RelativeLayout mContainer;
    final TextView mName;
    final TextView mRating;
    final ImageView mThumb;

    MainListViewHolder(View view) {
        super(view);
        mContainer = view.findViewById(R.id.item_container);
        mName = view.findViewById(R.id.name_tv);
        mRating = view.findViewById(R.id.rating_tv);
        mThumb = view.findViewById(R.id.thumb_iv);
    }

}
