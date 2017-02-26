package com.example.audiolibros;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by usuwi on 29/01/2017.
 */

public class LibroSharedPreferenceStorage implements LibroStorage {
    public static final String PREF_AUDIOLIBROS = "com.example.audiolibros_internal";
    public static final String KEY_ULTIMO_LIBRO = "ultimo";
    private final Context context;

    private LibroSharedPreferenceStorage(Context context) {
        this.context = context;
    }

    private static LibroSharedPreferenceStorage instance;

    public static LibroSharedPreferenceStorage getInstance(Context context) {
        if (instance == null) {
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

    public String getLastBook() {
        return getPreference().getString(KEY_ULTIMO_LIBRO,"-1");
    }

    public void saveLastBook(String key) {

        SharedPreferences pref = instance.getPreference();
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("ultimo", key);
        editor.commit();

    }


    public String getUserName() {
        return getPreference().getString("name", null);
    }

    public void removeProvider(String provider, String email, String name) {
        SharedPreferences pref = instance.getPreference();
        pref.edit().remove(provider).commit();
        pref.edit().remove(email).commit();
        pref.edit().remove(name).commit();
    }

    public void saveProvider (String provider, String email, String name){
        SharedPreferences pref = instance.getPreference();
        pref.edit().putString("provider", provider).commit();
        pref.edit().putString("name", name).commit();
        if (email != null) {
            pref.edit().putString("email", email).commit();
        }
    }


}


