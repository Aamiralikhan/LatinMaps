package com.latinmaps.app.ui.activities;

import android.view.View;

import com.latinmaps.app.R;
import com.latinmaps.app.base.LatinMaps;
import com.latinmaps.app.ui.dialogs.MainDialog;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by Administrator on 1/26/2016.
 */
public class SendFeedback extends LatinMaps {

    MaterialEditText editText;

    @Override
    public int getLayout(int id) {
        return R.layout.activity_feedback;
    }

    @Override
    public void init() {
        super.init();

        mainDialog = new MainDialog(this, android.R.style.Theme_Light);

        editText = (MaterialEditText) findViewById(R.id.SYF_text);

        attachClickListener(R.id.SYF_back, R.id.SYF_send);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.SYF_back:
                super.onBackPressed();
                break;
            case R.id.SYF_send:
                break;
        }
    }
}
