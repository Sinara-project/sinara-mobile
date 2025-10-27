package com.example.mobilesinara.ui.telaHomeEmpresa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TelaHomeEmpresaViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public TelaHomeEmpresaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is TelaHomeEmpresa fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
