package com.example.audiolibros;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ResultCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;



/**
 * Created by usuwi on 10/02/2017.
 */


public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth auth;
    private FirebaseAuthSingleton firebaseAuthSingleton;
    private FirebaseUser currentUser;
    private LibroSharedPreferenceStorage pref;


    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuthSingleton = new FirebaseAuthSingleton();
        firebaseAuthSingleton.getInstance();
        auth = firebaseAuthSingleton.getAuth();
        pref = LibroSharedPreferenceStorage.getInstance(this);


        doLogin();
    }

    private void doLogin() {
        currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            getCurrentUserData();
            if (provider.equals("password") && !currentUser.isEmailVerified()) {
                Log.d("LoginActivity", "notificación verificar");
                sendVerificationNotify();


            } else {
                appAccess();
            }


        } else {

            Log.d("LoginActivity", "va a llamar al startActivityForResult");
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setProviders(Arrays.asList(
                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                    new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                    new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build()))
                    .setIsSmartLockEnabled(false).build(), RC_SIGN_IN);


        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == ResultCodes.OK) {
                Intent i = new Intent(this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

            }
        }
    }


    private void getCurrentUserData() {
        String name = currentUser.getDisplayName();
        String email = currentUser.getEmail();
        provider = currentUser.getProviders().get(0);
        pref.saveProvider(provider,email,name);
    }

    private void sendVerificationNotify() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Se ha enviado un email de verificación a su cuenta de correo electrónico, verifique su correo para acceder a AudioLibros")
                .setTitle("VERIFICAR CORREO ELECTRÓNICO")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        currentUser.sendEmailVerification();
                        singOut();


                    }
                }).show();


    }

    private void appAccess() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void singOut() {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("Firebase", "cerrando sesión");

                pref.removeProvider("provider","email", "name");

                Intent i = new Intent(LoginActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });
    }

 /*   @Override
    protected void onDestroy() {
        try {
            if (alertDialog != null && alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }*/

}