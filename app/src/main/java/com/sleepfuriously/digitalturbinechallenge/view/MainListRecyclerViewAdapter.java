package com.sleepfuriously.digitalturbinechallenge.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sleepfuriously.digitalturbinechallenge.R;
import com.sleepfuriously.digitalturbinechallenge.model.DummyContent;
import com.sleepfuriously.digitalturbinechallenge.model.TopLevelItem;
import com.sleepfuriously.digitalturbinechallenge.model.dtXmlData.DTXmlDataAd;
import com.sleepfuriously.digitalturbinechallenge.model.dtXmlData.DTXmlDataRoot;

import java.util.List;

/**
 * Adapter for the recyclerview of the main list
 */
public class MainListRecyclerViewAdapter
                extends RecyclerView.Adapter<MainListViewHolder> {


    private final MainActivity mParentActivity;
    private List<DTXmlDataAd> mValues = null;
    private final boolean mTwoPane;


    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "click", Toast.LENGTH_SHORT).show();

            DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.id);
                ItemDetailFragment fragment = new ItemDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id);

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
                .inflate(R.layout.item_list_content, parent, false);
        return new MainListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MainListViewHolder holder, int position) {
        holder.mName.setText(mValues.get(position).productName);
        holder.mRating.setText(Float.toString(mValues.get(position).rating));

        // todo: make sure this works
        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


}
