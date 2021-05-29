package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Adapters.TaulesAdapter;
import GestioRestaurant.NMCambrer;
import GestioRestaurant.NMComanda;
import GestioRestaurant.NMTaula;

public class TaulesActivity extends AppCompatActivity {
    RecyclerView rcyTaules;
    private TaulesAdapter mAdapter;
    List<NMTaula> lTaules = new ArrayList<>();
    int taulaIndex;
    TaulesActivity mActivity;
    NMCambrer cambrer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taules);

        Intent i = getIntent();
        cambrer = (NMCambrer) i.getSerializableExtra("CAMBRER");

        Log.d("XXX",cambrer.toString());
        mActivity = this;
        rcyTaules=findViewById(R.id.rcyTaules);

        rcyTaules.setLayoutManager(new GridLayoutManager(this, 3));
        rcyTaules.setHasFixedSize(true);


        /*Cambrer c = new Cambrer(1, "Pepe", "Rodriguez", "String cognom2", "String user", "String password");
        Cambrer c1 = new Cambrer(2, "Javi", "Rodriguez", "String cognom2", "String user", "String password");
        Cambrer c2 = new Cambrer(3, "Maria", "Rodriguez", "String cognom2", "String user", "String password");
        Cambrer c3 = new Cambrer(4, "Paco", "Rodriguez", "String cognom2", "String user", "String password");

        int time = (int) (System.currentTimeMillis());
        Timestamp tsTemp = new Timestamp(time);
        Comanda co = new Comanda(1, tsTemp, 1, c);
        Comanda co1 = new Comanda(2, tsTemp, 2, c1);
        Comanda co2 = new Comanda(3, tsTemp, 3, c2);
        Comanda co3 = new Comanda(4, tsTemp, 4, c3);

        Taula t = new Taula(1,co);
        Taula t1 = new Taula(2,co1);
        Taula t2 = new Taula(3,co2);
        Taula t3 = new Taula(4,co3);

        lTaules.add(t);
        lTaules.add(t1);
        lTaules.add(t2);
        lTaules.add(t3);*/

        rebreTaules("2");





    }

    public void onTaulaSelected(List<NMTaula> mTaules, int filaSeleccionada) {
        taulaIndex = filaSeleccionada;
        NMTaula t = mTaules.get(taulaIndex);


        Log.d("APP", "" + this.getPackageName());
        Intent i = new Intent(this, ComandaActivity.class);
        i.putExtra("TAULA", t);
        //i.putExtra("TYPE", typeName);

        this.startActivity(i);
    }
    private void rebreTaules(final String msgCod) {


        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //Replace below IP with the IP of that device in which server socket open.
                    //If you change port then change the port number in the server side code also.
                    Socket s = new Socket("192.168.1.34", 9876);

                    ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                    out.writeObject(msgCod);
                    //out.writeObject(user);
                    //out.writeObject(password);
                    //PrintWriter output = new PrintWriter(out);

                    //output.println(msg);
                    //output.flush();
                    ObjectInputStream input = new ObjectInputStream(s.getInputStream());
                    //final String st = (String) input.readObject();
                    //Log.d("XXX",st);
                    final String resposta = (String) input.readObject();


                    Log.d("XXX",resposta);
                    for(int i = 0; i<Integer.parseInt(resposta);i++){
                        Log.d("XXX","Rebent taula");

                        NMCambrer cambrer = (NMCambrer) input.readObject();
                        NMComanda comanda = (NMComanda) input.readObject();
                        NMTaula t = (NMTaula) input.readObject();

                        comanda.setNMCambrer(cambrer);
                        comanda.setTaula(t);
                        t.setNMComanda(comanda);







                        lTaules.add(t);

                    }


                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            mAdapter = new TaulesAdapter(lTaules,mActivity,getApplicationContext(),cambrer);
                            rcyTaules.setAdapter(mAdapter);
                        }
                    });

                    //output.close();
                    out.close();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        });

        thread.start();

    }
}