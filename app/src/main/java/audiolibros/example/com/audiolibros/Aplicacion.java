package audiolibros.example.com.audiolibros;

import android.app.Application;

import java.util.List;

/**
 * Created by William_ST on 03/02/19.
 */

public class Aplicacion extends Application {

    private List<Libro> listaLibros;
    private AdaptadorLibros adaptador;

    @Override
    public void onCreate() {
        super.onCreate();
        listaLibros = Libro.ejemploLibros();
        adaptador = new AdaptadorLibros(this, listaLibros);
    }

    public AdaptadorLibros getAdaptador() {
        return adaptador;
    }

    public List<Libro> getListaLibros() {
        return listaLibros;
    }

}