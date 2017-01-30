package com.example.audiolibros;

/**
 * Created by usuwi on 30/01/2017.
 */

public class OpenDetailClickAction implements ClickAction {
    private final MainActivity mainActivity;

    public OpenDetailClickAction(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void execute(int position) {
        mainActivity.mostrarDetalle(position);

    }
}
