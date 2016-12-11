package com.amador.pruebaexamen;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by amador on 11/12/16.
 */

public class SportsPreferences {

    private static SharedPreferences preferences;
    public static final String FILE_PREF_SPORTS_NAME = "pref_sports";

    public static void loadLikeSpots(Context context){

        preferences = context.getSharedPreferences(FILE_PREF_SPORTS_NAME, Context.MODE_PRIVATE);

        for (Sport sport: RepositorySports.getInstance()){

            sport.setLike(preferences.getBoolean(sport.getName(), false));
        }
    }

    public static void saveSports(Context context){

        preferences = context.getSharedPreferences(FILE_PREF_SPORTS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        for(Sport sport: RepositorySports.getInstance()){

            editor.putBoolean(sport.getName(), sport.isLike());
        }
        editor.commit();

    }

    public static void removePref(Context context, String prefName){

        preferences = context.getSharedPreferences(FILE_PREF_SPORTS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(prefName);
        editor.commit();
    }

    public static void removeAllPref(Context context){

        preferences = context.getSharedPreferences(FILE_PREF_SPORTS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        for(Sport sport: RepositorySports.getInstance()){

            editor.remove(sport.getName());
        }

        editor.commit();


    }
}
