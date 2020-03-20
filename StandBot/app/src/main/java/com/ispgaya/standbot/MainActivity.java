package com.ispgaya.standbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;
import org.jsoup.select.Selector;

import java.lang.String;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //TODO Variaveis a retirar do config para já estão hardCoded
    public int variacaoKM = 50000;
    public int anoMinimo = 1990;
    public int variacaoAno = 1;
    public int cavalosMin = 50;
    public int variacaoCavalos = 10;

    private Button getBtn;
    private TextView result;
    private ImageView logo;
    public String combustivel = "";
    public String marca = "";
    public String modelo = "";
    public String titulo = "";
    public int ano = 2010;
    public int quilometros = 100000;
    public int valor;
    public int potencia;
    public String nomeAnunciador = "";
    public String tipoAnunciador = "";
    public boolean hasPhone;
    public long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize  and Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.config:
                        startActivity(new Intent(getApplicationContext(), config.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.notification:
                        startActivity(new Intent(getApplicationContext(), notifications.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.admin:
                        startActivity(new Intent(getApplicationContext(), admin.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        return true;
                }
                return false;
            }
        });

        result = (TextView) findViewById(R.id.result);
        getBtn = (Button) findViewById(R.id.getBtn);
        logo = (ImageView) findViewById(R.id.logo);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWebsite();
            }
        });
    }

    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                try {
                    Document doc = Jsoup.connect("https://www.standvirtual.com/carros/?search%5Border%5D=created_at_first%3Adesc&search%5Bbrand_program_id%5D%5B0%5D=&search%5Bcountry%5D=").get();
                    Elements links = doc.select("article");

                    for (Element link : links) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //logo.setImageResource(R.drawable.fiat);
                                if(marca.contains("fiat")){
                                    logo.setImageResource(R.drawable.fiat);
                                }
                                if(marca.contains("audi")){
                                    logo.setImageResource(R.drawable.audi);
                                }
                                if(marca.contains("mini")){
                                    logo.setImageResource(R.drawable.mini);
                                }
                                result.setText(marca);
                            }
                        });

                        String href = link.attr("data-href");
                        builder.append(getDetails(href)).append("\n");
                    }

                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(builder.toString());
                    }
                });
            }
        }).start();
    }

    private String getDetails(String href) {
        String idString = "";
        try {
            Document doc = Jsoup.connect(href).get();
            titulo = doc.select("#siteWrap > main > div.flex-container-main > div.flex-container-main__right > div.offer-content__aside > div.offer-summary > span.offer-title.big-text.fake-title").text().toUpperCase();
            titulo = titulo.replace("COM GARANTIA", "").replace("POUCOS KMS", "").replace("GARANTIA", "").trim();
            String valorString = doc.select("#siteWrap > main > div.flex-container-main > div.flex-container-main__right > div.offer-content__aside > div.offer-summary > div.price-wrapper > div > span.offer-price__number").text();
            valorString = valorString.replace("EUR", "").replace(" ", "").trim();
            valor = Integer.parseInt(valorString);
            nomeAnunciador = doc.select(".seller-box__seller-name").first().text().trim();
            hasPhone = doc.select("body").text().contains("Mostrar");
            idString = doc.select("#siteWrap > main > div.flex-container-main > div.flex-container-main__left > div.offer-content.offer-content--primary > div > div.offer-content__metabar > div > span:nth-child(2) > span.offer-meta__value").text().trim().replace("\"", "");
            try {
                id = Long.parseLong(idString);
            } catch (NumberFormatException e) {
                id = 0;
            }
            Elements details = doc.select("li");
            for (Element detail : details) {
                Elements a = detail.select("span");
                if (a.text().contains("Combustível")) {
                    combustivel = a.next().text();
                    combustivel = combustivel.trim();
                    combustivel = combustivel.toLowerCase();
                    if (combustivel.contains("gasolina")) {
                        combustivel = "gaz";
                    }
                    if (combustivel.contains("híbrido")) {
                        combustivel = "hibride-diesel";
                    }
                }
                if (a.text().contains("Marca")) {
                    marca = a.next().text();
                    marca = marca.trim().replace("ë", "e").replace(" ", "-").toLowerCase().trim();
                }
                if (a.text().contains("Modelo")) {
                    modelo = a.next().text();
                    modelo = modelo.trim().replace("é", "e").replace(" ", "-").toLowerCase().trim();

                }
                if (a.text().contains("Ano de Registo")) {
                    String anoTexto = a.next().text().trim();
                    ano = Integer.parseInt(anoTexto);
                }
                if (a.text().contains("Quilómetros")) {
                    String quilometrosString = a.next().text();
                    quilometros = Integer.parseInt(quilometrosString.trim().replace(" ", "").replace("km", ""));

                }
                if (a.text().contains("Potência")) {
                    String potenciaString = a.next().text();
                    potencia = Integer.parseInt(potenciaString.trim().replace(" ", "").replace("cv", ""));
                }
                if (a.text().contains("Anunciante")) {
                    tipoAnunciador = a.next().text().trim();
                }
            }
            //TODO se a marca for bmw temos que alterar o modelo para 116 -> serie-1, 320 -> serie-3

            // TODO Adicionar aqui a inserção na base de dados

            // TODO fazer outro html request para comparar preços com os dados acima
            int quilometrosDe = quilometros - variacaoKM;
            if (quilometrosDe < 0){
                quilometrosDe = 0;
            }
            int quilometrosAte = quilometros + variacaoKM;
            int cavalosDe = potencia - variacaoCavalos;
            int cavalosAte = potencia + variacaoCavalos;
            int anoDe = ano - variacaoAno;
            int anoAte = ano + variacaoAno;
            // 0 para nao sinistrado 1 para sinistrados
            int sinistrado = 0;

            String newUrl = String.format("https://www.standvirtual.com/carros/%s/%s/desde-%s/?search%%5Bfilter_enum_fuel_type%%5D=%s&search%%5Bfilter_float_first_registration_year%%3Ato%%5D=%s&search%%5Bfilter_float_mileage%%3Afrom%%5D=%s&search%%5Bfilter_float_mileage%%3Ato%%5D=%s&search%%5Bfilter_float_power%%3Afrom%%5D=%s&search%%5Bfilter_float_power%%3Ato%%5D=%s&search%%5Bfilter_enum_damaged%%5D=%s&search%%5Border%%5D=filter_float_price%%3Aasc&search%%5Bbrand_program_id%%5D%%5B0%%5D=&search%%5Bcountry%%5D=", marca,modelo, anoDe, combustivel, anoAte, quilometrosDe, quilometrosAte,cavalosDe,cavalosAte,sinistrado);
            System.out.println(newUrl);
        } catch (IOException e) {

        }
        //System.out.println(tipoAnunciador);
        return " " + id;
    }

}
