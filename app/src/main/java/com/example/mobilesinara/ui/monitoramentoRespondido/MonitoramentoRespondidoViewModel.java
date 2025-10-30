package com.example.mobilesinara.ui.monitoramentoRespondido;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MonitoramentoRespondidoViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public MonitoramentoRespondidoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is MonitoramentoRespondido fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
