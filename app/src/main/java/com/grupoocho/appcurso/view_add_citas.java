package com.grupoocho.appcurso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grupoocho.appcurso.Model.User;

public class view_add_citas extends AppCompatActivity {
    private TextView txtNombreUsuario;
    private EditText txtFechaCita;
    private EditText txtHoraCita;
    private String name;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_add_citas);
        txtNombreUsuario = findViewById(R.id.txtNombreUsuario);
        txtFechaCita = findViewById(R.id.txtFechaCita);
        txtHoraCita = findViewById(R.id.txtHoraCita);


        FirebaseDatabase.getInstance().getReference().child("Users").child(getIntent().getStringExtra("uid")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userModel = dataSnapshot.getValue(User.class);
                Toast.makeText(getApplicationContext(),"nombrees :"+userModel.getName(), Toast.LENGTH_SHORT).show();
                txtNombreUsuario.setText(userModel.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void btnIngresar(View view){
        String fecha = txtFechaCita.getText().toString();
        String hora = txtHoraCita.getText().toString();

        if(fecha.isEmpty() | hora.isEmpty()){
            Toast.makeText(this,"Debe ingrsar Fecha o hora ",Toast.LENGTH_SHORT).show();
        //} else if () {

        }else{

        }

    }

}