package com.example.audiolibros;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

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

    public static LibroSharedPreferenceStorage getInstance(Context context) {
        if (instance == null){
            synchronized (LibroSharedPreferenceStorage.class) {
                if (instance == null) {
                    instance = new LibroSharedPreferenceStorage(context);
                }
            }
        }
        return instance;
    }

    public boolean hasLastBook() {
        return getPreference().contains(KEY_ULTIMO_LIBRO);
    }

    private SharedPreferences getPreference() {
        return context.getSharedPreferences(PREF_AUDIOLIBROS, MODE_PRIVATE);
    }

    public int getLastBook() {
        return getPreference().getInt(KEY_ULTIMO_LIBRO, -1);
    }

    public void saveLastBook(int id){
        SharedPreferences pref = context.getSharedPreferences(
                "com.example.audiolibros_internal", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("ultimo", id);
        editor.commit();

    }

    public String getUserName(){
        return getPreference().getString("name", null);
    }

}



