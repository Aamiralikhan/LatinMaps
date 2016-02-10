package com.latinmaps.app.ui.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.latinmaps.app.R;
import com.latinmaps.app.adapters.CountryListAdapter;
import com.latinmaps.app.base.LatinMaps;
import com.latinmaps.app.helpers.CircleTransform;
import com.latinmaps.app.helpers.Help;
import com.latinmaps.app.models.CountryListModel;
import com.latinmaps.app.ui.dialogs.MainDialog;
import com.latinmaps.app.utilities.CountryData;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.parse.ParseUser;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Administrator on 1/26/2016.
 */
public class PersonalInformation extends LatinMaps {

    private final int SELECT_PICTURE = 1;
    private final int TAKE_PICTURE = 2;

    MaterialEditText firstName, lastName, countryCode, phoneNumber, email, password;
    CountryListAdapter countryListAdapter;
    CountryListModel countryListModel;
    RelativeLayout picSelect;
    File imageFile;
    ImageView profilePic;

    @Override
    public int getLayout(int id) {
        return R.layout.activity_personal_information;
    }

    @Override
    public void init() {
        super.init();

        mainDialog = new MainDialog(this, android.R.style.Theme_Light);

        firstName = (MaterialEditText) findViewById(R.id.PI_firstName);
        lastName = (MaterialEditText) findViewById(R.id.PI_lastName);
        countryCode = (MaterialEditText) findViewById(R.id.PI_countryCode);
        phoneNumber = (MaterialEditText) findViewById(R.id.PI_number);
        email = (MaterialEditText) findViewById(R.id.PI_email);
        password = (MaterialEditText) findViewById(R.id.PI_password);
        picSelect = (RelativeLayout) findViewById(R.id.profile_image_select);
        profilePic = (ImageView) findViewById(R.id.profilePic);
        profilePic.setImageResource(R.drawable.bg);

        methodPrepareAdapter();
        methodDialogForDialingCode();
        methodEmailFocus();
        methodAutoFill();

        attachClickListener(R.id.profilePic_layout, R.id.back);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profilePic_layout:
                methodPictureFrom();
                break;
            case R.id.back:
                super.onBackPressed();
                break;
        }
    }

    private void methodDialogForDialingCode() {
        countryCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DialogPlus dialog = DialogPlus.newDialog(PersonalInformation.this)
                            .setAdapter(countryListAdapter)
                            .setContentHolder(new ListHolder())
                            .setMargin(32, 32, 32, 32)
                            .setHeader(R.layout.header_country_and_code_list)
                            .setGravity(Gravity.CENTER)
                            .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                            .setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                                    if (position != 0) {
                                        countryCode.setText(CountryData.getInstance().getCountryCodeList().get(position - 1).toString());
                                        dialog.dismiss();
                                    }
                                }
                            })
                            .create();
                    dialog.show();
                }
            }
        });
    }

    private void methodPrepareAdapter(){

        countryListAdapter = new CountryListAdapter(getApplicationContext(), R.layout.templete_country_and_code_list);
        for (int i = 0; i < CountryData.getInstance().getCountryNameList().size(); i++) {
            countryListModel = new CountryListModel(CountryData.getInstance().getCountryNameList().get(i)
                    , CountryData.getInstance().getCountryCodeList().get(i));
            countryListAdapter.add(countryListModel);
        }
        countryListAdapter.notifyDataSetChanged();
    }

    private void methodEmailFocus(){
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

    private void methodAutoFill(){

        ParseUser user = ParseUser.getCurrentUser();
        Log.d("user", user.getUsername());
        firstName.setText(user.get("firstName").toString());
        lastName.setText(user.get("lastName").toString());
/*
        countryCode.setText(user.get("dCode").toString());

        phoneNumber.setText(user.get("phoneNumber").toString());*/
        email.setText(user.get("email").toString());

    }

    private void methodPictureFrom(){
        DialogPlus dialog = DialogPlus.newDialog(PersonalInformation.this)
                .setContentHolder(new ViewHolder(R.layout.dialog_camera_gallery))
                .setGravity(Gravity.TOP)
                .setPadding(16,150,16,150)
                .setMargin(0,0,0,0)
                .setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        switch (view.getId()) {
                            case R.id.cameraButton:
                                cameraIntent();
                                dialog.dismiss();
                                break;
                            case R.id.galleryButton:
                                galleryIntent();
                                dialog.dismiss();
                                break;
                        }
                    }
                })
                .create();
        dialog.show();

    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    private void cameraIntent() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        imageFile = new File(Environment.getExternalStorageDirectory(), "TestPic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {

                imageFile = new File(getPath(data.getData()));
                Picasso.with(this).load(imageFile).memoryPolicy(MemoryPolicy.NO_CACHE).resize(200, 200).transform(new CircleTransform()).into(profilePic, new Callback() {
                    @Override
                    public void onSuccess() {

                        profilePic.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {

                    }
                });
            }

            if (requestCode == TAKE_PICTURE) {

                Picasso.with(this).load(imageFile).memoryPolicy(MemoryPolicy.NO_CACHE).resize(200, 200).transform(new CircleTransform()).into(profilePic, new Callback() {
                    @Override
                    public void onSuccess() {
                        profilePic.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                    }
                });
            }
        }
    }

    public String getPath(Uri uri) {
        if (uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }


}
