package com.example.audiolibros;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by usuwi on 08/02/2017.
 */
public class VolleySingleton {

    private Context context;
    private static RequestQueue colaPeticiones;
    private static ImageLoader lectorImagenes;
    private Bitmap bitmap;

    private static VolleySingleton instance;

    public static VolleySingleton getInstance(Context context) {
        if (instance == null) {
            synchronized (VolleySingleton.class) {
                if (instance == null) {
                    instance = new VolleySingleton(context);
                    instance.inicializa();
                }

            }
        }

        return instance;
    }


    private void inicializa() {
        colaPeticiones = Volley.newRequestQueue(context);
        lectorImagenes = new ImageLoader(colaPeticiones, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return bitmap = cache.get(url);

            }

        });

    }

    private VolleySingleton(Context context) {
        this.context = context;

    }

    public static RequestQueue getColaPeticiones() {
        return colaPeticiones;
    }

    public static void setColaPeticiones(RequestQueue colaPeticiones) {
        VolleySingleton.colaPeticiones = colaPeticiones;
    }

    public static ImageLoader getLectorImagenes() {
        return lectorImagenes;
    }

    public static void setLectorImagenes(ImageLoader lectorImagenes) {
        VolleySingleton.lectorImagenes = lectorImagenes;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
