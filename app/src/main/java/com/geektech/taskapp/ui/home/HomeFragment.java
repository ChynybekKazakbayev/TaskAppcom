package com.geektech.taskapp.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.taskapp.App;
import com.geektech.taskapp.FormActivity;
import com.geektech.taskapp.OnItemClickListener;
import com.geektech.taskapp.R;
import com.geektech.taskapp.Task;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private TaskAdapter adapter;
    private List<Task> list = new ArrayList<>();
    private int pos;
    private Task task;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

//        list.add("Курманжан");
//        list.add("Айгерим");
//        list.add("Нургазы");
//        list.add("Арслан");
//        list.add("Эрмек");
//        list.add("Чыныбек");
//        list.add("Перизат");
//        list.add("Айпери");
//        list.add("Бегайым");
//        list.add("Бакыт");
        // list = new ArrayList<>();
        // list = App.getDatabase().taskDao().getAll();
        list = App.getDatabase().taskDao().getAll();
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));

        App.getDatabase().taskDao().getAllLive().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                list.clear();
                list.addAll(tasks);
                adapter.notifyDataSetChanged();
            }
        });
        adapter = new TaskAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                pos = position;
                task = list.get(position);
                Intent intent = new Intent(getContext(), FormActivity.class);
                intent.putExtra("task", task);
                startActivityForResult(intent, 102);
                // Toast.makeText(getContext(),list.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnLongItemClick(final int position) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Внимание!!!")
                        .setMessage("Вы действительно хотите удалить запись?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                App.getDatabase().taskDao().delete(list.get(position));
                                list.remove(task);
                                adapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("Отмена", null).show();
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Task task = (Task) data.getSerializableExtra("task");
            if (requestCode == 100) {
                list.add(task);
            } else if (requestCode == 102) {
                list.set(pos, task);
            }
            Log.e("==========", requestCode + "" + pos + "");
            adapter.notifyDataSetChanged();
        }
    }
}