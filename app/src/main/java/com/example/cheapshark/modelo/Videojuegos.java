package com.example.cheapshark.modelo;

import java.io.Serializable;
//Clase videojuego con un constructor parametrizado
//metodos getter y setter
public class Videojuegos implements Serializable {
    private String foto;
    private String title;
    private String precio;

    public Videojuegos(String title,String precio,String foto){
        this.title = title;
        this.precio = precio;
        this.foto = foto;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getTitle(){

        return title;
    }
    public String getPrecio(){

        return precio;
    }
}
