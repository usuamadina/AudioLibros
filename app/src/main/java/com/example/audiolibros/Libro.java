package com.example.audiolibros;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by usuwi on 15/12/2016.
 */

public class Libro {
    private String titulo;
    private String autor;
    private String urlImagen;
    private String urlAudio;

    private String genero;
    private Boolean novedad;
    private Map<String, Boolean> leido;

    public final static String G_TODOS = "Todos los géneros";
    public final static String G_EPICO = "Poema épico";
    public final static String G_S_XIX = "Literatura siglo XIX";
    public final static String G_SUSPENSE = "Suspense";
    public final static String[] G_ARRAY = new String[]{G_TODOS, G_EPICO, G_S_XIX, G_SUSPENSE};


    private int colorVibrante = -1;
    private int colorApagado = -1;


    public final static Libro LIBRO_EMPTY = new Libro("", "anónimo", "http://www.dcomg.upv.es/~jtomas/android/audiolibros/sin_portada.jpg", "", Libro.G_TODOS, false, -1, -1);

    public Libro(String titulo, String autor, String urlImagen, String urlAudio, String genero, Boolean novedad, int colorVibrante, int colorApagado) {
        this.titulo = titulo;
        this.autor = autor;
        this.urlImagen = urlImagen;
        this.urlAudio = urlAudio;
        this.genero = genero;
        this.novedad = novedad;
        this.leido = new HashMap<String, Boolean>();
        this.colorVibrante = colorVibrante;
        this.colorApagado = colorApagado;

    }

    public Libro(){

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getUrlAudio() {
        return urlAudio;
    }

    public void setUrlAudio(String urlAudio) {
        this.urlAudio = urlAudio;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Boolean getNovedad() {
        return novedad;
    }

    public void setNovedad(Boolean novedad) {
        this.novedad = novedad;
    }

    public Map<String, Boolean> getLeido() {
        return leido;
    }

    public void setLeido(Map<String, Boolean> leido) {
        this.leido = leido;
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


  /* public static Vector<Libro> ejemploLibros() {
        final String SERVIDOR =
                "http://www.dcomg.upv.es/~jtomas/android/audiolibros/";
        Vector<Libro> libros = new Vector<Libro>();

        libros.add(new LibroBuilder().withTitulo("Kappa").withAutor("Akutagawa")
                .withUrlImagen(SERVIDOR + "kappa.jpg").withUrlAudio(SERVIDOR + "kappa.mp3")
                .withGenero(Libro.G_S_XIX).withNuevo(false).withColorApagado(-1)
                .withColorVibrante(-1).build());
        libros.add(new LibroBuilder().withTitulo("Avecilla").withAutor("Alas Clarín, Leopoldo")
                .withUrlImagen(SERVIDOR + "avecilla.jpg").withUrlAudio(SERVIDOR + "avecilla.mp3")
                .withGenero(Libro.G_S_XIX).withNuevo(true).withColorApagado(-1)
                .withColorVibrante(-1).build());
        libros.add(new LibroBuilder().withTitulo("Divina Comedia").withAutor("Dante")
                .withUrlImagen(SERVIDOR + "divina_comedia.jpg").withUrlAudio(SERVIDOR + "divina_comedia.mp3")
                .withGenero(Libro.G_EPICO).withNuevo(true).withColorApagado(-1)
                .withColorVibrante(-1).build());
        libros.add(new LibroBuilder().withTitulo("Viejo Pancho, El").withAutor("Alonso y Trelles, José")
                .withUrlImagen(SERVIDOR + "viejo_pancho.jpg").withUrlAudio(SERVIDOR + "viejo_pancho.mp3")
                .withGenero(Libro.G_S_XIX).withNuevo(true).withColorApagado(-1)
                .withColorVibrante(-1).build());
        libros.add(new LibroBuilder().withTitulo("Canción de Rolando").withAutor("Anónimo")
                .withUrlImagen(SERVIDOR + "cancion_rolando.jpg").withUrlAudio(SERVIDOR + "cancion_rolando.mp3")
                .withGenero(Libro.G_EPICO).withNuevo(false).withColorApagado(-1)
                .withColorVibrante(-1).build());
        libros.add(new LibroBuilder().withTitulo("Matrimonio de sabuesos").withAutor("Agatha Christie")
                .withUrlImagen(SERVIDOR + "matrim_sabuesos.jpg").withUrlAudio(SERVIDOR + "matrim_sabuesos.mp3")
                .withGenero(Libro.G_SUSPENSE).withNuevo(false).withColorApagado(-1)
                .withColorVibrante(-1).build());
        libros.add(new LibroBuilder().withTitulo("La Iliada").withAutor("Homero")
                .withUrlImagen(SERVIDOR + "la_iliada.jpg").withUrlAudio(SERVIDOR + "la_iliada.mp3")
                .withGenero(Libro.G_EPICO).withNuevo(true).withColorApagado(-1)
                .withColorVibrante(-1).build());
        return libros;
    }*/

    public static class LibroBuilder {
        private String titulo = "";
        private String autor = "anónimo";
        private String urlImagen = "http://www.dcomg.upv.es/~jtomas/android/audiolibros/sin_portada.jpg";
        private String urlAudio = "";
        private String genero = G_TODOS;
        private boolean nuevo = true;
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

        public LibroBuilder withUrlImagen(String urlImagen) {
            this.urlImagen = urlImagen;
            return this;
        }

        public LibroBuilder withUrlAudio(String urlAudio) {
            this.urlAudio = urlAudio;
            return this;
        }

        public LibroBuilder withGenero(String genero) {
            this.genero = genero;
            return this;
        }

        public LibroBuilder withNuevo(boolean nuevo) {
            this.nuevo = nuevo;
            return this;
        }

        public LibroBuilder withColorVibrante(int colorVibrante) {
            this.colorVibrante = colorVibrante;
            return this;
        }

        private LibroBuilder withColorApagado(int colorApagado) {
            this.colorApagado = colorApagado;
            return this;
        }

        public Libro build() {
            return new Libro(titulo, autor, urlImagen, urlAudio, genero, nuevo, colorVibrante, colorApagado);
        }
    }

    public boolean leidoPor(String userID) {
        if (this.leido != null) {
            return this.leido.keySet().contains(userID);
        } else {
            return false;
        }
    }


}





