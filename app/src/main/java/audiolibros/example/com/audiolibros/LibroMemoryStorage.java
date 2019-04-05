package audiolibros.example.com.audiolibros;

import android.content.Context;

public class LibroMemoryStorage implements LibroStorage {

    private Context context;

    public LibroMemoryStorage(Context context) {
        this.context = context;
    }

    @Override
    public boolean hasLastBook() {
        // not implemting but not use
        return false;
    }

    @Override
    public int getLastBook() {
        // not implemting but not use
        return 0;
    }

    @Override
    public void saveLastBook(int id) {
        // not implemting but not use
    }

    @Override
    public Libro getBookById(int id) {
        return LibrosSingleton.getInstance(context).getLibroById(id);
    }

}
