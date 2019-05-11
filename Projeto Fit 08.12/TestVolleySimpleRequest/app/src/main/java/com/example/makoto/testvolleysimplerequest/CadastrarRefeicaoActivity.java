package com.example.makoto.testvolleysimplerequest;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.sip.SipSession;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CadastrarRefeicaoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String[] ali= null;
    AutoCompleteTextView acetRefeicao;
    TextView tvData;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    Spinner sRefeicao;
    Button btnSalvar;
    String refeicao= "", data= "";
    EditText etQuant;
    BDsqLite db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_refeicao);

        acetRefeicao= (AutoCompleteTextView) findViewById(R.id.acetRefeicao);
        tvData= (TextView) findViewById(R.id.tvData);
        sRefeicao= (Spinner) findViewById(R.id.sRefeicao);
        sRefeicao.setOnItemSelectedListener(this);
        btnSalvar= (Button) findViewById(R.id.btnSalvar);
        etQuant= (EditText) findViewById(R.id.etQuant);
        db= new BDsqLite(this);

        RequestQueue requestQueue= Volley.newRequestQueue(this);

        StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/NomeAlimentoFitness.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ali= response.split("\\|");
                criarLista();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);




        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.refeicao, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sRefeicao.setAdapter(adapter);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!acetRefeicao.getText().toString().matches("") && !etQuant.getText().toString().matches("") && !refeicao.matches("") && !data.matches("")){
                    final RequestQueue requestQueue= Volley.newRequestQueue(CadastrarRefeicaoActivity.this);

                    final StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/AlimentoFitness.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.equals("1")){

                                Toast.makeText(CadastrarRefeicaoActivity.this, "Refeição Cadastrada com Sucesso.", Toast.LENGTH_SHORT).show();

                                requestQueue.stop();
                                Intent intent= new Intent(CadastrarRefeicaoActivity.this, TelaUsuarioActivity.class);
                                startActivity(intent);
                                finish();

                            }
                            else{
                                Toast.makeText(CadastrarRefeicaoActivity.this, "Falha ao Cadastrar Alimento.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(CadastrarRefeicaoActivity.this, "Erro no código.", Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params= new HashMap<>();

                            params.put("Alimento", acetRefeicao.getText().toString());
                            params.put("Refeicao", refeicao);
                            params.put("Data", data);
                            params.put("Quantidade", etQuant.getText().toString());
                            params.put("Id_usuario", Integer.toString(MainActivity.id));

                            return params;
                        }
                    };
                    stringRequest.setShouldCache(false);
                    requestQueue.add(stringRequest);
                }
                else{
                    Toast.makeText(CadastrarRefeicaoActivity.this, "Preencha todos os Campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar= Calendar.getInstance();
                int ano= calendar.get(Calendar.YEAR);
                int mes= calendar.get(Calendar.MONTH);
                int dia= calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog= new DatePickerDialog(CadastrarRefeicaoActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, ano, mes, dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tvData.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                data= year+"-"+(month+1)+"-"+dayOfMonth;
            }
        };
    }


    public void criarLista(){
        ArrayAdapter<String> adapter2= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ali);
        acetRefeicao.setAdapter(adapter2);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        refeicao= parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
