package com.example.cheapshark.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cheapshark.R;

import Fragment.fr_preferencias;

public class Preferencias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);
        getSupportFragmentManager().beginTransaction().replace(R.id.ConstraintLayout,new fr_preferencias()).commit();
    }
}