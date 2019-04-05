package audiolibros.example.com.audiolibros.usescase;

import audiolibros.example.com.audiolibros.BooksRespository;
import audiolibros.example.com.audiolibros.LibroStorage;

/**
 * Created by everis on 4/04/19.
 */
public class GetLastBook {

    private final BooksRespository booksRespository;

    public GetLastBook(BooksRespository booksRespository) {
        this.booksRespository = booksRespository;
    }

    public int execute() {
        return booksRespository.getLastBook();
    }

}
