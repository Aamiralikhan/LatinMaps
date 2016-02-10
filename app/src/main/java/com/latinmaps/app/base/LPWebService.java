package com.latinmaps.app.base;

import android.content.Context;

import com.crystal.androidtoolkit.network.CrystalAsyncHttpClient;
import com.crystal.androidtoolkit.network.CrystalHttpResponseHandler;
import com.latinmaps.app.interfaces.IWSResponse;
import com.latinmaps.app.ui.dialogs.ProcessProgressDialog;
import com.latinmaps.app.utilities.Api;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Administrator on 12/23/2015.
 */
public abstract class LPWebService<T extends LPWebService<T>>{

    //////////////////////////////////////////
    // PRIVATE VAR
    //////////////////////////////////////////

    protected RequestParams params;
    protected CrystalAsyncHttpClient request;
    private ProcessProgressDialog processProgressDialog;
    private final Context context;

    //////////////////////////////////////////
    // CONSTRUCTOR
    //////////////////////////////////////////

    public LPWebService(Context context){
        this.context = context;
        processProgressDialog = new ProcessProgressDialog(this.context, android.R.style.Theme_Light);
    }

    //////////////////////////////////////////
    // PUBLIC FUNCTIONS
    //////////////////////////////////////////

    public final Context getContext(){
        return this.context;
    }

    public T setParameter(RequestParams parameter){
        this.params = parameter;
        return getThis();
    }

    public boolean dataIsJSONObject(){
        return true;
    }
    public boolean autoDismissProgressDialog(){
        return true;
    }

    public void dismissProcessProgressDialog(){
        if(processProgressDialog != null){
            if(processProgressDialog.isShowing()){
                processProgressDialog.dismiss();
            }
        }
    }

    public void execute(){
        execute(new IWSResponse() {
            @Override
            public void onData(JSONObject data) {

            }

            @Override
            public void noData(String message) {

            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void execute(final IWSResponse iwsResponse){
        if(params == null) params = new RequestParams();

        request = new CrystalAsyncHttpClient(getContext());
        request.setBasicAuth("AC05e81ad897c7d80e8df41caf4202ffab","80855dec4f4279c9aab2187cd2c03a9a");
        request.setProgressMessage(getProgressMessage());

        if(isProcessProgressDialog() && isShowProgressDialog()){
            processProgressDialog.show();
        }
        else if(isShowProgressDialog()){
            request.showProgressBar();
        }

        request.post(getApiUrl(), params, new CrystalHttpResponseHandler() {

            @Override
            public void onResponse(int arg0, Header[] arg1, String response) {
                super.onResponse(arg0, arg1, response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString(Api.Status.STATUS).equals("SUCCESS") || jsonObject.getString(Api.Status.STATUS).equals("success") ){
                        try{
                            if(dataIsJSONObject()){
                                iwsResponse.onData(jsonObject.getJSONObject("data"));
                            }
                            else{
                                JSONObject dataWrapper = new JSONObject();
                                dataWrapper.put("data", jsonObject.getJSONArray("data"));
                                iwsResponse.onData(dataWrapper);
                            }
                        }catch (JSONException e){
                            iwsResponse.onData(new JSONObject());
                        }
                    }
                    else{
                        iwsResponse.noData(jsonObject.getString(Api.Status.MESSAGE));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    iwsResponse.onError(e.getMessage());
                }

                if(isProcessProgressDialog() && isShowProgressDialog()){
                    if(processProgressDialog.isShowing()){
                        if(autoDismissProgressDialog()){
                            processProgressDialog.dismiss();
                        }
                    }
                }
                else if(isShowProgressDialog()){
                    request.dismissProgress();
                }
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable throwable) {
                super.onFailure(arg0, arg1, arg2, throwable);
                iwsResponse.onError((throwable.getMessage() == null) ? "Error contacting to server." : throwable.getMessage());
                if(isProcessProgressDialog() && isShowProgressDialog()){
                    if(processProgressDialog.isShowing()){
                        if(autoDismissProgressDialog()){
                            processProgressDialog.dismiss();
                        }
                    }
                }
                else if(isShowProgressDialog()){
                    request.dismissProgress();
                }
            }
        });
    }

    public abstract T getThis();
    public abstract String getApiUrl();
    public abstract String getProgressMessage();
    public abstract boolean isShowProgressDialog();
    public abstract boolean isProcessProgressDialog();
}
