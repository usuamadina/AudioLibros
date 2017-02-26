package com.example.audiolibros;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * Created by usuwi on 23/12/2016.
 */

public class AdaptadorLibrosFiltro extends AdaptadorLibros implements Observer {

    private Vector<Integer> indiceFiltro; // Índice en vectorSinFiltro de
    // Cada elemento de vectorLibros
    private String busqueda = ""; // Búsqueda sobre autor o título
    private String genero = ""; // Género seleccionado
    private boolean novedad = false; // Si queremos ver solo novedades
    private boolean leido = false; // Si queremos ver solo leidos
    private int librosUltimoFiltro;
    private LecturasSingleton lecturasSingleton;

    public AdaptadorLibrosFiltro(Context contexto, DatabaseReference reference) {
        super(contexto, reference);
        lecturasSingleton = LecturasSingleton.getInstance(contexto);
        recalculaFiltro();
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda.toLowerCase();
        recalculaFiltro();
    }

    public void setGenero(String genero) {
        this.genero = genero;
        recalculaFiltro();
    }

    public void setNovedad(boolean novedad) {
        this.novedad = novedad;
        recalculaFiltro();
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
        recalculaFiltro();
    }

    public void recalculaFiltro() {

        indiceFiltro = new Vector<Integer>();
        librosUltimoFiltro = super.getItemCount();
        for (int i = 0; i < librosUltimoFiltro; i++) {
            Libro libro = super.getItem(i);
            if ((libro.getTitulo().toLowerCase().contains(busqueda) || libro.getAutor().toLowerCase().contains(busqueda))
                    && (libro.getGenero().startsWith(genero))
                    && (!novedad || (novedad && libro.getNovedad()))
                    && (!leido || (leido && lecturasSingleton.libroLeido(super.getItemKey(i))))) {
                indiceFiltro.add(i);
                Log.d("recalculaFiltro", "leido"+lecturasSingleton.libroLeido(super.getItemKey(i)));
            }
        }
    }

    //libro.leidoPor(FirebaseAuth.getInstance().getCurrentUser().getUid())

    public Libro getItem(int posicion) {
        if (librosUltimoFiltro != super.getItemCount()) {
            recalculaFiltro();
        }
        return super.getItem(indiceFiltro.elementAt(posicion));
    }

    public int getItemCount() {
        if (librosUltimoFiltro != super.getItemCount()) {
            recalculaFiltro();
        }
        return indiceFiltro.size();
    }

    public long getItemId(int posicion) {
        //Este no cambia
        return indiceFiltro.elementAt(posicion);
        // Se incluye por claridad
    }

    public Libro getItemById(int id) {
        return super.getItem(id);
    }

    public void borrar(int posicion) {
        DatabaseReference reference = getRef(indiceFiltro.elementAt(posicion));
        reference.removeValue();
        recalculaFiltro();
    }


    public void insertar(Libro libro) {
        booksReference.push().setValue(libro);
        recalculaFiltro();
    }

    @Override
    public void update(Observable observable, Object data) {
        setBusqueda((String) data);
        notifyDataSetChanged();

    }

    public String getItemKey(int posicion) {
        if (librosUltimoFiltro != super.getItemCount()) {
            recalculaFiltro();
        }
        int id = indiceFiltro.elementAt(posicion);
        return super.getItemKey(id);
    }
}
