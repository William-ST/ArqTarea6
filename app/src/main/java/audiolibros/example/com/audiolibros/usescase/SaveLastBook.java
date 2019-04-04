package audiolibros.example.com.audiolibros.usescase;

import audiolibros.example.com.audiolibros.LibroStorage;

/**
 * Created by everis on 4/04/19.
 */
public class SaveLastBook {

    private final LibroStorage librosStorage;

    public SaveLastBook(LibroStorage librosStorage) {
        this.librosStorage = librosStorage;
    }
    public void execute(int id) {
        librosStorage.saveLastBook(id);
    }
}
