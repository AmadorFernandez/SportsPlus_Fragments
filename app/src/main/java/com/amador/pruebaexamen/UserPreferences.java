package com.amador.pruebaexamen;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by amador on 11/12/16.
 */

public class UserPreferences {

    private static SharedPreferences sharedPreferences;
    public static final String USER_NAME_KEY = "userName";
    public static final String USER_MAIL_KEY = "userMail";


    public static void saveUserName(String userName, Context context){

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME_KEY, userName);
        editor.commit();
    }

    public static void saveUserMail(String userMail, Context context){

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_MAIL_KEY, userMail);
        editor.commit();
    }

    public  static String getUserName(Context context){

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USER_NAME_KEY, null);
    }

    public  static String getUserMail(Context context){

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(USER_MAIL_KEY, null);
    }

}
