package com.example.cheapshark.controlador;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cheapshark.R;
import com.example.cheapshark.modelo.Videojuegos;



public class ThirdActivity extends AppCompatActivity {
    private Videojuegos videojuego;

    TextView titulo;
    TextView precio;
    ImageView fotillo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        //ya tenemos guardado el objeto videojuego de nuestro intent
        videojuego = (Videojuegos) getIntent().getSerializableExtra("videojuego");

        titulo = findViewById(R.id.titulo);
        precio = findViewById(R.id.precio);
        fotillo = findViewById(R.id.idImagen);


            titulo.setText(videojuego.getTitle());
            precio.setText(videojuego.getPrecio());

            Glide.with(this)
                    .load(videojuego.getFoto())
                    .placeholder(R.drawable.construccion)
                    .error(R.drawable.retro1)
                    .into(fotillo);

        }
}

