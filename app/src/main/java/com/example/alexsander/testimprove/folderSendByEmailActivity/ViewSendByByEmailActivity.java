package com.example.alexsander.testimprove.folderSendByEmailActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alexsander.testimprove.DB.UserData;
import com.example.alexsander.testimprove.R;
import com.example.alexsander.testimprove.SendByEmailActivity;

/**
 * Created by alexsander on 19.03.18.
 */

public class ViewSendByByEmailActivity {
    private Context context;
    private View rootView;

    private ImageView imgIco;
    private TextView email;
    private TextView phone;
    private TextView password;
    private Button btnSendByEmail;

    private ViewEventsSend viewEventsSend;

    public ViewSendByByEmailActivity(Context context, View rootView)
    {
        this.context=context;
        this.rootView=rootView;

        init();
    }

    private void init()
    {
        imgIco=rootView.findViewById(R.id.imgIco);
        email=rootView.findViewById(R.id.email);
        phone=rootView.findViewById(R.id.phone);
        password=rootView.findViewById(R.id.password);
        btnSendByEmail=rootView.findViewById(R.id.btnSendByEmail);

        viewEventsSend=(ViewEventsSend) context;

        btnSendByEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewEventsSend.btnSendClick();
            }
        });
    }


    public void setUserData(UserData userData)
    {
        // Get the dimensions of the View
        int targetW = 720;
        int targetH = 600;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(userData.getPhotoPath(), bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(userData.getPhotoPath(), bmOptions);
        imgIco.setImageBitmap(bitmap);

        email.setText(userData.getEmail());
        phone.setText(userData.getPhone());
        password.setText(userData.getPassword());
    }


    public void showAllert(String text)
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

    public void finishActivity()
    {
        ((SendByEmailActivity)context).finish();
    }

}
