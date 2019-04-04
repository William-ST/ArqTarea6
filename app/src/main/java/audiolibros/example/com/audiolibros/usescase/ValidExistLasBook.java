package audiolibros.example.com.audiolibros.usescase;

import audiolibros.example.com.audiolibros.LibroStorage;

/**
 * Created by everis on 4/04/19.
 */
public class ValidExistLasBook {

    private final LibroStorage librosStorage;

    public ValidExistLasBook(LibroStorage librosStorage) {
        this.librosStorage = librosStorage;
    }
    public boolean execute() {
        return librosStorage.hasLastBook();
    }

}
