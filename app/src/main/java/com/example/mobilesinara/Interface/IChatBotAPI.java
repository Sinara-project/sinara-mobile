package com.example.mobilesinara.Interface;

import com.example.mobilesinara.Models.ChatRequest;
import com.example.mobilesinara.Models.ChatResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IChatBotAPI {
    @Headers("Content-Type: application/json")
    @POST("docs/chat_bot/chat_real/sinara/main.py")
    Call<ChatResponse> sendMessage(@Body ChatRequest request);
}
