package com.example.movietime.autentication;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;

    public Session(Context context) {

        this.context = context;
        prefs = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        editor = prefs.edit();

    }

    public boolean setLoggedin(boolean loggedin) {

        editor.putBoolean("loggedInmode", loggedin);
        editor.commit();

        return loggedin;
    }

    public boolean loggedin() {
        return prefs.getBoolean("loggedInmode", false);
    }
}



