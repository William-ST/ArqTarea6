package audiolibros.example.com.audiolibros.fragments;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import audiolibros.example.com.audiolibros.AdaptadorLibros;
import audiolibros.example.com.audiolibros.AdaptadorLibrosFiltro;
import audiolibros.example.com.audiolibros.Aplicacion;
import audiolibros.example.com.audiolibros.Libro;
import audiolibros.example.com.audiolibros.MainActivity;
import audiolibros.example.com.audiolibros.R;
import audiolibros.example.com.audiolibros.SearchObservable;
import audiolibros.example.com.audiolibros.command.OpenDetailClickAction;
import audiolibros.example.com.audiolibros.command.OpenMenuLongClickAction;

/**
 * Created by William_ST on 05/02/19.
 */

public class SelectorFragment extends Fragment implements Animator.AnimatorListener {

    private Activity actividad;
    private RecyclerView recyclerView;
    private AdaptadorLibrosFiltro adaptador;
    private Aplicacion app;

    @Override
    public void onAttach(Activity actividad) {
        super.onAttach(actividad);
        this.actividad = actividad;
        app = (Aplicacion) actividad.getApplication();
        adaptador = app.getAdaptador();
    }

    @Override
    public void onResume() {
        ((MainActivity) getActivity()).mostrarElementos(true);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        View vista = inflador.inflate(R.layout.fragment_selector, contenedor, false);
        recyclerView = (RecyclerView) vista.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(actividad, 2));
        recyclerView.setAdapter(adaptador);
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(2000);
        animator.setMoveDuration(2000);
        recyclerView.setItemAnimator(animator);

        setHasOptionsMenu(true);

        adaptador.setClickAction(new OpenDetailClickAction(((MainActivity)getActivity())));
        adaptador.setLongClickAction(new OpenMenuLongClickAction(this));

        return vista;
    }

    public void showMenuItemOptions(final View v) {
        if (app == null) return;

        final int id = recyclerView.getChildAdapterPosition(v);
        AlertDialog.Builder menu = new AlertDialog.Builder(actividad);
        CharSequence[] opciones = {"Compartir", "Borrar ", "Insertar"};
        menu.setItems(opciones, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int opcion) {
                switch (opcion) {
                    case 0: //Compartir
                        Animator anim = AnimatorInflater.loadAnimator(actividad, R.animator.share);
                        anim.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                Libro libro = app.getLibroById(id);
                                if (libro != Libro.LIBRO_EMPTY) {
                                    Intent i = new Intent(Intent.ACTION_SEND);
                                    i.setType("text/plain");
                                    i.putExtra(Intent.EXTRA_SUBJECT, libro.titulo);
                                    i.putExtra(Intent.EXTRA_TEXT, libro.urlAudio);
                                    startActivity(Intent.createChooser(i, "Compartir"));
                                }
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        anim.setTarget(v);
                        anim.start();
                        break;
                    case 1: //Borrar
                        Snackbar.make(v, "¿Estás seguro?", Snackbar.LENGTH_LONG)
                                .setAction("SI", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Animator anim = AnimatorInflater.loadAnimator(actividad, R.animator.menguar);
                                        anim.addListener(SelectorFragment.this);
                                        anim.setTarget(v);
                                        anim.start();

                                        adaptador.borrar(id);
                                    }
                                })
                                .show();
                        break;
                    case 2: //Insertar
                        int posicion = recyclerView.getChildLayoutPosition(v);
                        adaptador.insertar((Libro) adaptador.getItem(posicion));
                        adaptador.notifyItemInserted(0);
                        Snackbar.make(v, "Libro insertado", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
                        break;
                }
            }
        });
        menu.create().show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_selector, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_buscar);
        SearchView searchView = (SearchView) searchItem.getActionView();

        SearchObservable searchObservable = new SearchObservable();
        searchObservable.addObserver(adaptador);
        searchView.setOnQueryTextListener(searchObservable);

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                adaptador.setBusqueda("");
                adaptador.notifyDataSetChanged();
                return true; // Para permitir cierre
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true; // Para permitir expansión
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_ultimo) {
            ((MainActivity) actividad).irUltimoVisitado();
            return true;
        } else if (id == R.id.menu_buscar) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        adaptador.notifyDataSetChanged();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}