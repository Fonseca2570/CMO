package com.ispgaya.standbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class admin extends AppCompatActivity {
    Button button;
    TextView textView;
    DBInterface dbInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView5);

        //Initialize  and Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.admin);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.config:
                        startActivity(new Intent(getApplicationContext(),config.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.notification:
                        startActivity(new Intent(getApplicationContext(),notifications.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.admin:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getCheapest();
                getEachCarroPorMarca();
            }
        });
    }

    public void getCheapest(){
        dbInterface = DBHandler.getDBHandler().create(DBInterface.class);
        retrofit2.Call<Cheapest> call = dbInterface.cheapest();
        call.enqueue(new Callback<Cheapest>() {
            @Override
            public void onResponse(Call<Cheapest> call, Response<Cheapest> response) {
                //Cheapest cheapest = response.body();
                String marca = response.body().getMarca();
                String modelo = response.body().getModelo();
                int ano = response.body().getAno();
                int preco = response.body().getPreco();
                System.out.println("Marca: " + marca + "\nModelo: " + modelo + "\nAno: " + ano + "\nPreco: " + preco);
            }

            @Override
            public void onFailure(Call<Cheapest> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public void getEachCarroPorMarca(){
        dbInterface = DBHandler.getDBHandler().create(DBInterface.class);
        retrofit2.Call<List<EachCarroPorMarca>> call = dbInterface.each(2019);
        call.enqueue(new Callback<List<EachCarroPorMarca>>() {
            @Override
            public void onResponse(Call<List<EachCarroPorMarca>> call, Response<List<EachCarroPorMarca>> response) {
                List<EachCarroPorMarca> rs = response.body();
                for (EachCarroPorMarca carro : rs){
                    int quantidade = carro.getQuantidadeVendidos();
                    String marca = carro.getMarca();
                    String modelo = carro.getModelo();
                    int ano = carro.getAno();
                    int precoMed = carro.getPrecoMed();
                    System.out.println("Quantidade: " + quantidade + "\nMarca: " + marca + "\nModelo: " + modelo + "\nAno: " + ano + "\nPreço Médio: " + precoMed);
                }
            }

            @Override
            public void onFailure(Call<List<EachCarroPorMarca>> call, Throwable t) {
                System.out.println(t);
            }
        });

    }
}

