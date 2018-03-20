package com.example.alexsander.testimprove;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.alexsander.testimprove.folderMainActivity.PresenterEventsMain;
import com.example.alexsander.testimprove.folderMainActivity.PresenterMainActivity;
import com.example.alexsander.testimprove.folderMainActivity.ViewEventsMain;
import com.example.alexsander.testimprove.folderMainActivity.ViewMainActivity;
import com.example.alexsander.testimprove.DB.UserData;

public class MainActivity extends AppCompatActivity implements ViewEventsMain,PresenterEventsMain {
    ViewMainActivity viewMainActivity;
    PresenterMainActivity presenterMainActivity;

    boolean permissionReceived =false;

   public static final String TAG = "mLog";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewMainActivity=new ViewMainActivity(this,findViewById(android.R.id.content));
        presenterMainActivity=new PresenterMainActivity(this);

        requestPermission();
    }



    //region Request_permission
    final int MY_PERMISSIONS_REQUEST =97;
    private void requestPermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED  ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST);
        }
        else
        {
            permissionReceived =true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionReceived =true;
                } else {

                }
                return;
            }
        }
    }
    //endregion

    //region presenter events
    @Override
    public void madeAPhoto(String photePath) {
        viewMainActivity.setPhoto(photePath);
    }

    @Override
    public void formIsInvalid(String error) {
        viewMainActivity.showAller(error);
    }

    @Override
    public void testDataPassed() {
        viewMainActivity.nextPage();
    }
    //endregion

    //region view events
    @Override
    public void clickIco() {
        if(permissionReceived) {
            presenterMainActivity.goToCamera();
        }
        else
        {
            requestPermission();
        }
    }


    @Override
    public void clickBtnView(UserData userData) {
        presenterMainActivity.testData(userData);
    }
    //endregion

    //возвращение данных фотографирования
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        presenterMainActivity.cameraResult(requestCode,resultCode,intent);
    }

}
