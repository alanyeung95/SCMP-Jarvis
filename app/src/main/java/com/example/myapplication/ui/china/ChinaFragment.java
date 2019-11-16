package com.example.myapplication.ui.china;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;

public class ChinaFragment extends Fragment {

    private ChinaViewModel chinaViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.e("fragment", "china is coming");
        if (chinaViewModel == null) {
            Log.e("asdf", "null");
        }

        chinaViewModel =
                ViewModelProviders.of(this).get(ChinaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_china, container, false);
        final TextView textView = root.findViewById(R.id.text_china);

        if (getArguments() != null) {
            if (getArguments().getString("state").equals("STATE_READING_ONE_NEWS") || getArguments().getString("state").equals("STATE_READING_ALL_NEWS")){

                String text="";
                text = getArguments().getString("newsTitle"  ) + "\n\n" + getArguments().getString("newsContent" )+ "\n";
                SpannableStringBuilder str = new SpannableStringBuilder(text);
                textView.setText(str);
                textView.setMovementMethod(new ScrollingMovementMethod());
            }else{

                String text="(Speak the number of the title to hear the news, e.g. one)\n\n";

                int numberOfNews = getArguments().getInt("newsLength");
                for (int i=0;i<numberOfNews;i++){
                    text = text + (i+1) + ".\n";
                    text = text +  getArguments().getString("newsTitle" + i)+ "\n\n";
                    //text = text + getArguments().getString("newsContent" + i)+ "\n\n";
                }
                SpannableStringBuilder str = new SpannableStringBuilder(text);

                textView.setText(str);
                textView.setMovementMethod(new ScrollingMovementMethod());
            }
        }
        return root;
    }
}