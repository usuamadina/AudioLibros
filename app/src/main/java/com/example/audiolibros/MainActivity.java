package com.example.audiolibros;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.audiolibros.fragments.DetalleFragment;
import com.example.audiolibros.fragments.SelectorFragment;

import static android.R.id.toggle;
import static com.example.audiolibros.R.id.appBarLayout;
import static com.example.audiolibros.R.id.search_edit_frame;
import static com.example.audiolibros.R.id.tabs;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdaptadorLibrosFiltro adaptador;

    //Ocultar elementos interfaz de usuario
    private AppBarLayout appBarLayout;
    private TabLayout tabs;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adaptador = ((Aplicacion) getApplicationContext()).getAdaptador();
        if ((findViewById(R.id.contenedor_pequeno) != null) &&
                (getSupportFragmentManager().findFragmentById(R.id.contenedor_pequeno) == null)) {
            SelectorFragment primerFragment = new SelectorFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contenedor_pequeno, primerFragment).commit();
        }

        //Barra de acciones
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Botón flotante
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.botonFlotante);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Mostrar último visitado", Snackbar.LENGTH_LONG).setAction("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        irUltimoVisitado();
                    }
                }).show();
            }
        });

        //Pestañas
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Todos"));
        tabs.addTab(tabs.newTab().setText("Nuevos"));
        tabs.addTab(tabs.newTab().setText("Leidos"));
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        //
        // Aplicacion app = (Aplicacion)getApplication();
        // recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        // recyclerView.setAdapter(app.getAdaptador());
        // layoutManager = new LinearLayoutManager(this);
        // recyclerView.setLayoutManager(layoutManager);
       /* app.getAdaptador().setOnItemClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
                Toast.makeText(MainActivity.this,"Seleccionando el elemento: " + recyclerView.getChildAdapterPosition(v), Toast.LENGTH_SHORT).show();
            }
        });*/

        // Navigation Drawer
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Ocultar elementos interfaz usuario
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        tabs = (TabLayout) findViewById(R.id.tabs);

        //Crear icono flechita de vuelta atrás al visualizar un fragment
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // método que hace que se muestre el icono con la flecha hacia la izquierda
        }

        // Escuchador para que el botón haga retroceder en la plila de navegación
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void mostrarDetalle(int id) {
        DetalleFragment detalleFragment = (DetalleFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_detalle);
        if (detalleFragment != null) {
            detalleFragment.ponInfoLibro(id);
        } else {
            DetalleFragment nuevoFragment = new DetalleFragment();
            Bundle args = new Bundle();
            args.putInt(DetalleFragment.ARG_ID_LIBRO, id);
            nuevoFragment.setArguments(args);
            FragmentTransaction transaccion = getSupportFragmentManager()
                    .beginTransaction();
            transaccion.replace(R.id.contenedor_pequeno, nuevoFragment);
            transaccion.addToBackStack(null);
            transaccion.commit();
        }

        SharedPreferences pref = getSharedPreferences(
                "com.example.audiolibros_internal", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("ultimo", id);
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_preferencias) {
            Toast.makeText(this, "Preferencias", Toast.LENGTH_LONG).show();
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
        SharedPreferences pref = getSharedPreferences(
                "com.example.audiolibros_internal", MODE_PRIVATE);
        int id = pref.getInt("ultimo", -1);
        if (id >= 0) {
            mostrarDetalle(id);
        } else {
            Toast.makeText(this, "Sin última vista", Toast.LENGTH_LONG).show();
        }
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
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

