package com.example.alexsander.testimprove.folderMainActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import com.example.alexsander.testimprove.MainActivity;
import com.example.alexsander.testimprove.R;
import com.example.alexsander.testimprove.DB.DBModel;
import com.example.alexsander.testimprove.DB.UserData;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by alexsander on 17.03.18.
 */

public class PresenterMainActivity {
    Context context;

    public static final int REQUEST_CODE_PHOTO = 1;

    PresenterEventsMain presenterEvents;

    DBModel dbModel;

    public PresenterMainActivity(Context context)
    {
        this.context=context;

        presenterEvents=(PresenterEventsMain)this.context;

        dbModel=DBModel.getInstance();
    }

    //region camera
    public void goToCamera()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
               Log.wtf(MainActivity.TAG,ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                ((MainActivity)context).startActivityForResult(takePictureIntent, REQUEST_CODE_PHOTO);
            }
        }
    }

    public void cameraResult(int requestCode, int resultCode, Intent intent)
    {
        if (requestCode == REQUEST_CODE_PHOTO && resultCode == ((MainActivity)context).RESULT_OK) {

            presenterEvents.madeAPhoto(mCurrentPhotoPath);
        }
    }


    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    //endregion

    public void testData(UserData userData)
    {
        if(!isValidPhoto(userData.getPhotoPath()))
            return;

        if(!isValidEmail(userData.getEmail()))
            return;

        if(!isValidPhone(userData.getPhone()))
            return;

        if(!isValidPassword(userData.getPassword()))
            return;

        saveDataInTheDB(userData);
        presenterEvents.testDataPassed();
    }

    private void saveDataInTheDB(UserData userData)
    {
        dbModel.setData(context,userData);
    }

    //region test valid data
    private boolean isValidPhoto(String photePath)
    {
        if(photePath==null || photePath.isEmpty())
        {
            presenterEvents.formIsInvalid(context.getString(R.string.addAPhoto));
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            presenterEvents.formIsInvalid(context.getString(R.string.addAEmail));
            return false;
        } else {
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches())
            {
                return true;
            }
            else
            {
                presenterEvents.formIsInvalid(context.getString(R.string.emailNotValid));
                return false;
            }
        }
    }

    private boolean isValidPhone(String target)
    {
        if (TextUtils.isEmpty(target)) {
            presenterEvents.formIsInvalid(context.getString(R.string.addAPhone));
            return false;
        } else {
            boolean b1=!target.substring(0,2).equals("+7");
            boolean b2=target.length()!=12;
            boolean b3=target.lastIndexOf("+")!=0;

            if(b1 || b2 || b3) {
                presenterEvents.formIsInvalid(context.getString(R.string.phoneNotValid));
                return false;
            }

            return true;
        }
    }

    private boolean isValidPassword(String target)
    {
        if (TextUtils.isEmpty(target)) {
            presenterEvents.formIsInvalid(context.getString(R.string.addAPassword));
            return false;
        } else {
            if(target.length()<6)
            {
                presenterEvents.formIsInvalid(context.getString(R.string.errSizePassword));
                return false;
            }

            Boolean b1= Pattern.matches("[a-zA-Zа-яА-Я]+", target);
            Boolean b2=Pattern.matches("[0-9]+", target);

            if(b1 || b2)
            {
                presenterEvents.formIsInvalid(context.getString(R.string.errPasswordContainSimbols));
                return false;
            }

            return true;
        }
    }
    //endregion

}
