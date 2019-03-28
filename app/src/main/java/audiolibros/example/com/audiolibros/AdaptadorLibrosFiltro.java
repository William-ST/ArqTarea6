package audiolibros.example.com.audiolibros;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by William_ST on 08/02/19.
 */

public class AdaptadorLibrosFiltro extends AdaptadorLibros implements Observer {

    private List<Libro> listaSinFiltro;
    private List<Integer> indiceFiltro;
    private String busqueda = "";
    private String genero = "";
    private boolean novedad = false;
    private boolean leido = false;

    public AdaptadorLibrosFiltro(Context contexto, List<Libro> listaLibros) {
        super(contexto, listaLibros);
        listaSinFiltro = listaLibros;
        recalculaFiltro();
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda.toLowerCase();
        recalculaFiltro();
    }

    public void setGenero(String genero) {
        this.genero = genero;
        recalculaFiltro();
    }

    public void setNovedad(boolean novedad) {
        this.novedad = novedad;
        recalculaFiltro();
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
        recalculaFiltro();
    }

    public void recalculaFiltro() {
        listaLibros = new ArrayList<Libro>();
        listaLibros.clear();
        indiceFiltro = new ArrayList<Integer>();
        indiceFiltro.clear();
        for (int i = 0; i < listaSinFiltro.size(); i++) {
            Libro libro = listaSinFiltro.get(i);
            if ((libro.titulo.toLowerCase().contains(busqueda) ||
                    libro.autor.toLowerCase().contains(busqueda)) && (libro.genero.startsWith(genero))
                    && (!novedad || (novedad && libro.novedad))
                    && (!leido || (leido && libro.leido))) {
                listaLibros.add(libro);
                indiceFiltro.add(i);
            }
        }
    }

    public Libro getItem(int posicion) {
        return listaSinFiltro.get(indiceFiltro.get(posicion));
    }

    public long getItemId(int posicion) {
        return indiceFiltro.get(posicion);
    }

    public void borrar(int posicion) {
        listaSinFiltro.remove((int) getItemId(posicion));
        recalculaFiltro();
    }

    public void insertar(Libro libro) {
        listaSinFiltro.add(0, libro);
        recalculaFiltro();
    }

    @Override
    public void update(Observable observable, Object data) { setBusqueda((String) data);
        notifyDataSetChanged();
    }

}