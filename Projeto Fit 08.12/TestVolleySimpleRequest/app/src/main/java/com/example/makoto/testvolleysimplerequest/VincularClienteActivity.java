package com.example.makoto.testvolleysimplerequest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class VincularClienteActivity extends AppCompatActivity {
    Button btnVerNome, btnVincular;
    TextView tvNome;
    EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vincular_cliente);
        btnVerNome= (Button) findViewById(R.id.btnVerNome);
        btnVincular= (Button) findViewById(R.id.btnVincular);
        tvNome= (TextView) findViewById(R.id.tvNome);
        etEmail= (EditText) findViewById(R.id.etEmail);

        btnVerNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RequestQueue requestQueue= Volley.newRequestQueue(VincularClienteActivity.this);

                StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/VerClienteNomeFitness.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.equals("-1")){
                            String[] dados= response.split("\\|");

                            tvNome.setText(dados[1]);
                            requestQueue.stop();
                        }
                        else{
                            Toast.makeText(VincularClienteActivity.this, "Email Inválido.", Toast.LENGTH_SHORT).show();
                            requestQueue.stop();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VincularClienteActivity.this, "Erro no Código.", Toast.LENGTH_SHORT).show();
                        requestQueue.stop();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params= new HashMap<>();

                        params.put("Email", etEmail.getText().toString());

                        return params;
                    }
                };

                stringRequest.setShouldCache(false);
                requestQueue.add(stringRequest);
            }
        });

        btnVincular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue= Volley.newRequestQueue(VincularClienteActivity.this);

                StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/VincularClienteFitness.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("1")){
                            Toast.makeText(VincularClienteActivity.this, "Cliente Vinculado com Succeso.", Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(VincularClienteActivity.this, TelaNutricionistaActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(VincularClienteActivity.this, "Erro ao vincular.", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VincularClienteActivity.this, "Erro de Código.", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params= new HashMap<>();

                        params.put("Email", etEmail.getText().toString());
                        params.put("Id", Integer.toString(MainActivity.id));

                        return params;
                    }
                };

                stringRequest.setShouldCache(false);
                requestQueue.add(stringRequest);
            }
        });
    }
}
