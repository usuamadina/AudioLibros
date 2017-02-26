package com.example.audiolibros;

/**
 * Created by usuwi on 09/02/2017.
 */

public class SaveLastBook {
    private final BooksRepository booksRepository;

    public SaveLastBook(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public void execute(String key) {
        booksRepository.saveLastBook(key);
    }

}
