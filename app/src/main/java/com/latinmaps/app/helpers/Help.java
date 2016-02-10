package com.latinmaps.app.helpers;

import android.view.View;
import android.widget.EditText;

import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by Administrator on 1/21/2016.
 */
public class Help {

    public static boolean isPasswordMatches(MaterialEditText editText1, MaterialEditText editText2){
        boolean status = false;
        String pass = editText1.getText().toString().trim();
        String confirmPass = editText2.getText().toString().trim();
        if(pass.equals(confirmPass)){
            status = true;
        }
        return status;
    }

    public static boolean isEmptyEditText(MaterialEditText editText){
        boolean status = false;
        if(editText.getText().toString().trim().isEmpty()){
            status = true;
        }
        return status;
    }

    public static boolean isValidEmailAddress(MaterialEditText editText) {
        String EMAIL_REGEX = "^[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$";
        return editText.getText().toString().matches(EMAIL_REGEX);
    }

    public static boolean isValidPhoneNumber(MaterialEditText editText) {
        String PHONE_REGEX = "^((\\+)|(00))[0-9]{6,16}$";
        return editText.getText().toString().matches(PHONE_REGEX);
    }

    public static boolean passwordLength(EditText editText){
        boolean status = true;
        if(editText.getText().toString().trim().length() >= 6){
            status = false;
        }
        return status;
    }

  /*  public static boolean isEmptySpinner(MaterialSpinner spinner){
        if(spinner.getSelectedItem().toString().length()>1){
            return true;
        }else {
            return false;
        }
    }

    public static boolean isEmptyCountry(MaterialSpinner spinner){
        if(spinner.getSelectedItem().toString().equals("Country")){
            return true;
        }else {
            return false;
        }
    }*/

}
