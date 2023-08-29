package com.grupoocho.appcurso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grupoocho.appcurso.Model.User;

public class view_login extends AppCompatActivity {
    private TextView txtTipoUsuario = null;
    private String txtName = null;
    private String txtId = null;
    private ImageView imgInicioSesion;

    private EditText txtEmail;
    private EditText txtPassword;
    private FirebaseAuth mAuth;

    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_login);

        txtTipoUsuario = findViewById(R.id.txtTipoUsuario);
        imgInicioSesion = findViewById(R.id.imgInicioSesion);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        pd = new ProgressDialog(this);
        String tipo = getIntent().getStringExtra("tipo");
        txtTipoUsuario.setText(tipo);

        switch (tipo){
            case "Paciente":
                imgInicioSesion.setImageResource(R.drawable.user);
                break;
            case "Doctor":
                imgInicioSesion.setImageResource(R.drawable.doc);
                break;
            case "Eps":
                imgInicioSesion.setImageResource(R.drawable.ips);
                break;
        }
        final Button btnRegistrarView = findViewById(R.id.btnRegistrarView);
        final ImageButton btnBackInicio = findViewById(R.id.btnBackInicio);
        btnRegistrarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), view_register.class);
                intent.putExtra("tipo", txtTipoUsuario.getText());
                startActivity(intent);
            }
        });
        btnBackInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        mAuth = FirebaseAuth.getInstance();
    }


    public void btnInicio(View view) {

        pd.setMessage("Please Wait!");
        pd.show();
        final String emailTxt = txtEmail.getText().toString();
        final String passwTxt = txtPassword.getText().toString();

        if (emailTxt.isEmpty() || passwTxt.isEmpty()) {
            pd.dismiss();
            Toast.makeText(this, "Ingrese Email o Password porfavor.", Toast.LENGTH_SHORT).show();
        } else {

            mAuth.signInWithEmailAndPassword(emailTxt,passwTxt).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();

                        Intent i = null;
                        switch (txtTipoUsuario.getText().toString()) {
                            case "Paciente":
                                i = new Intent(getApplicationContext(), view__paciente_dashboard.class);
                                break;
                            case "Doctor":
                                i = new Intent(getApplicationContext(), view_doctor_dashboard.class);
                                break;
                            case "Eps":
                                i = new Intent(getApplicationContext(), view_Eps_dashboard.class);
                                break;
                        }
                        if (i != null) {
                            i.putExtra("uid", user.getUid());
                            startActivity(i);
                        } else {
                            pd.dismiss();
                            Toast.makeText(getApplicationContext(), "El tipo de usuario no encointrado.", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Usuario o passwor incorrectos",Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(),"Usuario o passwor incorrectos",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}