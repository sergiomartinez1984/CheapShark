package com.example.cheapshark.controlador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class DBAccess extends SQLiteOpenHelper {

    //Extendemos la clase con SQLiteOpenHelper para tener acceso a los métodos que gestion la propia base de datos.

    //Database name
    private static final String DB_NAME = "cheapshark";

    //Table name
    private static final String DB_TABLE_NAME ="login";

    //Database version must be >= 1
    private static final int DB_VERSION = 1;

    //Columns
    private static final String Users_COLUMN="Users";

    private static final String Password_COLUMN="Password";


    //Application Context
    private Context mContext;


     //Constructor de la base de datos, si no existe la base de datos la crea, sino se conecta.
      //@param context Contexto de la aplicación

    public DBAccess(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        mContext = context;

    }

    //Sobrecargamos onCreate, encargado de crear las tablas asociadas a la base de datos.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_USER_TABLE = "CREATE TABLE " +DB_TABLE_NAME+ "(" +Users_COLUMN+ " TEXT PRIMARY KEY ," +  Password_COLUMN + " TEXT NOT NULL)";

        //Se Lanza la consulta con execSQL
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);

    }
    //esste metodo al cual llama el onCreate para la actualizcion de la version,en este caso
    //no se implementa porque nuestra aplicacion solo tendra una version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    //Creamos un método para insertar datos en la BD.En este caso se le pasa por parametro el usuario y el password
    public long insert(String users, String password) {

        //Le Pedimos acceso de escritura a la base de datos.
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;

        // Contenedor clave,valor -> columna, valor de entrada registro
        ContentValues values = new ContentValues();

        values.put(Users_COLUMN,users);
        values.put(Password_COLUMN,password);

        //Insertamos a través del método insert, cuyos parametro son:
        //1 nombre de la tabla
        //2 nullColumnHack permite indicar si hay una columna cuyo valor pueda ser nulo.
        //3 valores asociados a la inserción.Users y password definidos en el contenedor clave

        result = db.insert(DB_TABLE_NAME, null, values);

        //Se cierra la conexión de la base de datos
        db.close();

        return result;

    }

    //Creamos un método para recuperar datos en la BD.
    public String getUser(String users, String password) {
        String resultado = null;
        String[] result = new String[2];

        //Pedimos acceso de lectura a la BD.
        SQLiteDatabase db = this.getReadableDatabase();

        //Realizamos la consulta a través del método 'query',.
        //Este método devuelve un cursor que nos permite recorrer las tuplas del resultado.
        String[] cols = new String[]{Users_COLUMN, Password_COLUMN};

        //"Users_column=?"; // -> el caracter interrogación será sustituido por los valores del array 'args' en orden de aparición

        //Un cursor es un tipo de dato que se mueve entre los registros devueltos por una consulta de una base de datos.
        Cursor c = db.query(DB_TABLE_NAME, cols, Users_COLUMN+"=?", new String[]{users}, null, null, null);

        if (c.moveToFirst()) {
            //Todo 5.4. Cogemos el valor referente a la posicion de la columna
            result[0] = c.getString(0);
            result[1] = c.getString(1);

            if (result[0].equals(users) && result[1].equals(password)) {
                resultado = result[0];
            }
            // Cerramos el cursor
            if (c != null) {
                c.close();
            }
            //Cerramos la base de datos.
            db.close();

        }
        return resultado;
    }

    public String checkUser(String users) {
        String resultado = null;
        String[] result = new String[2];
        SQLiteDatabase db = this.getReadableDatabase();
        String[] cols = new String[]{Users_COLUMN, Password_COLUMN};
        Cursor c = db.query(DB_TABLE_NAME, cols, Users_COLUMN+"=?", new String[]{users}, null, null, null);

        if (c.moveToFirst()) {
            //Todo 5.4. Cogemos el valor referente a la posicion de la columna
            result[0] = c.getString(0);
            result[1] = c.getString(1);

            if (result[0].equals(users)) {
                resultado = result[0];
            }
            if (c != null) {
                c.close();
            }
            db.close();
        }
        return resultado;
    }
}