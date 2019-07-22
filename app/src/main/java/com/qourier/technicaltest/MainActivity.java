package com.qourier.technicaltest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.qourier.technicaltest.question1.QuestionOneActivity;
import com.qourier.technicaltest.question2.QuestionTwoActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btQuestion1, btQuestion2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btQuestion1 = findViewById(R.id.btQuestion1);
        btQuestion2 = findViewById(R.id.btQuestion2);

        btQuestion1.setOnClickListener(this);
        btQuestion2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btQuestion1:
                QuestionOneActivity.startActivity(MainActivity.this);
                break;

            case R.id.btQuestion2:
                QuestionTwoActivity.startActivity(MainActivity.this);
                break;
        }
    }
}
