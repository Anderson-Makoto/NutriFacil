package com.example.makoto.testvolleysimplerequest;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ExcluirClienteActivity extends AppCompatActivity {

    Button btnExcluir, btnVerNome;
    EditText etEmail;
    TextView tvNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir_cliente);

        tvNome= (TextView) findViewById(R.id.tvNome);
        etEmail= (EditText) findViewById(R.id.etEmail);
        btnExcluir= (Button) findViewById(R.id.btnExcluir);
        btnVerNome= (Button) findViewById(R.id.btnVerNome);

        btnVerNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RequestQueue requestQueue= Volley.newRequestQueue(ExcluirClienteActivity.this);

                StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/VerClienteNomeFitness.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.equals("-1")){
                            String[] dados= response.split("\\|");
                            Toast.makeText(ExcluirClienteActivity.this, dados[1], Toast.LENGTH_SHORT).show();

                            tvNome.setText(dados[1]);
                            requestQueue.stop();
                        }
                        else{
                            Toast.makeText(ExcluirClienteActivity.this, "Email Inválido.", Toast.LENGTH_SHORT).show();
                            requestQueue.stop();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ExcluirClienteActivity.this, "Erro no Código.", Toast.LENGTH_SHORT).show();
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


        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue= Volley.newRequestQueue(ExcluirClienteActivity.this);
                StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/DeletarClienteFitness.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("1")){
                            Toast.makeText(ExcluirClienteActivity.this, "Excluído.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else if(response.equals("-1")){
                            Toast.makeText(ExcluirClienteActivity.this, "Não foi excluído", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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
    }
}
