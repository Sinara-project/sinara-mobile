    package com.example.mobilesinara.ui.ChatBot;

    import android.os.Bundle;
    import android.util.Log;
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

    import com.example.mobilesinara.Interface.IChatBotAPI;
    import com.example.mobilesinara.Models.ChatRequest;
    import com.example.mobilesinara.Models.ChatResponse;
    import com.example.mobilesinara.R;
    import com.example.mobilesinara.adapter.RetrofitClient;
    import com.example.mobilesinara.databinding.FragmentChatBotBinding;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;

    public class ChatBot extends Fragment {

        private FragmentChatBotBinding binding;
        private static final String TAG = "ChatBot";

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
                    Log.d(TAG, "Usuário enviou: " + message);

                    addUserMessage(message, layoutMessages, inflater);
                    input.setText("");

                    scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));

                    sendMessageToBot(message, layoutMessages, inflater, scrollView);
                } else {
                    Log.w(TAG, "Campo de texto vazio, nada enviado.");
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

            Call<ChatResponse> call = api.sendMessage(userMessage, null, "auto");

            Log.d(TAG, "Enviando mensagem para o servidor: " + userMessage);
            Log.d(TAG, "URL chamada: " + call.request().url());

            call.enqueue(new Callback<ChatResponse>() {
                @Override
                public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                    Log.d(TAG, "Resposta recebida do servidor. Código HTTP: " + response.code());

                    if (response.isSuccessful() && response.body() != null) {
                        String reply = response.body().getAnswer(); // Notei que no seu modelo é 'answer', não 'reply'
                        Log.d(TAG, "Resposta do bot: " + reply);

                        addBotMessage(reply, layout, inflater);
                        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
                    } else {
                        String errorBody = "";
                        try {
                            errorBody = response.errorBody() != null ? response.errorBody().string() : "null";
                        } catch (Exception e) {
                            Log.e(TAG, "Erro ao ler corpo de erro", e);
                        }

                        Log.e(TAG, "Erro na resposta do servidor. Código: " + response.code() +
                                " Corpo: " + errorBody);

                        addBotMessage("Erro: resposta inválida do servidor (" + response.code() + ")", layout, inflater);
                    }
                }

                @Override
                public void onFailure(Call<ChatResponse> call, Throwable t) {
                    Log.e(TAG, "Falha na conexão com o servidor", t);
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
