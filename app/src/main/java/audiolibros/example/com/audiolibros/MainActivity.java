package audiolibros.example.com.audiolibros;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import audiolibros.example.com.audiolibros.fragments.DetalleFragment;
import audiolibros.example.com.audiolibros.fragments.SelectorFragment;

import static audiolibros.example.com.audiolibros.LibroSharedPreferenceStorage.KEY_ULTIMO_LIBRO;
import static audiolibros.example.com.audiolibros.LibroSharedPreferenceStorage.PREF_AUDIOLIBROS;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdaptadorLibrosFiltro adaptador;

    private AppBarLayout appBarLayout;
    private TabLayout tabs;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private LibroStorage libroStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adaptador = LibrosSingleton.getInstance(this).getAdaptador();
        libroStorage = LibroSharedPreferenceStorage.getInstance(this);

        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);

        if ((findViewById(R.id.contenedor_pequeno) != null) && (getFragmentManager().findFragmentById(R.id.contenedor_pequeno) == null)) {
            SelectorFragment primerFragment = new SelectorFragment();
            getFragmentManager().beginTransaction().add(R.id.contenedor_pequeno, primerFragment).commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Navigation Drawer
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(
                R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /*
        Aplicacion app = (Aplicacion) getApplication();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(app.getAdaptador());
        layoutManager = new GridLayoutManager(this, 2);//LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        */

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irUltimoVisitado();
            }
        });

        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Todos"));
        tabs.addTab(tabs.newTab().setText("Nuevos"));
        tabs.addTab(tabs.newTab().setText("Leidos"));
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: //Todos
                        adaptador.setNovedad(false);
                        adaptador.setLeido(false);
                        break;
                    case 1: //Nuevos
                        adaptador.setNovedad(true);
                        adaptador.setLeido(false);
                        break;
                    case 2: //Leidos
                        adaptador.setNovedad(false);
                        adaptador.setLeido(true);
                        break;
                }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_todos) {
            adaptador.setGenero("");
            adaptador.notifyDataSetChanged();
        } else if (id == R.id.nav_epico) {
            adaptador.setGenero(Libro.G_EPICO);
            adaptador.notifyDataSetChanged();
        } else if (id == R.id.nav_XIX) {
            adaptador.setGenero(Libro.G_S_XIX);
            adaptador.notifyDataSetChanged();
        } else if (id == R.id.nav_suspense) {
            adaptador.setGenero(Libro.G_SUSPENSE);
            adaptador.notifyDataSetChanged();
        } else if (id == R.id.nav_preferencias) {
            Intent i = new Intent(this, PreferenciasActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(
                R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(
                R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_preferencias) {
            Intent i = new Intent(this, PreferenciasActivity.class);
            startActivity(i);
            return true;
        } else if (id == R.id.menu_acerca) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Mensaje de Acerca De");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.create().show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void irUltimoVisitado() {
        if (libroStorage.hasLastBook()) {
            mostrarDetalle(libroStorage.getLastBook());
        } else {
            Toast.makeText(this,"Sin última vista",Toast.LENGTH_LONG).show();
        }
    }

    public void mostrarDetalle(int id) {
        DetalleFragment detalleFragment = (DetalleFragment) getFragmentManager().findFragmentById(R.id.detalle_fragment);
        if (detalleFragment != null) {
            detalleFragment.ponInfoLibro(id);
        } else {
            DetalleFragment nuevoFragment = new DetalleFragment();
            Bundle args = new Bundle();
            args.putInt(DetalleFragment.ARG_ID_LIBRO, id);
            nuevoFragment.setArguments(args);
            FragmentTransaction transaccion = getFragmentManager().beginTransaction();
            transaccion.replace(R.id.contenedor_pequeno, nuevoFragment);
            transaccion.addToBackStack(null);
            transaccion.commit();
        }

        SharedPreferences pref = getSharedPreferences(PREF_AUDIOLIBROS, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(KEY_ULTIMO_LIBRO  , id);
        editor.apply();
    }

    public void mostrarElementos(boolean mostrar) {
        appBarLayout.setExpanded(mostrar);
        toggle.setDrawerIndicatorEnabled(mostrar);
        if (mostrar) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            tabs.setVisibility(View.VISIBLE);
        } else {
            tabs.setVisibility(View.GONE);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }
}
