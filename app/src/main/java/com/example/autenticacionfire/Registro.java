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

public class Registro extends AppCompatActivity {

    EditText NC,EMAIL,PAS,PHONE;
    Button Registro;
    TextView mloginBtn;
    FirebaseAuth fAuth; // firebase autenticacion
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        NC = findViewById(R.id.nombreCompleto);
        EMAIL = findViewById(R.id.mail);
        PAS = findViewById(R.id.password);
        PHONE = findViewById(R.id.phone);
        Registro = findViewById(R.id.loginBtn);
        mloginBtn = findViewById(R.id.crearText);
        progressBar = findViewById(R.id.progressBar);

        fAuth = FirebaseAuth.getInstance(); // recibiendo la isntancia para realizar todas las operaciones

        //2*** Verificar si el user ya inicio sesion o ya lo hizo
        //*** ver la cuenta antes o despues o es un user que regreso
        // *** current = obtener usuario actual
        if(fAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        //**********
        Registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // el método trim sirve manipulación de cadenas (String), el cual sirve para quitar los espacios a la cadena
                String email = EMAIL.getText().toString().trim();
                String password = PAS.getText().toString().trim();
                //validar los datos que se ingresaron
                if(TextUtils.isEmpty(email)){
                    EMAIL.setError("Email es requerido");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    PAS.setError("Password es requerido");
                    return;
                }
                //si es nenor a 6 caracteres
                if(password.length()<6){
                    PAS.setError("Password tiene que ser  >=6 caracteres");
                    return;
                }
                // se hara visible cuando el proceso de registro se esta realizando
                progressBar.setVisibility(view.VISIBLE);

                //REGISTRO DE USER EN FIREBASE
                // enviar datos firebase como (email - password) - oyente que nos confirme si el registro se realizo exitoso o no  <resultado autenticacion>
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // si la tarea es exitosa
                        if(task.isSuccessful()){
                            Toast.makeText(Registro.this,"Usuario creado exitosamente",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            // caso contrario se produjo un error
                        }else{
                            Toast.makeText(Registro.this,
                                    " Se produjo un ERROR! usuario no creado" +task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            //
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}
