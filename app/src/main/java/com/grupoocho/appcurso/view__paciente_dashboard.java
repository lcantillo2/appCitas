package com.grupoocho.appcurso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.grupoocho.appcurso.Model.User;

public class view__paciente_dashboard extends AppCompatActivity {
    private TextView txtCliente;
    private String name;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_paciente_dashboard);
        txtCliente = findViewById(R.id.txtCliente);

        FirebaseDatabase.getInstance().getReference().child("Users").child(getIntent().getStringExtra("uid")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userModel = dataSnapshot.getValue(User.class);
                Toast.makeText(getApplicationContext(),"nombrees :"+userModel.getName(), Toast.LENGTH_SHORT).show();
                txtCliente.setText(userModel.getName());
                uid = userModel.getId();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void btnAddCitas(View view){
        Intent t = new Intent(this, view_add_citas.class);
        t.putExtra("uid",uid);
        startActivity(t);
    }

    public void regresar(View view){
        startActivity(new Intent(this, view__paciente_dashboard.class));
    }

}