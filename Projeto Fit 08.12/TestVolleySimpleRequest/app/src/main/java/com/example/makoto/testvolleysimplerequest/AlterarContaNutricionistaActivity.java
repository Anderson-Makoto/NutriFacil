package com.example.makoto.testvolleysimplerequest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AlterarContaNutricionistaActivity extends AppCompatActivity {
    EditText etSenhaAntiga, etSenhaNova;
    Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_conta_nutricionista);

        etSenhaAntiga= (EditText) findViewById(R.id.etSenhaAtual);
        etSenhaNova= (EditText) findViewById(R.id.etSenhaNova);
        btnSalvar= (Button) findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!etSenhaNova.getText().toString().equals("") && !etSenhaAntiga.getText().toString().equals("")){
                    RequestQueue requestQueue= Volley.newRequestQueue(AlterarContaNutricionistaActivity.this);

                    StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/MudarSenhaNutricionistaFitness.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("1")){
                                Toast.makeText(AlterarContaNutricionistaActivity.this, "Senha Alterada.", Toast.LENGTH_SHORT).show();
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

                            params.put("Id", Integer.toString(MainActivity.id));
                            params.put("SenhaNova", etSenhaNova.getText().toString());

                            return params;
                        }
                    };
                    stringRequest.setShouldCache(false);
                    requestQueue.add(stringRequest);
                }
            }
        });
    }
}
