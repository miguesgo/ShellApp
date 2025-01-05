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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class tortugaMediterranea extends AppCompatActivity {

    TextView wikiTituloPrincipal;
    TextView wikiAcercaDe;
    TextView wikiDescripcion;
    TextView wikiTaxonomia;
    TextView wikiSexo;
    TextView wikiAlimentacion;
    TextView wikiEspacio;
    TextView wikiCuidados;
    TextView wikiEnfermedades;
    TextView wikiEsperanzaVida;

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
        setContentView(R.layout.activity_tortuga_mediterranea);

        autent = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        wikiTituloPrincipal = findViewById(R.id.wikiTituloPrincipal);
        wikiAcercaDe = findViewById(R.id.wikiAcercaDe);
        wikiDescripcion = findViewById(R.id.wikiDescripcion);
        wikiTaxonomia = findViewById(R.id.wikiTaxonomia);
        wikiSexo = findViewById(R.id.wikiSexo);
        wikiAlimentacion = findViewById(R.id.wikiAlimentacion);
        wikiEspacio = findViewById(R.id.wikiEspacio);
        wikiCuidados = findViewById(R.id.wikiCuidados);
        wikiEnfermedades = findViewById(R.id.wikiEnfermedades);
        wikiEsperanzaVida = findViewById(R.id.wikiEsperanzaVida);

        userConfiguracion = findViewById(R.id.userConfiguracion);
        userButtonHome = findViewById(R.id.userButtonHome);
        userButtonBuscar = findViewById(R.id.userButtonBuscar);
        userButtonEdit = findViewById(R.id.userButtonEdit);
        userButtonUser = findViewById(R.id.userButtonUser);
        userUsername = findViewById(R.id.userUsername);

        userConfiguracion.setOnClickListener(view -> {
            startActivity(new Intent(tortugaMediterranea.this, Configuracion.class));
        });

        userButtonUser.setOnClickListener(view -> {
            startActivity(new Intent(tortugaMediterranea.this, User.class));
        });

        userButtonHome.setOnClickListener(view -> {
            startActivity(new Intent(tortugaMediterranea.this, Wiki.class));
        });

        userButtonBuscar.setOnClickListener(view -> {
            startActivity(new Intent(tortugaMediterranea.this, Search.class));
        });

        userButtonEdit.setOnClickListener(view -> {
            startActivity(new Intent(tortugaMediterranea.this, Edit.class));
        });

        DocumentReference docRef = firebaseFirestore.collection("tortugaTierra").
                document("tortugaMediterranea");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        wikiTituloPrincipal.setText(document.getString("Nombre"));
                        wikiAcercaDe.setText(document.getString("Especie"));
                        wikiDescripcion.setText(document.getString("Descripción"));

                        wikiTaxonomia.setText(document.getString("Taxonomía"));
                        wikiSexo.setText(document.getString("Sexo"));
                        wikiAlimentacion.setText(document.getString("Alimentación"));
                        wikiEspacio.setText(document.getString("Espacio"));
                        wikiCuidados.setText(document.getString("Cuidados"));
                        wikiEnfermedades.setText(document.getString("Enfermedades"));
                        wikiEsperanzaVida.setText(document.getString("Esperanza de vida"));
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
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