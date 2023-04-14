package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private Button buttonStartQuiz;

    private TextView textViewWelcomeBack;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        buttonStartQuiz = findViewById(R.id.buttonStartQuiz);
        textViewWelcomeBack = findViewById(R.id.textViewWelcomeBack);

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");

        if (userName != null && !userName.isEmpty()) {
            editTextName.setVisibility(View.GONE);
            textViewWelcomeBack.setVisibility(View.VISIBLE);
            textViewWelcomeBack.setText("Welcome back, " + userName + "!");
        }

        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName == null || userName.isEmpty()) {
                    userName = editTextName.getText().toString();
                }
                if (!userName.isEmpty()) {
                    startQuiz(userName);
                } else {
                    editTextName.setError("Please enter your name");
                }
            }
        });
    }

    private void startQuiz(String userName) {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }
}
