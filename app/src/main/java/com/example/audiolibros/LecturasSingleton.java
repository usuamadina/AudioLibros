package com.example.audiolibros;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by usuwi on 24/02/2017.
 */

public class LecturasSingleton implements ChildEventListener {
    private static LecturasSingleton instance;
    private Context context;

    private String UIDActual;
    private DatabaseReference referenciaMisLecturas;
    private FirebaseDatabaseSingleton firebaseDatabaseSingleton;
    private static FirebaseDatabase database;
    private ArrayList<String> idLibros;

    public static LecturasSingleton getInstance(Context context) {
        if (instance == null) {
            synchronized (LecturasSingleton.class) {
                if (instance == null) {
                    instance = new LecturasSingleton(context);

                }

            }
            instance.inicializa();
        }

        return instance;
    }

    public void inicializa() {
        firebaseDatabaseSingleton = FirebaseDatabaseSingleton.getInstance();
        database = firebaseDatabaseSingleton.getDatabase();
        UIDActual = FirebaseAuth.getInstance().getCurrentUser().getUid();
        referenciaMisLecturas = database.getReference().child("lecturas").child(UIDActual);
        idLibros = new ArrayList<String>();

    }


    private LecturasSingleton(Context context) {
        this.context = context;
    }


    public boolean libroLeido(String key) {
        int index = idLibros.indexOf(key);
        Log.d("IdLibros Libro leido", "= " + idLibros);
        return !(index == -1);
    }

    public void leidoPorMi(String libro, boolean leido) {
        if (leido) {
            referenciaMisLecturas.push().setValue(libro);
            idLibros.add(libro);
            Log.d("LecturasSingleton", "referencia añadida en leidoPorMi");
        } else {
            referenciaMisLecturas.child(libro).removeValue();
            idLibros.remove(libro);
        }

    }


    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public void activaEscuchadorMisLecturas() {
        referenciaMisLecturas.addChildEventListener(this);
    }

    public void desactivaEscuchadorMisLecturas() {
        referenciaMisLecturas.removeEventListener(this);
    }

}
