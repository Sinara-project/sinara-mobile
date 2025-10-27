package com.example.mobilesinara.ui.notificacaoEmpresa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificacaoEmpresaViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public NotificacaoEmpresaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is NotificacaoEmpresa fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
