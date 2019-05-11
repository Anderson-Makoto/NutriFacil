package com.example.makoto.testvolleysimplerequest;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class CaracteristicasUsuarioActivity extends AppCompatActivity {

    Button btnSalvar, btnData;
    EditText etPeso, etAltura, etCircTorso, etCircBraco, etCircPerna;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caracteristicas_usuario);

        btnSalvar= (Button) findViewById(R.id.btnSalvar);
        etPeso= (EditText) findViewById(R.id.etPeso);
        etAltura= (EditText) findViewById(R.id.etAltura);
        etCircTorso= (EditText) findViewById(R.id.etCircTorso);
        etCircBraco= (EditText) findViewById(R.id.etCircBraco);
        etCircPerna= (EditText) findViewById(R.id.etCircPerna);
        btnData= (Button) findViewById(R.id.btnData);

        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar= Calendar.getInstance();
                int ano= calendar.get(Calendar.YEAR);
                int mes= calendar.get(Calendar.MONTH);
                int dia= calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog= new DatePickerDialog(CaracteristicasUsuarioActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, ano, mes, dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }

        });
        dateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                btnData.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                data= year+"-"+(month+1)+"-"+dayOfMonth;
            }
        };

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RequestQueue requestQueue= Volley.newRequestQueue(CaracteristicasUsuarioActivity.this);

                final StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/CadastrarCaracteristicasUsuarioFitness.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("1")){

                            Toast.makeText(CaracteristicasUsuarioActivity.this, "Medidas cadastradas com sucesso", Toast.LENGTH_SHORT).show();

                            requestQueue.stop();
                            finish();

                        }
                        else{
                            Toast.makeText(CaracteristicasUsuarioActivity.this, "Falha ao Cadastrar medidas.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CaracteristicasUsuarioActivity.this, "Erro no código.", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params= new HashMap<>();

                        params.put("Data", data);
                        params.put("Perna", etCircPerna.getText().toString());
                        params.put("Braco", etCircBraco.getText().toString());
                        params.put("Torso", etCircTorso.getText().toString());
                        params.put("Altura", etAltura.getText().toString());
                        params.put("Peso", etPeso.getText().toString());
                        params.put("Id_usuario", Integer.toString(MainActivity.id));

                        return params;
                    }
                };
                stringRequest.setShouldCache(false);
                requestQueue.add(stringRequest);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.ver_caracteristicas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.item1:
                Intent intent= new Intent(CaracteristicasUsuarioActivity.this, ListaCaracteristicasActivity.class);
                startActivity(intent);
                finish();
                break;

        }


        return super.onOptionsItemSelected(item);
    }


}
