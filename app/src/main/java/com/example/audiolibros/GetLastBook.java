package com.example.audiolibros;

/**
 * Created by usuwi on 09/02/2017.
 */

public class GetLastBook {
    private final BooksRepository booksRepository;

    public GetLastBook(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public int execute() {
        return booksRepository.getLastBook();
    }
}
