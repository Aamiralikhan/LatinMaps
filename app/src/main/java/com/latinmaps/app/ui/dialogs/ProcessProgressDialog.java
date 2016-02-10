package com.latinmaps.app.ui.dialogs;

import android.content.Context;

import com.latinmaps.app.R;
import com.latinmaps.app.base.LPDialog;

/**
 * Created by Administrator on 12/23/2015.
 */
public class ProcessProgressDialog extends LPDialog<ProcessProgressDialog> {

    //////////////////////////////////////////////
    // PRIVATE STATIC INSTANCE
    //////////////////////////////////////////////

    private static ProcessProgressDialog INSTANCE;

    //////////////////////////////////////////////
    // PRIVATE VAR
    //////////////////////////////////////////////

    //////////////////////////////////////////////
    // FACTORY METHOD
    //////////////////////////////////////////////

    public static ProcessProgressDialog getInstance(Context context){
        return new ProcessProgressDialog(context);
    }

    public static ProcessProgressDialog getInstance(Context context, int style){
        return new ProcessProgressDialog(context, style);
    }

    //////////////////////////////////////////////
    // CONSTRUCTOR
    //////////////////////////////////////////////

    public ProcessProgressDialog(Context context) {
        super(context);
    }

    public ProcessProgressDialog(Context context, int style) {
        super(context, style);
    }

    //////////////////////////////////////////////
    // OVERRIDE FUNCTIONS
    //////////////////////////////////////////////

    @Override
    public int getLayout() {
        return R.layout.dialog_main;
    }

    @Override
    public ProcessProgressDialog getThis() {
        return this;
    }
}
