package com.example.audiolibros;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by usuwi on 10/02/2017.
 */

public class FirebaseAuthSingleton {

    private static FirebaseAuth auth;
    private static FirebaseAuthSingleton instance;




    public FirebaseAuthSingleton getInstance() {
        if (instance == null) {
            synchronized (FirebaseAuthSingleton.class) {
                if (instance == null) {
                    instance = new FirebaseAuthSingleton();
                    inicializa();
                }

            }
        }
        return instance;
    }

    private void inicializa(){
        auth = FirebaseAuth.getInstance();
    }


    public FirebaseAuth getAuth() {
        return auth;
    }
}
