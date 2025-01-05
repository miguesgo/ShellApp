package com.example.shellapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth autent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autent = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = autent.getCurrentUser(); //Revisamos si el usuario ya inicio sesion, si
        // no se manda a la Activity de Login
        if(user == null) {
            startActivity(new Intent(MainActivity.this, Login.class));
        }
        else {
            startActivity(new Intent(MainActivity.this, Wiki.class));
        }
    }
}