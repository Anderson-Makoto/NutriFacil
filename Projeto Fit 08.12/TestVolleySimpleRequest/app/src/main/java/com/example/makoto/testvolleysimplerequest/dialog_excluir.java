package com.example.makoto.testvolleysimplerequest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

public class dialog_excluir extends AppCompatDialogFragment {

    private passando passando;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

        LayoutInflater inflater= getActivity().getLayoutInflater();

        View view= inflater.inflate(R.layout.excluir_layout, null);

        builder.setView(view).setTitle("Excluir").setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                passando.applyTexts("1");
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            passando= (passando) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString());
        }

    }

    public interface passando{
        void applyTexts(String sim);
    }
}
