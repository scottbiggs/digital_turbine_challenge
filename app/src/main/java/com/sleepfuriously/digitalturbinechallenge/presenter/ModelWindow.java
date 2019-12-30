package com.sleepfuriously.digitalturbinechallenge.presenter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sleepfuriously.digitalturbinechallenge.model.DetailItem;
import com.sleepfuriously.digitalturbinechallenge.model.TopLevelItem;
import com.sleepfuriously.digitalturbinechallenge.model.UrlConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
    public void requestTopLevelList(final ModelWindowTopLevelListener listener, Context ctx) {

        RequestQueue q = Volley.newRequestQueue(ctx);

        StringRequest request = new StringRequest(Request.Method.GET, UrlConstants.FIRST_WORKING_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse() successful");

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // todo: handle error
                        Log.e(TAG, "onResponse() ERROR!!!");
                    }
                });

//            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, UrlConstants.FIRST_WORKING_URL, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        List<TopLevelItem> topLevelItems = new ArrayList<>();
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                JSONObject jsonObject = (JSONObject) response.get(i);
//                                topLevelItems.add(new TopLevelItem(jsonObject));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        listener.returnTopLevelList(topLevelItems, true, null);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        listener.returnTopLevelList(null, false, error.getMessage());
//                    }
//                });

        q.add(request);

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
