package com.example.myapplication.ui.economy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EconomyViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EconomyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is china fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}