package com.trucs.slangtionary;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WordViewModel mModel;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    WordAdapter wordAdapter;

    List<Word> words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("On Create", "Hello");

        mModel = ViewModelProviders.of(this).get(WordViewModel.class);
        mRecyclerView = findViewById(R.id.wordList);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        wordAdapter = new WordAdapter(words);

        // Observer to retrieve livedata of our word list
        final Observer<List<Word>> wordObserver = new Observer<List<Word>>(){
            @Override
            public void onChanged(@Nullable final List<Word> newWords) {
                Log.d("New Words", "Observed");

                if(newWords != null) {
                    Log.d("New Words", newWords.toString());
                    wordAdapter.setWords(newWords);
                    wordAdapter.notifyDataSetChanged();
                }
            }
        };

        mModel.getWords().observe(this, wordObserver);
        wordAdapter = new WordAdapter(null);
        mRecyclerView.setAdapter(wordAdapter);
    }

    public void addWord(View view)
    {
//        String word = "Test 2";
//        String description = "Testing 2";
//        String language = "English";
//
//        Word myWord = new Word(word, description, language);
//
//        Toast.makeText(this, "Attempting to add word", Toast.LENGTH_SHORT).show();
//        mModel.addWord(myWord);

        // Switch activity to add word
        Intent intent = new Intent(MainActivity.this, AddWordActivity.class);
        startActivity(intent);
    }
}