package com.example.mobilesinara.ui.formularioRespondido;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FormularioRespondidoViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public FormularioRespondidoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is FormularioRespondido fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
