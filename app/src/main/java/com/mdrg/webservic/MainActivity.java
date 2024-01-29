package com.mdrg.webservic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    EditText edNum1, edNum2;
    Button btnProce;
    TextView res;
    String respuesta="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edNum1=findViewById(R.id.txtNum1);
        edNum2=findViewById(R.id.txtNum2);
        btnProce=findViewById(R.id.btnIni);
        res=findViewById(R.id.txtResul);

        btnProce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConsumirApi();
            }
        });
    }

    private void ConsumirApi () {

        String pro="https://ejemplo2apimovil20240128220859.azurewebsites.net/api/Operaciones?a="+edNum1.getText()+"&b="+edNum2.getText();
        OkHttpClient cliente=new OkHttpClient();

        Request get=new Request.Builder()
                .url(pro)
                .build();


        cliente.newCall(get).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    ResponseBody responseBody=response.body();
                    if(response.isSuccessful()){
                        respuesta = responseBody.string();
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                res.setText(respuesta);
                            }
                        });
                    }else{
                        throw new IOException("Respuesta inesperada"+response);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        });

    }


}