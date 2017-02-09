package com.example.audiolibros;

/**
 * Created by usuwi on 09/02/2017.
 */

public class HasLastBook {
    private final BooksRepository booksRepository;

    public HasLastBook(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public boolean execute() {
        return booksRepository.hasLastBook();
    }
}
