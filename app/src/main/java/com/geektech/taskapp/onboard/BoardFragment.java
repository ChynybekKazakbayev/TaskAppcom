package com.geektech.taskapp.onboard;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geektech.taskapp.MainActivity;
import com.geektech.taskapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {
    Button button;
    ImageView imageView;

    public BoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_board, container, false);
         button = view.findViewById(R.id.fr_btn);
         imageView = view.findViewById(R.id.imageView);
       // LinearLayout fra = view.findViewById(R.id.fra);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
                preferences.edit().putBoolean("isShown", true).apply();
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
                Toast.makeText(getActivity(), "Error" , Toast.LENGTH_LONG).show();

            }
        });

return view; }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.textView);
        LinearLayout fra = view.findViewById(R.id.fra);

        int pos = getArguments().getInt("pos");
        switch (pos) {
            case 1:
                textView.setText("Привет");
                button.setVisibility(View.INVISIBLE);
                imageView.setImageResource(R.drawable.board_1);
                fra.setBackground(getResources().getDrawable(R.drawable.act_fo));

                break;
            case 2:
                textView.setText("Hello");
                button.setVisibility(View.INVISIBLE);
                imageView.setImageResource(R.drawable.board_2);
                fra.setBackground(getResources().getDrawable(R.drawable.gradient_2));

                break;
            case 3:
                textView.setText("Салам");
                button.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.board_3);
                fra.setBackground(getResources().getDrawable(R.drawable.gradient_3));

                break;
        }

    }

//    public void onClick (View view){
//        Toast.makeText(getActivity(), "Error" , Toast.LENGTH_LONG).show();
//    }
}
