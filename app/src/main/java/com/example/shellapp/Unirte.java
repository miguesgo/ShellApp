package com.example.shellapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Unirte extends AppCompatActivity {

    EditText loginEditEmail;
    EditText loginEditPassword;
    EditText loginEditUserName;
    EditText loginEditTurtleName;
    Button unirteButtonUnirte;
    Button unirteButtonInicioSesion;

    FirebaseAuth autent;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unirte);

        loginEditEmail = findViewById(R.id.loginEditEmail);
        loginEditPassword = findViewById(R.id.loginEditPassword);
        unirteButtonUnirte = findViewById(R.id.unirteButtonUnirte);
        unirteButtonInicioSesion = findViewById(R.id.unirteButtonInicioSesion);
        loginEditUserName = findViewById(R.id.loginEditUserName);
        loginEditTurtleName = findViewById(R.id.loginEditTurtleName);

        autent = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        unirteButtonUnirte.setOnClickListener(view -> {
            createUser();
        });

        unirteButtonInicioSesion.setOnClickListener(view -> {
            startActivity(new Intent(Unirte.this, Login.class));
        });
    }

    private void createUser() {
        String email = loginEditEmail.getText().toString();
        String password = loginEditPassword.getText().toString();
        String userName = loginEditUserName.getText().toString();
        String turtleName = loginEditTurtleName.getText().toString();

        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("password", password);
        user.put("turtleName", turtleName);
        user.put("userName", userName);

        if(TextUtils.isEmpty(email)) {
            loginEditEmail.setError("Por favor ingresa un correo");
            loginEditEmail.requestFocus();
        }
        else if(TextUtils.isEmpty(password)) {
            loginEditPassword.setError("Por favor ingresa una contraseña");
            loginEditPassword.requestFocus();
        }
        else if(TextUtils.isEmpty(userName)) {
            loginEditUserName.setError("Por favor ingresa un nombre");
            loginEditUserName.requestFocus();
        }
        else if(TextUtils.isEmpty(turtleName)) {
            loginEditTurtleName.setError("Por favor ingresa el nombre de tu tortuga");
            loginEditTurtleName.requestFocus();
        }
        else {
            autent.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        firebaseFirestore.collection("User").add(user);
                        Toast.makeText(Unirte.this, "Usuario registrado con exito", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Unirte.this, Login.class));
                    }
                    else {
                        Toast.makeText(Unirte.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}