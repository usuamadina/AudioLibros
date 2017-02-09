package com.example.audiolibros;

/**
 * Created by usuwi on 09/02/2017.
 */

public class HasLastBook {
    private final LibroStorage libroStorage;

    public HasLastBook(LibroStorage libroStorage) {
        this.libroStorage = libroStorage;
    }

    public boolean execute() {
        return libroStorage.hasLastBook();
    }
}
