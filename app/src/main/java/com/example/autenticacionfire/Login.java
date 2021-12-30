package com.example.autenticacionfire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText EMAIL,PAS;
    Button Registro;
    TextView mloginBtn;
    FirebaseAuth fAuth; // firebase autenticacion
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EMAIL = findViewById(R.id.txtmail);
        PAS = findViewById(R.id.txtpassword);
        progressBar = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();
        Registro = findViewById(R.id.btnlogin);
        mloginBtn = findViewById(R.id.textView4);

        Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = EMAIL.getText().toString().trim();
                String password = PAS.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    EMAIL.setError("Ingrese in Email valido.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    PAS.setError("Ingrese un password valido.");
                    return;
                }
                if(password.length()< 6){
                    PAS.setError("El password debe tener mas de 5 caracteres");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                //AUTENTICACION DEL USER
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"Login exitoso", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Login.this,"Error con el login, "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            ///
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Registro.class));
            }
        });


    }
}