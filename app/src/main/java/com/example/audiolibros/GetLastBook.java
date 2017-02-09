package com.example.audiolibros;

/**
 * Created by usuwi on 09/02/2017.
 */

public class GetLastBook {
    private final LibroStorage libroStorage;

    public GetLastBook(LibroStorage libroStorage) {
        this.libroStorage = libroStorage;
    }

    public int execute() {
        return libroStorage.getLastBook();
    }
}
