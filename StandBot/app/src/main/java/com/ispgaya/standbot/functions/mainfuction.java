package com.ispgaya.standbot.functions;

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
}
