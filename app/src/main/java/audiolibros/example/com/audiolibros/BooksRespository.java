package audiolibros.example.com.audiolibros;

public class BooksRespository {

    private final LibroStorage librosStorage;

    public BooksRespository(LibroStorage librosStorage) {
        this.librosStorage = librosStorage;
    }

    public int getLastBook() {
        return librosStorage.getLastBook();
    }

    public void saveLastBook(int id) {
        librosStorage.saveLastBook(id);
    }

    public boolean hasLastBook() {
        return librosStorage.hasLastBook();
    }

    public Libro getBookById(int id) {
        return this.librosStorage.getBookById(id);
    }

}
