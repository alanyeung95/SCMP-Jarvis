package com.example.myapplication.ui.hongkong;

import android.os.Bundle;
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
            textView.setText(getArguments().getString("news"));
            String text = "Student shot and man set ablaze in one of Hong Kong’s most " +
                    "violent days of unrest yetTear gas, petrol bombs, barricades and pepper " +
                    "spray across multiple districts after protesters cause traffic mayhem in " +
                    "attempt to spark general strikeCity chief warns anyone who believes violence " +
                    "will force her government to give in to their political demands that they are" +
                    " indulging in ‘wishful thinking’";

            //f//romBundle(arguments).noteId
            //getArguments().getString("123")
            //textView.setText(text + text + text);
            textView.setMovementMethod(new ScrollingMovementMethod());
            //}
            //MainActivity.class.
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