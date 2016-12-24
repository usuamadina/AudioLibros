package com.example.audiolibros;

import android.app.Application;

import java.util.Vector;

/**
 * Created by usuwi on 15/12/2016.
 */

public class Aplicacion extends Application {
    private Vector<Libro> vectorLibros;
    private AdaptadorLibrosFiltro adaptadorLibrosFiltro;

    @Override
    public void onCreate() {
        vectorLibros = Libro.ejemploLibros();
        adaptadorLibrosFiltro = new AdaptadorLibrosFiltro(this, vectorLibros);
    }

    public AdaptadorLibrosFiltro getAdaptador() {
        return adaptadorLibrosFiltro;

    }

    public Vector<Libro> getVectorLibros() {
        return vectorLibros;
    }


}
