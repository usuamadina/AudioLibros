package com.example.audiolibros;

/**
 * Created by usuwi on 09/02/2017.
 */

public class BooksRepository {
    private final LibroStorage libroStorage;

    public BooksRepository(LibroStorage libroStorage) {
        this.libroStorage = libroStorage;
    }

    public String getLastBook() {
        return libroStorage.getLastBook();
    }

    public boolean hasLastBook(){
        return libroStorage.hasLastBook();
    }

    public void saveLastBook(String key){
        libroStorage.saveLastBook(key);
    }
}