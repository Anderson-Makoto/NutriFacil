package com.example.makoto.testvolleysimplerequest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView tvLogin, tvCadastro, tvCadastroNutri, tvOffline;
    EditText etEmail, etSenha;
    static int id= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvLogin= (TextView) findViewById(R.id.tvLogin);
        tvCadastro= (TextView) findViewById(R.id.tvCadastro);
        etEmail= (EditText) findViewById(R.id.etEmail);
        etSenha= (EditText) findViewById(R.id.etSenha);
        tvCadastroNutri= (TextView) findViewById(R.id.tvCadastroNutricionista);
        tvOffline= (TextView) findViewById(R.id.tvOffline);

        Alarme alarme= new Alarme();
        alarme.startAlarmManager(MainActivity.this);

        tvCadastroNutri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, CadastroNutricionistaActivity.class);
                startActivity(intent);
            }
        });

        tvOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, OfflineActivity.class);
                startActivity(intent);
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
                StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/LoginFitness.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.equals("-1")){

                            String[] divide= response.split("|");

                            id= Integer.parseInt(divide[1]);
                            int tipo= Integer.parseInt(divide[3]);


                            if(tipo== 0){
                                Intent intent= new Intent(MainActivity.this, TelaUsuarioActivity.class);
                                startActivity(intent);
                            }

                            else{
                                Intent intent= new Intent(MainActivity.this, TelaNutricionistaActivity.class);
                                startActivity(intent);
                            }
                            etEmail.setText("");
                            etSenha.setText("");

                        }
                        else{
                            Toast.makeText(MainActivity.this, "Usuario nao cadastrado.", Toast.LENGTH_SHORT).show();
                        }
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Erro", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        requestQueue.stop();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params= new HashMap<>();

                        params.put("Email", etEmail.getText().toString());
                        params.put("Senha", etSenha.getText().toString());
                        return params;
                    }
                };
                stringRequest.setShouldCache(false);
                requestQueue.add(stringRequest);
            }
        });

        tvCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });
    }
}
