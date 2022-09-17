package com.live.programming.learningapp.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LocalSession {
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEditor;

    public LocalSession(Context ctx){
        //Initializing the interfaces
        pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        prefEditor = pref.edit();
    }

    public boolean getLoginStatus(){
        return pref.getBoolean(AppConstants.LOGIN_STATUS, false);
    }

    public void setLoginStatus(){
        prefEditor.putBoolean(AppConstants.LOGIN_STATUS, true);
        prefEditor.commit();
    }

    public void logout(){
        prefEditor.clear(); //To clear all the info present in shared pref related to the app
//      prefEditor.remove(AppConstants.LOGIN_STATUS);   //To clear a particular key value
        prefEditor.commit();
    }
}
