package com.example.makoto.testvolleysimplerequest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.Map;

public class VerDatasClienteActivity extends AppCompatActivity {
    ListView lvClienteDatas;
    Button btnCaracteristicas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_datas_cliente);

        Intent intent= getIntent();
        final String[] dados= intent.getStringExtra("Dados").split("\\|");

        lvClienteDatas= (ListView) findViewById(R.id.lvClienteDatas);
        btnCaracteristicas= (Button) findViewById(R.id.btnCaracteristicas);

        btnCaracteristicas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(VerDatasClienteActivity.this, ListaCaracteristicasUsuarioActivity.class);
                intent.putExtra("Email", dados[1]);
                startActivity(intent);
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/DatasClienteFitness.php", new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                if(!response.equals("-1")){
                    final ArrayList<String> lista= new ArrayList<>(Arrays.asList(response.split("\\|")));
                    ArrayAdapter<String> adapter= new ArrayAdapter<>(VerDatasClienteActivity.this, android.R.layout.simple_list_item_1, lista);
                    lvClienteDatas.setAdapter(adapter);

                    lvClienteDatas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent= new Intent(VerDatasClienteActivity.this, VerValoresDiaClienteActivity.class);
                            intent.putExtra("Valores", lista.get(position)+"|"+dados[0]+"|"+dados[1]+"|");
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

                params.put("Nome", dados[0].toString());
                params.put("Email", dados[1].toString());

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }
}
