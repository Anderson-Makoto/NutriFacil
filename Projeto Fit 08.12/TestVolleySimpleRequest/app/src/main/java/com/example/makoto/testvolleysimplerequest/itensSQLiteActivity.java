package com.example.makoto.testvolleysimplerequest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class itensSQLiteActivity extends AppCompatActivity {

    ListView lvprodutos;
    BDsqLite db;
    List<usuario> arrayList= new ArrayList<>();
    int identificador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens_sqlite);

        lvprodutos= (ListView) findViewById(R.id.lvProdutos);
        db= new BDsqLite(itensSQLiteActivity.this);

        arrayList= db.getAll();

        ArrayAdapter<usuario> arrayAdapter= new ArrayAdapter<>(itensSQLiteActivity.this, android.R.layout.simple_list_item_1, arrayList);
        lvprodutos.setAdapter(arrayAdapter);

        registerForContextMenu(lvprodutos);
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
                db.excluir(arrayList.get(info.position).getId());
                break;
        }

        return super.onContextItemSelected(item);
    }
}
