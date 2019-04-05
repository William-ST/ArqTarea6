package audiolibros.example.com.audiolibros.usescase;

import audiolibros.example.com.audiolibros.BooksRespository;
import audiolibros.example.com.audiolibros.LibroStorage;

/**
 * Created by everis on 4/04/19.
 */
public class ValidExistLasBook {

    private final BooksRespository booksRespository;

    public ValidExistLasBook(BooksRespository booksRespository) {
        this.booksRespository = booksRespository;
    }
    public boolean execute() {
        return booksRespository.hasLastBook();
    }

}
