package com.example.audiolibros;

import android.app.Activity;
import android.os.Bundle;

import com.example.audiolibros.fragments.PreferenciasFragment;

/**
 * Created by usuwi on 28/12/2016.
 */

public class PreferenciasActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferenciasFragment()).commit();
    }
}
