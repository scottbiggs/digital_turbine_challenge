package com.sleepfuriously.digitalturbinechallenge.presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.sleepfuriously.digitalturbinechallenge.model.DetailItem;
import com.sleepfuriously.digitalturbinechallenge.model.TopLevelItem;

import java.util.List;

/**
 * All access to data comes through this class (using the
 * Singleton Pattern).
 *
 * The network and caching is done here.
 */
public class ModelWindow {

    //------------------------
    //  constants
    //------------------------

    private static final String TAG = ModelWindow.class.getSimpleName();


    //------------------------
    //  data
    //------------------------

    private static ModelWindow mInstance = null;


    //------------------------
    //  methods
    //------------------------

    private ModelWindow() {
    }

    /**
     * As per the Singleton Pattern, return an instance of this
     * class.
     */
    public static synchronized ModelWindow getInstance() {
        if (mInstance == null) {
            mInstance = new ModelWindow();
        }
        return  mInstance;
    }


    /**
     * Starts the process of getting a list of all the items at the top-level.
     * That data will be returned the the supplied listener (see below).
     *
     * @param listener  The instance that implements {@link ModelWindowTopLevelListener}.
     *                  Its {@link ModelWindowTopLevelListener#returnTopLevelList(List, boolean, String)}
     *                  method will be called when the value has been retrieved.
     *
     * @param ctx   Ye good ol' Context.
     */
    public void getTopLevelList(final ModelWindowTopLevelListener listener, Context ctx) {

        RequestQueue q = Volley.newRequestQueue(ctx);

    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //  interfaces & classes
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Implement this interface to receive a callback when the
     * list of top-level items is retrieved from server.
     */
    public interface ModelWindowTopLevelListener {

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
        void returnTopLevelList(List<TopLevelItem> topLevelList, boolean successful, String errMsg);
    }


    /**
     * Implement this interface to receive a callback for details on
     * a top-level item.
     */
    public interface ModelWindowDetailListener {

        /**
         * Called when a detail is ready.
         *
         * @param detail        Will be filled in with details of a top-level item.
         *                      Unchanged if error.
         *
         * @param successful    If false indicates there was an error retrieving data.
         *
         * @param errMsg        Contains an error message if successful == false.
         */
        void returnDetail(@NonNull DetailItem detail, boolean successful, String errMsg);
    }

}
