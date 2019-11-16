package com.example.myapplication.ui.tech;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TechViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TechViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is china fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}