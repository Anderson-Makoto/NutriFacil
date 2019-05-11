package com.example.makoto.testvolleysimplerequest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.util.List;
import java.util.Map;

public class VerClientesActivity extends AppCompatActivity {

    ListView lvClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_clientes);

        lvClientes= (ListView) findViewById(R.id.lvClientes);

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/VerClientesFitness.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.equals("-1")){
                    final ArrayList<String> lista;
                    lista= new ArrayList<>(Arrays.asList(response.split("\\/")));

                    ArrayAdapter<String> adapter= new ArrayAdapter<>(VerClientesActivity.this, android.R.layout.simple_list_item_1, lista);
                    lvClientes.setAdapter(adapter);

                    lvClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent= new Intent(VerClientesActivity.this, VerDatasClienteActivity.class);
                            intent.putExtra("Dados", lista.get(position).toString());
                            startActivity(intent);
                        }
                    });
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

                params.put("Id_nutri", Integer.toString(MainActivity.id));

                return params;
            }
        };

        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }
}
