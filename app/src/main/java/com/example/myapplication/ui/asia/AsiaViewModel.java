package com.example.myapplication.ui.asia;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AsiaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AsiaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is china fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}