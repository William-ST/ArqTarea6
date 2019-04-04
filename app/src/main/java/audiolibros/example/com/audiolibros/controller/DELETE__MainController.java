package audiolibros.example.com.audiolibros.controller;

import audiolibros.example.com.audiolibros.LibroStorage;

public class DELETE__MainController {

    LibroStorage libroStorage;

    public DELETE__MainController(LibroStorage libroStorage) {
        this.libroStorage = libroStorage;
    }

    public void saveLastBook(int id) {
        libroStorage.saveLastBook(id);
    }

    public boolean hasLastBook() {
        return libroStorage.hasLastBook();
    }

    public int getLastBook() {
        return libroStorage.getLastBook();
    }

}
