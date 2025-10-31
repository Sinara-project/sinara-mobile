package com.example.mobilesinara.ui.ChatBot;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChatBotViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ChatBotViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ChatBot fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
