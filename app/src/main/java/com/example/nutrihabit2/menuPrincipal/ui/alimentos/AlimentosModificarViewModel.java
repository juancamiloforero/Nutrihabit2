package com.example.nutrihabit2.menuPrincipal.ui.alimentos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AlimentosModificarViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public AlimentosModificarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}