package com.example.makoto.testvolleysimplerequest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class ListaCaracteristicasUsuarioActivity extends AppCompatActivity {

    ListView lvCaracteristicas;
    ArrayList<String> arrayList;
    String Email;
    static String medidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_caracteristicas_usuario);
        lvCaracteristicas= (ListView) findViewById(R.id.lvCaracteristicas);

        Intent intent= getIntent();
        Email= intent.getStringExtra("Email");

        final RequestQueue requestQueue= Volley.newRequestQueue(ListaCaracteristicasUsuarioActivity.this);

        StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/PegarDatasClienteFitness.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                arrayList= new ArrayList<>(Arrays.asList(response.split("\\|")));

                onFinish();


                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListaCaracteristicasUsuarioActivity.this, "Erro no Código.", Toast.LENGTH_SHORT).show();
                requestQueue.stop();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();

                params.put("Email", Email);


                return params;
            }
        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    public void onFinish(){
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(ListaCaracteristicasUsuarioActivity.this, android.R.layout.simple_list_item_1, arrayList);
        lvCaracteristicas.setAdapter(arrayAdapter);

        lvCaracteristicas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final RequestQueue requestQueue= Volley.newRequestQueue(ListaCaracteristicasUsuarioActivity.this);

                StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/PegarCaracteristicadoDiaUsuarioFitness.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.equals("-1")){
                            medidas= response;

                            DialogCaracteristicas2 dialogCaracteristicas= new DialogCaracteristicas2();
                            dialogCaracteristicas.show(getSupportFragmentManager(), "medidas");
                        }


                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListaCaracteristicasUsuarioActivity.this, "Erro no Código.", Toast.LENGTH_SHORT).show();
                        requestQueue.stop();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params= new HashMap<>();

                        params.put("Email", Email);
                        params.put("Data", arrayList.get(position));

                        return params;
                    }
                };
                stringRequest.setShouldCache(false);
                requestQueue.add(stringRequest);
            }
        });
    }
}
