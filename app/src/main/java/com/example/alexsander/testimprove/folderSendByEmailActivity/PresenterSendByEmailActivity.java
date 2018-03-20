package com.example.alexsander.testimprove.folderSendByEmailActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.alexsander.testimprove.DB.DBModel;
import com.example.alexsander.testimprove.DB.UserData;
import com.example.alexsander.testimprove.MainActivity;
import com.example.alexsander.testimprove.R;

/**
 * Created by alexsander on 19.03.18.
 */

public class PresenterSendByEmailActivity {
    Context context;
    DBModel dbModel;

    PresenterEventsSend presenterEventsSend;

    UserData userData;

    public PresenterSendByEmailActivity(Context context)
    {
        this.context=context;
        dbModel=DBModel.getInstance();

        presenterEventsSend=(PresenterEventsSend)context;

        getDataFromDB();
    }

    private void getDataFromDB()
    {
        userData=dbModel.getData();
        presenterEventsSend.dataReceived(userData);
    }

    public void clickSend()
    {
        String appName=context.getString(R.string.app_name);

        String[] TO={userData.getEmail()};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType(/*"text/plain"*/"image/*");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, appName+": данные анкеты");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email: "+userData.getEmail()+
                " \n Phone: "+userData.getPhone());

        Uri uri=Uri.parse("file://" +userData.getPhotoPath());
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

        } catch (android.content.ActivityNotFoundException ex) {
            Log.wtf(MainActivity.TAG,"There is no email client installed");
            presenterEventsSend.showMessage("There is no email client installed");
        }
    }
}
