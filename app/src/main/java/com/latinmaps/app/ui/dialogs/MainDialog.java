package com.latinmaps.app.ui.dialogs;

import android.app.Dialog;
import android.content.Context;

import com.latinmaps.app.R;
import com.latinmaps.app.base.LPDialog;

/**
 * Created by Administrator on 1/27/2016.
 */
public class MainDialog extends LPDialog<MainDialog>{


    private static MainDialog INSTANCE;


    public static MainDialog getInstance(Context context){
        return new MainDialog(context);
    }

    public static MainDialog getInstance(Context context, int style){
        return new MainDialog(context, style);
    }

    public MainDialog(Context context, int style) {
        super(context, style);
    }

    public MainDialog(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.dialog_main;
    }

    @Override
    public MainDialog getThis() {
        return null;
    }
}
