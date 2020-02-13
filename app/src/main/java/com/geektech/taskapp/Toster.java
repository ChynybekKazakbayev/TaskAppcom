package com.geektech.taskapp;

import android.widget.Toast;

public class Toster {
    public static void show(String message){
        Toast.makeText(App.getInstance().getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }
}
