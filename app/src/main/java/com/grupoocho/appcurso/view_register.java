package com.grupoocho.appcurso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class view_register extends AppCompatActivity {
    private TextView txtTipoUser = null;
    private EditText txtEmailReg;
    private EditText txtPassReg;
    private EditText txtPass2Reg;
    private EditText txtNombre;
    private EditText txtTelefono;
    private EditText txtFecNac;
    private FirebaseAuth mAuth;
    private ImageView imgRegistrar;
    private DatabaseReference mRootRef;
    ProgressDialog pd;

    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_register);
        txtTipoUser = findViewById(R.id.txtTipoUser);
        txtEmailReg = findViewById(R.id.txtEmailReg);
        txtPassReg = findViewById(R.id.txtPassReg);
        txtPass2Reg = findViewById(R.id.txtPass2Reg);
        txtNombre = findViewById(R.id.txtNombreReg);
        txtTelefono = findViewById(R.id.txtTelefonoReg);
        imgRegistrar = findViewById(R.id.imgRegistrar);
        String tipo = getIntent().getStringExtra("tipo");
        txtTipoUser.setText(tipo);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        pd = new ProgressDialog(this);

        switch (tipo) {
            case "Paciente":
                imgRegistrar.setImageResource(R.drawable.user);
                break;
            case "Doctor":
                imgRegistrar.setImageResource(R.drawable.doc);
                break;
            case "Eps":
                imgRegistrar.setImageResource(R.drawable.ips);
                break;
        }


    }

    public void regregar(View view) {
        startActivity(new Intent(this, view_login.class));
    }

    public void registar(View view) {

        pd.setMessage("Please Wait!");
        pd.show();

        final String email = txtEmailReg.getText().toString();
        final String pass = txtPassReg.getText().toString();
        final String pass2 = txtPass2Reg.getText().toString();
        final String tel = txtTelefono.getText().toString();
        final String name = txtNombre.getText().toString();

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Ingrese email o password.", Toast.LENGTH_SHORT).show();
        } else if (name.isEmpty() || tel.isEmpty()) {
            Toast.makeText(this, "Ingrese nombre o telefono", Toast.LENGTH_SHORT).show();
        } else {
            if (pass.equals(pass2)) {
                mAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        DocumentReference documentReference = fStore.collection("Users").document(mAuth.getCurrentUser().getUid());
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("name", name);
                        map.put("email", email);
                        map.put("telefono", tel);
                        map.put("tipouser", txtTipoUser.getText().toString());
                        map.put("id", mAuth.getCurrentUser().getUid());

                        mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    pd.dismiss();
                                    Toast.makeText(getApplicationContext(), "Update the profile " +
                                            "for better expereince", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                Toast.makeText(this, "Las contraseña no coinciden", Toast.LENGTH_SHORT).show();
            }
        }
    }
}