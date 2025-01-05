package com.example.shellapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class Configuracion extends AppCompatActivity {

    ImageButton userButtonHome;
    ImageButton userButtonBuscar;
    ImageButton userButtonEdit;
    ImageButton userButtonUser;
    ImageButton configuracionRegreso;
    ImageButton configuracionLogOut;
    FirebaseAuth autent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        userButtonHome = findViewById(R.id.userButtonHome);
        userButtonUser = findViewById(R.id.userButtonUser);
        userButtonBuscar = findViewById(R.id.userButtonBuscar);
        userButtonEdit = findViewById(R.id.userButtonEdit);

        configuracionRegreso = findViewById(R.id.configuracionRegreso);
        configuracionLogOut = findViewById(R.id.configuracionLogOut);
        autent = FirebaseAuth.getInstance();

        userButtonUser.setOnClickListener(view -> {
            startActivity(new Intent(Configuracion.this, User.class));
        });

        userButtonHome.setOnClickListener(view -> {
            startActivity(new Intent(Configuracion.this, Wiki.class));
        });

        userButtonBuscar.setOnClickListener(view -> {
            startActivity(new Intent(Configuracion.this, Search.class));
        });

        userButtonEdit.setOnClickListener(view -> {
            startActivity(new Intent(Configuracion.this, Edit.class));
        });

        configuracionLogOut.setOnClickListener(view -> {
            autent.signOut();
            startActivity(new Intent(Configuracion.this, Login.class));
        });

        configuracionRegreso.setOnClickListener(view -> {
            startActivity(new Intent(Configuracion.this, User.class));
        });


    }
}