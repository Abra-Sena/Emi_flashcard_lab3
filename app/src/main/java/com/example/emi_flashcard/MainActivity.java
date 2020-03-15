package com.example.emi_flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;

    int currentCardDisplayedIndex = 0;
    private boolean showingResult=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        flashcardDatabase = new FlashcardDatabase(this);
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
        }

        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
            }
        });
        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.answer_option1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) findViewById(R.id.answer_option1)).setBackground(getDrawable(R.drawable.wrong_answer_background));
                ((TextView) findViewById(R.id.answer_option2)).setBackground(getDrawable(R.drawable.answer_background));
            }
        });
        findViewById(R.id.answer_option2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) findViewById(R.id.answer_option2)).setBackground(getDrawable(R.drawable.answer_background));
            }
        });
        findViewById(R.id.answer_option3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView) findViewById(R.id.answer_option3)).setBackground(getDrawable(R.drawable.wrong_answer_background));
                ((TextView) findViewById(R.id.answer_option2)).setBackground(getDrawable(R.drawable.answer_background));
            }
        });

        findViewById(R.id.eye_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showingResult){
                    ((ImageView) findViewById(R.id.eye_view)).setImageResource(R.drawable.ic_eye_hide);
                    findViewById(R.id.answer_option1).setVisibility(View.VISIBLE);
                    findViewById(R.id.answer_option2).setVisibility(View.VISIBLE);
                    findViewById(R.id.answer_option3).setVisibility(View.VISIBLE);
                    showingResult=false;
                } else {
                    ((ImageView) findViewById(R.id.eye_view)).setImageResource(R.drawable.ic_eye_view);
                    findViewById(R.id.answer_option1).setVisibility(View.INVISIBLE);
                    findViewById(R.id.answer_option2).setVisibility(View.INVISIBLE);
                    findViewById(R.id.answer_option3).setVisibility(View.INVISIBLE);
                    showingResult=true;
                }
            }
        });
        findViewById(R.id.rootView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);

                ((TextView) findViewById(R.id.answer_option1)).setBackground(getDrawable(R.drawable.option_answer_background));
                ((TextView) findViewById(R.id.answer_option2)).setBackground(getDrawable(R.drawable.option_answer_background));
                ((TextView) findViewById(R.id.answer_option3)).setBackground(getDrawable(R.drawable.option_answer_background));
            }
        });
        findViewById(R.id.my_add_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent someData = new Intent(MainActivity.this, AddCard.class);

                MainActivity.this.startActivityForResult(someData, 100);
            }
        });

        findViewById(R.id.my_arrow_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //advance pointer index so we can show next card
                currentCardDisplayedIndex++;

                //to not get an IndexOutOfBoundsError if viewing the last index card in the list
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }
                //set question and answer TextViews with data from the database
                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            String question = data.getExtras().getString("question");
            String answer = data.getExtras().getString("answer");
            String answerWrong1 = data.getExtras().getString("answerWrong1");
            String answerWrong2 = data.getExtras().getString("answerWrong2");

            ((TextView) findViewById(R.id.flashcard_question)).setText(question);
            ((TextView) findViewById(R.id.flashcard_answer)).setText(answer);
            ((TextView) findViewById(R.id.answer_option1)).setText(answerWrong1);
            ((TextView) findViewById(R.id.answer_option2)).setText(answer);
            ((TextView) findViewById(R.id.answer_option3)).setText(answerWrong2);

            flashcardDatabase.insertCard(new Flashcard(question, answer));
            allFlashcards = flashcardDatabase.getAllCards();
        }

        Snackbar.make(findViewById(R.id.flashcard_question),
                "Card Successfully Created",
                Snackbar.LENGTH_SHORT).show();
    }
}
