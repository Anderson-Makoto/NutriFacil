package com.example.makoto.testvolleysimplerequest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            Alarme alarme= new Alarme();
            alarme.startAlarmManager(context);
        }

        final List<usuario> arrayList;

        InternetTeste internetTeste= new InternetTeste(context);
        final BDsqLite db= new BDsqLite(context);

       arrayList= db.getAll();

        if(internetTeste.estaConectado()){
          boolean ver= arrayList.isEmpty();
          if(!ver){
              RequestQueue requestQueue= Volley.newRequestQueue(context);

              StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://redgreencompany.website/ServidorLocalParaRemotoFitness.php", new Response.Listener<String>() {
                  @Override
                  public void onResponse(String response) {
                      if(response.equals("1")){
                          Toast.makeText(context, "nao enviou", Toast.LENGTH_SHORT).show();
                      }
                      else{
                          Toast.makeText(context, "Enviado", Toast.LENGTH_SHORT).show();
                          db.limparTudo();
                      }
                  }
              }, new Response.ErrorListener() {
                  @Override
                  public void onErrorResponse(VolleyError error) {

                  }
              }){
                  @Override
                  protected Map<String, String> getParams() throws AuthFailureError {
                      Map<String, String> params= new HashMap<>();

                      params.put("Email", arrayList.get(0).getEmail());
                      params.put("Senha", arrayList.get(0).getSenha());
                      params.put("Nome", arrayList.get(0).getNome());
                      params.put("Quant", arrayList.get(0).getQuantidade());
                      params.put("Refeicao", arrayList.get(0).getRefeicao());
                      params.put("Data", arrayList.get(0).getData());
                      params.put("Id_usuario", Integer.toString(arrayList.get(0).getId()));

                      return params;
                  }
              };
              stringRequest.setShouldCache(false);
              requestQueue.add(stringRequest);
          }

        }
        else{
            Toast.makeText(context, "desconectado", Toast.LENGTH_SHORT).show();
        }
    }
}
