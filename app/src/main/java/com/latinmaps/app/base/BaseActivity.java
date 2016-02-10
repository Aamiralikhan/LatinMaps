package com.latinmaps.app.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Administrator on 1/20/2016.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog progressDialog;
    private Intent intent;
    private AlertDialog.Builder alert;
    public static final int TEXT_VIEW = 0;
    public static final int EDIT_TEXT = 1;
    public static boolean logout = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onClick(View v) {

    }

    public final void attachClickListener(int... views) {
        for (int i = 0; i < views.length; i++) {
            findViewById(views[i]).setOnClickListener(this);
        }
    }

    // goto activity ----------------------------------------------->
    public void gotoActivity(Class<?> activity) {
        this.gotoActivity(activity, false, new Bundle());
    }

    public void gotoActivity(Class<?> activity, boolean finish) {
        this.gotoActivity(activity, finish, new Bundle());
    }

    public void gotoActivity(Class<?> activity, Bundle bundle) {
        this.gotoActivity(activity, false, bundle);
    }

    public void gotoActivity(Class<?> activity, boolean finish, Bundle bundle) {
        if (finish)
            finish();

        intent = new Intent(this, activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    // toast ------------------------------------------------------->
    public void toast(String message, int length, int gravity) {
        try {
            Toast toast = Toast.makeText(this, message, length);
            toast.setGravity(gravity | Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void toast(String message) {
        this.toast(String.valueOf(message), Toast.LENGTH_LONG, Gravity.BOTTOM);
    }

    public void toast(int message) {
        this.toast(String.valueOf(message), Toast.LENGTH_LONG, Gravity.BOTTOM);
    }

    public void toast(float message) {
        this.toast(String.valueOf(message), Toast.LENGTH_LONG, Gravity.BOTTOM);
    }

    public void toast(int message, int length) {
        toast(String.valueOf(message), length, Gravity.BOTTOM);
    }

    public void toast(float message, int length) {
        toast(String.valueOf(message), length, Gravity.BOTTOM);
    }

    // animation
    public void anim(RelativeLayout l, int anim) {

        Animation anim_ee = AnimationUtils.loadAnimation(getApplicationContext(), anim);
        anim_ee.reset();
        l.setVisibility(View.VISIBLE);
        l.startAnimation(anim_ee);
    }

    public void anim(LinearLayout l, int anim) {

        Animation anim_ee = AnimationUtils.loadAnimation(getApplicationContext(), anim);
        anim_ee.reset();
        l.setVisibility(View.VISIBLE);
        l.startAnimation(anim_ee);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void startDialog(String Title, String Message, Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(Title);
        progressDialog.setMessage(Message);
        progressDialog.show();
    }

    public void stopDialog() {
        progressDialog.dismiss();
    }

    public void startAlert(String Title, String Message, Context context) {
        alert = new AlertDialog.Builder(context);
        alert.setTitle(Title);
        alert.setMessage(Message);
        alert.setPositiveButton("OK", null);
        alert.show();
    }

    public void setIconColor(ImageView imageView) {
        int intColor = Color.parseColor("#ed1b3e"); //The color u want
        imageView.setColorFilter(intColor);
    }

    public void removeIconColor(ImageView imageView){
        int intColor = Color.parseColor("#000000");
        imageView.setColorFilter(intColor);
    }


}

