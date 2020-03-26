package com.ispgaya.standbot;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DBInterface {
    @FormUrlEncoded
    @POST("criarCarro.php")
    Call<android.telecom.Call.Details> addData(@Field("url") String url, @Field("matricula") String matricula, @Field("modelo") String modelo, @Field("km") int km,
                                               @Field("local") String local, @Field("dataAnuncio") String dataAnuncio, @Field("anunciante") String anunciante,
                                               @Field("preco") int preco, @Field("dataRetirado") String dataRetirado);
}
