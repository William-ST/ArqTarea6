package audiolibros.example.com.audiolibros;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import audiolibros.example.com.audiolibros.command.ClickAction;
import audiolibros.example.com.audiolibros.command.EmptyClickAction;
import audiolibros.example.com.audiolibros.command.EmptyLongClickAction;
import audiolibros.example.com.audiolibros.command.LongClickAction;

/**
 * Created by William_ST on 03/02/19.
 */

public class AdaptadorLibros extends RecyclerView.Adapter<AdaptadorLibros.ViewHolder> {

    private LayoutInflater inflador; //Crea Layouts a partir del XML
    protected List<Libro> listaLibros; //Lista de libros a visualizar
    private Context contexto;

    private ClickAction clickAction = new EmptyClickAction();
    private LongClickAction longClickAction = new EmptyLongClickAction();

    public void setClickAction(ClickAction clickAction) {
        this.clickAction = clickAction;
    }

    public void setLongClickAction(LongClickAction longClickAction) {
        this.longClickAction = longClickAction;
    }

    public AdaptadorLibros(Context contexto, List<Libro> listaLibros) {
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listaLibros = listaLibros;
        this.contexto = contexto;
    }

    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView portada;
        public TextView titulo;

        public ViewHolder(View itemView) {
            super(itemView);
            portada = (ImageView) itemView.findViewById(R.id.portada);
            titulo = (TextView) itemView.findViewById(R.id.titulo);
        }
    }

    // Creamos el ViewHolder con las vista de un elemento sin personalizar
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // Inflamos la vista desde el xml
        View v = inflador.inflate(R.layout.elemento_selector, null);
        return new ViewHolder(v);
    }

    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int posicion) {
        final Libro libro = listaLibros.get(posicion);
        //holder.portada.setImageResource(libro.recursoImagen);
        Aplicacion aplicacion = (Aplicacion) contexto.getApplicationContext();
        aplicacion.getLectorImagenes().get(libro.urlImagen,
                new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer
                                                   response, boolean isImmediate) {
                        Bitmap bitmap = response.getBitmap();
                        if (bitmap != null) {
                            holder.portada.setImageBitmap(bitmap);

                            if (libro.colorVibrante != -1 && libro.colorApagado != -1) {
                                holder.itemView.setBackgroundColor(libro.colorApagado);
                                holder.titulo.setBackgroundColor(libro.colorVibrante);
                                holder.portada.invalidate();
                            } else {
                                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                                    public void onGenerated(Palette palette) {
                                        libro.colorVibrante = palette.getLightVibrantColor(0);
                                        libro.colorApagado = palette.getLightMutedColor(0);

                                        holder.itemView.setBackgroundColor(libro.colorApagado);
                                        holder.titulo.setBackgroundColor(libro.colorVibrante);
                                        holder.portada.invalidate();
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        holder.portada.setImageResource(R.drawable.books);
                    }
                });

        holder.titulo.setText(libro.titulo);
        holder.itemView.setScaleX(1);
        holder.itemView.setScaleY(1);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAction.execute(posicion);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClickAction.execute(v);
                return true;
            }
        });
    }

    // Indicamos el número de elementos de la lista
    @Override
    public int getItemCount() {
        return listaLibros.size();
    }

}