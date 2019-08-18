package com.example.clubactivity.Class;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubactivity.R;

import java.util.ArrayList;
import java.util.List;

public class ClassList extends Fragment {
    final int ITEM_SIZE = 5;
    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.class_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.class_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<Item> items = new ArrayList<>();
        Item[] item = new Item[ITEM_SIZE];
        item[0] = new Item(R.drawable.class1, "창업 클래스");
        item[1] = new Item(R.drawable.class2, "개발자 클래스");
        item[2] = new Item(R.drawable.class3, "인프라 클래스");
        item[3] = new Item(R.drawable.class4, "취업 크래스");
        item[4] = new Item(R.drawable.class1, "헤헿");

        for (int i = 0; i < ITEM_SIZE; i++) {
            items.add(item[i]);
        }

        recyclerView.setAdapter(new RecyclerAdapter(getContext(), items, R.layout.class_list));


        //위에 제목 지정
        TextView classListTitle = view.findViewById(R.id.class_list_area_or_title);
        classListTitle.setText(ClassFragment.classmenuTitle);

        //뒤로가기
        ImageView classBack = view.findViewById(R.id.class_list_back_button);
        classBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(ClassList.this).commit();
                fragmentManager.popBackStack();
            }
        });


        return view;
    }




}
