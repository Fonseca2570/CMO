package com.ispgaya.standbot;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ispgaya.standbot.functions.*;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.os.CountDownTimer;
import android.widget.Toast;

import java.lang.String;

import java.io.IOException;

import retrofit2.Callback;
import retrofit2.Response;

import static org.apache.commons.lang3.StringUtils.capitalize;

public class MainActivity extends AppCompatActivity {

    //TODO Variaveis a retirar do config para já estão hardCoded
    public int variacaoKM = 50000;
    public int kmMin = 0;
    public int kmMax = 500000;
    public int anoMinimo = 1990;
    public int anoMaximo = 2019;
    public int variacaoAno = 1;
    public int cavalosMin = 50;
    public int cavalosMax = 999;
    public int variacaoCavalos = 10;
    public double descontoConfig = 0.01;
    public int precoMin;
    public int precoMax;
    public String[] listaMarcas;
    public String[] listacombustiveis;
    public String marcas = "";
    public String combustiveis = "";


    private Button getBtn;
    private TextView result;
    private ImageView logo;
    private Button stopBtn;
    private FloatingActionButton notBtn;
    private TextView counter;
    private static final String FileName = "Notifications.txt";

    public int contador = 0;
    public String combustivel = "";
    public String combustivelPreformat = "";
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
    List<String> sitesJaVisitados = new ArrayList<String>();
    DBInterface dbInterface;

    Button teste;

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
                        Thread[] threads = new Thread[Thread.activeCount()];
                        Thread.enumerate(threads);
                        for (int i= threads.length ; i==0; i--) {
                            if (!threads[i].isInterrupted()) {
                                threads[i].interrupt();
                            }
                        }
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
        aplicarConfigs();
        teste = findViewById(R.id.teste);


        result = (TextView) findViewById(R.id.result);
        getBtn = (Button) findViewById(R.id.getBtn);
        stopBtn = (Button) findViewById(R.id.stop);
        logo = (ImageView) findViewById(R.id.logo);
        notBtn = (FloatingActionButton) findViewById(R.id.alertNotification);
        counter = (TextView) findViewById(R.id.counter);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBtn.setVisibility(View.GONE);
                stopBtn.setVisibility(View.VISIBLE);
                getWebsite();
            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(0, 0);
            }
        });
        notBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), notifications.class));
                overridePendingTransition(0, 0);
            }
        });

    }

    private void aplicarConfigs(){
        SharedPreferences sp = getSharedPreferences("com.ispgaya.standbot.MarcasString", Context.MODE_PRIVATE);
        marcas = sp.getString("Marcas", "");
        Log.i("Marcas: ", marcas);
        sp = getSharedPreferences("com.ispgaya.standbot.CombustiveisString", Context.MODE_PRIVATE);
        combustiveis = sp.getString("Combustiveis", "");
        //Log.i("Combustiveis: ", combustiveis);
        sp = getSharedPreferences("com.ispgaya.standbot.Dados", Context.MODE_PRIVATE);
        //Log.i("Anos Min", String.valueOf(sp.getInt("anos_min", 0)));
        anoMinimo = sp.getInt("anos_min", 1900);
        anoMaximo = sp.getInt("anos_max", 2019);
        cavalosMin = sp.getInt("cavalos_min", 0);
        cavalosMax = sp.getInt("cavalos_max", 1000);
        kmMin = sp.getInt("kms_min", 0);
        kmMax = sp.getInt("kms_max", 500000);
        precoMin = sp.getInt("precos_min", 0);
        precoMax = sp.getInt("precos_max", 200000);
    }

    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //mainfuction.cleanFile(getApplicationContext(), FileName);
                while (!Thread.currentThread().isInterrupted()) {
                    final StringBuilder builder = new StringBuilder();
                    try {
                        Document doc = Jsoup.connect("https://www.standvirtual.com/carros/?search%5Bfilter_enum_damaged%5D=0&search%5Border%5D=created_at_first%3Adesc&search%5Bbrand_program_id%5D%5B0%5D=&search%5Bcountry%5D=").get();
                        Elements links = doc.select("article");
                        for (Element link : links) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //logo.setImageResource(R.drawable.fiat);
                                    int idLogo = logoSwap.switchlogo(marca);
                                    if (idLogo != 0) {
                                        logo.setImageResource(idLogo);
                                    }
                                    result.setText("A Pesquisar");
                                }
                            });

                            String href = link.attr("data-href");
                            if (!sitesJaVisitados.contains(href)) {
                                sitesJaVisitados.add(href);
                                getDetails(href);
                            }
                        }

                    } catch (IOException e) {
                        builder.append("Error : ").append(e.getMessage()).append("\n");
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new CountDownTimer(300000, 1000) {

                                public void onTick(long millisUntilFinished) {
                                    result.setText("Aguardar: " + millisUntilFinished / 1000);
                                }

                                public void onFinish() {
                                    result.setText("A Pesquisar");
                                }
                            }.start();
                            logo.setImageResource(R.drawable.indexcar);
                        }
                    });
                    try {
                        TimeUnit.MINUTES.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void addBase(String href) {
        dbInterface = DBHandler.getDBHandler().create(DBInterface.class);
        int pro = 0; int phone = 0;
        if(tipoAnunciador.contains("Profissional")){
            pro = 0;
        }
        else{
            pro = 1;
        }
        if(hasPhone == true){
            phone = 1;
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        retrofit2.Call<Detalhes> call = dbInterface.addCarro(id, href, marca, modelo, quilometros, "", dateFormat.format(date),
                nomeAnunciador, valor, titulo, phone, potencia, pro, ano, "0000-00-00");

        call.enqueue(new Callback<Detalhes>() {
            @Override
            public void onResponse(retrofit2.Call<Detalhes> call, Response<Detalhes> response) {
                //Toast.makeText(this, "Carro criado com sucesso", Toast.LENGTH_LONG).show();
                Detalhes detalhes = response.body();
                System.out.println("Criado com sucesso--------" + detalhes.getResposta());
            }

            @Override
            public void onFailure(retrofit2.Call<Detalhes> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    private void getDetails(String href) {
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
                    combustivelPreformat = combustivel;
                    if (combustivel.contains("híbrido") && combustivel.contains("gasolina")) {
                        combustivel = "hibride-gaz";
                    }
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

            // TODO Adicionar aqui a inserção na base de dados (FEito)
            addBase(href);

            // TODO fazer outro html request para comparar preços com os dados acima (Feito)
            int quilometrosDe = quilometros - variacaoKM;
            if (quilometrosDe < 0) {
                quilometrosDe = 0;
            }
            int quilometrosAte = quilometros + variacaoKM;
            int cavalosDe = potencia - variacaoCavalos;
            int cavalosAte = potencia + variacaoCavalos;
            int anoDe = ano - variacaoAno;
            int anoAte = ano + variacaoAno;
            // 0 para nao sinistrado 1 para sinistrados
            int sinistrado = 0;

            String newUrl = String.format("https://www.standvirtual.com/carros/%s/%s/desde-%s/?search%%5Bfilter_enum_fuel_type%%5D=%s&search%%5Bfilter_float_first_registration_year%%3Ato%%5D=%s&search%%5Bfilter_float_mileage%%3Afrom%%5D=%s&search%%5Bfilter_float_mileage%%3Ato%%5D=%s&search%%5Bfilter_float_power%%3Afrom%%5D=%s&search%%5Bfilter_float_power%%3Ato%%5D=%s&search%%5Bfilter_enum_damaged%%5D=%s&search%%5Border%%5D=filter_float_price%%3Aasc&search%%5Bbrand_program_id%%5D%%5B0%%5D=&search%%5Bcountry%%5D=", marca, modelo, anoDe, combustivel, anoAte, quilometrosDe, quilometrosAte, cavalosDe, cavalosAte, sinistrado);
            Document docComparacao = Jsoup.connect(newUrl).get();
            //System.out.println(newUrl);
            if (!docComparacao.select("body").text().contains("Não existem anúncios para a sua pesquisa")) {
                Element primeiro = docComparacao.select("article").first();
                String primeiroLink = primeiro.attr("data-href");
                Element segundo = primeiro;
                try {
                    segundo = docComparacao.select("article").next().first();
                } catch (Exception e) {
                    System.out.println("So existe 1 artigo deste carro");
                }
                float preco1 = Float.parseFloat(primeiro.select(".offer-price__number").text().replace("EUR", "").replace(" ", "").trim());
                float preco2 = preco1;
                try {
                    preco2 = Float.parseFloat(segundo.select(".offer-price__number").text().replace("EUR", "").replace(" ", "").trim());
                } catch (Exception e) {
                    preco2 = preco1;
                }
                double desconto = 1 - (preco1 / preco2);
                if (desconto >= descontoConfig) {
                    // TODO fazer validaçoes de configs externas e enviar para notificações (Verificar se funciona no portatil NAO TESTADO
                    if (ano >= anoMinimo && ano<= anoMaximo && potencia >= cavalosMin && potencia <= cavalosMax && quilometros >= kmMin && quilometros <= kmMax && valor >= precoMin && valor <= precoMax) {
                        if(marcas.isEmpty() || marcas.contains(capitalize(marca))){
                            if (combustiveis.isEmpty() || combustiveis.contains(capitalize(combustivelPreformat))) {
                                // Tornar visible o botão de notification
                                String enviarTexto = String.format("%s;%s;%s;%s;%s;;", id, newUrl, primeiroLink, marca, modelo);
                                boolean aumentar = mainfuction.escreverNotifations(this, FileName, enviarTexto);
                                if (aumentar) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (contador == 0) {
                                                contador += 1;
                                                counter.setText(String.valueOf(contador));
                                                counter.setVisibility(View.VISIBLE);
                                                notBtn.setVisibility(View.VISIBLE);
                                            } else if (contador < 9) {
                                                contador += 1;
                                                counter.setText(String.valueOf(contador));
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {

        }

    }


}
