package com.latinmaps.app.ui.activities;

import android.view.View;

import com.latinmaps.app.R;
import com.latinmaps.app.base.LatinMaps;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by Administrator on 1/22/2016.
 */
public class ForgotPassword extends LatinMaps {

    MaterialEditText email;

    @Override
    public int getLayout(int id) {
        return R.layout.activity_forgot_password;
    }

    @Override
    public void init() {
        super.init();

        email = (MaterialEditText) findViewById(R.id.FP_email);

        attachClickListener(R.id.FP_submit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.FP_submit:
                break;
        }
    }
}
