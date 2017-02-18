package com.example.audiolibros;

import android.content.SharedPreferences;

/**
 * Created by usuwi on 30/01/2017.
 */

public interface LibroStorage {
    boolean hasLastBook();
    int getLastBook();
    void saveLastBook(int id);
    String getUserName();
    void removeProvider(String provider, String email, String name);
    void saveProvider(String provider, String email, String name);
}
