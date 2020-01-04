package com.sleepfuriously.digitalturbinechallenge.presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.sleepfuriously.digitalturbinechallenge.model.dtXmlData.DTXmlDataRoot;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

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

    /** actual base url! */
    private static final String DT_BASE_URL = "http://ads.appia.com/";

    private static final String DT_ORIG_DATA_URL =
            "http://ads.appia.com/getAds?id=236&password=OVUJ1DJN&siteId=10777&deviceId=4230&sessionId=techtestsession&totalCampaignsRequested=10";

    /** defaults for the endpoint queries */
    @SuppressWarnings("unused")
    private static final String
        DEFAULT_ID = "236",
        DEFAULT_PASSWORD = "OVUJ1DJN",
        DEFAULT_SITE_ID = "10777",
        DEFAULT_DEVICE_ID = "4230",
        DEFAULT_SESSIONO_ID = "techtestsession",
        DEFAULT_ADS_REQUESTED = "10";


    //------------------------
    //  data
    //------------------------

    private static ModelWindow mInstance = null;

    /** interface handler to access digital turbine's server */
    private DTXmlClientInterface mRetrofitHandler;


    //------------------------
    //  methods
    //------------------------

    /**
     * Constructor -- private ala the singleton pattern.
     */
    private ModelWindow() {
        // setup retrofit
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(DT_BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        mRetrofitHandler =
                mRetrofit.create(DTXmlClientInterface.class);
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
     * This version of requestXmlData() is primarily for testing.
     *
     * @param listener  The instance that implements
     */
    public void requestXmlData(final ModelWindowXMLDataListener listener) {

        Call<DTXmlDataRoot> call = mRetrofitHandler.accessDTXmlServerForRoot();

        call.enqueue(new Callback<DTXmlDataRoot>() {
            @Override
            public void onResponse(@NonNull Call<DTXmlDataRoot> call,
                                   @NonNull Response<DTXmlDataRoot> response) {
                Log.d(TAG, "really? it worked?");
                DTXmlDataRoot rootData = response.body();
                listener.returnXMLData(rootData, true, null);
            }

            @Override
            public void onFailure(@NonNull Call<DTXmlDataRoot> call,
                                  @NonNull Throwable t) {
                Log.e(TAG, "failure acquiring xml data");
                t.printStackTrace();
                listener.returnXMLData(null, true, t.getMessage());
            }
        });
    }

    /**
     * Like the above, this requests xml data from the DT server.  But this
     * version is much more specific on which and how many ads to load.
     *
     * Since I don't have API docs, I don't know how to start at any place
     * other than the beginning, making this VERY inefficient.
     *
     * @param listener  An instance of the listener. It'll receive the callback
     *                  once the data arrives.
     *
     * @param lastName  Required by specs
     *
     * @param quantity  How many ads should this try to fetch at a time.
     */
    public void requestXmlData(final ModelWindowXMLDataListener listener,
                               String lastName,
                               int quantity) {

        Call<DTXmlDataRoot> call = mRetrofitHandler.accessRootData2(
                DEFAULT_ID,
                DEFAULT_PASSWORD,
                DEFAULT_SITE_ID,
                DEFAULT_DEVICE_ID,
                DEFAULT_SESSIONO_ID,
                lastName,
                Integer.toString(quantity));

        call.enqueue(new Callback<DTXmlDataRoot>() {
            @Override
            public void onResponse(@NonNull Call<DTXmlDataRoot> call,
                                   @NonNull Response<DTXmlDataRoot> response) {
                Log.d(TAG, "really? it worked?");
                DTXmlDataRoot rootData = response.body();
                listener.returnXMLData(rootData, true, null);
            }

            @Override
            public void onFailure(@NonNull Call<DTXmlDataRoot> call,
                                  @NonNull Throwable t) {
                Log.e(TAG, "failure acquiring xml data");
                t.printStackTrace();
                listener.returnXMLData(null, true, t.getMessage());
            }
        });

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


    public interface DTXmlClientInterface {

        @GET(DT_ORIG_DATA_URL)
        Call<DTXmlDataRoot> accessDTXmlServerForRoot();

//        @GET("http://ads.appia.com/getAds?id=236&password=OVUJ1DJN&siteId=10777&deviceId=4230&sessionId=techtestsession&totalCampaignsRequested=10")
//        Call<DTXmlDataRoot> test(); // works
//
//        @GET("http://ads.appia.com/getAds?{id}&password=OVUJ1DJN&siteId=10777&deviceId=4230&sessionId=techtestsession&totalCampaignsRequested=10")
//        Call<DTXmlDataRoot> test2(@Query("id") String id);  // doesn't work

        /**
         * Provides a way to change the parameters of the GET request.
         *
         * @param lastName          My last name. Required spec.
         *
         * @param adsRequestedStr   Will return this many ads or fewer if
         *                          no more are available.
         */
        @GET("getAds")
        Call<DTXmlDataRoot> accessRootData2(
                @Query("id") String idStr,
                @Query("password") String password,
                @Query("siteId") String siteId,
                @Query("deviceId") String deviceId,
                @Query("sessionId") String sessionId,
                @Query("lname") String lastName,
                @Query("totalCampaignsRequested") String adsRequestedStr);
    }

}
