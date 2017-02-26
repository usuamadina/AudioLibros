package com.example.audiolibros;

/**
 * Created by usuwi on 09/02/2017.
 */

public class MainPresenter {
    private final HasLastBook hasLastBook;
    private final GetLastBook getLastBook;
    private final SaveLastBook saveLastBook;
    private final LibroStorage libroStorage;
    private final View view;

    public MainPresenter(HasLastBook hasLastBook, GetLastBook getLastBook, SaveLastBook saveLastBook, LibroStorage libroStorage, View view) {
        this.getLastBook = getLastBook;
        this.hasLastBook = hasLastBook;
        this.saveLastBook = saveLastBook;
        this.libroStorage = libroStorage;
        this.view = view;
    }

    public void clickFavoriteButton() {
        if (libroStorage.hasLastBook()) {
            view.mostrarFragmentDetalle(libroStorage.getLastBook());
        } else {
            view.mostrarNoUltimaVisita();
        }
    }

    public void openDetalle(String key) {
        saveLastBook.execute(key);
        view.mostrarFragmentDetalle(key);
    }

    public interface View {
        void mostrarFragmentDetalle(String lastBook);
        void mostrarNoUltimaVisita();
    }
}
