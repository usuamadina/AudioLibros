package com.example.audiolibros;

import java.util.Vector;

/**
 * Created by usuwi on 15/12/2016.
 */

public class Libro {
    public String titulo;
    public String autor;
    //public String urlImagen;
    public int recursoImagen;
    public String urlAudio;

    public String genero;
    public Boolean novedad;
    public Boolean leido;

    public final static String G_TODOS = "Todos los géneros";
    public final static String G_EPICO = "Poema épico";
    public final static String G_S_XIX = "Literatura siglo XIX";
    public final static String G_SUSPENSE = "Suspense";
    public final static String[] G_ARRAY = new String[]{G_TODOS, G_EPICO, G_S_XIX, G_SUSPENSE};



    public int colorVibrante = -1;
    public int colorApagado=-1;


    public final static Libro LIBRO_EMPTY = new Libro("", "anónimo", R.drawable.sin_portada, "", Libro.G_TODOS, true, false, -1,-1);

    public Libro(String titulo, String autor, int recursoImagen, String urlAudio, String genero, Boolean novedad, Boolean leido, int colorVibrante, int colorApagado) {
        this.titulo = titulo;
        this.autor = autor;
       // this.urlImagen= urlImagen;
        this.recursoImagen = recursoImagen;
        this.urlAudio = urlAudio;
        this.genero = genero;
        this.novedad = novedad;
        this.leido = leido;
        this.colorVibrante = colorVibrante;
        this.colorApagado = colorApagado;

    }


    public int getColorApagado() {
        return this.colorApagado;
    }

    public void setColorApagado(int colorApagado) {
        this.colorApagado = colorApagado;
    }

    public int getColorVibrante(){
        return this.colorVibrante;
    }

    public void setColorVibrante(int colorVibrante){
         this.colorVibrante = colorVibrante;
    }




    public static Vector<Libro> ejemploLibros(){
        final String SERVIDOR =
                "http://www.dcomg.upv.es/~jtomas/android/audiolibros/";
        Vector<Libro> libros = new Vector<Libro>();

        libros.add(new Libro("Kappa", "Akutagawa",
                R.drawable.kappa, SERVIDOR+"kappa.mp3",
                Libro.G_S_XIX, false, false,-1,-1));
        libros.add(new Libro("Avecilla", "Alas Clarín, Leopoldo",
                R.drawable.avecilla, SERVIDOR+"avecilla.mp3",
                Libro.G_S_XIX, true, false,-1,-1));
        libros.add(new Libro("Divina Comedia", "Dante",
                R.drawable.divinacomedia, SERVIDOR+"divina_comedia.mp3",
                Libro.G_EPICO, true, false,-1,-1));
        libros.add(new Libro("Viejo Pancho, El", "Alonso y Trelles, José",
                R.drawable.viejo_pancho, SERVIDOR+"viejo_pancho.mp3",
                Libro.G_S_XIX, true, true,-1,-1));
        libros.add(new Libro("Canción de Rolando", "Anónimo",
                R.drawable.cancion_rolando,
                SERVIDOR+"cancion_rolando.mp3",
                Libro.G_EPICO, false, true,-1,-1));
        libros.add(new Libro("Matrimonio de sabuesos", "Agata Christie",
                R.drawable.matrimonio_sabuesos,SERVIDOR+"matrim_sabuesos.mp3",
                Libro.G_SUSPENSE, false, true, -1, -1));
        libros.add(new Libro("La iliada", "Homero",
                R.drawable.iliada, SERVIDOR+"la_iliada.mp3",
                Libro.G_EPICO, true, false, -1, -1));
        return libros;
    }
}
