package com.sleepfuriously.digitalturbinechallenge.model;

import com.sleepfuriously.digitalturbinechallenge.model.dtXmlData.DTXmlDataRoot;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Provides retrofit with a way to contact the DT's server.
 */
public interface DTXmlServerInterface {

    /**
     * Access point for retrofit to get xml data from their server.
     *
     * @return  A callback
     */
    @GET(UrlConstants.FIRST_WORKING_URL)
    Call<DTXmlDataRoot> accessDTXmlServer();


}
