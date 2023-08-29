package com.grupoocho.appcurso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class view_add_citas extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    private TextView txtNombreUsuario;
    private TextView txtNombreDoc;
    private EditText txtFechaCita;
    private EditText txtHoraCita;
    private String uid;
    private List<String> users = new ArrayList<>();
    private List<String> idUsers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_add_citas);
        txtNombreUsuario = findViewById(R.id.txtNombreUsuario);
        txtFechaCita = findViewById(R.id.txtFechaCita);
        txtHoraCita = findViewById(R.id.txtHoraCita);
        txtNombreDoc = findViewById(R.id.txtNameDoc);
        uid= getIntent().getStringExtra("uid");
        Spinner spin = (Spinner) findViewById(R.id.spNombreDoc);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users");

// Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    String name = ds.child("name").getValue(String.class);
                    String tipo = ds.child("tipouser").getValue(String.class);
                    if(key.equals(uid)){
                        txtNombreUsuario.setText(name);
                    }
                    if(tipo.equals("Doctor")) {
                        idUsers.add(key);
                        users.add(name);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        /*
        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(getIntent().getStringExtra("uid")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userModel = dataSnapshot.getValue(User.class);
                txtNombreUsuario.setText(userModel.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        Toast.makeText(this, "Selected User: "+ users.get(position),Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO - Custom Code
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