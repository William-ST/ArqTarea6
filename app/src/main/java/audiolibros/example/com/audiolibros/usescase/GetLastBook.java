package audiolibros.example.com.audiolibros.usescase;

import audiolibros.example.com.audiolibros.LibroStorage;

/**
 * Created by everis on 4/04/19.
 */
public class GetLastBook {

    private final LibroStorage librosStorage;

    public GetLastBook(LibroStorage librosStorage) {
        this.librosStorage = librosStorage;
    }

    public int execute() {
        return librosStorage.getLastBook();
    }

}
