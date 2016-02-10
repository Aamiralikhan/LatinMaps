package com.latinmaps.app.ui.dialogs;

import android.content.Context;
import android.provider.ContactsContract;

import com.latinmaps.app.R;
import com.latinmaps.app.base.LPDialog;

/**
 * Created by Administrator on 2/2/2016.
 */
public class PhoneVerificationDialog extends LPDialog<PhoneVerificationDialog> {

    private static PhoneVerificationDialog INSTANCE;


    public static PhoneVerificationDialog getINSTANCE(Context context) {
        return new PhoneVerificationDialog(context);
    }

    public static PhoneVerificationDialog getINSTANCE(Context context, int style){
        return new PhoneVerificationDialog(context, style);
    }

    public PhoneVerificationDialog(Context context, int style) {
        super(context, style);
    }

    public PhoneVerificationDialog(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.dialog_phone_code_verification;
    }

    @Override
    public PhoneVerificationDialog getThis() {
        return null;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.dismiss();
    }
}
