package com.example.mobilesinara.ui.configuration;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConfigurationViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ConfigurationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Configuration fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
