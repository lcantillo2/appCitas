package com.grupoocho.appcurso;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button btnPaciente = findViewById(R.id.btnPaciente);
        final Button btnDoctores = findViewById(R.id.btnDoctores);
        final Button btnIps = findViewById(R.id.btnIps);
        btnPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), view_login.class);
                i.putExtra("tipo", "Paciente");
                startActivity(i);
            }
        });
        btnDoctores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), view_login.class);
                i.putExtra("tipo", "Doctor");
                startActivity(i);
            }
        });
        btnIps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), view_login.class);
                i.putExtra("tipo", "Eps");
                startActivity(i);
            }
        });

    }
}