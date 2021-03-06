package com.sleepfuriously.digitalturbinechallenge.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sleepfuriously.digitalturbinechallenge.R;
import com.sleepfuriously.digitalturbinechallenge.model.dtXmlData.DTXmlDataAd;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter for the recyclerview of the main list
 */
public class MainListRecyclerViewAdapter
                extends RecyclerView.Adapter<MainListViewHolder> {

    //---------------------------
    //  constants
    //---------------------------

    private final static String TAG = MainListRecyclerViewAdapter.class.getSimpleName();

    //---------------------------
    //  data
    //---------------------------

    /** primary data holder */
    private List<DTXmlDataAd> mValues;

    private final MainActivity mParentActivity;

    private final boolean mTwoPane;

    //---------------------------
    //  methods
    //---------------------------


    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DTXmlDataAd data = (DTXmlDataAd) view.getTag();  // returns the DTXmlAdData object

            if (mTwoPane) {
                // just start a fragment
                Bundle bundle = new Bundle();
                bundle.putSerializable(ItemDetailFragment.DATA_ITEM_KEY, data);

                ItemDetailFragment frag = new ItemDetailFragment();
                frag.setArguments(bundle);

                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, frag)
                        .commit();
            }
            else {
                // start activity (it'll start the fragment)
                Context context = view.getContext();
                Intent intent = new Intent(context, ItemDetailActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable(ItemDetailFragment.DATA_ITEM_KEY, data);
                intent.putExtras(bundle);

                context.startActivity(intent);

            }

        }
    };


    MainListRecyclerViewAdapter(MainActivity parent,
                                List<DTXmlDataAd> items,
                                 boolean twoPane) {
        mValues = items;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    @NonNull
    @Override
    public MainListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_list_data_item, parent, false);
        return new MainListViewHolder(view);
    }


    @SuppressLint("SetTextI18n")    // removes warning for number locality issues
    @Override
    public void onBindViewHolder(final MainListViewHolder holder, int position) {
        holder.mName.setText(mValues.get(position).productName);
        holder.mRating.setText(Float.toString(mValues.get(position).rating));

        Context ctx = holder.mThumb.getContext();
        DTXmlDataAd data = mValues.get(position);

        Picasso.get().load(data.productThumbnail)
                .into(holder.mThumb);

        // put the entire data into the tag
        holder.itemView.setTag(data);
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


}
