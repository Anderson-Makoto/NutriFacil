package com.example.makoto.testvolleysimplerequest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class CadastroNutricionistaActivity extends AppCompatActivity {

    TextView tvSalvar;
    EditText etNome, etEmail, etSenha, etCRN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_nutricionista);

        tvSalvar= (TextView) findViewById(R.id.tvSalvar);
        etNome= (EditText) findViewById(R.id.etNome);
        etEmail= (EditText) findViewById(R.id.etEmail);
        etSenha= (EditText) findViewById(R.id.etSenha);
        etCRN= (EditText) findViewById(R.id.etCRN);

        tvSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RequestQueue requestQueue= Volley.newRequestQueue(CadastroNutricionistaActivity.this);

                StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/CadastroNutricionistaFitness.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(Integer.parseInt(response)== 1){
                            Toast.makeText(CadastroNutricionistaActivity.this, "Nutricionista cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
                            requestQueue.stop();
                            finish();
                        }
                        else{
                            Toast.makeText(CadastroNutricionistaActivity.this, response, Toast.LENGTH_SHORT).show();
                            requestQueue.stop();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CadastroNutricionistaActivity.this, "Erro no codigo.", Toast.LENGTH_SHORT).show();
                        requestQueue.stop();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params= new HashMap<>();

                        params.put("Nome", etNome.getText().toString());
                        params.put("Email", etEmail.getText().toString());
                        params.put("Senha", etSenha.getText().toString());
                        params.put("CRN", etCRN.getText().toString());
                        params.put("tipo", "1");

                        return params;
                    }
                };
                stringRequest.setShouldCache(false);
                requestQueue.add(stringRequest);
            }
        });
    }
}
