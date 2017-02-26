package com.example.audiolibros;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DatabaseReference;

import java.util.Vector;

/**
 * Created by usuwi on 30/01/2017.
 */

public class OpenContextualMenuClickAction implements ClickAction {

    private final Activity activity;
    private DatabaseReference reference;
    private AdaptadorLibrosFiltro adapter;
    private RecyclerView recyclerView;
    private View view;

    public OpenContextualMenuClickAction(Activity activity, DatabaseReference reference, AdaptadorLibrosFiltro adapter, View v) {
        this.activity = activity;
        this.reference = reference;
        this.adapter = adapter;
        this.view = v;

    }

    @Override
    public void execute (String key){

    }


    @Override
    public void execute(final int posicion) {
       // Log.d("LongClick posicion:", String.valueOf(position));

        AlertDialog.Builder menu = new AlertDialog.Builder(activity);
        CharSequence[] opciones = {"Compartir", "Borrar ", "Insertar"};
        menu.setItems(opciones, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int opcion) {
                switch (opcion) {
                    case 0: //Compartir
                        Libro libro = adapter.getItemById(posicion);
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_SUBJECT, libro.getTitulo());
                        i.putExtra(Intent.EXTRA_TEXT, libro.getUrlAudio());

                        activity.startActivity(Intent.createChooser(i, "Compartir"));
                        break;
                    case 1: //Borrar
                        Snackbar.make(view, "¿Estás seguro?", Snackbar.LENGTH_LONG).setAction("SI", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                       /* Animation anim = AnimationUtils.loadAnimation(actividad, R.anim.menguar);
                                        anim.setAnimationListener(SelectorFragment.this);
                                        v.startAnimation(anim);*/
                                adapter.borrar(posicion);
                                adapter.notifyItemRemoved(posicion);

                            }
                        }).show();
                        break;
                    case 2: //Insertar
                         Libro book = adapter.getItemById(posicion);
                         adapter.insertar(book);
                       // adapter.insertar(adapter.getItem(recyclerView.getChildAdapterPosition(view)));
                        adapter.notifyItemInserted(adapter.getItemCount()+1);
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


