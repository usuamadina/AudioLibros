package com.example.audiolibros;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.Vector;

/**
 * Created by usuwi on 15/12/2016.
 */

public class AdaptadorLibros extends RecyclerView.Adapter<AdaptadorLibros.ViewHolder> {
    private LayoutInflater inflador;
    protected Vector<Libro> vectorLibros;
    private Context contexto;
    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;
    private int colorVibrante = -1, colorApagado = -1;

    public AdaptadorLibros(Context contexto, Vector<Libro> vectorLibros) {
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.vectorLibros = vectorLibros;
        this.contexto = contexto;
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnItemLongClickListener


            (View.OnLongClickListener onLongClickListener) {
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.elemento_selector, null);
        v.setOnClickListener(onClickListener);
        v.setOnLongClickListener(onLongClickListener);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Libro libro = vectorLibros.elementAt(position);
        holder.portada.setImageResource(libro.recursoImagen);

        Bitmap bitmap = BitmapFactory.decodeResource(contexto.getResources(), libro.recursoImagen);
        if (bitmap != null && !bitmap.isRecycled() && libro.getColorApagado()==-1 && libro.getColorVibrante()==-1){
           // holder.portada.setImageBitmap(bitmap);
            //generamos la paleta de modo asíncrono
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    libro.setColorApagado(palette.getLightMutedColor(0));
                    libro.setColorVibrante(palette.getLightVibrantColor(0));

                   holder.itemView.setBackgroundColor(libro.getColorApagado());
                   holder.titulo.setBackgroundColor(libro.getColorVibrante());
                  // holder.portada.invalidate();
                }
            });

        }
        else{
            holder.itemView.setBackgroundColor(libro.getColorApagado());
            holder.titulo.setBackgroundColor(libro.getColorVibrante());
        }


       /* En caso de cargar imagenes mediante librería Volley
         Aplicacion aplicacion = (Aplicacion) contexto.getApplicationContext();
       aplicacion.getLectorImagenes().get(libro.urlImagen, new ImageLoader.ImageListener() {
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

                   // Palette palette = Palette.from(bitmap).generate();

                }


            }

            @Override
            public void onErrorResponse(VolleyError error) {
                holder.portada.setImageResource(R.drawable.books);
            }
        });*/
        holder.titulo.setText(libro.titulo);


      /*  //Extraer color principal del bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(contexto.getResources(), libro.urlImagen);
        Palette palette = Palette.from(bitmap).generate();

        holder.itemView.setBackground(palette.getLightMutedColor(0));
        holder.titulo.setBackground(palette.getVibrantColor(0));*/


    }


    @Override
    public int getItemCount() {
        return vectorLibros.size();
    }


}
