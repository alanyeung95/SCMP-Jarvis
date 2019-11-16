package com.example.myapplication.ui.china;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChinaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ChinaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is china fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}