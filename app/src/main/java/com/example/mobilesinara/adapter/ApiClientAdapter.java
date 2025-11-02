package com.example.mobilesinara.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClientAdapter {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://ms-sinara-sql-oox0.onrender.com/"; // 🔹 substitua pela URL real

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {

            // 🔹 Cria um Gson mais tolerante (aceita JSON malformado)
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            // 🔹 Log detalhado de requisição/resposta
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // 🔹 Autenticação básica
            Interceptor authInterceptor = chain -> {
                Request original = chain.request();
                String credentials = Credentials.basic("Lorena", "lorena@2025");

                Request request = original.newBuilder()
                        .header("Authorization", credentials)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            };

            // 🔹 Cliente HTTP com interceptores
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .addInterceptor(logging)
                    .build();

            // 🔹 Retrofit com suporte a texto e JSON leniente
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    // ⚠️ Ordem importa: Scalars primeiro (para respostas em texto puro)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }
}
