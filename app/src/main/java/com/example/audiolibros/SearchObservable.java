package com.example.audiolibros;

import android.widget.SearchView;

import java.util.Observable;

/**
 * Created by usuwi on 29/01/2017.
 */

public class SearchObservable extends Observable implements android.support.v7.widget.SearchView.OnQueryTextListener {
    @Override
    public boolean onQueryTextSubmit(String query) {
        setChanged();
        notifyObservers(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
