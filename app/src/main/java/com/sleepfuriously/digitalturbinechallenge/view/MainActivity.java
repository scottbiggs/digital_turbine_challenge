package com.sleepfuriously.digitalturbinechallenge.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sleepfuriously.digitalturbinechallenge.R;
import com.sleepfuriously.digitalturbinechallenge.model.DummyContent;
import com.sleepfuriously.digitalturbinechallenge.model.TopLevelItem;
import com.sleepfuriously.digitalturbinechallenge.presenter.ModelWindow;

import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MainActivity extends AppCompatActivity
        implements ModelWindow.ModelWindowTopLevelListener {

    //----------------------
    //  constants
    //----------------------

    private static final String TAG = MainActivity.class.getSimpleName();

    //----------------------
    //  widgets
    //----------------------

    RecyclerView mMainRecyclerView;

    /** Gives user something to look at while waiting for data access */
    ProgressDialog mProgressDialog;


    //----------------------
    //  data
    //----------------------

    SimpleItemRecyclerViewAdapter mRecyclerAdapter;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    //----------------------
    //  methods
    //----------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        // First things first, gotta be on the internet!
        if (!isInternetAvailable()) {
            Toast.makeText(this, R.string.no_internet_warning, Toast.LENGTH_LONG).show();
            finish();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        mMainRecyclerView = findViewById(R.id.item_list);
        assert mMainRecyclerView != null;

//        mRecyclerAdapter = new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane);
//        mRecyclerAdapter = new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane);
//        mMainRecyclerView.setAdapter(mRecyclerAdapter);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setTitle(R.string.initial_load_msg);
        mProgressDialog.show();

        ModelWindow mw = ModelWindow.getInstance();
        mw.requestTopLevelList(this, this);
    }


    /**
     * Goes through the ConnectivityManager to determine if this
     * app has access to the internet currently.
     */
    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null)
                && activeNetworkInfo.isAvailable()
                && activeNetworkInfo.isConnected();
    }

    /**
     * Called when the top-level list is ready.
     *
     * @param topLevelList  A list of TopLevelItems populated with the results
     *                      from the server call.
     *
     * @param successful    True means that the call was successful.
     *                      False indicates an error.
     *
     * @param errMsg        Error message. Only used if successful == false
     */
    @Override
    public void returnTopLevelList(List<TopLevelItem> topLevelList, boolean successful, String errMsg) {

        if (!successful) {
            Log.e(TAG, "returnTopLevelList() is unsuccessful. Msg = " + errMsg);
            Toast.makeText(this, R.string.no_internet_warning, Toast.LENGTH_LONG).show();
            finish();
            return; // might be redundant?
        }

        mRecyclerAdapter = new SimpleItemRecyclerViewAdapter(this, topLevelList, mTwoPane);
        mMainRecyclerView.setAdapter(mRecyclerAdapter);

        mProgressDialog.dismiss();

    }


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //  classes
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final MainActivity mParentActivity;
        private final List<DummyContent.DummyItem> mValues = null;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        SimpleItemRecyclerViewAdapter(MainActivity parent,
                                      List<TopLevelItem> items,
//                                      List<DummyContent.DummyItem> items,
                                      boolean twoPane) {
            // todo: fill in data
//            mValues = items;

            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(mValues.get(position).content);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = view.findViewById(R.id.id_text);
                mContentView = view.findViewById(R.id.content);
            }
        }
    }
}
