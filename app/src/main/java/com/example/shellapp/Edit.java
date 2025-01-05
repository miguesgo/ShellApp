package com.example.shellapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Edit extends AppCompatActivity {
    ImageButton userButtonHome;
    ImageButton userButtonBuscar;
    ImageButton userButtonEdit;
    ImageButton userButtonUser;
    ImageButton userConfiguracion;
    TextView userUsername;

    FirebaseAuth autent;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        autent = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        userConfiguracion = findViewById(R.id.userConfiguracion);
        userButtonHome = findViewById(R.id.userButtonHome);
        userButtonBuscar = findViewById(R.id.userButtonBuscar);
        userButtonUser = findViewById(R.id.userButtonUser);
        userUsername = findViewById(R.id.userUsername);

        userConfiguracion.setOnClickListener(view -> {
            startActivity(new Intent(Edit.this, Configuracion.class));
        });

        userButtonUser.setOnClickListener(view -> {
            startActivity(new Intent(Edit.this, User.class));
        });

        userButtonHome.setOnClickListener(view -> {
            startActivity(new Intent(Edit.this, Wiki.class));
        });

        userButtonBuscar.setOnClickListener(view -> {
            startActivity(new Intent(Edit.this, Search.class));
        });

        FirebaseUser user = autent.getCurrentUser();
        firebaseFirestore.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getString("email").equals(user.getEmail().toString())) {
                                    userUsername.setText(document.getString("userName"));
                                }
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("this", e.getMessage());
                    }
                });

    }
}