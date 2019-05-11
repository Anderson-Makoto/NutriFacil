package com.example.makoto.testvolleysimplerequest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TelaUsuarioActivity extends AppCompatActivity {

    ListView lvData;
    Button btnSobreUsuario;
    List<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_usuario);
        lvData= (ListView) findViewById(R.id.lvRefeicao);
        btnSobreUsuario= (Button) findViewById(R.id.btnSobreUsuario);

        btnSobreUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TelaUsuarioActivity.this, CaracteristicasUsuarioActivity.class);
                startActivity(intent);
            }
        });

        final RequestQueue requestQueue= Volley.newRequestQueue(TelaUsuarioActivity.this);

        StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/PegarDatasFitness.php", new Response.Listener<String>() {
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
                Toast.makeText(TelaUsuarioActivity.this, "Erro no Código.", Toast.LENGTH_SHORT).show();
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



    }

    void onFinish() {
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(TelaUsuarioActivity.this, android.R.layout.simple_list_item_1, datas);
        lvData.setAdapter(adapter);

        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(TelaUsuarioActivity.this, DataActivity.class);
                intent.putExtra("data",  datas.get(position));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.tela_usuario_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.item1:
                Intent intent= new Intent(TelaUsuarioActivity.this, CadastrarRefeicaoActivity.class);
                startActivity(intent);
                break;

            case R.id.item2:
                Intent intent2= new Intent(TelaUsuarioActivity.this, VerNutricionistaActivity.class);
                startActivity(intent2);
                break;

        }


        return super.onOptionsItemSelected(item);
    }


}