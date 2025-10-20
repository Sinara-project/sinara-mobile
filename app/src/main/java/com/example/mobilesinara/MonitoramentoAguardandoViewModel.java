package com.example.mobilesinara;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MonitoramentoAguardandoViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public MonitoramentoAguardandoViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}
