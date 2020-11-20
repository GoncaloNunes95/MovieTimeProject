package com.example.movietime;

import android.content.Context;
import android.content.SharedPreferences;

public class SingletonUser {

    private static SingletonUser singleton_instance = null;

    Context context;
    SharedPreferences prefs;
    SharedPreferences.Editor sharedPrefsEditor;
    String username;

    public static SingletonUser singleton(Context context) {
        if (singleton_instance == null) {
            singleton_instance = new SingletonUser();
            singleton_instance.configSessionUtils(context);
        }
        return singleton_instance;
    }

    public static SingletonUser singleton() {
        return singleton_instance;
    }

    public void configSessionUtils(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        sharedPrefsEditor = prefs.edit();
    }

    public void storeValueString(String key, String value) {
        sharedPrefsEditor.putString(key, value);
        sharedPrefsEditor.commit();
    }

    public String fetchValueString(String key) {
        return prefs.getString(key, null);
    }

    public void logout() {
        prefs = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        sharedPrefsEditor = prefs.edit();
        sharedPrefsEditor.clear();
        sharedPrefsEditor.apply();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
