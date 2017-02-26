package com.example.audiolibros;

import android.content.SharedPreferences;

/**
 * Created by usuwi on 30/01/2017.
 */

public interface LibroStorage {
    boolean hasLastBook();
    String getLastBook();
    void saveLastBook(String key);
    String getUserName();
    void removeProvider(String provider, String email, String name);
    void saveProvider(String provider, String email, String name);
}
