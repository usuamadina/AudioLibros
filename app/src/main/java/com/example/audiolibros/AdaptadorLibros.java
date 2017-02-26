package com.example.audiolibros;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by usuwi on 15/12/2016.
 */

public class AdaptadorLibros extends RecyclerView.Adapter<AdaptadorLibros.ViewHolder>
        implements ChildEventListener {
    private LayoutInflater inflador;
    protected DatabaseReference booksReference;
    //protected Vector<Libro> vectorLibros;
    private Context contexto;
    private int colorVibrante = -1, colorApagado = -1;

    private ArrayList<String> keys;
    private ArrayList<DataSnapshot> items;

    private ClickAction clickAction = new EmptyClickAction();
    private ClickAction longClickAction = new EmptyClickAction();

    public void setLongClickAction(ClickAction longClickAction) {
        this.longClickAction = longClickAction;
    }

    public void setClickAction(ClickAction clickAction) {
        this.clickAction = clickAction;
    }

    public AdaptadorLibros(Context contexto, DatabaseReference booksReference) {
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        keys = new ArrayList<String>();
        items = new ArrayList<DataSnapshot>();

        this.booksReference = booksReference;
        this.contexto = contexto;
        booksReference.addChildEventListener(this);

    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        items.add(dataSnapshot);
        keys.add(dataSnapshot.getKey());
        notifyItemInserted(getItemCount() - 1);

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        String key = dataSnapshot.getKey();
        int index = keys.indexOf(key);
        if (index != -1) {
            items.set(index, dataSnapshot);
            notifyItemChanged(index, dataSnapshot.getValue(Libro.class));
        }

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        String key = dataSnapshot.getKey();
        int index = keys.indexOf(key);
        if (index != -1) {
            keys.remove(index);
            items.remove(index);
            notifyItemRemoved(index);

        }
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

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

    public DatabaseReference getRef(int pos) {
        return items.get(pos).getRef();
    }

    public Libro getItem(int pos) {
        return items.get(pos).getValue(Libro.class);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.elemento_selector, null);
        return new ViewHolder(v);

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Libro libro = getItem(position);

        VolleySingleton volleySingleton = VolleySingleton.getInstance(contexto);
        volleySingleton.getLectorImagenes().get(libro.getUrlImagen(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Bitmap bitmap = response.getBitmap();
                holder.portada.setImageBitmap(bitmap);
                if (bitmap != null) {
                    holder.portada.setImageBitmap(bitmap);
                    Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                        public void onGenerated(Palette palette) {
                            holder.itemView.setBackgroundColor(palette.getLightMutedColor(0));
                            holder.titulo.setBackgroundColor(palette.getLightVibrantColor(0));
                            holder.portada.invalidate();
                            // Use generated instance
                        }
                    });


                }


            }

            @Override
            public void onErrorResponse(VolleyError error) {
                holder.portada.setImageResource(R.drawable.books);
            }
        });

        Bitmap bitmap = volleySingleton.getBitmap();
        if (bitmap != null && !bitmap.isRecycled() && libro.getColorApagado() == -1 && libro.getColorVibrante() == -1) {

            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    libro.setColorApagado(palette.getLightMutedColor(0));
                    libro.setColorVibrante(palette.getLightVibrantColor(0));

                    holder.itemView.setBackgroundColor(libro.getColorApagado());
                    holder.titulo.setBackgroundColor(libro.getColorVibrante());
                    holder.portada.invalidate();
                }
            });

        } else {
            holder.itemView.setBackgroundColor(libro.getColorApagado());
            holder.titulo.setBackgroundColor(libro.getColorVibrante());
        }

        holder.titulo.setText(libro.getTitulo());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAction.execute(getItemKey(position));
            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickAction.execute(position);
                return true;
            }
        });


    }


    public void activaEscuchadorLibros() {
        FirebaseDatabase.getInstance().goOnline();
    }

    public void desactivaEscuchadorLibros() {
        FirebaseDatabase.getInstance().goOffline();
    }

    public String getItemKey(int pos) {
        return keys.get(pos);
    }

    public Libro getItemByKey(String key) {
        int index = keys.indexOf(key);
        if (index != -1) {
            return items.get(index).getValue(Libro.class);
        } else {
            return null;
        }
    }




   /* @Override
    public int getItemCount() {
        return vectorLibros.size();
    }*/


}
