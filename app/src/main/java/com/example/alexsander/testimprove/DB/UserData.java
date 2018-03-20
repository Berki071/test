package com.example.alexsander.testimprove.DB;

import android.graphics.Bitmap;

/**
 * Created by alexsander on 19.03.18.
 */

public class UserData {
    private String photoPath;
    private String email;
    private String phone;
    private String password;

    public UserData(String photoPath,String email,String phone,String password)
    {
       this.photoPath =photoPath;
       this.email=email;
       this.phone=phone;
       this.password=password;
    }

    public String getPassword() {
        return password;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }


}
