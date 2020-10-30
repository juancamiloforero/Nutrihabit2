package com.example.nutrihabit2.menuPrincipal.ui.alimentos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AlimentosMainViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public AlimentosMainViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}