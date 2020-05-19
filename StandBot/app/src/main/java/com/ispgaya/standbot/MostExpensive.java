package com.ispgaya.standbot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MostExpensive {

    @SerializedName("marca")
    @Expose
    private String marca;

    @SerializedName("modelo")
    @Expose
    private String modelo;

    @SerializedName("ano")
    @Expose
    private int ano;

    @SerializedName("preco")
    @Expose
    private int preco;

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

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }
}
