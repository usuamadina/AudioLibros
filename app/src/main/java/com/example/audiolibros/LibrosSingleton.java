package com.example.audiolibros;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;

import java.util.Vector;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by usuwi on 07/02/2017.
 */
public class LibrosSingleton {

    private static LibrosSingleton instance;
    private Context context;

    private AdaptadorLibrosFiltro adaptador;
    private FirebaseDatabaseSingleton firebaseDatabaseSingleton;
    private DatabaseReference reference;
    private Vector<Libro> vectorLibros = new Vector<Libro>();


    public static LibrosSingleton getInstance(Context context) {
        if (instance == null) {
            synchronized (LibrosSingleton.class) {
                if (instance == null) {
                    instance = new LibrosSingleton(context);

                }

            }
            instance.inicializa();
        }

        return instance;
    }

    private void inicializa() {

        firebaseDatabaseSingleton = FirebaseDatabaseSingleton.getInstance();
        reference = firebaseDatabaseSingleton.getBooksReference();
        adaptador = new AdaptadorLibrosFiltro(context, reference);
    }

    private LibrosSingleton(Context context) {
        this.context = context;

    }

    public AdaptadorLibrosFiltro getAdaptador() {
        return adaptador;
    }


}
