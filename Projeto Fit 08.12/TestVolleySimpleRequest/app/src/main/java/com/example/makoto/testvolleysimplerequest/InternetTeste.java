package com.example.makoto.testvolleysimplerequest;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetTeste {
    Context context;

    public InternetTeste(Context context) {
        this.context = context;
    }

    public boolean estaConectado(){
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);

        if(connectivityManager!= null){
            NetworkInfo info= connectivityManager.getActiveNetworkInfo();
            if(info!= null){
                if(info.getState()== NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }

        return false;
    }
}
