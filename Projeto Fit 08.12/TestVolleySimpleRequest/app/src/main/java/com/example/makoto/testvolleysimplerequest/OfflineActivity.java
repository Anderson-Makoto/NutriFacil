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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.Calendar;

public class OfflineActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button btnSalvar;
    private EditText etNome, etEmail, etSenha, etQuant;
    private TextView tvData;
    private Spinner Refeicao;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    String data= "", refeicao= "";
    BDsqLite db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);

        btnSalvar= (Button) findViewById(R.id.btnSalvar);
        etNome= (EditText) findViewById(R.id.etNome);
        etEmail= (EditText) findViewById(R.id.etEmail);
        tvData= (TextView) findViewById(R.id.tvData);
        Refeicao= (Spinner) findViewById(R.id.sRefeicao);
        etQuant= (EditText) findViewById(R.id.etQuant);
        etSenha= (EditText) findViewById(R.id.etSenha);
        db= new BDsqLite(OfflineActivity.this);

        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.refeicao, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Refeicao.setAdapter(adapter);

        Refeicao.setOnItemSelectedListener(this);

        tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar= Calendar.getInstance();
                int ano= calendar.get(Calendar.YEAR);
                int mes= calendar.get(Calendar.MONTH);
                int dia= calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog= new DatePickerDialog(OfflineActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, ano, mes, dia);
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

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!refeicao.equals("") && !data.equals("") && !etEmail.getText().toString().matches("") && !etSenha.getText().toString().matches("") && !etNome.getText().toString().matches("") && !etQuant.getText().toString().matches("")){
                    db.insert(etEmail.getText().toString(), etSenha.getText().toString(), etNome.getText().toString(), etQuant.getText().toString(), refeicao, data);
                    Toast.makeText(OfflineActivity.this, refeicao+" "+data, Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        refeicao= parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.itens_sqlite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.item1:
                Intent intent= new Intent(OfflineActivity.this, itensSQLiteActivity.class);
                startActivity(intent);
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
