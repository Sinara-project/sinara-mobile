package com.example.mobilesinara.ui.ProfileEmpresa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileEmpresaViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ProfileEmpresaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ProfileEmpresa fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
