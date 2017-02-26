package com.example.audiolibros;

import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by usuwi on 19/02/2017.
 */
public class FirebaseDatabaseSingleton {

    private final static String BOOKS_CHILD = "libros";
    private final static String USERS_CHILD = "usuarios";
    private static FirebaseDatabaseSingleton instance;
    private static FirebaseDatabase database;
    private DatabaseReference usersReference;
    private DatabaseReference booksReference;


    public static FirebaseDatabaseSingleton getInstance() {
        if (instance == null) {
            synchronized (FirebaseDatabaseSingleton.class) {
                if (instance == null) {
                    instance = new FirebaseDatabaseSingleton();


                }

            }
            instance.inicializa();
        }
        return instance;

    }

    public static FirebaseDatabase getDatabase() {
        Log.d("FirebaseDatabase", "CrearBD");
        if (database == null) {
            Log.d("FirebaseDatabase", "ha entrado en database == null");
            database = FirebaseDatabase.getInstance();
            database.setPersistenceEnabled(true);
        }
        return database;
    }

    private void inicializa() {
        database = getDatabase();
        booksReference = database.getReference().child(BOOKS_CHILD);
        usersReference = database.getReference().child(USERS_CHILD);
    }



    public DatabaseReference getUsersReference() {

        return usersReference;
    }


    public DatabaseReference getBooksReference(){
        return booksReference;
    }



}
