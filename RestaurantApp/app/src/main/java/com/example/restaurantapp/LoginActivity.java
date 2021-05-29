package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import GestioRestaurant.NMCambrer;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnEntra;
    EditText edtUser;
    EditText edtPwd;
    Boolean correcte = true;
    String correcte2;
    TextView txvLoginCorrecte;
    NMCambrer cambrer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnEntra = findViewById(R.id.btnEntra);
        edtUser = findViewById(R.id.edtUser);
        edtPwd = findViewById(R.id.edtPwd);
        txvLoginCorrecte = findViewById(R.id.txvLoginCorrecte);

        btnEntra.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        String a  = sendMessage("1",edtUser.getText().toString(),edtPwd.getText().toString());




    }

    private String sendMessage(final String msg, final String user, final String password) {


        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    //Replace below IP with the IP of that device in which server socket open.
                    //If you change port then change the port number in the server side code also.
                    Socket s = new Socket("192.168.1.34", 9876);

                    ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                    out.writeObject(msg);
                    out.writeObject(user);
                    out.writeObject(password);
                    //PrintWriter output = new PrintWriter(out);

                    //output.println(msg);
                    //output.flush();
                    ObjectInputStream input = new ObjectInputStream(s.getInputStream());
                    //final String st = (String) input.readObject();
                    //Log.d("XXX",st);
                    final String resposta = (String) input.readObject();

                    correcte2 = resposta;

                    cambrer = (NMCambrer) input.readObject();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(resposta.equals("Login incorrecte")) {
                                txvLoginCorrecte.setVisibility(View.VISIBLE);
                            }else{
                                txvLoginCorrecte.setVisibility(View.INVISIBLE);
                            }
                            if(txvLoginCorrecte.getVisibility() == View.VISIBLE){
                                correcte = false;
                            }else{
                                correcte = true;
                            }

                            if(correcte) {
                                Intent i = new Intent(getApplicationContext(), TaulesActivity.class);
                                i.putExtra("CAMBRER",cambrer);
                                startActivity(i);
                                Log.d("XXX",cambrer.toString());
                            }
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
        return correcte2;
    }
}