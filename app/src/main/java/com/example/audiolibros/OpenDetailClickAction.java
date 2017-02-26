package com.example.audiolibros;

import android.app.Activity;

/**
 * Created by usuwi on 30/01/2017.
 */

public class OpenDetailClickAction implements ClickAction {
    private final MainActivity mainActivity;

    public OpenDetailClickAction(Activity mainActivity) {
        this.mainActivity = (MainActivity) mainActivity;
    }

    @Override
    public void execute(int posicion) {


    }
    @Override
    public void execute(final String key){
        mainActivity.mostrarDetalle(key);
    }
}
