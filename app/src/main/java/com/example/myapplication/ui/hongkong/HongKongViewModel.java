package com.example.myapplication.ui.hongkong;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HongKongViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HongKongViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is hong kong fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}