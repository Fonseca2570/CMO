package com.ispgaya.standbot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;

public class config extends AppCompatActivity {
    String[] marcasSelect, combustivelSelect;
    boolean[] marcasChecked, combustivelChecked;
    ArrayList<Integer> marcaItems = new ArrayList<>();
    ArrayList<Integer> combustivelItems = new ArrayList<>();

    public String testeM = "";
    public String testeC = "";

    EditText desconto;
    Button save, marcas, combustiveis;

    RangeSeekBar anos, cavalos, kms, precos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        marcasSelect = getResources().getStringArray(R.array.marcas);
        marcasChecked = new boolean[marcasSelect.length];
        combustivelSelect = getResources().getStringArray(R.array.combustiveis);
        combustivelChecked = new boolean[combustivelSelect.length];

        anos = findViewById(R.id.ano);
        cavalos = findViewById(R.id.cavalo);
        kms = findViewById(R.id.km);
        precos = findViewById(R.id.preco);
        desconto = findViewById(R.id.desconto);
        marcas = findViewById(R.id.marca);
        combustiveis = findViewById(R.id.combustivel);
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
                //checkData();
                //limparCombustiveis();
                //limparMarcas();
            }
        });

        marcas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarMarcas();
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(config.this);
                mBuilder.setTitle("Marcas");
                mBuilder.setMultiChoiceItems(marcasSelect, marcasChecked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked){
                            marcaItems.add(position);
                        }else {
                            marcaItems.remove(Integer.valueOf(position));
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for (int i = 0; i < marcaItems.size(); i++){
                            item = item + marcasSelect[marcaItems.get(i)];
                            if(i != marcaItems.size() - 1){
                                item = item + ", ";
                            }
                        }
                        guardarMarcas(item);
                    }
                });
                mBuilder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < marcasChecked.length; i++){
                            marcasChecked[i] = false;
                            marcaItems.clear();
                            limparMarcas();
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        combustiveis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarCombustiveis();
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(config.this);
                mBuilder.setTitle("Combustiveis");
                mBuilder.setMultiChoiceItems(combustivelSelect, combustivelChecked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if(isChecked) {
                            combustivelItems.add(position);
                        }else{
                            combustivelItems.remove(Integer.valueOf(position));
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for (int i = 0; i < combustivelItems.size(); i++){
                            item = item + combustivelSelect[combustivelItems.get(i)];
                            if(i != combustivelItems.size() - 1){
                                item = item + ", ";
                            }
                        }
                        guardarCombustiveis(item);
                    }
                });
                mBuilder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < combustivelChecked.length; i++){
                            combustivelChecked[i] = false;
                            combustivelItems.clear();
                            limparCombustiveis();
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }

    public void guardarMarcas(String item){
        String[] _marcas = getResources().getStringArray(R.array.marcas);
        SharedPreferences sp = getSharedPreferences("com.ispgaya.standbot.Marcas", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        for (int i = 0; i < _marcas.length; i++){
            if(item.contains(_marcas[i])){
                editor.putBoolean(_marcas[i], true);
            }else{
                editor.putBoolean(_marcas[i], false);
            }
        }
        editor.apply();
        sp = getSharedPreferences("com.ispgaya.standbot.MarcasString", Context.MODE_PRIVATE);
        sp.edit().putString("Marcas", item).apply();
    }

    public void buscarMarcas(){
        String[] _marcas = getResources().getStringArray(R.array.marcas);
        SharedPreferences sp = getSharedPreferences("com.ispgaya.standbot.Marcas", Context.MODE_PRIVATE);
        for (int i = 0; i < _marcas.length; i++){
            if(sp.getBoolean(_marcas[i], false)){
                marcaItems.add(i);
                marcasChecked[i] = true;
                testeM = testeM + marcasSelect[i];
                if(i != marcaItems.size() - 1){
                    testeM = testeM + ", ";
                }
            }else{
                marcasChecked[i] = false;
            }
        }
    }

    public void limparMarcas() {
        SharedPreferences sp = getSharedPreferences("com.ispgaya.standbot.Marcas", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().apply();
    }

    public void guardarCombustiveis(String item){
        String[] _combustiveis = getResources().getStringArray(R.array.combustiveis);
        SharedPreferences sp = getSharedPreferences("com.ispgaya.standbot.Combustiveis", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        for (int i = 0; i < _combustiveis.length; i++){
            if(item.contains(_combustiveis[i])){
                editor.putBoolean(_combustiveis[i], true);
            }else{
                editor.putBoolean(_combustiveis[i], false);
            }
        }
        editor.apply();
        sp = getSharedPreferences("com.ispgaya.standbot.CombustiveisString", Context.MODE_PRIVATE);
        sp.edit().putString("Combustiveis", item).apply();
    }

    public void buscarCombustiveis(){
        String[] _combustiveis = getResources().getStringArray(R.array.combustiveis);
        SharedPreferences sp = getSharedPreferences("com.ispgaya.standbot.Combustiveis", Context.MODE_PRIVATE);
        for (int i = 0; i < _combustiveis.length; i++){
            if(sp.getBoolean(_combustiveis[i], false)){
                combustivelItems.add(i);
                combustivelChecked[i] = true;
                testeC = testeC + combustivelSelect[i];
                if(i != combustivelItems.size() - 1){
                    testeC = testeC + ", ";
                }
            }else{
                combustivelChecked[i] = false;
            }
        }
    }

    public void limparCombustiveis() {
        SharedPreferences sp = getSharedPreferences("com.ispgaya.standbot.Combustiveis", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().apply();
    }
}

