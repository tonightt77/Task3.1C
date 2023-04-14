package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView textViewCongratulations;
    TextView textViewYourScore;
    TextView textViewScore;
    Button buttonTakeNewQuiz;
    Button buttonFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewCongratulations = findViewById(R.id.textViewCongratulations);
        textViewYourScore = findViewById(R.id.textViewYourScore);
        textViewScore = findViewById(R.id.textViewScore);
        buttonTakeNewQuiz = findViewById(R.id.buttonTakeNewQuiz);
        buttonFinish = findViewById(R.id.buttonFinish);

        // Return the intent that started this activity
        Intent intent = getIntent();
        // Receive the data passed from the QuizActivity
        String userName = intent.getStringExtra("userName");
        int correctAnswers = intent.getIntExtra("correctAnswers", 0);
        int totalQuestions = intent.getIntExtra("totalQuestions", 5);
        // Display congratulations message and score
        textViewCongratulations.setText("Congratulations, " + userName + "!");
        textViewScore.setText(correctAnswers + "/" + totalQuestions);

        buttonTakeNewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivityIntent = new Intent(ResultActivity.this, MainActivity.class);
                mainActivityIntent.putExtra("userName", userName);
                startActivity(mainActivityIntent);
                finish();
            }
        });

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }
}
