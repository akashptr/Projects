package com.semproject.shoppingnow;

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

    public void setLoginStatus(){
        prefEditor.putBoolean(AppConstants.LOGIN_STATUS, true);
        prefEditor.commit();
    }

    public void setUserId(String uid)
    {
        if(uid != null && !uid.isEmpty())
        {
            prefEditor.putString(AppConstants.UID, uid);
            prefEditor.commit();
        }
    }

    public boolean getLoginStatus()
    {
        return pref.getBoolean(AppConstants.LOGIN_STATUS, false);
    }

    public String getUserId()
    {
        return pref.getString(AppConstants.UID, null);
    }

    public void logout(){
        prefEditor.clear();
        prefEditor.commit();
    }
}
