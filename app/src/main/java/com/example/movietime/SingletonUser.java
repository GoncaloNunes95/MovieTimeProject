package com.example.movietime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.movietime.autentication.LoginActivity;
import com.example.movietime.autentication.Session;
import com.example.movietime.main.activity.MainActivity;

public class SingletonUser {

    private static SingletonUser singleton_instance = null;

    Context context;
    SharedPreferences prefs;
    SharedPreferences.Editor sharedPrefsEditor;
    String username, email, password;
    Session session;

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
        session.setLoggedin(false);
        prefs = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        sharedPrefsEditor = prefs.edit();
        sharedPrefsEditor.clear();
        sharedPrefsEditor.apply();
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
