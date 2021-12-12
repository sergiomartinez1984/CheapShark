package com.example.cheapshark.controlador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.preference.PreferenceManager;

import com.example.cheapshark.R;
import com.example.cheapshark.io.HttpConnectVideojuego;
import com.example.cheapshark.modelo.Videojuegos;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import adapters.RecyclerViewAdapter;

public class SecondActivity extends AppCompatActivity {
    private androidx.appcompat.view.ActionMode mActionMode;
    int posicion;
    RecyclerView recyclerView;
    RecyclerViewAdapter recAdapter;
    HttpConnectVideojuego httpvideojuego = new HttpConnectVideojuego();
    ArrayList<Videojuegos>ArrayVideojuegos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        checkPreferences();
        for(int i = 0;i <= 20 ;i ++){
           new taskConnections().execute(String.valueOf(i));
        }


        // Se inicializan los objetos recyclerView y recyclerAdapter.
        // le pasamos a RecyclerAdapter mi Array.

        recyclerView = findViewById(R.id.recyclerView);
        recAdapter = new RecyclerViewAdapter(ArrayVideojuegos);

        //La clase layaoutManager se encarga de gestionar la disposición de los elementos de la lista dentro del recyclerView.
        // Hay otras opciones, como gridLayoutManager que coloca los elementos en vista de rejilla.
        // Yo he utilizado el LinearLayout como el ejemplo del profe, para una disposición de un elemento debajo de otro

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        //Ahora añadimos los elementos creados anteriormente a la vista padre (RecyclerView) con sus respectivos métodos.

        recyclerView.setAdapter(recAdapter);
        recyclerView.setLayoutManager(layoutManager);


        recAdapter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Creamos una nueva actividad mediante un intent,para indicar donde estamosn y hacia donde queremos ir(de pantalla2 a pantalla3)
                Intent thirdView = new Intent(SecondActivity.this,ThirdActivity.class);
                Videojuegos videojuego = ArrayVideojuegos.get(recyclerView.getChildAdapterPosition(view));
                thirdView.putExtra("videojuego",videojuego);
                startActivity(thirdView);
            }
        });

        recAdapter.setOnLongClick(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                if ( mActionMode != null) {
                    return false;
                }
                posicion = recyclerView.getChildAdapterPosition(view);
                mActionMode = startSupportActionMode(mActionCallback);
                    return true;

            }
        });
    }

    //Metodo al realizar un longCLick en el item, que abre el menu de accion
    private ActionMode.Callback mActionCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_delete, menu);
            mode.setTitle("Action Menu");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        //Al hacer click en el icono de la palelera,eliminamos el objeto seleccionado de la lista
        //Luego indicamos al adaptador que la informacion a cambiado
        //Como la lista se crea automaticamente al crear la actividad, cada vez que realizamos un intent hacia la actividad
        // o reiniciamos la App, el recyclerView aparece al completo
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            ArrayVideojuegos.remove(posicion);
            recAdapter.notifyDataSetChanged();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }

    };
    //metodo que chequea las preferencias,por defecto le tengo activado el modo oscuro
    public void checkPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SecondActivity.this);
        boolean tema = sharedPreferences.getBoolean("oscuro",true);
            if (tema){
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }else{
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
    }

    //onResume sirve para mostrar las preferencias guardadas sin tener que salir de la aplicacion
    @Override
    public void onResume(){
        super.onResume();
        checkPreferences();
    }

    //mostrara el menu o lo ocultara dependiendo de la seleccion
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;

    }
    //seleccionamos la opcion de nuestro menu,y nos llevara a una nueva actividad creada para las preferencias
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuId) {
            Intent inten = new Intent(SecondActivity.this, Preferencias.class);
            startActivity(inten);
        }
        return super.onOptionsItemSelected(item);
    }

    // el TaskConnection hará la peticion,como es una tarea que implica una espera,
    // como es la respuesta del servidor, se tiene que llevar a cabo a través de un hilo secundario.

    private class taskConnections extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            result = httpvideojuego.getRequest(strings[0]);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                if(s != null){

                    //La respuesta que nos devuelve es un texto en formato JSON. Para ello,en este caso, haremos uso de las clases que nos proporciona Android.
                    //creamos un jsonObject para poder descargar la informacion del objeto entero.
                    //como mi Api me devuelve la informacion en string, creamos dos variables de tipo string,para guardar la informacion que vamos a obtener(titulo,imagen)
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject jsoninfo = jsonObject.getJSONObject("info");
                    String title = jsoninfo.getString("title");
                    String url = jsoninfo.getString("thumb");
                    //volvemos a crear otro objeto JsonObject para obtener la informacion de otro objeto de la Api
                    JSONObject cheapestPriceEver = jsonObject.getJSONObject("cheapestPriceEver");
                    //creo una variable de tipo String para guardar el precio,que en mi api es de tipo string
                    String price = cheapestPriceEver.getString("price");
                    //creo un objeto de mi clase videojuego con sus atributo
                    Videojuegos videojuego = new Videojuegos(title,price,url);
                    //añado en mi Array el videojuego creado con todos los datos obtenidos de la Api
                    ArrayVideojuegos.add(videojuego);
                    recAdapter.notifyDataSetChanged();
                    //Ya tenemos los datos en nuestra colección,ahora avisamos al adaptador que la información ha cambiado con el adapter.notifyDataSetChanged();



                }else{
                    Toast.makeText(getApplicationContext(), "Problema al cargar los datos", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
