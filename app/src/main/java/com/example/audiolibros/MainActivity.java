package com.example.audiolibros;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.example.audiolibros.fragments.DetalleFragment;
import com.example.audiolibros.fragments.SelectorFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainPresenter.View {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdaptadorLibrosFiltro adaptador;
    private MainPresenter mainPresenter;
    private LibrosSingleton librosSingleton;
    private BooksRepository booksRepository;
    private LibroStorage libroStorage;
    private VolleySingleton volleySingleton;
    private FirebaseDatabaseSingleton firebaseDatabaseSingleton;
    private DatabaseReference booksReference;



    //Ocultar elementos interfaz de usuario
    private AppBarLayout appBarLayout;
    private TabLayout tabs;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabaseSingleton = FirebaseDatabaseSingleton.getInstance();
        booksReference = firebaseDatabaseSingleton.getBooksReference();


        librosSingleton = LibrosSingleton.getInstance(this);

        libroStorage = LibroSharedPreferenceStorage.getInstance(this);
        booksRepository = new BooksRepository(libroStorage);

        mainPresenter = new MainPresenter(new HasLastBook(booksRepository), new GetLastBook(booksRepository),
                new SaveLastBook(booksRepository), libroStorage, this);


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

                mainPresenter.clickFavoriteButton();
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


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        // Navigation Drawer
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Ocultar elementos interfaz usuario
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        tabs = (TabLayout) findViewById(R.id.tabs);

        name = libroStorage.getUserName();
        View headerLayout = navigationView.getHeaderView(0);
        TextView txtName = (TextView) headerLayout.findViewById(R.id.txtName);
        txtName.setText(String.format(getString(R.string.wellcome_message), name));

        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        Uri urlImagen = usuario.getPhotoUrl();
        if (urlImagen != null) {
            NetworkImageView fotoUsuario = (NetworkImageView) headerLayout.findViewById(R.id.userImageView);
            volleySingleton = VolleySingleton.getInstance(this);
           fotoUsuario.setImageUrl(urlImagen.toString(), volleySingleton.getLectorImagenes());
        }


        adaptador = librosSingleton.getAdaptador();



    }


    public void mostrarDetalle(String key) {
        mainPresenter.openDetalle(key);
    }

   /* @Override
    public void mostrarFragmentDetalle(int lastBook) {
        mainPresenter.openDetalle(lastBook);

    }*/

    @Override
    public void mostrarNoUltimaVisita() {
        Toast.makeText(this, "Sin última vista", Toast.LENGTH_LONG).show();
    }


    public void mostrarFragmentDetalle(String key) {
        DetalleFragment detalleFragment = (DetalleFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_detalle);
        if (detalleFragment != null) {
            detalleFragment.ponInfoLibro(key);
        } else {
            DetalleFragment nuevoFragment = new DetalleFragment();
            Bundle args = new Bundle();
            args.putString(DetalleFragment.ARG_ID_LIBRO, key);
            nuevoFragment.setArguments(args);
            FragmentTransaction transaccion = getSupportFragmentManager()
                    .beginTransaction();
            transaccion.replace(R.id.contenedor_pequeno, nuevoFragment);
            transaccion.addToBackStack(null);
            transaccion.commit();
        }
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
            Intent i = new Intent(this, PreferenciasActivity.class);
            startActivity(i);
            return true;
        } else if (id == R.id.menu_acerca) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("AudioLibros");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.create().show();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void irUltimoVisitado() {
        mainPresenter.clickFavoriteButton();
    }

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
        } else if (id == R.id.nav_signout) {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d("Firebase", "cerrando sesión");
                    SharedPreferences pref = getSharedPreferences("com.example.audiolibros_internal", MODE_PRIVATE);
                    pref.edit().remove("provider").commit();
                    pref.edit().remove("email").commit();
                    pref.edit().remove("name").commit();
                    Intent i = new Intent(MainActivity.this, CustomLoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }
            });
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
