package com.latinmaps.app.interfaces;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 12/23/2015.
 */
public interface IWSResponse {
    void onData(JSONObject data) throws JSONException;
    void noData(String message);
    void onError(String error);
}
