package com.ispgaya.standbot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;
import org.jsoup.select.Selector;
import java.lang.String;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    /*public class Carro {
        String titulo;
        String url;
        String marca;
        String modelo;
        String Combustivel;
        int ano;
        int quilometros;
        String tipoAnunciante;
        int cavalos;

    }*/

    private Button getBtn;
    private TextView result;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.result);
        getBtn = (Button) findViewById(R.id.getBtn);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWebsite();
            }
        });
    }

    private void getWebsite(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                try {
                    Document doc = Jsoup.connect("https://www.standvirtual.com/carros/?search%5Border%5D=created_at_first%3Adesc&search%5Bbrand_program_id%5D%5B0%5D=&search%5Bcountry%5D=").get();
                    Elements links = doc.select("article");

                    //builder.append(title).append("\n");
                    for (Element link : links){
                        String href = link.attr("data-href");

                        //builder.append(link.attr("data-href")).append("\n");
                        builder.append(getDetails(href)).append("\n");
                    }

                } catch (IOException e){
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

    private String getDetails(String href){
        try {
            Document doc = Jsoup.connect(href).get();
            titulo = doc.select("#siteWrap > main > div.flex-container-main > div.flex-container-main__right > div.offer-content__aside > div.offer-summary > span.offer-title.big-text.fake-title").text().toUpperCase();
            titulo = titulo.replace("COM GARANTIA", "").replace("POUCOS KMS", "").replace("GARANTIA","").trim();
            String valorString = doc.select("#siteWrap > main > div.flex-container-main > div.flex-container-main__right > div.offer-content__aside > div.offer-summary > div.price-wrapper > div > span.offer-price__number").text();
            valorString = valorString.replace("EUR","").replace(" ","").trim();
            valor = Integer.parseInt(valorString);
            nomeAnunciador = doc.select(".seller-box__seller-name").first().text().trim();
            Elements details = doc.select("li");
            for (Element detail : details){
                Elements a = detail.select("span");
                if(a.text().contains("Combustível")){
                    combustivel = a.next().text();
                    combustivel = combustivel.trim();
                    combustivel = combustivel.toLowerCase();
                    if(combustivel.contains("gasolina")){
                        combustivel = "gaz";
                    }
                    if(combustivel.contains("híbrido")){
                        combustivel = "hibride-diesel";
                    }
                }
                if(a.text().contains("Marca")){
                   marca = a.next().text();
                   marca = marca.trim().replace("ë", "e").replace(" ", "-").toLowerCase().trim();
                }
                if(a.text().contains("Modelo")){
                    modelo = a.next().text();
                    modelo = modelo.trim().replace("é","e").replace(" ", "-").toLowerCase().trim();

                }
                if(a.text().contains("Ano de Registo")){
                    String anoTexto = a.next().text().trim();
                    ano = Integer.parseInt(anoTexto);
                }
                if(a.text().contains("Quilómetros")){
                    String quilometrosString = a.next().text();
                    quilometros = Integer.parseInt(quilometrosString.trim().replace(" ", "").replace("km", ""));

                }
                if(a.text().contains("Potência")){
                    String potenciaString = a.next().text();
                    potencia = Integer.parseInt(potenciaString.trim().replace(" ", "").replace("cv", ""));
                }
                if(a.text().contains("Anunciante")){
                    tipoAnunciador = a.next().text().trim();
                }

            }
            // TODO Adicionar aqui a inserção na base de dados
            // TODO fazer outro html request para comparar preços com os dados acima

        } catch(IOException e){

        }
        return " "+tipoAnunciador;
    }

}
