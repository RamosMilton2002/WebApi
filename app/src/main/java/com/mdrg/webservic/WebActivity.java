package com.mdrg.webservic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebActivity extends AppCompatActivity {

    private TextView registroTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        registroTextView = findViewById(R.id.registroTextView);

        // Llamando al método para realizar la solicitud al servicio web
        new ConsultarRegistroTask().execute("http://10.10.30.143/WEBSERVICES/webapi.php?op=validar");
    }

    private class ConsultarRegistroTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String resultado = "";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urls[0])
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    resultado = response.body().string();
                } else {
                    resultado = "Error: " + response.code() + " " + response.message();
                }
            } catch (IOException e) {
                e.printStackTrace();
                resultado = "Error: " + e.getMessage();
            }
            return resultado;
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);

            if (resultado.startsWith("Error: 500")) {
                // Mensaje específico para errores 500
                Toast.makeText(WebActivity.this, "Error interno del servidor", Toast.LENGTH_SHORT).show();
            } else {
                // Mostrar el resultado en el TextView
                registroTextView.setText(resultado);
                // Puedes realizar cualquier otro procesamiento necesario con el resultado aquí
            }
    }
    }
}
