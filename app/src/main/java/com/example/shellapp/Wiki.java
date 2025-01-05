package com.example.shellapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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

public class Wiki extends AppCompatActivity {

    TextView wikiAcercaDe;
    TextView wikiAutor;
    TextView userUsername;
    ImageButton userConfiguracion;
    ImageButton userButtonHome;
    ImageButton userButtonBuscar;
    ImageButton userButtonEdit;
    ImageButton userButtonUser;
    Button wikiTortugasRio;
    Button wikiTortugasTierra;
    Button wikiTortugasMar;

    FirebaseAuth autent;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki);

        autent = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        wikiAcercaDe = findViewById(R.id.wikiAcercaDe);
        wikiAutor = findViewById(R.id.wikiAutor);
        userConfiguracion = findViewById(R.id.userConfiguracion);
        userButtonHome = findViewById(R.id.userButtonHome);
        userButtonBuscar = findViewById(R.id.userButtonBuscar);
        userButtonEdit = findViewById(R.id.userButtonEdit);
        userButtonUser = findViewById(R.id.userButtonUser);
        userUsername = findViewById(R.id.userUsername);
        wikiTortugasRio = findViewById(R.id.wikiTortugasRio);
        wikiTortugasTierra = findViewById(R.id.wikiTortugasTierra);
        wikiTortugasMar = findViewById(R.id.wikiTortugasMar);


        userConfiguracion.setOnClickListener(view -> {
            startActivity(new Intent(Wiki.this, Configuracion.class));
        });

        userButtonUser.setOnClickListener(view -> {
            startActivity(new Intent(Wiki.this, User.class));
        });

        userButtonBuscar.setOnClickListener(view -> {
            startActivity(new Intent(Wiki.this, Search.class));
        });

        userButtonEdit.setOnClickListener(view -> {
            startActivity(new Intent(Wiki.this, Edit.class));
        });

        wikiTortugasRio.setOnClickListener((view -> {
            startActivity(new Intent(Wiki.this, RioIndex.class));
        }));

        wikiTortugasTierra.setOnClickListener((view -> {
            startActivity(new Intent(Wiki.this, tierraIndex.class));
        }));

        wikiTortugasMar.setOnClickListener((view -> {
            startActivity(new Intent(Wiki.this, tortugaMarina.class));
        }));

        firebaseFirestore.collection("InformacionGeneral")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                wikiAcercaDe.setText(document.getString("acercaDe"));
                                wikiAutor.setText("App by: " + document.getString("autor"));
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