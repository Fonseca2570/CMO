package com.ispgaya.standbot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.ispgaya.standbot.functions.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.capitalize;

public class notifications extends AppCompatActivity {

    private ListView listView;
    private TextView semNotifications;

    private static final String FileName = "Notifications.txt";
    List<ElementoLista> ListaCarros = new ArrayList<ElementoLista>();

    public class ElementoLista {
        long id;
        String urlPesquisa;
        String urlCarro;
        String marca;
        String modelo;
    }



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        //Initialize  and Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.notification);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.config:
                        startActivity(new Intent(getApplicationContext(),config.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.notification:
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
        class MyAdapter extends ArrayAdapter<String>{
            Context context;
            String rTitle[];
            String rDescription[];
            int rImage[];
            Button rApagar[];

            MyAdapter(Context c, String title[], String description[], int images[]){
                super(c, R.layout.row, R.id.textView1, title);
                this.context = c;
                this.rTitle = title;
                this.rDescription = description;
                this.rImage = images;
            }

            @NonNull
            @Override
            public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View row = layoutInflater.inflate(R.layout.row, parent, false);
                ImageView images = row.findViewById(R.id.image);
                TextView myTitle = row.findViewById(R.id.textView1);
                TextView description = row.findViewById(R.id.textView2);
                Button btnApagar = row.findViewById(R.id.apagar);



                images.setImageResource(rImage[position]);
                myTitle.setText(rTitle[position]);
                description.setText(rDescription[position]);

                btnApagar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialogDelete(position);
                        //Toast.makeText(notifications.this, "Apagar " + position, Toast.LENGTH_SHORT).show();
                    }
                });

                images.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialogInfo(position);
                        //Toast.makeText(notifications.this, ListaCarros.get(position).marca + " " + ListaCarros.get(position).id, Toast.LENGTH_SHORT).show();
                    }
                });

                myTitle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialogInfo(position);
                        //Toast.makeText(notifications.this, ListaCarros.get(position).marca + " " + ListaCarros.get(position).id, Toast.LENGTH_SHORT).show();
                    }
                });

                description.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDialogInfo(position);
                        //Toast.makeText(notifications.this, ListaCarros.get(position).marca + " " + ListaCarros.get(position).id, Toast.LENGTH_SHORT).show();
                    }
                });



                return row;
            }
        }
        listView = (ListView) findViewById(R.id.listView);
        semNotifications = (TextView) findViewById(R.id.semNotifcations);

        String dadosParaLista = mainfuction.lerNotifications(this,FileName);
        String[] linhas = dadosParaLista.split("\n");
        if(dadosParaLista == ""){
            System.out.println("entrou aqui");
            semNotifications.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        }
        else {
            String[] ArrayMarca = new String[linhas.length];
            String[] ArrayModelo = new String[linhas.length];
            int[] ArrayImagens = new int[linhas.length];
            for (int i = 0; i < linhas.length; i++) {
                ElementoLista adicionar = new ElementoLista();
                adicionar.id = Long.parseLong(linhas[i].split(";")[0]);
                adicionar.urlPesquisa = linhas[i].split(";")[1];
                adicionar.urlCarro = linhas[i].split(";")[2];
                adicionar.marca = linhas[i].split(";")[3];
                adicionar.modelo = linhas[i].split(";")[4];
                ListaCarros.add(adicionar);
                ArrayImagens[i] = logoSwap.switchlogo(adicionar.marca);
                ArrayMarca[i] = capitalize(adicionar.marca);
                ;
                ArrayModelo[i] = capitalize(adicionar.modelo);

            }

            MyAdapter adaptar = new MyAdapter(this, ArrayMarca, ArrayModelo, ArrayImagens);
            listView.setAdapter(adaptar);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(notifications.this, ListaCarros.get(position).marca + " " + ListaCarros.get(position).id, Toast.LENGTH_SHORT).show();
                    System.out.println("carregar no item");
                }
            });
        }

    }

    public void openDialogDelete(int position){
        DialogDelete d = new DialogDelete();
        d.setPosition(position);
        d.setMarca(ListaCarros.get(position).marca);
        d.setModelo(ListaCarros.get(position).modelo);
        d.show(getSupportFragmentManager(), "");
    }

    public void openDialogInfo(int position){
        DialogInfo d = new DialogInfo();
        d.setPosition(position);
        d.setMarca(ListaCarros.get(position).marca);
        d.setModelo(ListaCarros.get(position).modelo);
        d.setLinkPesquisa(ListaCarros.get(position).urlPesquisa);
        d.setLink(ListaCarros.get(position).urlCarro);
        d.show(getSupportFragmentManager(), "");
    }
}
