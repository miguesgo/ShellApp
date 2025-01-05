package com.example.shellapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText loginEditEmail;
    EditText loginEditPassword;
    Button loginButtonUnirte;
    Button unirteButtonUnirte;

    FirebaseAuth autent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEditEmail = findViewById(R.id.loginEditEmail);
        loginEditPassword = findViewById(R.id.loginEditPassword);
        loginButtonUnirte = findViewById(R.id.loginButtonUnirte);
        unirteButtonUnirte = findViewById(R.id.unirteButtonUnirte);

        autent = FirebaseAuth.getInstance();
        loginButtonUnirte.setOnClickListener(view -> {
            loginUser();
        });
        unirteButtonUnirte.setOnClickListener(view -> {
            startActivity(new Intent(Login.this, Unirte.class));
        });
    }

    private void loginUser() {
        String email = loginEditEmail.getText().toString();
        String password = loginEditPassword.getText().toString();

        if(TextUtils.isEmpty(email)) {
            loginEditEmail.setError("Por favor ingresa un correo");
            loginEditEmail.requestFocus();
        }
        else if(TextUtils.isEmpty(password)) {
            loginEditPassword.setError("Por favor ingresa una contraseña");
            loginEditPassword.requestFocus();
        }
        else {
            autent.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(Login.this, "Sesión iniciada con éxito", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, Wiki.class));
                    }
                    else {
                        Toast.makeText(Login.this, "Sesión Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}