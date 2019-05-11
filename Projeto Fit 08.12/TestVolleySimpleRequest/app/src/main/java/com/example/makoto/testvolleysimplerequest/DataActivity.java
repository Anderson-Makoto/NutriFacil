package com.example.makoto.testvolleysimplerequest;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.util.List;
import java.util.Map;

public class DataActivity extends AppCompatActivity {
    ListView lvRefeicao;
    TextView tvtotal;
    ArrayList<String> refeicao= new ArrayList<>();
    static public String dados;
    String dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);


        lvRefeicao= (ListView) findViewById(R.id.lvRefeicao);
        tvtotal= (TextView) findViewById(R.id.tvTotal);

        Intent intent= getIntent();
        dia= intent.getStringExtra("data");

        refeicao.add("Café da Manhã");
        refeicao.add("Almoço");
        refeicao.add("Café da Tarde");
        refeicao.add("Jantar");

        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, refeicao);
        lvRefeicao.setAdapter(adapter);

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/PegarNomesDataFitness.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tvtotal.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();

                params.put("Id", Integer.toString(MainActivity.id));
                params.put("Data", dia);

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

        lvRefeicao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                RequestQueue requestQueue= Volley.newRequestQueue(DataActivity.this);

                StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/PegarDadosRefeicaoUsuarioFitness.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       dados= response;

                        RefeicaoDialog refeicaoDialog= new RefeicaoDialog();

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

                        params.put("Id_usuario", Integer.toString(MainActivity.id));
                        params.put("Refeicao", refeicao.get(position));
                        params.put("Dia", dia);

                        return params;
                    }
                };
                stringRequest.setShouldCache(false);
                requestQueue.add(stringRequest);


            }
        });

        registerForContextMenu(lvRefeicao);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.excluir_refeicao, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()){
            case R.id.item1:
                RequestQueue requestQueue= Volley.newRequestQueue(DataActivity.this);

                StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/DeletarRefeicaoFitness.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("1")){
                            Toast.makeText(DataActivity.this, "Excluído", Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(DataActivity.this, TelaUsuarioActivity.class);
                            startActivity(intent);
                            finish();
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

                        params.put("Id_usuario", Integer.toString(MainActivity.id));
                        params.put("Refeicao", refeicao.get(info.position));
                        params.put("Dia", dia);

                        return params;
                    }
                };
                stringRequest.setShouldCache(false);
                requestQueue.add(stringRequest);
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent= new Intent(DataActivity.this, TelaUsuarioActivity.class);
        startActivity(intent);
        finish();
    }
}
