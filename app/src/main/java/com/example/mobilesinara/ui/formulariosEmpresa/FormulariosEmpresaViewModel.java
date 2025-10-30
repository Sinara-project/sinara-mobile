package com.example.mobilesinara.ui.formulariosEmpresa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FormulariosEmpresaViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public FormulariosEmpresaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is formulariosEmpresa fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
