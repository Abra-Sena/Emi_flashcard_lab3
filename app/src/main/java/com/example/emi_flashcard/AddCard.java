package com.example.emi_flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data=new Intent();
                if(((EditText) findViewById(R.id.questionTextField)).getText().toString().isEmpty()||
                        ((EditText) findViewById(R.id.answerTextField)).getText().toString().isEmpty()||
                        ((EditText) findViewById(R.id.answerWrong1TextField)).getText().toString().isEmpty()||
                        ((EditText) findViewById(R.id.answerWrong2TextField)).getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "All fields must be answered", Toast.LENGTH_SHORT).show();
                    return;
                }
                data.putExtra("question",((EditText) findViewById(R.id.questionTextField)).getText().toString());
                data.putExtra("answer", ((EditText) findViewById(R.id.answerTextField)).getText().toString());
                data.putExtra("answerWrong1", ((EditText) findViewById(R.id.answerWrong1TextField)).getText().toString());
                data.putExtra("answerWrong2", ((EditText) findViewById(R.id.answerWrong2TextField)).getText().toString());

                setResult(RESULT_OK, data);
                finish();
            }
        });
        ((EditText) findViewById(R.id.questionTextField)).getText().toString();
        ((EditText) findViewById(R.id.answerTextField)).getText().toString();
        ((EditText) findViewById(R.id.answerWrong1TextField)).getText().toString();
        ((EditText) findViewById(R.id.answerWrong2TextField)).getText().toString();

        findViewById(R.id.my_cancel_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCard.this, MainActivity.class);
                AddCard.this.startActivityForResult(intent, 100);
            }
        });
    }
}
