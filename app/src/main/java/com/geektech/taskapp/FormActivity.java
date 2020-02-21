package com.geektech.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class FormActivity extends AppCompatActivity {

    private EditText editTitle;
    private EditText editDesc;
    private Task task;
    private Intent intent;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        ImageView imageView = findViewById(R.id.imageView_phone);
        imageView.setImageResource(R.drawable.ic_phone_iphone_black_24dp);

        Glide.with(this).load(
                "https://lh3.googleusercontent.com/proxy/Bf4InAT8L9Kzzbav3ywu7tg-JeCu-J8GHdRWWGkGN7QgbjccxjICAhVI6KxWcN4TQtiR7KS5CaHivHMj6HmZNlJ-1H8j4qXByL0_Ag");
        editTitle = findViewById(R.id.editTitle);
        editDesc = findViewById(R.id.editDesc);

        secondIntent();

    }

    private void secondIntent() {
        task = (Task) getIntent().getSerializableExtra("task");
        if (task != null) {
            editTitle.setText(task.getTitle());
            editDesc.setText(task.getDesc());
        }
    }


    public void onClick(View view) {
        String title = editTitle.getText().toString().trim();
        String desc = editDesc.getText().toString().trim();


        // if (editDesc.getText().toString().matches("")) {
        //   Toast.makeText(getApplicationContext(), "Enter some text", Toast.LENGTH_LONG).show();
        if (title.isEmpty() || desc.isEmpty()) {
            editTitle.setError("Vedete title");
            // Toster.show("Vedite Title");
            editDesc.setError("Vedite edit");

        }
        if (task != null) {
            task.setTitle(title);
            task.setDesc(desc);
            App.getDatabase().taskDao().update(task);
        } else {
            task = new Task(title, desc);
            App.getDatabase().taskDao().insert(task);
        }
        Intent intent = new Intent();
        intent.putExtra("task", task);
        setResult(RESULT_OK, intent);
        finish();
    }
}
