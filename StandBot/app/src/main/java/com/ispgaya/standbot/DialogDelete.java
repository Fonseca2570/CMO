package com.ispgaya.standbot;
import com.ispgaya.standbot.functions.*;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import static org.apache.commons.lang3.StringUtils.capitalize;

public class DialogDelete extends AppCompatDialogFragment {

    int position;
    String modelo = "";
    String marca = "";

    public void setMarca(String marca) {
        this.marca = capitalize(marca);
    }

    public void setModelo(String modelo) {
        this.modelo = capitalize(modelo);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Tem a certeza que quer Apagar "+ marca).setMessage(modelo).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mainfuction.deleteNotification(getContext(), "Notifications.txt", position);
                startActivity(new Intent(getContext(), notifications.class));
            }
        })
        ;

        return builder.create();
    }
}
