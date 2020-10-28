package com.example.nutrihabit2.menuPrincipal.ui.detalleComida;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetalleComidaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DetalleComidaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}