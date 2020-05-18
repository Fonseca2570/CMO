package com.ispgaya.standbot;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

interface DBInterface {
    @FormUrlEncoded
    @POST("criarCarro.php")
    Call<Detalhes> addCarro(@Field("id") long id, @Field("url") String url, @Field("marca") String marca, @Field("modelo") String modelo, @Field("km") int km,
                                               @Field("local") String local, @Field("dataAnuncio") String dataAnuncio, @Field("anunciante") String anunciante,
                                               @Field("preco") int preco, @Field("titulo") String titulo, @Field("telemovel") int telemovel, @Field("potencia") int potencia,
                                               @Field("tipoAnunciador") int tipoAnunciador, @Field("ano") int ano, @Field("dataVendido") String dataVendido);

    @FormUrlEncoded
    @POST("existeCarro.php")
    Call<Detalhes> existe(@Field("id") long id);

    @POST("cheapestCar.php")
    Call<Cheapest> cheapest();

    @FormUrlEncoded
    @POST("eachCarPorMarca.php")
    Call<List<EachCarroPorMarca>> each(@Field("ano") long ano);
}
