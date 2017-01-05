package com.example.audiolibros;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by usuwi on 03/01/2017.
 */

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establecer orientación
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Ocultar barra de título
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.splash_screen);


        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView textView = (TextView) findViewById(R.id.tituloSplash);
        Typeface fuente = Typeface.createFromAsset(getAssets(),"fonts/Bevan.ttf");
        textView.setTypeface(fuente);

        //Animación
      //  Animation animationZoom = AnimationUtils.loadAnimation(this, R.anim.zoom);
      //  imageView.startAnimation(animationZoom);
        Animation animationAlpha = AnimationUtils.loadAnimation(this, R.anim.aparecer);
        textView.startAnimation(animationAlpha);
        animationAlpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                Intent mainIntent = new Intent().setClass(
                        SplashScreenActivity.this, MainActivity.class);
                startActivity(mainIntent);
                overridePendingTransition(R.anim.zoom,R.anim.menguar);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }


}
