package com.example.makoto.testvolleysimplerequest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import java.util.Random;

public class CadastroActivity extends AppCompatActivity {
    TextView tvSalvar;
    EditText etNome, etEmail, etSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        tvSalvar= (TextView) findViewById(R.id.tvSalvar);
        etNome= (EditText) findViewById(R.id.etNome);
        etEmail= (EditText) findViewById(R.id.etEmail);
        etSenha= (EditText) findViewById(R.id.etSenha);

        tvSalvar.setText("Salvar");
        final Random rand= new Random();

        tvSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RequestQueue requestQueue= Volley.newRequestQueue(CadastroActivity.this);
                StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/CadastroUsuarioFitness.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(Integer.parseInt(response)== 1){
                            Toast.makeText(CadastroActivity.this, "Usuário cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
                            requestQueue.stop();
                            finish();
                            int codigo= rand.nextInt(1000);
                            //enviarEmail();
                            //finish();
                        }

                        else{
                            Toast.makeText(CadastroActivity.this, "Campos inválidos.", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CadastroActivity.this, "Erro no programa", Toast.LENGTH_SHORT).show();
                        requestQueue.stop();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params= new HashMap<>();

                        params.put("Nome", etNome.getText().toString());
                        params.put("Email", etEmail.getText().toString());
                        params.put("Senha", etSenha.getText().toString());
                        params.put("tipo", "0");

                        return params;
                    }
                };
                stringRequest.setShouldCache(false);
                requestQueue.add(stringRequest);
            }
        });

    }

    private void enviarEmail(){

        final String nome = etNome.getText().toString();
        final String email = etEmail.getText().toString();
        final String subject = "Aplicativo Fitness";
        final String body = nome+", bem vindo ao aplicativo de controle de consumo de nutrientes.";


        if(!isOnline()) {
            Toast.makeText(getApplicationContext(), "Não estava online para enviar e-mail!", Toast.LENGTH_SHORT).show();
            System.exit(0);
        }

        new Thread(new Runnable(){
            @Override
            public void run() {
                Mail m = new Mail();

                String[] toArr = {email};
                m.setTo(toArr);

                //m.setFrom("seunome@seuemail.com.br"); //caso queira enviar em nome de outro
                m.setSubject(subject);
                m.setBody(body);

                try {
                    //m.addAttachment("pathDoAnexo");//anexo opcional
                    m.send();
                }
                catch(RuntimeException rex){ }//erro ignorado
                catch(Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }

                Toast.makeText(getApplicationContext(), "Email enviado!", Toast.LENGTH_SHORT).show();
            }
        }).start();
    }



    public boolean isOnline() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Erro ao verificar se estava online! (" + ex.getMessage() + ")", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
