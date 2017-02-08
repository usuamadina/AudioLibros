package com.example.audiolibros;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import java.util.Vector;

/**
 * Created by usuwi on 30/01/2017.
 */

public class OpenContextualMenuClickAction implements ClickAction {

    private final Activity activity;
    private Vector<Libro> vectorLibros;
    private AdaptadorLibrosFiltro adapter;
    private View view;

    public OpenContextualMenuClickAction(Activity activity, Vector<Libro> vectorLibros, AdaptadorLibrosFiltro adapter, View v) {
        this.activity = activity;
        this.vectorLibros = vectorLibros;
        this.adapter = adapter;
        this.view = v;

    }


    @Override
    public void execute(int position) {
        Log.d("LongClick posicion:", String.valueOf(position));

        final int id = position;

        AlertDialog.Builder menu = new AlertDialog.Builder(activity);
        CharSequence[] opciones = {"Compartir", "Borrar ", "Insertar"};
        menu.setItems(opciones, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int opcion) {
                switch (opcion) {
                    case 0: //Compartir
                        Libro libro = vectorLibros.elementAt(id);
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_SUBJECT, libro.titulo);
                        i.putExtra(Intent.EXTRA_TEXT, libro.urlAudio);

                        activity.startActivity(Intent.createChooser(i, "Compartir"));
                        break;
                    case 1: //Borrar
                        Snackbar.make(view, "¿Estás seguro?", Snackbar.LENGTH_LONG).setAction("SI", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                       /* Animation anim = AnimationUtils.loadAnimation(actividad, R.anim.menguar);
                                        anim.setAnimationListener(SelectorFragment.this);
                                        v.startAnimation(anim);*/
                                adapter.borrar(id);
                                adapter.notifyItemRemoved(id);

                            }
                        }).show();
                        break;
                    case 2: //Insertar
                        adapter.insertar(adapter.getItem(id));
                        adapter.notifyItemInserted(0);
                        Snackbar.make(view, "Libro insertado", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        }).show();
                        break;
                }
            }
        });

        menu.create().show();


    }
}


