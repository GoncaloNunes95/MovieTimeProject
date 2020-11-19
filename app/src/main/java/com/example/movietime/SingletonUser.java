package com.example.movietime;

import android.content.Context;
import android.content.SharedPreferences;

public class SingletonUser {

    private static SingletonUser singleton_instance = null;

    Context context;
    SharedPreferences prefs;
    SharedPreferences.Editor sharedPrefsEditor;
    String username, email, password;

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

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
