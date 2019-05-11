package com.example.makoto.testvolleysimplerequest;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RefeicaoDialog extends AppCompatDialogFragment {
    TextView tvRefeicao;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());

        LayoutInflater inflater= getActivity().getLayoutInflater();

        View view= inflater.inflate(R.layout.layout_dialog, null);
        tvRefeicao= view.findViewById(R.id.tvRefeicaoValor);
        tvRefeicao.setText(DataActivity.dados);

        builder.setView(view).setTitle("Refeição").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });



        return builder.create();
    }
}
