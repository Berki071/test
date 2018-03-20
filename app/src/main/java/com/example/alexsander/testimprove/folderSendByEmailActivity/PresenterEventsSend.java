package com.example.alexsander.testimprove.folderSendByEmailActivity;

import com.example.alexsander.testimprove.DB.UserData;

/**
 * Created by alexsander on 19.03.18.
 */

public interface PresenterEventsSend {
    void dataReceived(UserData userData);
    void showMessage(String text);
}
