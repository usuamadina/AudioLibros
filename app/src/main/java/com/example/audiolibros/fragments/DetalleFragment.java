package com.example.audiolibros.fragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.android.volley.toolbox.NetworkImageView;
import com.example.audiolibros.LecturasSingleton;
import com.example.audiolibros.Libro;
import com.example.audiolibros.LibrosSingleton;
import com.example.audiolibros.MainActivity;
import com.example.audiolibros.R;
import com.example.audiolibros.VolleySingleton;

import java.io.IOException;

/**
 * Created by usuwi on 21/12/2016.
 */

public class DetalleFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnTouchListener, MediaPlayer.OnPreparedListener, MediaController.MediaPlayerControl {
    public static String ARG_ID_LIBRO = "id_libro";
    MediaPlayer mediaPlayer;
    MediaController mediaController;
    LibrosSingleton librosSingleton;
    VolleySingleton volleySingleton;
    ToggleButton toggleButton;
    private LecturasSingleton lecturasSingleton;
    private String key;


    @Override
    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        librosSingleton = LibrosSingleton.getInstance(getContext());
        volleySingleton = VolleySingleton.getInstance(getContext());
        lecturasSingleton = LecturasSingleton.getInstance(getContext());


        View vista = inflador.inflate(R.layout.fragment_detalle, contenedor, false);
        toggleButton = (ToggleButton) vista.findViewById(R.id.leidoButton);



        Bundle args = getArguments();
        if (args != null) {
            key = args.getString(ARG_ID_LIBRO);
            ponInfoLibro(key, vista);
        } else {
            ponInfoLibro("", vista);
        }


        return vista;


    }

    public void ponInfoLibro(String key) {
        ponInfoLibro(key, getView());
                       /*(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged

                    (CompoundButton buttonView, boolean isChecked) {

            }});*/


    }


    private void ponInfoLibro(String key, View vista) {
        Libro libro = librosSingleton.getAdaptador().getItemByKey(key);
        ((TextView) vista.findViewById(R.id.titulo)).setText(libro.getTitulo());
        ((TextView) vista.findViewById(R.id.autor)).setText(libro.getAutor());

        Log.d("DetalleFragment", "libroleido = " + lecturasSingleton.libroLeido(key));
        toggleButton.setChecked(lecturasSingleton.libroLeido(key));

        ((NetworkImageView) vista.findViewById(R.id.portada)).setImageUrl(libro.getUrlImagen(), volleySingleton.getLectorImagenes());

        vista.setOnTouchListener(this);
        toggleButton.setOnCheckedChangeListener(this);

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaController = new MediaController(getActivity());
        Uri audio = Uri.parse(libro.getUrlAudio());
        try {
            mediaPlayer.setDataSource(getActivity(), audio);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e("Audiolibros", "ERROR: No se puede reproducir " + audio, e);
        }

    }

   /* public void ponInfoLibro(int id) {
        ponInfoLibro(id, getView());
    }*/

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(getView().findViewById(R.id.fragment_detalle));
        mediaController.setPadding(0, 0, 0, 110);
        mediaController.setEnabled(true);
        mediaController.show();
    }

    @Override
    public boolean onTouch(View vista, MotionEvent evento) {
        mediaController.show();
        return false;
    }

    @Override
    public void onResume() {
        DetalleFragment detalleFragment = (DetalleFragment) getFragmentManager().findFragmentById(R.id.fragment_detalle);
        if (detalleFragment == null) {
            ((MainActivity) getActivity()).mostrarElementos(false);
        }
        super.onResume();
    }

    @Override
    public void onStop() {
        mediaController.hide();
        try {
            mediaPlayer.stop();
            mediaPlayer.release();
        } catch (Exception e) {
            Log.e("Audiolibros", "ERROR: Error en mediaPlayer.stop()");
        }
        super.onStop();
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        try {
            return mediaPlayer.getCurrentPosition();
        } catch (
                Exception e
                )

        {
            return 0;
        }
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d("ButtonToogle", "checked = " + toggleButton.isChecked());

        lecturasSingleton.leidoPorMi(key, isChecked);
    }


}


