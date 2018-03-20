package com.example.alexsander.testimprove;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.alexsander.testimprove.DB.UserData;
import com.example.alexsander.testimprove.folderSendByEmailActivity.PresenterEventsSend;
import com.example.alexsander.testimprove.folderSendByEmailActivity.PresenterSendByEmailActivity;
import com.example.alexsander.testimprove.folderSendByEmailActivity.ViewEventsSend;
import com.example.alexsander.testimprove.folderSendByEmailActivity.ViewSendByByEmailActivity;

public class SendByEmailActivity extends AppCompatActivity implements ViewEventsSend,PresenterEventsSend {

    ViewSendByByEmailActivity viewSendByByEmailActivity;
    PresenterSendByEmailActivity presenterSendByEmailActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_by_email);

        viewSendByByEmailActivity=new     ViewSendByByEmailActivity(this,findViewById(android.R.id.content));
        presenterSendByEmailActivity=new PresenterSendByEmailActivity(this);

        // добавление кнопки назад в ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    //ActionBar событие кнопки назад
    @Override
    public boolean onSupportNavigateUp() {
        viewSendByByEmailActivity.finishActivity();
        return true;
    }

    @Override
    public void onBackPressed() {
        viewSendByByEmailActivity.finishActivity();
        super.onBackPressed();
    }

    //region view event
    @Override
    public void btnSendClick() {
        presenterSendByEmailActivity.clickSend();
    }

    //endregion

    //region presenter events
    @Override
    public void dataReceived(UserData userData) {
        viewSendByByEmailActivity.setUserData(userData);
    }

    @Override
    public void showMessage(String text) {
        viewSendByByEmailActivity.showAllert(text);
    }
    //endregion
}
