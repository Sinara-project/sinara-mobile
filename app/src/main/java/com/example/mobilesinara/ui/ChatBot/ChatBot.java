package com.example.mobilesinara.ui.ChatBot;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mobilesinara.adapter.RetrofitClient;
import com.example.mobilesinara.Interface.IChatBotAPI;
import com.example.mobilesinara.Models.ChatRequest;
import com.example.mobilesinara.Models.ChatResponse;
import com.example.mobilesinara.R;
import com.example.mobilesinara.databinding.FragmentChatBotBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatBot extends Fragment {

    private FragmentChatBotBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        new ViewModelProvider(this).get(ChatBotViewModel.class);
        binding = FragmentChatBotBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        EditText input = root.findViewById(R.id.text_pesquisa);
        ImageView sendButton = root.findViewById(R.id.imageView14);
        LinearLayout layoutMessages = root.findViewById(R.id.layoutMessages);
        ScrollView scrollView = root.findViewById(R.id.scrollViewChat);

        sendButton.setOnClickListener(v -> {
            String message = input.getText().toString().trim();
            if (!message.isEmpty()) {
                addUserMessage(message, layoutMessages, inflater);
                input.setText("");

                scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));

                sendMessageToBot(message, layoutMessages, inflater, scrollView);
            }
        });

        return root;
    }

    private void addUserMessage(String message, LinearLayout layout, LayoutInflater inflater) {
        View messageView = inflater.inflate(R.layout.item_chat_user, layout, false);
        TextView textMessage = messageView.findViewById(R.id.textViewUser);
        textMessage.setText(message);
        layout.addView(messageView);
    }

    private void addBotMessage(String message, LinearLayout layout, LayoutInflater inflater) {
        View messageView = inflater.inflate(R.layout.item_chat_bot, layout, false);
        TextView textMessage = messageView.findViewById(R.id.textViewBot);
        textMessage.setText(message);
        layout.addView(messageView);
    }

    private void sendMessageToBot(String userMessage, LinearLayout layout, LayoutInflater inflater, ScrollView scrollView) {
        IChatBotAPI api = RetrofitClient.getInstance().create(IChatBotAPI.class);
        ChatRequest request = new ChatRequest(userMessage);

        api.sendMessage(request).enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String reply = response.body().getReply();
                    addBotMessage(reply, layout, inflater);
                    scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
                } else {
                    addBotMessage("Erro: resposta inválida do servidor.", layout, inflater);
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                addBotMessage("Falha na conexão: " + t.getMessage(), layout, inflater);
                Toast.makeText(getContext(), "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
