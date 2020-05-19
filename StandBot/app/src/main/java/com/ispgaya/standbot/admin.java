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

import static org.apache.commons.lang3.StringUtils.capitalize;
import java.util.List;

public class admin extends AppCompatActivity {
    Button button;
    DBInterface dbInterface;
    TextView maisVendidoPreco, maisVendidoMarca, maisVendidoModelo, maisRapidoPreco, maisRapidoMarca, maisRapidoModelo,
            maisCaroPreco, maisCaroMarca, maisCaroModelo, maisBaratoPreco, maisBaratoMarca, maisBaratoModelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        button = findViewById(R.id.button);
        maisVendidoPreco = findViewById(R.id.maisVendidoPreco);
        maisVendidoMarca = findViewById(R.id.maisVendidoMarca);
        maisVendidoModelo = findViewById(R.id.maisVendidoModelo);
        maisRapidoPreco = findViewById(R.id.maisRapidoPreco);
        maisRapidoMarca = findViewById(R.id.maisRapidoMarca);
        maisRapidoModelo = findViewById(R.id.maisRapidoModelo);
        maisCaroPreco = findViewById(R.id.maisCaroPreco);
        maisCaroMarca = findViewById(R.id.maisCaroMarca);
        maisCaroModelo = findViewById(R.id.maisCaroModelo);
        maisBaratoPreco = findViewById(R.id.maisBaratoPreco);
        maisBaratoMarca = findViewById(R.id.maisBaratoMarca);
        maisBaratoModelo = findViewById(R.id.maisBaratoModelo);

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
                getCheapest();
                //getEachCarroPorMarca();
                getFastest();
                getExpensive();
                //getRacio();
                getTopCar();
            }
        });
    }

    public void getCheapest(){
        dbInterface = DBHandler.getDBHandler().create(DBInterface.class);
        retrofit2.Call<Cheapest> call = dbInterface.cheapest();
        call.enqueue(new Callback<Cheapest>() {
            @Override
            public void onResponse(Call<Cheapest> call, Response<Cheapest> response) {
                String marca = response.body().getMarca();
                String modelo = response.body().getModelo();
                int ano = response.body().getAno();
                int preco = response.body().getPreco();
                maisBaratoMarca.setText("Marca: " + capitalize(marca));
                maisBaratoModelo.setText("Modelo: " + capitalize(modelo));
                maisBaratoPreco.setText("Preço: " + preco + "€");
                //System.out.println("Marca: " + marca + "\nModelo: " + modelo + "\nAno: " + ano + "\nPreco: " + preco);
            }

            @Override
            public void onFailure(Call<Cheapest> call, Throwable t) {
                maisBaratoMarca.setText("Marca:");
                maisBaratoModelo.setText("Modelo:");
                maisBaratoPreco.setText("Preço:");
            }
        });
    }

    public void getEachCarroPorMarca(){
        dbInterface = DBHandler.getDBHandler().create(DBInterface.class);
        retrofit2.Call<List<EachCarroPorMarca>> call = dbInterface.each(2019);
        call.enqueue(new Callback<List<EachCarroPorMarca>>() {
            @Override
            public void onResponse(Call<List<EachCarroPorMarca>> call, Response<List<EachCarroPorMarca>> response) {
                for (EachCarroPorMarca carro : response.body()){
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

    public void getFastest(){
        dbInterface = DBHandler.getDBHandler().create(DBInterface.class);
        retrofit2.Call<FastestSell> call = dbInterface.fastest();
        call.enqueue(new Callback<FastestSell>() {
            @Override
            public void onResponse(Call<FastestSell> call, Response<FastestSell> response) {
                String marca = response.body().getMarca();
                String modelo = response.body().getModelo();
                int ano = response.body().getAno();
                int preco = response.body().getPreco();
                String dataVendido = response.body().getDataVendido();
                String dataAnuncio = response.body().getDataAnuncio();
                int dias = response.body().getDias();
                maisRapidoMarca.setText("Marca: " + capitalize(marca));
                maisRapidoModelo.setText("Modelo: " + capitalize(modelo));
                maisRapidoPreco.setText("Preço: " + preco + "€");
                //System.out.println("Marca: " + marca + "\nModelo: " + modelo + "\nAno: " + ano + "\nPreço: " + preco + "\nData Vendido: " + dataVendido + "\nData Anuncio: " + dataAnuncio + "\nDias: " + dias);
            }

            @Override
            public void onFailure(Call<FastestSell> call, Throwable t) {
                maisRapidoMarca.setText("Marca:");
                maisRapidoModelo.setText("Modelo:");
                maisRapidoPreco.setText("Preço:");
            }
        });
    }

    public void getExpensive(){
        dbInterface = DBHandler.getDBHandler().create(DBInterface.class);
        retrofit2.Call<MostExpensive> call = dbInterface.expensive();
        call.enqueue(new Callback<MostExpensive>() {
            @Override
            public void onResponse(Call<MostExpensive> call, Response<MostExpensive> response) {
                String marca = response.body().getMarca();
                String modelo = response.body().getModelo();
                int ano = response.body().getAno();
                int preco = response.body().getPreco();
                maisCaroMarca.setText("Marca: " + capitalize(marca));
                maisCaroModelo.setText("Modelo: " + capitalize(modelo));
                maisCaroPreco.setText("Preço: " + preco + "€");
                //System.out.println("Marca: " + marca + "\nModelo: " + modelo + "\nAno: " + ano + "\nPreço: " + preco);
            }

            @Override
            public void onFailure(Call<MostExpensive> call, Throwable t) {
                maisCaroMarca.setText("Marca:");
                maisCaroModelo.setText("Modelo:");
                maisCaroPreco.setText("Preço:");
            }
        });
    }

    public void getRacio(){
        dbInterface = DBHandler.getDBHandler().create(DBInterface.class);
        retrofit2.Call<List<Racio>> call = dbInterface.racio();
        call.enqueue(new Callback<List<Racio>>() {
            @Override
            public void onResponse(Call<List<Racio>> call, Response<List<Racio>> response) {
                for (Racio racios : response.body()){
                    double racio = racios.getRacio();
                    int potencia = racios.getPotencia();
                    String marca = racios.getMarca();
                    String modelo = racios.getModelo();
                    int ano = racios.getAno();
                    int preco = racios.getPreco();
                    System.out.println("Racio: " + racio + "\nPotência: " + potencia + "\nMarca: " + marca + "\nModelo: " + modelo + "\nAno: " + ano + "\nPreço: " + preco);
                }
            }

            @Override
            public void onFailure(Call<List<Racio>> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public void getTopCar(){
        dbInterface = DBHandler.getDBHandler().create(DBInterface.class);
        retrofit2.Call<TopCar> call = dbInterface.topCar();
        call.enqueue(new Callback<TopCar>() {
            @Override
            public void onResponse(Call<TopCar> call, Response<TopCar> response) {
                int contador = response.body().getContador();
                String marca = response.body().getMarca();
                String modelo = response.body().getModelo();
                int ano = response.body().getAno();
                int preco = response.body().getPrecoMed();
                maisVendidoMarca.setText("Marca: " + capitalize(marca));
                maisVendidoModelo.setText("Modelo: " + capitalize(modelo));
                maisVendidoPreco.setText("Preço: " + preco + "€");
                //System.out.println("Contador: " + contador + "\nMarca: " + marca + "\nModelo: " + modelo + "\nAno: " + ano + "Preço: " + preco);
            }

            @Override
            public void onFailure(Call<TopCar> call, Throwable t) {
                maisVendidoMarca.setText("Marca:");
                maisVendidoModelo.setText("Modelo:");
                maisVendidoPreco.setText("Preço:");
            }
        });
    }
}

