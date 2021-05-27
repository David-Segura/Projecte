package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import Adapters.TaulesAdapter;
import Model.Cambrer;
import Model.Comanda;
import Model.Taula;

public class TaulesActivity extends AppCompatActivity {
    RecyclerView rcyTaules;
    private TaulesAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taules);


        rcyTaules=findViewById(R.id.rcyTaules);

        rcyTaules.setLayoutManager(new GridLayoutManager(this, 3));
        rcyTaules.setHasFixedSize(true);

        List<Taula> lTaules = new ArrayList<>();
        Cambrer c = new Cambrer(1, "Pepe", "Rodriguez", "String cognom2", "String user", "String password");
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
        lTaules.add(t3);

        mAdapter = new TaulesAdapter(lTaules,this,this);
        rcyTaules.setAdapter(mAdapter);


    }
}