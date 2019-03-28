package audiolibros.example.com.audiolibros;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.List;

/**
 * Created by William_ST on 03/02/19.
 */

public class Aplicacion extends Application {

    private List<Libro> listaLibros;
    private AdaptadorLibrosFiltro adaptador;
    private RequestQueue colaPeticiones;
    private ImageLoader lectorImagenes;

    @Override
    public void onCreate() {
        super.onCreate();
        listaLibros = Libro.ejemploLibros();
        adaptador = new AdaptadorLibrosFiltro(this, listaLibros);
        colaPeticiones = Volley.newRequestQueue(this);
        lectorImagenes = new ImageLoader(colaPeticiones,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> cache =
                            new LruCache<String, Bitmap>(10);

                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }

                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }
                });
    }

    public AdaptadorLibrosFiltro getAdaptador() {
        return adaptador;
    }

    public List<Libro> getListaLibros() {
        return listaLibros;
    }

    public Libro getLibroById(int id) {
        Libro libro = Libro.LIBRO_EMPTY;
        if (getListaLibros() != null && getListaLibros().size() > id) {
            libro = getListaLibros().get(id);
        }
        return libro;
    }

    public ImageLoader getLectorImagenes() {
        return lectorImagenes;
    }

}