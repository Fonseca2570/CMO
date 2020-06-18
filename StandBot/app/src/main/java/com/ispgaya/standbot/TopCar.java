package com.ispgaya.standbot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopCar {

    @SerializedName("contador")
    @Expose
    private int contador;

    @SerializedName("marca")
    @Expose
    private String marca;

    @SerializedName("modelo")
    @Expose
    private String modelo;

    @SerializedName("ano")
    @Expose
    private int ano;

    @SerializedName("precoMed")
    @Expose
    private double precoMed;

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getPrecoMed() {
        return precoMed;
    }

    public void setPrecoMed(double precoMed) {
        this.precoMed = precoMed;
    }
}
