package com.sleepfuriously.digitalturbinechallenge.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.widget.Toast;

import com.sleepfuriously.digitalturbinechallenge.R;
import com.sleepfuriously.digitalturbinechallenge.model.dtXmlData.DTXmlDataAd;
import com.sleepfuriously.digitalturbinechallenge.model.dtXmlData.DTXmlDataRoot;
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
        implements ModelWindow.ModelWindowXMLDataListener {

    //----------------------
    //  constants
    //----------------------

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String MY_LAST_NAME = "biggs";

    //----------------------
    //  widgets
    //----------------------

    RecyclerView mMainRecyclerView;

    /** Gives user something to look at while waiting for data access */
    ProgressDialog mProgressDialog;


    //----------------------
    //  data
    //----------------------

    MainListRecyclerViewAdapter mRecyclerAdapter;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    /** lock to prevent from loading data while loading data */
    private boolean mLoadingData = false;

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

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        mMainRecyclerView = findViewById(R.id.top_list_rv);
        assert mMainRecyclerView != null;

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setTitle(R.string.initial_load_msg);
        mProgressDialog.show();

        ModelWindow mw = ModelWindow.getInstance();
        mw.requestXmlData(this, this, MY_LAST_NAME, 25);
        mLoadingData = true;

        initScrollListener();
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
     * Called when the xml data is ready.
     *
     * @param addData       A list of TopLevelItems populated with the results
     *                      from the server call.
     *
     * @param success       True means that the call was successful.
     *                      False indicates an error.
     *
     * @param errMsg        Error message. Only used if successful == false
     */
    @Override
    public void returnXMLData(DTXmlDataRoot addData, boolean success, String errMsg) {

        mLoadingData = false;
        mProgressDialog.dismiss();

        if (!success) {
            Log.e(TAG, "returnXMLData() is unsuccessful. Msg = " + errMsg);
            Toast.makeText(this, R.string.no_internet_warning, Toast.LENGTH_LONG).show();
            finish();
            return; // nec because finish() is not guaranteed to be immediate
        }

        Log.d(TAG, "success");

        // get the list of items from our data
        List<DTXmlDataAd> dataList = addData.adList;

        mRecyclerAdapter = new MainListRecyclerViewAdapter(this, dataList, mTwoPane);
        mMainRecyclerView.setAdapter(mRecyclerAdapter);

    }


    /**
     * The Scroll Listener is used to keep track of when the user reaches the bottom of the
     * main list.  When they do, load some more data.
     */
    private void initScrollListener() {
        mMainRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (mLoadingData) {
                    return; // already loading data, don't bother
                }

                LinearLayoutManager mgr = (LinearLayoutManager) mMainRecyclerView.getLayoutManager();

                // check to see if the user has scrolled to the bottom of the list
                if (mgr != null) {
                    int lastPos = mgr.findLastCompletelyVisibleItemPosition();
                    int count = mRecyclerAdapter.getItemCount();
                    if (lastPos == mRecyclerAdapter.getItemCount() - 1) {
                        Log.d(TAG, "loading more data! lastPos = " + lastPos + ", count = " + count);
//                        loadMoreData();
                    }
                }
            }
        });
    }


    private void loadMoreData() {

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setTitle(R.string.initial_load_msg);
        mProgressDialog.show();

        ModelWindow mw = ModelWindow.getInstance();
        mw.requestXmlData(this, this, MY_LAST_NAME, 25);
        mLoadingData = true;
    }


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //  classes
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


}
