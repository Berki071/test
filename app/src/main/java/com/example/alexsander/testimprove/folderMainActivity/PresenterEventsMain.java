package com.example.alexsander.testimprove.folderMainActivity;

import android.graphics.Bitmap;

/**
 * Created by alexsander on 18.03.18.
 */

public interface PresenterEventsMain {
    void madeAPhoto(String photePath);
    void formIsInvalid(String error);
    void testDataPassed();
}
