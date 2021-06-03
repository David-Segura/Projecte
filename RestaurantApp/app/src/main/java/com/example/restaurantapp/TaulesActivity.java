package com.example.restaurantapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
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
    public static final String IP_SERVER = "10.132.0.116";
    RecyclerView rcyTaules;
    private TaulesAdapter mAdapter;
    List<NMTaula> lTaules = new ArrayList<>();
    int taulaIndex;
    TaulesActivity mActivity;
    NMCambrer cambrer;
    Handler handler;
    boolean clicaTaula = false;
    static boolean parar = false;
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

        handler = new Handler();

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

        actualitzarTaules();



    }

    public void onTaulaSelected(List<NMTaula> mTaules, int filaSeleccionada) {
        clicaTaula = true;
        taulaIndex = filaSeleccionada;
        try {
            NMTaula t = mTaules.get(taulaIndex);



            Log.d("APP", "" + this.getPackageName());
            Intent i = new Intent(this, ComandaActivity.class);
            i.putExtra("TAULA", t);
            i.putExtra("CAMBRER", cambrer);

            //i.putExtra("TYPE", typeName);

            this.startActivityForResult(i,0);
        }catch(Exception e){}
    }
    private void rebreTaules(final String msgCod) {

        lTaules.clear();
        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //Replace below IP with the IP of that device in which server socket open.
                    //If you change port then change the port number in the server side code also.

                    Socket s = new Socket(IP_SERVER, 9876);

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

    public void mostrarDialeg(String msgCod, List<NMTaula> mTaules, int filaSeleccionada, String msg) {
        clicaTaula = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setCancelable(true);

        if(msg.equals("Segur que vols eliminar la comanda?")) {
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                    eliminaComanda(msgCod, mTaules, filaSeleccionada);
                    lTaules.clear();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    lTaules.clear();

                }
            });
        }else{
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                    lTaules.clear();

                }
            });
        }
        AlertDialog a = builder.create();
        a.show();

    }

    public void onLongTaulaSelected(String msgCod, List<NMTaula> mTaules, int filaSeleccionada) {
        clicaTaula = true;
        try {
            NMTaula t = mTaules.get(filaSeleccionada);
            if (t.getNMComanda().getNMCambrer().getNom().equals(cambrer.getNom())) {
                mostrarDialeg(msgCod, mTaules, filaSeleccionada, "Segur que vols eliminar la comanda?");
            } else {
                mostrarDialeg(null, null, -1, "No pots eliminar la comanda d'un altre cambrer");
            }
        }catch (Exception e){}

    }

    public void eliminaComanda(String msgCod, List<NMTaula> mTaules, int filaSeleccionada) {


        try {
            NMTaula t = mTaules.get(filaSeleccionada);
            final Handler handler = new Handler();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        //Replace below IP with the IP of that device in which server socket open.
                        //If you change port then change the port number in the server side code also.

                        Socket s = new Socket(IP_SERVER, 9876);

                        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                        out.writeObject(msgCod);
                        out.writeObject(t);
                        //out.writeObject(user);
                        //out.writeObject(password);
                        //PrintWriter output = new PrintWriter(out);

                        //output.println(msg);
                        //output.flush();
                        ObjectInputStream input = new ObjectInputStream(s.getInputStream());
                        //final String st = (String) input.readObject();
                        //Log.d("XXX",st);
                        final String resposta = (String) input.readObject();

                        Log.d("XXX", resposta);


                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                mAdapter = new TaulesAdapter(lTaules, mActivity, getApplicationContext(), cambrer);
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
        }catch(Exception e){}
    }



    private final int TIEMPO = 6500;

    public void actualitzarTaules() {
        handler.postDelayed(new Runnable() {
            public void run() {

                // función a ejecutar
                rebreTaules("2"); // función para refrescar la ubicación del conductor, creada en otra línea de código
                if(!parar)
                    handler.postDelayed(this, TIEMPO);
                else
                    Log.d("XXX","clica taula, surto actualitzacio taules");
                    //return;
            }

        }, TIEMPO);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("STOP","STOP");
        parar = true;
    }

    public static void setParar(boolean t){
        parar = t;

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        lTaules.clear();
        Log.d("result","result");
        parar = false;
        rebreTaules("2");
        actualitzarTaules();
    }
}