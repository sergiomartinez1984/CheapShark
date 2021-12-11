package com.example.cheapshark.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;



//Clase que gestiona la conexión con la base de datos externa, a través de una url base a la
// que debemos concatenarle los "endpoint" o final de url para recibir la información que necesitemos.
// Así mismo los parametros que se quieran enviar se forman en la URL a través del caracter '?'
public class HttpConnectVideojuego {

    //Declaramos la url base.Esta no va a cambiar
    private static String URL_BASE = "https://www.cheapshark.com/api/1.0/games?id=";

    //Definimos el método para peticiones GET el cual se usará para la consulta de la información.
    public static String getRequest(String id)
    {
        HttpURLConnection http = null;
        String content = null;
        try {
            //formaremos la url juntando la url más el endpoint.
            // Así como la cabecera, que permitira decidir la codificación de los datos que se están trasmitiendo.
            URL url = new URL( URL_BASE + id);
            http = (HttpURLConnection)url.openConnection();
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");

            //Si el servidor devuelve un codigo 200 (HTTP_OK == 200),la informacion que devuelva sabremos que es correcta.
            if( http.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                //Se codifica el texto de la respuesta como String.
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader( http.getInputStream() ));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                content = sb.toString();
                reader.close();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {

            //Se desconecta la conexión.
            if( http != null ) http.disconnect();
        }
        return content;
    }

}
