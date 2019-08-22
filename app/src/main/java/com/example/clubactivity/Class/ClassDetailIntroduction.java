package com.example.clubactivity.Class;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clubactivity.R;

public class ClassDetailIntroduction extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fragment_introduction, container, false);

        TextView textView2 = view.findViewById(R.id.class_sogae_intent);//소개
        textView2.setText(ClassDetailActivity.desc);
        TextView textView3 = view.findViewById(R.id.class_member_intent);//대상
        textView3.setText(ClassDetailActivity.people);
        TextView textView4 = view.findViewById(R.id.class_location_intent);//장소
        textView4.setText(ClassDetailActivity.location);
        TextView textView5 = view.findViewById(R.id.class_time_intent);//시간
        textView5.setText(ClassDetailActivity.date);
        TextView textView6 = view.findViewById(R.id.class_people_number_intent);//인원
        textView6.setText(ClassDetailActivity.number);

        /*if(getArguments() != null) {
            TextView textView2 = view.findViewById(R.id.class_sogae_intent);//소개
            textView2.setText("");
            TextView textView3 = view.findViewById(R.id.class_member_intent);//대상
            textView3.setText(getArguments().getString("people"));
            TextView textView4 = view.findViewById(R.id.class_location_intent);//장소
            textView4.setText(getArguments().getString("location"));
            TextView textView5 = view.findViewById(R.id.class_time_intent);//시간
            textView5.setText(getArguments().getString("date"));
            TextView textView6 = view.findViewById(R.id.class_people_number_intent);//인원
            textView6.setText(getArguments().getString("number"));
        }*/

        return view;
    }
}
