package com.example.makoto.testvolleysimplerequest;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.GridLayout;

import java.util.concurrent.TimeUnit;

public class TelaNutricionistaActivity extends AppCompatActivity {

    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_nutricionista);

        gridLayout= (GridLayout) findViewById(R.id.mainGrid);

        setSingEvent(gridLayout);
    }

    private void setSingEvent(GridLayout gridLayout) {
        CardView cardView= (CardView)gridLayout.getChildAt(0);
        CardView cardView1= (CardView)gridLayout.getChildAt(1);
        CardView cardView2= (CardView)gridLayout.getChildAt(2);
        CardView cardView3= (CardView)gridLayout.getChildAt(3);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TelaNutricionistaActivity.this, VerClientesActivity.class);
                startActivity(intent);
            }
        });

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TelaNutricionistaActivity.this, VincularClienteActivity.class);
                startActivity(intent);
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TelaNutricionistaActivity.this, ExcluirClienteActivity.class);
                startActivity(intent);
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(TelaNutricionistaActivity.this, AlterarContaNutricionistaActivity.class);
                startActivity(intent);
            }
        });
    }
}
