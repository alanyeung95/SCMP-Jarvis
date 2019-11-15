package com.example.myapplication.ui.hongkong;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class HongKongFragment extends Fragment {

    private HongKongViewModel hongKongViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (hongKongViewModel == null) {
            Log.e("asdf", "null");
        }

        hongKongViewModel =
                ViewModelProviders.of(this).get(HongKongViewModel.class);
        View root = inflater.inflate(R.layout.fragment_hong_kong, container, false);
        final TextView textView = root.findViewById(R.id.text_hong_kong);

        if (getArguments() != null) {
            if (getArguments().getString("state").equals("STATE_READING_ONE_NEWS") || getArguments().getString("state").equals("STATE_READING_ALL_NEWS")){
                Log.e("hkong fragment", "creating");
                String text="";
                text = getArguments().getString("newsTitle"  ) + "\n\n" + getArguments().getString("newsContent" )+ "\n";
                SpannableStringBuilder str = new SpannableStringBuilder(text);
                textView.setText(str);
                textView.setMovementMethod(new ScrollingMovementMethod());
            }else{

                String text="";

                int numberOfNews = getArguments().getInt("newsLength");
                for (int i=0;i<numberOfNews;i++){
                    text = text + "(Speak \"" + (i+1) +"\")\n" + getArguments().getString("newsTitle" + i)+ "\n\n";
                    //text = text + getArguments().getString("newsContent" + i)+ "\n\n";
                }
                SpannableStringBuilder str = new SpannableStringBuilder(text);

                textView.setText(str);
                textView.setMovementMethod(new ScrollingMovementMethod());
            }
        }
            return root;
    }

    public static HongKongFragment newInstance(String text) {
        HongKongFragment hongKongFragment = new HongKongFragment();
        Bundle bundle = new Bundle();
        bundle.putString("123", text);
        //fragment保存参数，传入一个Bundle对象
        hongKongFragment.setArguments(bundle);
        return hongKongFragment;
    }

}