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
    public int colorApagado = -1;


    public final static Libro LIBRO_EMPTY = new Libro("", "anónimo", R.drawable.sin_portada, "", Libro.G_TODOS, true, false, -1, -1);

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

    public int getColorVibrante() {
        return this.colorVibrante;
    }

    public void setColorVibrante(int colorVibrante) {
        this.colorVibrante = colorVibrante;
    }


    public static Vector<Libro> ejemploLibros() {
        final String SERVIDOR =
                "http://www.dcomg.upv.es/~jtomas/android/audiolibros/";
        Vector<Libro> libros = new Vector<Libro>();

        libros.add(new LibroBuilder().withTitulo("Kappa").withAutor("Akutagawa")
                .withRecursoImagen(R.drawable.kappa).withUrlAudio(SERVIDOR + "kappa.mp3")
                .withGenero(Libro.G_S_XIX).withLeido(false).withNuevo(false).withColorApagado(-1)
                .withColorVibrante(-1).build());
        libros.add(new LibroBuilder().withTitulo("Avecilla").withAutor("Alas Clarín, Leopoldo")
                .withRecursoImagen(R.drawable.avecilla).withUrlAudio(SERVIDOR + "avecilla.mp3")
                .withGenero(Libro.G_S_XIX).withNuevo(true).withLeido(false).withColorApagado(-1)
                .withColorVibrante(-1).build());
        libros.add(new LibroBuilder().withTitulo("Divina Comedia").withAutor("Dante")
                .withRecursoImagen(R.drawable.divinacomedia).withUrlAudio(SERVIDOR + "divina_comedia.mp3")
                .withGenero(Libro.G_EPICO).withNuevo(true).withLeido(false).withColorApagado(-1)
                .withColorVibrante(-1).build());
        libros.add(new LibroBuilder().withTitulo("Viejo Pancho, El").withAutor("Alonso y Trelles, José")
                .withRecursoImagen(R.drawable.viejo_pancho).withUrlAudio(SERVIDOR + "viejo_pancho.mp3")
                .withGenero(Libro.G_S_XIX).withNuevo(true).withLeido(true).withColorApagado(-1)
                .withColorVibrante(-1).build());
        libros.add(new LibroBuilder().withTitulo("Canción de Rolando").withAutor("Anónimo")
                .withRecursoImagen(R.drawable.cancion_rolando).withUrlAudio(SERVIDOR + "cancion_rolando.mp3")
                .withGenero(Libro.G_EPICO).withNuevo(false).withLeido(true).withColorApagado(-1)
                .withColorVibrante(-1).build());
        libros.add(new LibroBuilder().withTitulo("Matrimonio de sabuesos").withAutor("Agatha Christie")
                .withRecursoImagen(R.drawable.matrimonio_sabuesos).withUrlAudio(SERVIDOR + "matrim_sabuesos.mp3")
                .withGenero(Libro.G_SUSPENSE).withNuevo(false).withLeido(true).withColorApagado(-1)
                .withColorVibrante(-1).build());
        libros.add(new LibroBuilder().withTitulo("La Iliada").withAutor("Homero")
                .withRecursoImagen(R.drawable.iliada).withUrlAudio(SERVIDOR + "la_iliada.mp3")
                .withGenero(Libro.G_EPICO).withNuevo(true).withLeido(false).withColorApagado(-1)
                .withColorVibrante(-1).build());
        return libros;
    }

    public static class LibroBuilder {
        private String titulo = "";
        private String autor = "anónimo";
        private int recursoImagen = R.drawable.sin_portada;
        private String urlAudio = "";
        private String genero = G_TODOS;
        private boolean nuevo = true;
        private boolean leido = false;
        private int colorVibrante = -1;
        private int colorApagado = -1;


        public LibroBuilder withTitulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public LibroBuilder withAutor(String autor) {
            this.autor = autor;
            return this;
        }

        public LibroBuilder withRecursoImagen(int recursoImagen) {
            this.recursoImagen= recursoImagen;
            return this;
        }

        public LibroBuilder withUrlAudio(String urlAudio){
            this.urlAudio = urlAudio;
            return this;
        }

        public LibroBuilder withGenero(String genero){
            this.genero= genero;
            return this;
        }

        public LibroBuilder withNuevo(boolean nuevo){
            this.nuevo = nuevo;
            return this;
        }

        public LibroBuilder withLeido(boolean leido){
            this.leido = leido;
            return this;
        }

         public LibroBuilder withColorVibrante(int colorVibrante){
            this.colorVibrante = colorVibrante;
            return this;
           }

        private LibroBuilder withColorApagado(int colorApagado){
            this.colorApagado = colorApagado;
            return this;
        }

        public Libro build() { return new Libro(titulo, autor, recursoImagen, urlAudio, genero, nuevo, leido, colorVibrante, colorApagado); }
    }



}





