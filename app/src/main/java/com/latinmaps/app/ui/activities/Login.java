package com.latinmaps.app.ui.activities;

import android.util.Log;
import android.view.View;

import com.latinmaps.app.R;
import com.latinmaps.app.base.LatinMaps;
import com.latinmaps.app.helpers.Help;
import com.latinmaps.app.ui.dialogs.MainDialog;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by Administrator on 1/20/2016.
 */
public class Login extends LatinMaps {

    MaterialEditText email, password;

    @Override
    public int getLayout(int id) {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        super.init();
        attachClickListener(R.id.loginButton, R.id.createNewAccount, R.id.forgotPasswordButton);

        email = (MaterialEditText) findViewById(R.id.LI_email);
        password = (MaterialEditText) findViewById(R.id.LI_password);
        mainDialog = new MainDialog(this, android.R.style.Theme_Light);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                if(isNetworkAvailable()){
                    if(Toasts())
                    methodSignIn();
                }else {
                    startAlert("Oops !","connection problem", this);
                }
                break;
            case R.id.createNewAccount:
                gotoActivity(SignUp.class);
                break;
            case R.id.forgotPasswordButton:
                gotoActivity(ForgotPassword.class);
        }
    }

    private void methodSignIn() {
        mainDialog.show();
        ParseUser.logInInBackground(
                email.getText().toString().trim()
                , password.getText().toString().trim()
                , new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        mainDialog.dismiss();
                        if (user != null) {
                            gotoActivity(AllCategories.class, true);
                        } else {
                            startAlert("Oops !",e.getMessage(),Login.this);
                        }
                    }
                });
    }

    private boolean Toasts() {
        boolean status = true;
        if (!Help.isValidEmailAddress(email)) {
            email.setError("invalid email address");
            status = false;
        }
        if(Help.isEmptyEditText(password)){
            password.setError("password can't be empty");
            status = false;
        }
        return status;
    }
}
