package com.sleepfuriously.digitalturbinechallenge.presenter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.sleepfuriously.digitalturbinechallenge.model.DetailItem;
import com.sleepfuriously.digitalturbinechallenge.model.TopLevelItem;
import com.sleepfuriously.digitalturbinechallenge.model.UrlConstants;
import com.sleepfuriously.digitalturbinechallenge.model.dtXmlData.DTXmlDataRoot;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;

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

    /** needed by retrofit */
    public static final String NOT_USED_BASE_URL = "http://something.that.will.be.overriden.com/";

    public static final String DT_DATA_URL =
            "http://ads.appia.com/getAds?id=236&password=OVUJ1DJN&siteId=10777&deviceId=4230&sessionId=techtestsession&totalCampaignsRequested=10";


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
     * Starts the process of getting the XML data from the Digital Turbine
     * server.  The data will be returned to the supplied listener.
     *
     * @param listener  The instance that implements
     * @param ctx
     */
    public void requestXmlData(final ModelWindowXMLDataListener listener, Context ctx) {

        // setup retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NOT_USED_BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        DTXmlServerInterface retrofitHandler =
                retrofit.create(DTXmlServerInterface.class);

        Call<DTXmlDataRoot> call = retrofitHandler.accessDTXmlServerForRoot();
        call.enqueue(new Callback<DTXmlDataRoot>() {
            @Override
            public void onResponse(Call<DTXmlDataRoot> call, Response<DTXmlDataRoot> response) {
                Log.d(TAG, "really? it worked?");
                DTXmlDataRoot rootData = response.body();
                listener.returnXMLData(rootData, true, null);
            }

            @Override
            public void onFailure(Call<DTXmlDataRoot> call, Throwable t) {
                Log.e(TAG, "failure acquiring xml data");
                t.printStackTrace();
                listener.returnXMLData(null, true, t.getMessage());
            }
        });

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

//
//        RequestQueue q = Volley.newRequestQueue(ctx);
//
//        StringRequest request = new StringRequest(Request.Method.GET, UrlConstants.FIRST_WORKING_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d(TAG, "onResponse() successful");
//
//                    }
//                },
//
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // todo: handle error
//                        Log.e(TAG, "onResponse() ERROR!!!");
//                    }
//                });
//
////            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, UrlConstants.FIRST_WORKING_URL, null,
////                new Response.Listener<JSONArray>() {
////                    @Override
////                    public void onResponse(JSONArray response) {
////                        List<TopLevelItem> topLevelItems = new ArrayList<>();
////                        for (int i = 0; i < response.length(); i++) {
////                            try {
////                                JSONObject jsonObject = (JSONObject) response.get(i);
////                                topLevelItems.add(new TopLevelItem(jsonObject));
////                            } catch (JSONException e) {
////                                e.printStackTrace();
////                            }
////                        }
////                        listener.returnTopLevelList(topLevelItems, true, null);
////                    }
////                },
////                new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////                        listener.returnTopLevelList(null, false, error.getMessage());
////                    }
////                });
//
//        q.add(request);
//
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //  interfaces & classes
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Implement this interface to receive a callback when the
     * xml data is retrieved from server.
     */
    public interface ModelWindowXMLDataListener {

        /**
         * Called when the xml data is ready.
         *
         * @param addData       An instance of the Ads class populated with the results
         *                      from the server call.
         *
         * @param success       True means that the call was successful.
         *                      False indicates an error.
         *
         * @param errMsg        Error message. Only used if successful == false
         */
        void returnXMLData(DTXmlDataRoot addData, boolean success, String errMsg);
    }


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


    public interface DTXmlServerInterface {

        @GET(DT_DATA_URL)
        Call<DTXmlDataRoot> accessDTXmlServerForRoot();


    }
}
