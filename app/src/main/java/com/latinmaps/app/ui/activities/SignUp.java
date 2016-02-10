package com.latinmaps.app.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.latinmaps.app.R;
import com.latinmaps.app.base.LatinMaps;
import com.latinmaps.app.helpers.Help;

import com.latinmaps.app.ui.dialogs.MainDialog;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by Administrator on 1/21/2016.
 */
public class SignUp extends LatinMaps {

    MaterialEditText firstName, lastName, email, password, confirmPassword;

    @Override
    public int getLayout(int id) {
        return R.layout.activity_signup;
    }

    @Override
    public void init() {
        super.init();
        attachClickListener(R.id.signUpButton);

        mainDialog = new MainDialog(this, android.R.style.Theme_Light);
        firstName = (MaterialEditText) findViewById(R.id.CNA_firstName);
        lastName = (MaterialEditText) findViewById(R.id.CNA_lastName);
        email = (MaterialEditText) findViewById(R.id.CNA_email);
        password = (MaterialEditText) findViewById(R.id.CNA_password);
        confirmPassword = (MaterialEditText) findViewById(R.id.CNA_confirmPassword);

        methodEmailFocus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpButton:
                if (isNetworkAvailable()) {
                    if (methodToast()) {
                        methodSignUp();
                    }
                } else {
                    startAlert("Oops! ", "can't connect to internet", this);
                }
        }
    }

    private void methodSignUp() {
        mainDialog.show();

        ParseUser user = new ParseUser();
        user.setUsername(email.getText().toString().trim());
        user.setPassword(password.getText().toString().trim());
        user.setEmail(email.getText().toString().trim());

// other fields can be set just like with ParseObject
        user.put("firstName", firstName.getText().toString().trim());
        user.put("lastName", lastName.getText().toString().trim());

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                mainDialog.dismiss();
                if (e == null) {
                    AlertDialog alert_back = new AlertDialog.Builder(SignUp.this).create();
                    alert_back.setTitle("Congratulations !");
                    alert_back.setMessage("You have successfully created new account.");

                    alert_back.setButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            gotoActivity(VerifyPhone.class, true);
                            dialog.dismiss();
                        }
                    });
                    alert_back.show();

                } else {
                    startAlert("Oops !", e.getMessage(), SignUp.this);
                }
            }
        });
    }


    private boolean methodToast() {
        boolean status = true;
        if (Help.isEmptyEditText(firstName)) {
            firstName.setError("can't be empty");
            status = false;
        }
        if (Help.isEmptyEditText(lastName)) {
            lastName.setError("can't be empty");
            status = false;
        }
        if (!Help.isValidEmailAddress(email)) {
            email.setError("invalid email address");
            status = false;
        }
        if (Help.isEmptyEditText(password)) {
            password.setError("password can't be empty");
            status = false;
        }
        if (Help.isEmptyEditText(confirmPassword)) {
            confirmPassword.setError("password not confirmed");
            status = false;
        }
        if (!Help.isPasswordMatches(password, confirmPassword)) {
            confirmPassword.setText("");
            confirmPassword.setError("password did not match");
            status = false;
        }
        return status;
    }

    private void methodEmailFocus() {
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!Help.isValidEmailAddress(email)) {
                        email.setError("invalid email address");
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        if (ParseUser.getCurrentUser() != null) {
            this.finish();
        } else {
            super.onResume();
        }
    }
}
