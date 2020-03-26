package com.ispgaya.standbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Callback;
import retrofit2.Response;

public class config extends AppCompatActivity {

    EditText minCavalos, maxCavalos, minAno, maxAno, minKm, maxKm, minPreco, maxPreco, desconto, marca, combustivel, modelo;
    Button save;

    Button teste;
    DBInterface dbInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        teste = findViewById(R.id.teste);

        minCavalos = findViewById(R.id.minCavalos);
        maxCavalos = findViewById(R.id.maxCavalos);
        minAno = findViewById(R.id.minAno);
        maxAno = findViewById(R.id.maxAno);
        minKm = findViewById(R.id.minKm);
        maxKm = findViewById(R.id.maxKm);
        minPreco = findViewById(R.id.minPreco);
        maxPreco = findViewById(R.id.maxPreco);
        desconto = findViewById(R.id.desconto);
        marca = findViewById(R.id.marca);
        combustivel = findViewById(R.id.combustivel);
        modelo = findViewById(R.id.modelo);
        save = findViewById(R.id.save);

        //Initialize  and Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.config);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.config:
                        return true;
                    case R.id.notification:
                        startActivity(new Intent(getApplicationContext(),notifications.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.admin:
                        startActivity(new Intent(getApplicationContext(),admin.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                checkData();
            }
        });

        teste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbInterface = DBHandler.getDBHandler().create(DBInterface.class);
                retrofit2.Call<Call.Details> call = dbInterface.addData("google.pt", "AA-00-00", "mustang", 0, "porto", "2020-03-25",
                        "vanessa chata", 2, "2020-05-26");

                call.enqueue(new Callback<Call.Details>() {
                    @Override
                    public void onResponse(retrofit2.Call<Call.Details> call, Response<Call.Details> response) {
                        Toast.makeText(config.this, "Carro criado com sucesso", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Call.Details> call, Throwable t) {

                    }
                });
            }
        });
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkData(){
        if (isEmpty(minCavalos)) {
            minCavalos.setError("MinCavalos is required!");
        }
        if (isEmpty(maxCavalos)) {
            maxCavalos.setError("MaxCavalos is required!");
        }
        if (isEmpty(minKm)) {
            minKm.setError("MinKm is required!");
        }
        if (isEmpty(maxKm)) {
            maxKm.setError("MaxKm is required!");
        }
        if (isEmpty(minAno)) {
            minAno.setError("MinAno is required!");
        }
        if (isEmpty(maxAno)) {
            maxAno.setError("MaxAno is required!");
        }
        if (isEmpty(combustivel)) {
            combustivel.setError("Combustivel is required!");
        }
    }
}
