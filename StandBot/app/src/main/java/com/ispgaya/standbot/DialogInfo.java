package com.ispgaya.standbot;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.ispgaya.standbot.functions.logoSwap;
import com.ispgaya.standbot.functions.mainfuction;

import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.commons.lang3.StringUtils.lowerCase;

public class DialogInfo extends AppCompatDialogFragment {
    int position;
    String modelo = "";
    String marca = "";
    String linkPesquisa = "";
    String link = "";

    public void setMarca(String marca) {
        this.marca = capitalize(marca);
    }

    public void setModelo(String modelo) {
        this.modelo = capitalize(modelo);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setLinkPesquisa(String linkPesquisa) {
        this.linkPesquisa = linkPesquisa;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(marca).setIcon(logoSwap.switchlogo(lowerCase(marca))).setMessage(modelo + "\nClique em Carro para ver o carro \nClique em pesquisa pra ver os critérios de pesquisa").setNeutralButton("Carro", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(browserIntent);
            }
        }).setPositiveButton("Pesquisa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkPesquisa));
                startActivity(browserIntent);
            }
        });

        return builder.create();
    }
}
