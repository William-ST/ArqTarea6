package audiolibros.example.com.audiolibros.usescase;

import audiolibros.example.com.audiolibros.BooksRespository;
import audiolibros.example.com.audiolibros.LibroStorage;

/**
 * Created by everis on 4/04/19.
 */
public class SaveLastBook {

    private final BooksRespository booksRespository;

    public SaveLastBook(BooksRespository booksRespository) {
        this.booksRespository = booksRespository;
    }
    public void execute(int id) {
        booksRespository.saveLastBook(id);
    }
}
