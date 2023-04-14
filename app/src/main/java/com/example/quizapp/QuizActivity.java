package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    private TextView textViewWelcome, textViewQuestion;
    private ProgressBar progressBarQuiz;
    private TextView textViewProgress;
    private Button buttonAnswer1, buttonAnswer2, buttonAnswer3, buttonSubmit;
    private int currentQuestionIndex = 0;
    private int selectedOption;
    private int correctAnswers = 0;
    private boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        progressBarQuiz = findViewById(R.id.progressBarQuiz);
        textViewProgress = findViewById(R.id.textViewProgress);
        buttonAnswer1 = findViewById(R.id.buttonAnswer1);
        buttonAnswer2 = findViewById(R.id.buttonAnswer2);
        buttonAnswer3 = findViewById(R.id.buttonAnswer3);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        String userName = getIntent().getStringExtra("userName");
        textViewWelcome.setText("Welcome " + userName + "!");

        displayQuestion();

        buttonAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnswerSelected(0);
            }
        });

        buttonAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnswerSelected(1);
            }
        });

        buttonAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnswerSelected(2);
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answered) {
                    currentQuestionIndex++;
                    if (currentQuestionIndex < getQuestions().length) {
                        displayQuestion();
                    } else {
                        Intent resultIntent = new Intent(QuizActivity.this, ResultActivity.class);
                        resultIntent.putExtra("userName", userName);
                        resultIntent.putExtra("correctAnswers", correctAnswers);
                        startActivity(resultIntent);
                        finish();
                    }
                } else {
                    checkAnswer();
                }
            }
        });
    }

    private void displayQuestion() {
        resetButtons();
        progressBarQuiz.setProgress((currentQuestionIndex + 1) * 100 / getQuestions().length);
        textViewProgress.setText((currentQuestionIndex + 1) + "/" + getQuestions().length);

        Question question = getQuestions()[currentQuestionIndex];
        textViewQuestion.setText(question.getQuestion());
        buttonAnswer1.setText(question.getOptions()[0]);
        buttonAnswer2.setText(question.getOptions()[1]);
        buttonAnswer3.setText(question.getOptions()[2]);
    }

    private void resetButtons() {
        buttonAnswer1.setEnabled(true);
        buttonAnswer2.setEnabled(true);
        buttonAnswer3.setEnabled(true);
        buttonAnswer1.setBackgroundColor(Color.LTGRAY);
        buttonAnswer2.setBackgroundColor(Color.LTGRAY);
        buttonAnswer3.setBackgroundColor(Color.LTGRAY);
        buttonSubmit.setText("Submit");
        answered = false;
    }

    private void onAnswerSelected(int optionIndex) {
        if (!answered) {
            buttonAnswer1.setBackgroundColor(optionIndex == 0 ? Color.CYAN : Color.LTGRAY);
            buttonAnswer2.setBackgroundColor(optionIndex == 1 ? Color.CYAN : Color.LTGRAY);
            buttonAnswer3.setBackgroundColor(optionIndex == 2 ? Color.CYAN : Color.LTGRAY);
            selectedOption = optionIndex;
        }
    }

    private void checkAnswer() {
        answered = true;
        Question question = getQuestions()[currentQuestionIndex];

        for (int i = 0; i < question.getOptions().length; i++) {
            Button button = getButtonForIndex(i);
            button.setEnabled(false);


            // When user selected wrong answer
            if (selectedOption != question.getCorrectAnswerIndex() && i == selectedOption) {
                button.setBackgroundColor(Color.RED);
            }

            // Always display the right answer
            if (i == question.getCorrectAnswerIndex()) {
                button.setBackgroundColor(Color.GREEN);
            }
        }

        // Correct answers count + 1
        if (selectedOption == question.getCorrectAnswerIndex()) {
            correctAnswers++;
        }
        buttonSubmit.setText("Next");
    }



    private Button getButtonForIndex(int index) {
        switch (index) {
            case 0:
                return buttonAnswer1;
            case 1:
                return buttonAnswer2;
            case 2:
                return buttonAnswer3;
            default:
                return null;
        }
    }

    // Return an array of all questions, options and the index of the correct answer
    private Question[] getQuestions() {
        return new Question[]{
                new Question("99x99=?", new String[]{"9901", "9801", "9881"}, 1),
                new Question("Which of the following are multiples of 7?", new String[]{"2030", "2031", "2029"}, 0),
                new Question("Which of the following numbers is a prime number?", new String[]{"51", "61", "81"}, 1),
                new Question("Which of the following is the pi?", new String[]{"3.14159365359", "3.14159365357", "3.14159265359"}, 2),
                new Question("8 candles are lit on the table, 5 are blown out, how many are left?", new String[]{"3", "0", "5"}, 2)
        };
    }

    // Store questions, options and the index of the correct answer
    private static class Question {
        private final String question;
        private final String[] options;
        private final int correctAnswerIndex;

        public Question(String question, String[] options, int correctAnswerIndex) {
            this.question = question;
            this.options = options;
            this.correctAnswerIndex = correctAnswerIndex;
        }

        public String getQuestion() {
            return question;
        }

        public String[] getOptions() {
            return options;
        }

        public int getCorrectAnswerIndex() {
            return correctAnswerIndex;
        }
    }
}
