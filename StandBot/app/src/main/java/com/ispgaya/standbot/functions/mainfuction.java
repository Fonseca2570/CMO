package com.ispgaya.standbot.functions;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


import static android.content.Context.MODE_PRIVATE;

public class mainfuction {
    public static String bmw(String modelo){
        String[] modeloSplit = modelo.split("");
        String modeloRet;
        switch(modeloSplit[0]){
            case "1":
                modeloRet = "serie-1";
                break;
            case "2":
                modeloRet = "serie-2";
                break;
            case "3":
                modeloRet =  "serie-3";
                break;
            case "4":
                modeloRet = "serie-4";
                break;
            case "5":
                modeloRet =  "serie-5";
                break;
            case "6":
                modeloRet = "serie-6";
                break;
            case "7":
                modeloRet =  "serie-7";
                break;
            case "8":
                modeloRet = "serie-8";
                break;
            default:
                modeloRet =  modelo;
        }
        return modeloRet;
    }

    public static boolean escreverNotifations(Context context, String FileName, String enviarNotifications){
        FileOutputStream fos = null;
        boolean jaExiste = false;
        String NotifationsGuardadas = lerNotifications(context,FileName);
        String[] linhas = NotifationsGuardadas.split("\n");
        String[] linha = enviarNotifications.split(";");
        String idACheckar = linha[0];
        for(int i = 0; i<linhas.length; i++){
            String id = linhas[i].split(";")[0];
            if(id.contains(idACheckar)){
                jaExiste = true;
            }
        }
        if(!jaExiste){
            enviarNotifications = NotifationsGuardadas + enviarNotifications +"\n";
        }
        else{
            enviarNotifications = NotifationsGuardadas;
        }
        try {
            fos = context.openFileOutput(FileName, MODE_PRIVATE);
            fos.write(enviarNotifications.getBytes());
            System.out.println("Saved to "+ context.getFilesDir());
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        } finally {
            if (fos != null){
                try {
                    fos.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return !jaExiste;
    }

    public static void cleanFile(Context context, String FileName){
        FileOutputStream fos = null;
        String clean = "";
        try {
            fos = context.openFileOutput(FileName, MODE_PRIVATE);
            fos.write(clean.getBytes());
            System.out.println("Saved to "+ context.getFilesDir());
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        } finally {
            if (fos != null){
                try {
                    fos.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static String lerNotifications(Context context, String Filename){
        FileInputStream fis = null;
        StringBuilder sb = new StringBuilder();
        try {
            fis = context.openFileInput(Filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            ;
            String text;
            while((text = br.readLine()) != null){
                sb.append(text).append("\n");
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis != null){
                try {
                    fis.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static void deleteNotification(Context context, String FileName, int position){
        FileOutputStream fos = null;
        String NotifationsGuardadas = lerNotifications(context,FileName);
        String[] linhas = NotifationsGuardadas.split("\n");
        String enviarNotification = "";
        for(int j = 0; j < linhas.length; j++){
            if(!(j == position)){
                enviarNotification = enviarNotification + linhas[j] + "\n";
            }
        }
        try {
            fos = context.openFileOutput(FileName, MODE_PRIVATE);
            fos.write(enviarNotification.getBytes());
            System.out.println("Saved to "+ context.getFilesDir());
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        } finally {
            if (fos != null){
                try {
                    fos.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }


}


