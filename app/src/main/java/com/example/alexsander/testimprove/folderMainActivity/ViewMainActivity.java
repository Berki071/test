package com.example.alexsander.testimprove.folderMainActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.alexsander.testimprove.R;
import com.example.alexsander.testimprove.SendByEmailActivity;
import com.example.alexsander.testimprove.DB.UserData;

/**
 * Created by alexsander on 17.03.18.
 */

public class ViewMainActivity {
    private Context context;
    private View rootView;

    private ImageView imgPhoto;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etPassword;
    private Button btnView;

    private ViewEventsMain viewEvents;

    private Boolean photoAdded=false;

    public ViewMainActivity(Context context, View rootView)
    {
        this.context=context;
        this.rootView=rootView;

        init();
    }

    private void init()
    {
        viewEvents=(ViewEventsMain)context;

        imgPhoto =rootView.findViewById(R.id.imgIco);
        etEmail=rootView.findViewById(R.id.etEmail);
        etPhone=rootView.findViewById(R.id.etPhone);
        etPassword=rootView.findViewById(R.id.etPassword);
        btnView=rootView.findViewById(R.id.btnView);

        imgPhoto.setOnClickListener(ocl);
        btnView.setOnClickListener(ocl);

        etPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    if(etPhone.length()==0)
                    {
                        etPhone.append("+7");
                    }
                }
                else
                {
                    if(etPhone.getText().toString().equals("+7"))
                    {
                        etPhone.setText("");
                    }
                }
            }
        });


        etPassword.setFilters(new InputFilter[]{
            new InputFilter() {
                @Override
                public CharSequence filter(CharSequence source, int i, int i1, Spanned spanned, int i2, int i3) {
                    if(source.equals("")){
                        return source;
                    }
                    if(source.toString().matches("[a-zA-Zа-яА-Я0-9]+")){
                        return source;
                    }
                    return "";
                }
            }
        });
    }


    public View.OnClickListener ocl=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId())
            {
                case R.id.imgIco:
                    viewEvents.clickIco();
                    break;

                case R.id.btnView:
                    viewEvents.clickBtnView(new UserData(mCurrentPhotoPath,
                            etEmail.getText().toString(),
                            etPhone.getText().toString(),
                            etPassword.getText().toString()
                    ));
                    break;
            }
        }
    };

    String mCurrentPhotoPath;
    public void setPhoto(String photePath)
    {
        photoAdded=true;
        mCurrentPhotoPath=photePath;

        // Get the dimensions of the View
        int targetW = imgPhoto.getWidth();
        int targetH = imgPhoto.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imgPhoto.setImageBitmap(bitmap);
    }

    public void nextPage()
    {
        Intent intent=new Intent(context, SendByEmailActivity.class);
        context.startActivity(intent);
    }

    public void showAller(String text)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setMessage(text)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

}
