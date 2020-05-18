package com.ispgaya.standbot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EachCarroPorMarca {

    @SerializedName("quantidadeVendidos")
    @Expose
    private int quantidadeVendidos;

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
    private int precoMed;

    public int getQuantidadeVendidos() {
        return quantidadeVendidos;
    }

    public void setQuantidadeVendidos(int quantidadeVendidos) {
        this.quantidadeVendidos = quantidadeVendidos;
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

    public int getPrecoMed() {
        return precoMed;
    }

    public void setPrecoMed(int precoMed) {
        this.precoMed = precoMed;
    }
}
