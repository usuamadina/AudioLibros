package com.example.audiolibros.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.audiolibros.AdaptadorLibros;
import com.example.audiolibros.AdaptadorLibrosFiltro;
import com.example.audiolibros.Libro;
import com.example.audiolibros.LibrosSingleton;
import com.example.audiolibros.MainActivity;
import com.example.audiolibros.OpenContextualMenuClickAction;
import com.example.audiolibros.OpenDetailClickAction;
import com.example.audiolibros.R;
import com.example.audiolibros.SearchObservable;

import java.util.Vector;

/**
 * Created by usuwi on 21/12/2016.
 */

public class SelectorFragment extends Fragment implements Animation.AnimationListener {


    private Activity actividad;
    private RecyclerView recyclerView;
    private AdaptadorLibrosFiltro adaptador;
    private Vector<Libro> vectorLibros;
    private LibrosSingleton librosSingleton;

    @Override
    public void onAttach(Context contexto){
        super.onAttach(contexto);
        librosSingleton = LibrosSingleton.getInstance(getContext());

        if (contexto instanceof Activity) {
            this.actividad = (Activity) contexto;

            adaptador = librosSingleton.getAdaptador();
            vectorLibros = librosSingleton.getVectorLibros();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        View vista = inflador.inflate(R.layout.fragment_selector, contenedor, false);
        setHasOptionsMenu(true);

        recyclerView = (RecyclerView) vista.findViewById(R.id.recycler_View);
        recyclerView.setLayoutManager(new GridLayoutManager(actividad, 2));
        recyclerView.setAdapter(adaptador);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(800);
        animator.setMoveDuration(800);
        recyclerView.setItemAnimator(animator);


        adaptador.setClickAction(new OpenDetailClickAction((MainActivity)getActivity()));
        adaptador.setLongClickAction(new OpenContextualMenuClickAction(actividad,vectorLibros,adaptador,vista));

        return vista;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_selector, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_buscar);
        SearchView searchView = (SearchView) searchItem.getActionView();

        SearchObservable searchObservable = new SearchObservable();
        searchObservable.addObserver(adaptador);
        searchView.setOnQueryTextListener(searchObservable);



        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                adaptador.setBusqueda("");
                adaptador.notifyDataSetChanged();
                return true; // Para permitir cierre
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true; // Para permitir expansión
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_ultimo) {
            ((MainActivity) actividad).irUltimoVisitado();
            return true;
        } else if (id == R.id.menu_buscar) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        ((MainActivity) getActivity()).mostrarElementos(true);
        super.onResume();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        adaptador.notifyDataSetChanged();

    }
}
