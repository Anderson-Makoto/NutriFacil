package com.example.makoto.testvolleysimplerequest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class VerNutricionistaActivity extends AppCompatActivity {

    TextView tvNome, tvEmail, tvCRN;
    Button btnDesvincular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_nutricionista);

        tvNome= (TextView) findViewById(R.id.tvNome);
        tvEmail= (TextView) findViewById(R.id.tvEmail);
        tvCRN= (TextView) findViewById(R.id.tvCRN);
        btnDesvincular= (Button) findViewById(R.id.btnDesvincular);

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        final StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/PegarNutricionistaDadosFitness.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] dados= response.split("\\|");
                if(!response.equals("-1")){
                    tvNome.setText("Nome: "+dados[0]);
                    tvEmail.setText("Email: "+dados[1]);
                    tvCRN.setText("CRN: "+dados[2]);
                }
                else{
                    Toast.makeText(VerNutricionistaActivity.this, "Não Vinculado a Nenhum Nutricionista.", Toast.LENGTH_SHORT).show();
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

                params.put("Id", Integer.toString(MainActivity.id));

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

        btnDesvincular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue= Volley.newRequestQueue(VerNutricionistaActivity.this);
                StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/DesvincularFitness.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("1")){
                            Toast.makeText(VerNutricionistaActivity.this, "Desvinculado.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else if(response.equals("-1")){
                            Toast.makeText(VerNutricionistaActivity.this, "Não foi devinculado", Toast.LENGTH_SHORT).show();
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
