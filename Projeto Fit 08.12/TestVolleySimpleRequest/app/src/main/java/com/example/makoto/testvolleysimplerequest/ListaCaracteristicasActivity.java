package com.example.makoto.testvolleysimplerequest;

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

public class ListaCaracteristicasActivity extends AppCompatActivity {

    ListView lvCaracteristicas;
    ArrayList<String> datas;
    static String medidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_caracteristicas);

        lvCaracteristicas= (ListView) findViewById(R.id.lvCaracteristicas);

        final RequestQueue requestQueue= Volley.newRequestQueue(ListaCaracteristicasActivity.this);

        StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/PegarCaracteristicasFitness.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(!response.equals("-1")){
                    datas= new ArrayList<>(Arrays.asList(response.split("\\|")));

                    onFinish();
                }


                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListaCaracteristicasActivity.this, "Erro no Código.", Toast.LENGTH_SHORT).show();
                requestQueue.stop();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();

                params.put("Id_usuario", Integer.toString(MainActivity.id));

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

        registerForContextMenu(lvCaracteristicas);
    }

    void onFinish() {
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(ListaCaracteristicasActivity.this, android.R.layout.simple_list_item_1, datas);
        lvCaracteristicas.setAdapter(adapter);

        lvCaracteristicas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final RequestQueue requestQueue= Volley.newRequestQueue(ListaCaracteristicasActivity.this);

                StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/PegarCaracteristicasDoDiaFitness.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(!response.equals("-1")){
                            medidas= response;

                            DialogCaracteristicas dialogCaracteristicas= new DialogCaracteristicas();
                            dialogCaracteristicas.show(getSupportFragmentManager(), "medidas");
                        }


                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListaCaracteristicasActivity.this, "Erro no Código.", Toast.LENGTH_SHORT).show();
                        requestQueue.stop();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params= new HashMap<>();

                        params.put("Id_usuario", Integer.toString(MainActivity.id));
                        params.put("Data", datas.get(position));

                        return params;
                    }
                };
                stringRequest.setShouldCache(false);
                requestQueue.add(stringRequest);
            }
        });
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
                RequestQueue requestQueue= Volley.newRequestQueue(ListaCaracteristicasActivity.this);

                StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/ExcluirCaracteristicasFitness.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("1")){
                            Toast.makeText(ListaCaracteristicasActivity.this, "Excluído", Toast.LENGTH_SHORT).show();
                            recreate();
                        }
                        else{
                            Toast.makeText(ListaCaracteristicasActivity.this, "nao excluiu", Toast.LENGTH_SHORT).show();
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
                        params.put("Data", datas.get(info.position));

                        return params;
                    }
                };
                stringRequest.setShouldCache(false);
                requestQueue.add(stringRequest);
                break;
        }

        return super.onContextItemSelected(item);
    }
}
