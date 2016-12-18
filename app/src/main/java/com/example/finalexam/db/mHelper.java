package com.example.finalexam.db;

import com.example.finalexam.adapter.*;

public interface mHelper {

    public static final String DATABASE_NAME = "Login";
    public static final int DATABASE_VERSION = 1;

    public long registerUser(LoginAdapter user);

    public LoginAdapter checkUserLogin(LoginAdapter user);


}
