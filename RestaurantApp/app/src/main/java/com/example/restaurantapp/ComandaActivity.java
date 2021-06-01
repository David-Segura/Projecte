package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import Adapters.BotoCategoriaAdapter;
import Adapters.LineaComandaAdapter;
import Adapters.PlatsAdapter;
import Adapters.TaulesAdapter;
import GestioRestaurant.NMCambrer;
import GestioRestaurant.NMCategoria;
import GestioRestaurant.NMComanda;
import GestioRestaurant.NMLineaComanda;
import GestioRestaurant.NMPlat;
import GestioRestaurant.NMTaula;

public class ComandaActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rcyComanda;
    RecyclerView rcyCarta;
    List<NMPlat> lPlats = new ArrayList<>();
    List<NMPlat> mPlatsFiltrats = new ArrayList<>();
    PlatsAdapter mAdapter;
    ComandaActivity mActivity;
    List<NMLineaComanda> lComandes = new ArrayList<>();
    int numComanda = 1;
    LineaComandaAdapter lcAdapter;
    TextView txvPreuTotalComanda;
    LinearLayout lytPerBotons;
    Button btnConfirmar;
    NMTaula taula;
    int maxComandaCodi;
    NMCambrer c;
    NMComanda com;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda);
        rcyComanda = findViewById(R.id.rcyComanda);
        rcyCarta = findViewById(R.id.rcyCarta);
        txvPreuTotalComanda = findViewById(R.id.txvPreuTotalComanda);
        lytPerBotons = findViewById(R.id.lytPerBotons);
        btnConfirmar = findViewById(R.id.btnConfirmar);


        lComandes = new ArrayList<>();

        rcyCarta.setLayoutManager(new GridLayoutManager(this, 2));
        rcyCarta.setHasFixedSize(true);

        mActivity = this;

        Intent i = getIntent();
        taula = (NMTaula)i.getSerializableExtra("TAULA");
        c = (NMCambrer) i.getSerializableExtra("CAMBRER");
        getMaxCodiComanda("99");
        Log.d("YYY","Comandes: "+taula.getNMComanda().toString());

        if(taula.getNMComanda().getCodi()==0)
            rebreCarta("3");
        else
            rebreComanda("4",taula.getNMComanda());



       // mAdapter = new PlatsAdapter(lPlats,mActivity,getApplicationContext());
        //rcyCarta.setAdapter(mAdapter);

        rcyComanda.setLayoutManager(new GridLayoutManager(this, 1));
        rcyComanda.setHasFixedSize(true);
        lcAdapter = new LineaComandaAdapter(lComandes,mActivity,getApplicationContext());
        rcyComanda.setAdapter(lcAdapter);



        btnConfirmar.setOnClickListener(this);

    }

    public void onPlatSelected(NMPlat p){
        NMLineaComanda lc = new NMLineaComanda();
        lc.setItem(p);
        lc.setQuantitat(1);
        boolean trobat = false;


        for(int i = 0; i<lComandes.size();i++){
            NMPlat plat = lComandes.get(i).getItem();
            if(p.equals(plat)){
                Log.d("XXX","Plat repetit");

                NMLineaComanda linCom = lComandes.get(i);
                linCom.setQuantitat(linCom.getQuantitat()+1);
                trobat = true;
            }
        }

        if(!trobat){
            lc.setNum(numComanda);
            lComandes.add(lc);
            numComanda++;
        }

        String preuSenseEuro = txvPreuTotalComanda.getText().toString().substring(0,txvPreuTotalComanda.getText().length()-2);
        float preuAnterior = Float.parseFloat(preuSenseEuro);
        float preuAAfegir = lc.getItem().getPreu();
        float nouPreu = preuAnterior+preuAAfegir;
        txvPreuTotalComanda.setText(nouPreu+" €");

        lcAdapter = new LineaComandaAdapter(lComandes,mActivity,getApplicationContext());
        rcyComanda.setAdapter(lcAdapter);
        //lComandes.add(p);
    }

    public void onPlatDesseleccionat(NMPlat p) {
        boolean trobat = false;
        for(int i = 0; i<lComandes.size();i++){
            NMPlat plat = lComandes.get(i).getItem();
            if(p.equals(plat)){
                Log.d("XXX","Plat trobat");

                NMLineaComanda linCom = lComandes.get(i);
                if(linCom.getQuantitat() == 1){
                    lComandes.remove(linCom);
                }else{
                    linCom.setQuantitat(linCom.getQuantitat()-1);
                }
                //linCom.setQuantitat(linCom.getQuantitat()+1);
                String preuSenseEuro = txvPreuTotalComanda.getText().toString().substring(0,txvPreuTotalComanda.getText().length()-2);
                float preuAnterior = Float.parseFloat(preuSenseEuro);
                float preuATreure = linCom.getItem().getPreu();
                float nouPreu = preuAnterior-preuATreure;
                txvPreuTotalComanda.setText(nouPreu+" €");
            }
        }





        lcAdapter = new LineaComandaAdapter(lComandes,mActivity,getApplicationContext());
        rcyComanda.setAdapter(lcAdapter);
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
                    Socket s = new Socket("192.168.1.35", 9876);

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

                        //NMCategoria categoria = (NMCategoria) input.readObject();
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


                        //Log.d("XXX",categoria.toString());
                        Log.d("XXX",plat.toString());
                        lPlats.add(plat);
                        Log.d("XXX",lPlats.size()+"");






                    }


                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            mAdapter = new PlatsAdapter(lPlats,mActivity,getApplicationContext());
                            rcyCarta.setAdapter(mAdapter);

                            List<NMCategoria> lcats = new ArrayList<>();

                            for(NMPlat p : lPlats){
                                if(!lcats.contains(p.getCategoria())) {
                                    Log.d("XXX", "Categoria nova: " + p.getCategoria().toString());
                                    lcats.add(p.getCategoria());
                                }
                            }
                            /*NMCategoria c = new NMCategoria(1, "entrants", 0);
                            NMCategoria c1 = new NMCategoria(2, "primers", 0);
                            NMCategoria c2 = new NMCategoria(3, "segons", 0);
                            NMCategoria c3 = new NMCategoria(4, "postres", 0);
                            lcats.add(c);
                            lcats.add(c1);
                            lcats.add(c2);
                            lcats.add(c3);
                            NMPlat p = new NMPlat(1, "Entrant 1", "El rico entrante", 1.5F, null, true, c);
                            NMPlat p1 = new NMPlat(2, "Primer Plat", "Primero", 5.5F, null, true, c1);
                            NMPlat p2 = new NMPlat(3, "Segon Plat", "Segundo", 7.5F, null, true, c2);
                            NMPlat p3 = new NMPlat(4, "Postres", "Postre", 3.5F, null, true, c3);



                            lPlats.add(p);
                            lPlats.add(p1);
                            lPlats.add(p2);
                            lPlats.add(p3);



                            //int id = 1;
                            */for(NMCategoria categ : lcats) {

                                Button btnTag = new Button(getApplicationContext());
                                btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                btnTag.setText(categ.getNom());
                                btnTag.setBackgroundResource(R.color.main_color);
                                btnTag.setTextColor(Color.WHITE);
                                btnTag.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mPlatsFiltrats.clear();
                                        for (NMPlat plat : lPlats) {
                                            if (plat.getCategoria().equals(categ)) {
                                                mPlatsFiltrats.add(plat);

                                                mAdapter = new PlatsAdapter(mPlatsFiltrats,mActivity,getApplicationContext());
                                                rcyCarta.setAdapter(mAdapter);
                                            }
                                        }



                                    }
                                });
                                //add button to the layout
                                lytPerBotons.addView(btnTag);

                            }


                            //Afegir botó tots
                            Button btnTag = new Button(getApplicationContext());
                            btnTag.setLayoutParams(new LinearLayout.LayoutParams(227, LinearLayout.LayoutParams.WRAP_CONTENT));
                            btnTag.setText("Tots");
                            btnTag.setBackgroundResource(R.color.main_color);
                            btnTag.setTextColor(Color.WHITE);

                            btnTag.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mAdapter = new PlatsAdapter(lPlats,mActivity,getApplicationContext());
                                    rcyCarta.setAdapter(mAdapter);

                                }




                            });
                            //add button to the layout
                            lytPerBotons.addView(btnTag);

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


    @Override
    public void onClick(View v) {
        enviaLlistaLineaComanda("5",taula,lComandes);
    }

    private void enviaLlistaLineaComanda(String msgCod, NMTaula taula, List<NMLineaComanda> lComandes) {

        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //Replace below IP with the IP of that device in which server socket open.
                    //If you change port then change the port number in the server side code also.
                    Socket s = new Socket("192.168.1.35", 9876);

                    ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                    out.writeObject(msgCod);
                    //Log.d("XXX","Enviant taula "+taula.toString());
                    out.writeObject(c);
                    Log.d("Codi comanda: ", taula.getNMComanda().getCodi()+"");
                    out.writeObject(taula.getNMComanda());
                    out.writeObject(taula);
                    out.writeObject(lComandes.size());
                    for(int i = 0; i<lComandes.size();i++){
                        NMLineaComanda lc = lComandes.get(i);
                        Log.d("XXX","Enviant linea comanda "+lc.toString());
                        out.writeObject(lc);
                    }
                    //PrintWriter output = new PrintWriter(out);

                    //output.println(msg);
                    //output.flush();
                    ObjectInputStream input = new ObjectInputStream(s.getInputStream());

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                    //output.close();
                    out.close();
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } /*catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }*/
            }

        });

        thread.start();
    }


    private void getMaxCodiComanda(String msgCod) {

        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //Replace below IP with the IP of that device in which server socket open.
                    //If you change port then change the port number in the server side code also.
                    Socket s = new Socket("192.168.1.35", 9876);

                    ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                    out.writeObject(msgCod);
                    //Log.d("XXX","Enviant taula "+taula.toString());
                    ObjectInputStream oin = new ObjectInputStream(s.getInputStream());
                    int max = (int) oin.readObject();
                    //PrintWriter output = new PrintWriter(out);

                    //output.println(msg);
                    //output.flush();
                    ObjectInputStream input = new ObjectInputStream(s.getInputStream());

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            maxComandaCodi = max;
                            com = new NMComanda(maxComandaCodi+1, new Timestamp(System.currentTimeMillis()), taula, c);
                            taula.setNMComanda(com);
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






    private void rebreComanda(final String msgCod, NMComanda comanda) {
        //Log.d("XXX",edtUser.getText().toString());

        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //Replace below IP with the IP of that device in which server socket open.
                    //If you change port then change the port number in the server side code also.
                    Socket s = new Socket("192.168.1.35", 9876);

                    ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                    out.writeObject(msgCod);

                    out.writeObject(comanda.getCodi());

                    ObjectInputStream input = new ObjectInputStream(s.getInputStream());

                    final int resposta = (int) input.readObject();


                    Log.d("XXX",resposta+"");
                    for(int i = 0; i<resposta;i++){
                        Log.d("XXX","Rebent linea comanda");


                        NMLineaComanda lc = (NMLineaComanda) input.readObject();






                        Log.d("XXX",lc.toString());
                        lComandes.add(lc);
                        Log.d("XXX",lComandes.size()+"");






                    }


                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            lcAdapter = new LineaComandaAdapter(lComandes,mActivity,getApplicationContext());
                            rcyComanda.setAdapter(lcAdapter);




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

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("STOP","STOP");
        TaulesActivity.setParar(false);
    }
}