package com.example.cheapshark.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheapshark.R;
import com.example.cheapshark.io.HttpConnectVideojuego;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    //Declaracion de variables
    EditText Users;
    EditText Password;
    Button buttonLogin;
    Button buttonRegister;

    //declaracion de mi objeto base de datos
    DBAccess mDB = new DBAccess(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializacion de las variables
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        Users = (EditText) findViewById(R.id.Users);
        Password = (EditText) findViewById(R.id.Password);


        mDB = new DBAccess(this);
        //boton de Login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creamos tres variables una para el usuario,otra para la contraseña y otra que va a contener las dos para luego poder buscar en la
                //base de datos que el usuario no exista o si existe este bien puesta la contraseña
                String usuario = Users.getText().toString();
                String password = Password.getText().toString();
                String users = mDB.getUser(usuario,password);

                //si el usuario introducido existe,se conecta,y nos pasa a nuestra segunda pantalla.si no,saldra un aviso al usuario indicando que algo no es correcto
                if (users != null){
                    Intent seconView = new Intent(MainActivity.this,SecondActivity.class);
                    startActivity(seconView);
                    Toast("Logeado correctamente");
                }else{
                    Toast("Usuario o contraseña no son correctos");
                }
            }
        });
        //boton de Registro
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            //Aqui he creado otro metodo para comprobar si el usuario existe pasandole solo al usuario,sin la contraseña
            //asi, solo comprobara que ese nombre no este en nuestra base de datos
            //Si no existe ese nombre se registrará correctamente, si no indicara al usuario mediante un toas que ese usuario ya existe
            @Override
            public void onClick(View view) {
                String usuario = Users.getText().toString();
                String password = Password.getText().toString();
                String users = mDB.checkUser(usuario);

                if (users != null){
                    Toast("El usuario ya existe");
                } else {
                    mDB.insert(usuario,password);
                    Toast("Usuario creado correctamente");
                }

            }
        });

    }

    public void Toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
