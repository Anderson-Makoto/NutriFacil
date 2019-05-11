package com.example.makoto.testvolleysimplerequest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BDsqLite extends SQLiteOpenHelper {

    private static final String DBName= "Fitness";
    private static final int DBVersion= 2;

    public BDsqLite(Context context) {
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_TABLE_USER= "CREATE TABLE Usuario(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Email TEXT NOT NULL," +
                "Senha TEXT NOT NULL,"+
                "Nome TEXT NOT NULL," +
                "Refeicao TEXT NOT NULL," +
                "Data TEXT NOT NULL,"+
                "Quantidade TEXT NOT NULL)";


        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Usuario;");
        this.onCreate(db);
    }

    public void insert(String email, String senha, String alimento, String quant, String refeicao, String data){
        SQLiteDatabase db= this.getWritableDatabase();

        ContentValues contentValues= new ContentValues();

        contentValues.put("Email", email);
        contentValues.put("Senha", senha);
        contentValues.put("Nome", alimento);
        contentValues.put("Refeicao", refeicao);
        contentValues.put("Data", data);
        contentValues.put("Quantidade", quant);

        db.insert("Usuario", null, contentValues);

        db.close();
    }

    public List<usuario> getAll(){
        SQLiteDatabase db= this.getReadableDatabase();

        Cursor cursor= db.rawQuery("select * from Usuario", null);

        List<usuario> arrayList= new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                usuario u= new usuario();

                u.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                u.setEmail(cursor.getString(cursor.getColumnIndex("Email")));
                u.setSenha(cursor.getString(cursor.getColumnIndex("Senha")));
                u.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
                u.setRefeicao(cursor.getString(cursor.getColumnIndex("Refeicao")));
                u.setData(cursor.getString(cursor.getColumnIndex("Data")));
                u.setQuantidade(cursor.getString(cursor.getColumnIndex("Quantidade")));

                arrayList.add(u);
            }while(cursor.moveToNext());
        }

        cursor.close();



        return arrayList;
    }

    public void excluir(int id){
        SQLiteDatabase db= this.getWritableDatabase();

        db.delete("Usuario", "Id= ?", new String[]{Integer.toString(id)});

        db.close();
    }

    public void limparTudo(){
        SQLiteDatabase db= this.getWritableDatabase();

        db.delete("Usuario", null, null);
        db.close();
    }
}
