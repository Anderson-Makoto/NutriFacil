package com.example.makoto.testvolleysimplerequest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.HashMap;
import java.util.Map;

public class VerValoresDiaClienteActivity extends AppCompatActivity {
    TextView tvTotal;
    ListView lvRefeicao;
    static public String dados2;
    String refeicao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_valores_dia_cliente);
        tvTotal= (TextView) findViewById(R.id.tvTotal);
        lvRefeicao= (ListView) findViewById(R.id.lvRefeicao);

        Intent intent= getIntent();
        final String[] dados= intent.getStringExtra("Valores").split("\\|");

        final ArrayList<String> lista= new ArrayList<>();
        lista.add("Café da Manhã");
        lista.add("Almoço");
        lista.add("Café da Tarde");
        lista.add("Jantar");

        ArrayAdapter<String> adapter= new ArrayAdapter<>(VerValoresDiaClienteActivity.this, android.R.layout.simple_list_item_1, lista);
        lvRefeicao.setAdapter(adapter);

        lvRefeicao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                RequestQueue requestQueue= Volley.newRequestQueue(VerValoresDiaClienteActivity.this);

                StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/PegarDadosrefeicaoUsuarioFitness.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dados2= response;

                        RefeicaoDialog2 refeicaoDialog= new RefeicaoDialog2();

                        refeicaoDialog.show(getSupportFragmentManager(), "refeicao dialog");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params= new HashMap<>();

                        params.put("Email", dados[2]);
                        params.put("Refeicao", lista.get(position));
                        params.put("Dia", dados[0]);

                        return params;
                    }
                };
                stringRequest.setShouldCache(false);
                requestQueue.add(stringRequest);
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/VerTotalDiaClienteFitness.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                tvTotal.setText(response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();

                params.put("Nome", dados[1]);
                params.put("Email", dados[2]);
                params.put("Dia", dados[0]);

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);



    }
}
