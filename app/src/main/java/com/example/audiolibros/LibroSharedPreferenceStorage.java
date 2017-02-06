package com.example.audiolibros;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by usuwi on 29/01/2017.
 */

public class LibroSharedPreferenceStorage implements LibroStorage{
    public static final String PREF_AUDIOLIBROS = "com.example.audiolibros_internal";
    public static final String KEY_ULTIMO_LIBRO = "ultimo";
    private final Context context;

    private LibroSharedPreferenceStorage(Context context) {
        this.context = context;
    }

    private static LibroSharedPreferenceStorage instance;

    public static LibroStorage getInstance(Context context){
        if (instance == null){
            instance = new LibroSharedPreferenceStorage(context);

        }
        return instance;
    }

    public boolean hasLastBook() {
        return getPreference().contains(KEY_ULTIMO_LIBRO);
    }

    private SharedPreferences getPreference() {
        return context.getSharedPreferences(PREF_AUDIOLIBROS, Context.MODE_PRIVATE);
    }

    public int getLastBook() {
        return getPreference().getInt(KEY_ULTIMO_LIBRO, -1);
    }
}



