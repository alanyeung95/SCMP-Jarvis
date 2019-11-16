package com.example.myapplication.ui.business;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;

public class BusinessFragment extends Fragment {

    private BusinessViewModel businessViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        businessViewModel =
                ViewModelProviders.of(this).get(BusinessViewModel.class);
        View root = inflater.inflate(R.layout.fragment_china, container, false);
        final TextView textView = root.findViewById(R.id.text_china);
        businessViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}