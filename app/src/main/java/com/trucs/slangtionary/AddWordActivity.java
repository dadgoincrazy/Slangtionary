package com.trucs.slangtionary;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddWordActivity extends AppCompatActivity {

    private WordViewModel mModel;
    private EditText editWord;
    private EditText editDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        mModel = ViewModelProviders.of(this).get(WordViewModel.class);

        editWord = findViewById(R.id.editWord);
        editDescription = findViewById(R.id.editDescription);

    }

    public void addWord(View v)
    {
        Word word;

        String title = editWord.getText().toString().trim();
        String description = editDescription.getText().toString().trim();
        String language = "English";

        // Set to false if we can't make word
        Boolean flag = true;

        // Check if the boxes have the information for a word
        // Otherwise let them know they need to fill in stuff
        if(title.equals(""))
        {
            Toast.makeText(this, "Word Required", Toast.LENGTH_SHORT).show();
            flag = false;
        }
        if(description.equals(""))
        {
            Toast.makeText(this, "Description Required", Toast.LENGTH_SHORT).show();
            flag = false;
        }

        // If we can make word, lets make it and add it
        if(flag) {
            word = new Word(title,description,language);
            mModel.addWord(word);

            Intent intent = new Intent(AddWordActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
