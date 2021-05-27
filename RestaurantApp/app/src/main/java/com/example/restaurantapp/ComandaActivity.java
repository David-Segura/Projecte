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

import Adapters.PlatsAdapter;
import Adapters.TaulesAdapter;
import GestioRestaurant.NMCambrer;
import GestioRestaurant.NMCategoria;
import GestioRestaurant.NMComanda;
import GestioRestaurant.NMPlat;
import GestioRestaurant.NMTaula;

public class ComandaActivity extends AppCompatActivity {
    RecyclerView rcyComanda;
    RecyclerView rcyCarta;
    List<NMPlat> lPlats = new ArrayList<>();
    PlatsAdapter mAdapter;
    ComandaActivity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda);
        rcyComanda = findViewById(R.id.rcyComanda);
        rcyCarta = findViewById(R.id.rcyCarta);

        rcyCarta.setLayoutManager(new GridLayoutManager(this, 2));
        rcyCarta.setHasFixedSize(true);



        Intent i = getIntent();
        NMTaula taula = (NMTaula)i.getSerializableExtra("TAULA");

        rebreCarta("3");
    }

    public void onPlatSelected(){

    }

    private void rebreCarta(final String msgCod) {
        //Log.d("XXX",edtUser.getText().toString());

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

                        NMCategoria categoria = (NMCategoria) input.readObject();
                        NMPlat plat = (NMPlat) input.readObject();





                        /*NMTaula t = new NMTaula();
                        t.setNumero(input.readObject());
                        NMComanda c = new NMComanda();
                        c.setCodi(lt.get(i).getComanda().getCodi());
                        c.setData(lt.get(i).getComanda().getData());
                        c.setTaula(t);
                        NMCambrer cambrer = new NMCambrer();
                        cambrer.setCodi(lt.get(i).getComanda().getCambrer().getCodi());
                        cambrer.setNom(lt.get(i).getComanda().getCambrer().getNom());
                        cambrer.setCognom1(lt.get(i).getComanda().getCambrer().getCognom1());
                        cambrer.setCognom2(lt.get(i).getComanda().getCambrer().getCognom2());
                        cambrer.setCognom2(lt.get(i).getComanda().getCambrer().getCognom2());
                        cambrer.setUser(lt.get(i).getComanda().getCambrer().getUser());
                        cambrer.setPassword(lt.get(i).getComanda().getCambrer().getPassword());
                        c.setCambrer(cambrer);
                        t.setComanda(c);*/


                        Log.d("XXX",categoria.toString());
                        Log.d("XXX",plat.toString());
                        lPlats.add(plat);
                        Log.d("XXX",lPlats.size()+"");
                    }


                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            mAdapter = new PlatsAdapter(lPlats,mActivity,getApplicationContext());
                            rcyCarta.setAdapter(mAdapter);
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