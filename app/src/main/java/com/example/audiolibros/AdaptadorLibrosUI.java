package com.example.audiolibros;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.audiolibros.ClickAction;
import com.example.audiolibros.EmptyClickAction;
import com.example.audiolibros.Libro;
import com.example.audiolibros.LibrosSingleton;
import com.example.audiolibros.R;
import com.example.audiolibros.VolleySingleton;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class AdaptadorLibrosUI extends FirebaseRecyclerAdapter<Libro, AdaptadorLibrosUI.ViewHolder> {

    private LayoutInflater inflador;
    protected DatabaseReference booksReference;
    private Context contexto;
    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;
    private ClickAction clickAction = new EmptyClickAction();
    private VolleySingleton volleySingleton = VolleySingleton.getInstance(contexto);
    private ArrayList<String> keys;
    private ArrayList<DataSnapshot> items;


    public void setClickAction(ClickAction clickAction) {
        this.clickAction = clickAction;
    }

    public AdaptadorLibrosUI(Context contexto, DatabaseReference reference) {
        super(Libro.class, R.layout.elemento_selector, AdaptadorLibrosUI.ViewHolder.class, reference);
        keys = new ArrayList<String>();
        items = new ArrayList<DataSnapshot>();
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //this.vectorLibros = vectorLibros;
        this.booksReference = reference;
        this.contexto = contexto;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    public void setOnItemLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView portada;
        public TextView titulo;

        public ViewHolder(View itemView) {
            super(itemView);
            portada = (ImageView) itemView.findViewById(R.id.portada);
            portada.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            titulo = (TextView) itemView.findViewById(R.id.titulo);

        }
    }


    @Override
    public AdaptadorLibrosUI.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = inflador.inflate(R.layout.elemento_selector, null);

        v.setOnLongClickListener(onLongClickListener);
        return new ViewHolder(v);
    }


    @Override
    public void populateViewHolder(final AdaptadorLibrosUI.ViewHolder holder, Libro libro, final int posicion) {


        volleySingleton.getLectorImagenes().get(libro.getUrlImagen(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isInmediate) {
                Bitmap bitmap = response.getBitmap();
                holder.portada.setImageBitmap(bitmap);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                holder.portada.setImageResource(R.drawable.books);
            }

        });
        holder.titulo.setText(libro.getTitulo());
        holder.itemView.setScaleX(1);
        holder.itemView.setScaleY(1);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAction.execute(getItemKey(posicion));
            }
        });
    }

    public String getItemKey(int pos) {
        return keys.get(pos);
    }

    public Libro getItemByKey(String key) {
        int index = keys.indexOf(key);
        if (index != -1) {
            return items.get(index).getValue(Libro.class);
        } else {
            return Libro.LIBRO_EMPTY;
        }
    }


}
