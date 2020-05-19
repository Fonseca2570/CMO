package com.ispgaya.standbot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FastestSell {

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

    @SerializedName("dataVendido")
    @Expose
    private String dataVendido;

    @SerializedName("dataAnuncio")
    @Expose
    private String dataAnuncio;

    @SerializedName("dias")
    @Expose
    private int dias;

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

    public String getDataVendido() {
        return dataVendido;
    }

    public void setDataVendido(String dataVendido) {
        this.dataVendido = dataVendido;
    }

    public String getDataAnuncio() {
        return dataAnuncio;
    }

    public void setDataAnuncio(String dataAnuncio) {
        this.dataAnuncio = dataAnuncio;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }
}
