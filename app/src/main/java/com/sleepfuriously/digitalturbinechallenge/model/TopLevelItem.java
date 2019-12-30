package com.sleepfuriously.digitalturbinechallenge.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Describes an item that occurs on the top level for this project.
 */
public class TopLevelItem {

    private static final String TAG = TopLevelItem.class.getSimpleName();

    public String str;

    public TopLevelItem (String xmlStr) {
        str = xmlStr;
    }

    public TopLevelItem (JSONObject jsonObject) {
        str = jsonObject.toString();

        Log.d(TAG, "constructing: " + str);
//        try {
//        }
//        catch (JSONException e) {
//            e.printStackTrace();
//        }

    }
}
