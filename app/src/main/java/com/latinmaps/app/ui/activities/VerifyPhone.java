package com.latinmaps.app.ui.activities;

import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.latinmaps.app.R;
import com.latinmaps.app.adapters.CountryListAdapter;
import com.latinmaps.app.base.LatinMaps;
import com.latinmaps.app.helpers.Help;
import com.latinmaps.app.models.CountryListModel;
import com.latinmaps.app.ui.dialogs.PhoneVerificationDialog;
import com.latinmaps.app.utilities.Api;
import com.latinmaps.app.utilities.CountryData;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 1/21/2016.
 */
public class VerifyPhone extends LatinMaps {

    CountryListAdapter countryListAdapter;
    MaterialEditText countryCode;
    MaterialEditText phoneNumber;
    CountryListModel countryListModel;
    PhoneVerificationDialog phoneVerificationDialog;

    @Override
    public int getLayout(int id) {
        return R.layout.activity_verify_phone;
    }

    @Override
    public void init() {
        super.init();

        countryCode = (MaterialEditText) findViewById(R.id.VP_countryCode);
        phoneNumber = (MaterialEditText) findViewById(R.id.VP_number);

        attachClickListener(R.id.VP_confirmButton);
        phoneVerificationDialog = new PhoneVerificationDialog(this,android.R.style.Theme_Light);

        methodPrepareAdapter();
        methodDialogForDialingCode();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.VP_confirmButton:
                if(isNetworkAvailable()){
                    if(Toast())
                    methodTwillio();

                }else {
                    startAlert("Network Error","Please check your connection", this);
                }
        }
    }

    private boolean Toast(){
        boolean status = true;
        if (Help.isEmptyEditText(countryCode)) {
            countryCode.setError("country code can't be empty");
            status = false;
        }
        if(Help.isEmptyEditText(phoneNumber)){
            phoneNumber.setError("phone number can't be empty");
            status = false;
        }
        return status;
    }

    private void methodDialogForDialingCode() {
        countryCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DialogPlus dialog = DialogPlus.newDialog(VerifyPhone.this)
                            .setAdapter(countryListAdapter)
                            .setContentHolder(new ListHolder())
                            .setMargin(32, 32, 32, 32)
                            .setHeader(R.layout.header_country_and_code_list)
                            .setGravity(Gravity.CENTER)
                            .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                            .setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                                    if (position != 0) {
                                        countryCode.setText(CountryData.getInstance().getCountryCodeList().get(position - 1).toString());
                                        dialog.dismiss();
                                    }
                                }
                            })
                            .create();
                    dialog.show();
                }
            }
        });
    }

    private void methodPrepareAdapter(){

        countryListAdapter = new CountryListAdapter(getApplicationContext(), R.layout.templete_country_and_code_list);

        for (int i = 0; i < CountryData.getInstance().getCountryNameList().size(); i++) {
            countryListModel = new CountryListModel(CountryData.getInstance().getCountryNameList().get(i)
                    , CountryData.getInstance().getCountryCodeList().get(i));
            countryListAdapter.add(countryListModel);
        }
        countryListAdapter.notifyDataSetChanged();
    }

    private void methodTwillio(){

       /* RequestParams params = new RequestParams();
        params.put("From", "+13479527772");
        params.put("To", "+923154849506");
        params.put("Body", "This is code: " + random + " Thanks");

        Twillio.getInstance(this).setParameter(params).execute(new IWSResponse() {
            @Override
            public void onData(JSONObject data) throws JSONException {
                Log.d("alpha", "SUCCESS");
            }

            @Override
            public void noData(String message) {
                Log.d("alpha", "NODATA");
            }

            @Override
            public void onError(String error) {
                Log.d("alpha", "ERROR" + error);
            }
        });*/

        StringRequest postRequest = new StringRequest(Request.Method.POST, Api.HOST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("response", response.toString());
                            JSONObject resJSON = new JSONObject(response);
                            Log.d("responseStatus", resJSON.getString("status").toString());
                            toast("Message send successfully");

                            if(resJSON.getString("status").toString().equals("queued")){
                                phoneVerificationDialog.show();
                            }

                        } catch (Exception e) {
                            toast("Exception: " + e.getMessage().toString());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        toast("Invalid Number: " + error.toString());
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            { Random r = new Random();
                int random = r.nextInt(9999 - 1000) + 1000;

                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("From", "+13479527772");
                params.put("To", "+92" + phoneNumber.getText().toString() );
                params.put("Body", "This is code: " + random + " Thanks");
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                //String creds = String.format("%s:%s","AC05e81ad897c7d80e8df41caf4202ffab","80855dec4f4279c9aab2187cd2c03a9a");
                //String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", "Basic QUMwNWU4MWFkODk3YzdkODBlOGRmNDFjYWY0MjAyZmZhYjo4MDg1NWRlYzRmNDI3OWM5YWFiMjE4N2NkMmMwM2E5YQ==");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);

    }



}
