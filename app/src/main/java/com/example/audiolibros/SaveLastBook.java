package com.example.audiolibros;

/**
 * Created by usuwi on 09/02/2017.
 */

public class SaveLastBook {
    private final LibroStorage libroStorage;

    public SaveLastBook(LibroStorage libroStorage) {
        this.libroStorage = libroStorage;
    }

    public void execute(int id) {
        libroStorage.saveLastBook(id);
    }

}
