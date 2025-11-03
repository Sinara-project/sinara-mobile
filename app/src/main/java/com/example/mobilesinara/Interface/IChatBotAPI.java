package com.example.mobilesinara.Interface;

import com.example.mobilesinara.Models.ChatResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IChatBotAPI {
    @GET(".")
    Call<ChatResponse> sendMessage(
            @Query("query") String query,
            @Query("session_id") String sessionId,
            @Query("agent") String agent
    );
}
