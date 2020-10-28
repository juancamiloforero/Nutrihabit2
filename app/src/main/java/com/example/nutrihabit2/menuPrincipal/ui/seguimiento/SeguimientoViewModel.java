package com.example.nutrihabit2.menuPrincipal.ui.seguimiento;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SeguimientoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SeguimientoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}