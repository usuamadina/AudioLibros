package com.example.audiolibros;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by usuwi on 10/01/2017.
 */

public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] widgetIds) {
        for (int widgetId : widgetIds) {
            actualizaWidget(context, widgetId);
        }
    }


    public static void actualizaWidget(Context context, int widgetId) {

        //Obtenemos ultimo libro
        SharedPreferences prefs = context.getSharedPreferences("com.example.audioLibros_internal", Context.MODE_PRIVATE);
        int id = prefs.getInt("ultimo", 0);
        Libro ultimoLibro = ((Aplicacion) context.getApplicationContext()).getVectorLibros().elementAt(id);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

        //Comprobamos si hay último libro

        if (!ultimoLibro.equals(null)) {

            String autor = ultimoLibro.autor;
            String titulo = ultimoLibro.titulo;


            remoteViews.setTextViewText(R.id.autorWidget,autor);
            remoteViews.setTextViewText(R.id.tituloWidget,titulo);

        }


        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.widget_layout,pendingIntent);

        AppWidgetManager.getInstance(context).updateAppWidget(widgetId, remoteViews);
    }


}
