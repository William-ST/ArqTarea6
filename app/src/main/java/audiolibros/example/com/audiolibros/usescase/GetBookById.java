package audiolibros.example.com.audiolibros.usescase;

import audiolibros.example.com.audiolibros.BooksRespository;
import audiolibros.example.com.audiolibros.Libro;

/**
 * Created by everis on 4/04/19.
 */
public class GetBookById {

    private final BooksRespository booksRespository;

    public GetBookById(BooksRespository booksRespository) {
        this.booksRespository = booksRespository;
    }

    public Libro execute(int id) {
        return booksRespository.getBookById(id);
    }

}
