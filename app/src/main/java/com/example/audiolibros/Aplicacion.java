package com.example.audiolibros;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.Vector;

/**
 * Created by usuwi on 15/12/2016.
 */

public class Aplicacion extends Application {
    private Vector<Libro> vectorLibros = new Vector<Libro>();
    private AdaptadorLibrosFiltro adaptadorLibrosFiltro;

    //Carga de imagenes de las portadas desde internet
    //private static RequestQueue colaPeticiones;
    //private static ImageLoader lectorImagenes;

    @Override
    public void onCreate() {
        vectorLibros = Libro.ejemploLibros();
        adaptadorLibrosFiltro = new AdaptadorLibrosFiltro(this, vectorLibros);
        /*colaPeticiones = Volley.newRequestQueue(this);
        lectorImagenes = new ImageLoader(colaPeticiones, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10);

            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }
        });*/
    }

    public AdaptadorLibrosFiltro getAdaptador() {
        return adaptadorLibrosFiltro;

    }

    public Vector<Libro> getVectorLibros() {
        return vectorLibros;
    }

   /* public static RequestQueue getColaPeticiones() {
        return colaPeticiones;
    }

    public static void setColaPeticiones(RequestQueue colaPeticiones) {
        Aplicacion.colaPeticiones = colaPeticiones;
    }

    public static ImageLoader getLectorImagenes() {
        return lectorImagenes;
    }

    public static void setLectorImagenes(ImageLoader lectorImagenes) {
        Aplicacion.lectorImagenes = lectorImagenes;
    }*/
}
