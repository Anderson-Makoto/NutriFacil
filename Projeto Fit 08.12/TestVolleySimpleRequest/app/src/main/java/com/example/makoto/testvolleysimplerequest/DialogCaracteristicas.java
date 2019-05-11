package com.example.makoto.testvolleysimplerequest;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class DialogCaracteristicas extends AppCompatDialogFragment {
    TextView tvCaracteristica;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

        LayoutInflater inflater= getActivity().getLayoutInflater();

        View view= inflater.inflate(R.layout.caracteristicas, null);
        tvCaracteristica= view.findViewById(R.id.tvCaracteristicas);
        tvCaracteristica.setText(ListaCaracteristicasActivity.medidas);

        builder.setView(view).setTitle("Medidas").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });



        return builder.create();
    }
}
