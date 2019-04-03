package audiolibros.example.com.audiolibros;

import android.content.Context;

import java.util.List;

public class LibrosSingleton {

    private static LibrosSingleton ourInstance;

    public static LibrosSingleton getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new LibrosSingleton(context);
        }
        return ourInstance;
    }

    private List<Libro> listaLibros;
    private AdaptadorLibrosFiltro adaptador;

    private LibrosSingleton(Context context) {
        listaLibros = Libro.ejemploLibros();
        adaptador = new AdaptadorLibrosFiltro(context, listaLibros);
    }

    public List<Libro> getListaLibros() {
        return listaLibros;
    }

    public AdaptadorLibrosFiltro getAdaptador() {
        return adaptador;
    }

    public Libro getLibroById(int id) {
        Libro libro = Libro.LIBRO_EMPTY;
        if (getListaLibros().size() > id) {
            libro = getListaLibros().get(id);
        }
        return libro;
    }
}
