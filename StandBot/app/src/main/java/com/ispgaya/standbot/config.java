package com.ispgaya.standbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import retrofit2.Callback;
import retrofit2.Response;

public class config extends AppCompatActivity {

    EditText minCavalos, maxCavalos, minAno, maxAno, minKm, maxKm, minPreco, maxPreco, desconto, marca, combustivel, modelo;
    Button save;

    Button teste;
    DBInterface dbInterface;

    RangeSeekBar anos, cavalos, kms, precos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        teste = findViewById(R.id.teste);
        anos = findViewById(R.id.ano);
        cavalos = findViewById(R.id.cavalo);
        kms = findViewById(R.id.km);
        precos = findViewById(R.id.preco);
        desconto = findViewById(R.id.desconto);
        marca = findViewById(R.id.marca);
        combustivel = findViewById(R.id.combustivel);
        modelo = findViewById(R.id.modelo);
        save = findViewById(R.id.save);

        anos.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                Number min_value = bar.getSelectedMinValue();
                Number max_value = bar.getSelectedMaxValue();

                Toast.makeText(config.this, "Ano Min = " + min_value + "\n" + "Ano Max = " + max_value, Toast.LENGTH_LONG).show();
            }
        });

        cavalos.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                Number min_value = bar.getSelectedMinValue();
                Number max_value = bar.getSelectedMaxValue();

                Toast.makeText(config.this, "Cavalos Min = " + min_value + "\n" + "Cavalos Max = " + max_value, Toast.LENGTH_LONG).show();
            }
        });

        kms.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                Number min_value = bar.getSelectedMinValue();
                Number max_value = bar.getSelectedMaxValue();

                Toast.makeText(config.this, "Kms Min = " + min_value + "\n" + "Kms Max = " + max_value, Toast.LENGTH_LONG).show();
            }
        });

        precos.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                Number min_value = bar.getSelectedMinValue();
                Number max_value = bar.getSelectedMaxValue();

                Toast.makeText(config.this, "Preço Min = " + min_value + "\n" + "Preço Max = " + max_value, Toast.LENGTH_LONG).show();
            }
        });
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

        /*teste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbInterface = DBHandler.getDBHandler().create(DBInterface.class);
                retrofit2.Call<Call.Details> call = dbInterface.addCarro(4, "google.pt", "ford", "mustang", 0, "porto", "2020-03-25",
                        "hugo", 2, "teste", 0, 300, 0, 2020, "2020-06-26");

                call.enqueue(new Callback<Call.Details>() {
                    @Override
                    public void onResponse(retrofit2.Call<Call.Details> call, Response<Call.Details> response) {
                        //Toast.makeText(config.this, "Carro criado com sucesso", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Call.Details> call, Throwable t) {

                    }
                });
            }
        });*/

        teste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbInterface = DBHandler.getDBHandler().create(DBInterface.class);
                retrofit2.Call<Detalhes> call = dbInterface.existe(1);

                call.enqueue(new Callback<Detalhes>() {
                    @Override
                    public void onResponse(retrofit2.Call<Detalhes> call, Response<Detalhes> response) {
                        Detalhes detalhes = response.body();
                        Toast.makeText(config.this, "" + detalhes.getResposta(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Detalhes> call, Throwable t) {

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
