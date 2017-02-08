package com.example.audiolibros;

import android.content.Context;

import java.util.Vector;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by usuwi on 07/02/2017.
 */
public class LibrosSingleton {

    private static LibrosSingleton instance;
    private Context context;

    private Vector<Libro> vectorLibros = new Vector<Libro>();
    private AdaptadorLibrosFiltro adaptador;



    public static LibrosSingleton getInstance(Context context) {
        if (instance == null){
            synchronized(LibrosSingleton.class){
                if (instance == null){
                    instance = new LibrosSingleton(context);
                    instance.inicializa();
                }

            }
        }

        return instance;
    }

    private void inicializa(){
        vectorLibros = Libro.ejemploLibros();
        adaptador= new AdaptadorLibrosFiltro(context,vectorLibros);
    }

    private LibrosSingleton(Context context) {
        this.context = context;

    }

    public AdaptadorLibrosFiltro getAdaptador() {
        return adaptador;
    }

    public Vector<Libro> getVectorLibros() {
        return vectorLibros;
    }
}
