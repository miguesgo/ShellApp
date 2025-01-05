package com.example.shellapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Search extends AppCompatActivity {

    ImageButton userButtonHome;
    ImageButton userButtonBuscar;
    ImageButton userButtonEdit;
    ImageButton userButtonUser;
    ImageButton userConfiguracion;
    TextView userUsername;
    EditText loginEditEmail;
    Button wikiBuscar;

    FirebaseAuth autent;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        autent = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        userConfiguracion = findViewById(R.id.userConfiguracion);
        userButtonHome = findViewById(R.id.userButtonHome);
        userButtonEdit = findViewById(R.id.userButtonEdit);
        userButtonUser = findViewById(R.id.userButtonUser);
        userUsername = findViewById(R.id.userUsername);
        loginEditEmail = findViewById(R.id.loginEditEmail);
        wikiBuscar = findViewById(R.id.wikiBuscar);

        userConfiguracion.setOnClickListener(view -> {
            startActivity(new Intent(Search.this, Configuracion.class));
        });

        userButtonUser.setOnClickListener(view -> {
            startActivity(new Intent(Search.this, User.class));
        });

        userButtonHome.setOnClickListener(view -> {
            startActivity(new Intent(Search.this, Wiki.class));
        });

        userButtonEdit.setOnClickListener(view -> {
            startActivity(new Intent(Search.this, Edit.class));
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

        wikiBuscar.setOnClickListener(view -> {
            switch(loginEditEmail.getText().toString().toLowerCase()) {
                case "orejas":
                case "galapagos":
                case "japon":
                case "japonesas":
                case "rojas":
                    startActivity(new Intent(Search.this, TortugasRio.class));
                    break;
                case "verde":
                case "linea en medio":
                case "concha":
                    startActivity(new Intent(Search.this, RioTortugaPintada.class));
                    break;
                case "caiman":
                case "picos":
                case "peligrosa":
                    startActivity(new Intent(Search.this, tortugaAligator.class));
                    break;
                case "cafe":
                case "redonda":
                case "africa":
                case "crema":
                    startActivity(new Intent(Search.this, tortugaAfricana.class));
                    break;
                case "negra":
                case "amarillo":
                case "lineas amarillas":
                    startActivity(new Intent(Search.this, tortugaEstrellada.class));
                    break;
                case "cafe con amarillo":
                case "ojos negros":
                case "patrones":
                    startActivity(new Intent(Search.this, tortugaMediterranea.class));
                    break;
                case "amarillo con cafe":
                case "tierra":
                    startActivity(new Intent(Search.this, tortugaMora.class));
                    break;
                case "rusa":
                    startActivity(new Intent(Search.this, tortugaRusa.class));
                    break;
                case "mar":
                case "oceano":
                    startActivity(new Intent(Search.this, tortugaMarina.class));
                    break;
                default:
                    Toast.makeText(Search.this, "Búsqueda fallida", Toast.LENGTH_SHORT).show();
            }
        });
    }
}