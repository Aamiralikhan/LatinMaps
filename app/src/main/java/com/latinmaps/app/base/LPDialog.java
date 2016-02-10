package com.latinmaps.app.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.latinmaps.app.interfaces.IDialogListener;

import java.io.File;

/**
 * Created by Administrator on 12/23/2015.
 */
public abstract class LPDialog<T extends LPDialog<T>> extends Dialog implements View.OnClickListener {

    //////////////////////////////////////////////
    // PRIVATE VAR
    //////////////////////////////////////////////

    protected IDialogListener iDialogListener;

    //////////////////////////////////////////////
    // CONSTRUCTOR
    //////////////////////////////////////////////

    public LPDialog(Context context, int style){
        super(context, style);
        initDialog();
    }

    public LPDialog(Context context) {
        super(context);
        initDialog();
    }

    //////////////////////////////////////////////
    // FUNCTIONS
    //////////////////////////////////////////////

    private void initDialog(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(getLayout());
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.x = 20;
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
        getWindow().setAttributes(params);
    }

    @Override
    public void show() {
        try{
            super.show();

            setCancelable(isCancelable());
            setCanceledOnTouchOutside(isCancelOnTouchOutside());
            cancelDialog();
            init();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void cancelDialog(){
        if(findViewById(getCancelButtonId()) != null){
            findViewById(getCancelButtonId()).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }
    }
    public void init(){}
    public boolean isCancelable(){return false;}
    public boolean isCancelOnTouchOutside(){return false;}
    public int getCancelButtonId(){
        return 0;
    }
    public T setDialogListener(IDialogListener iDialogListener){
        this.iDialogListener = iDialogListener;
        return getThis();
    }

    @Override
    public void onClick(View v) {}


    //////////////////////////////////////////////
    // ABSTRACT FUNCTION
    //////////////////////////////////////////////

    public abstract int getLayout();
    public abstract T getThis();
}
