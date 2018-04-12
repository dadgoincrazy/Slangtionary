package com.trucs.slangtionary;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import java.util.Observer;

public class MainActivity extends AppCompatActivity {

    private WordDao wordDao;

    private WordViewModel mModel;

    private LiveData<List<Word>> words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mModel = ViewModelProviders.of(this).get(WordViewModel.class);

        wordDao = AppDatabase.getDB(getApplicationContext()).wordDao();

//        new GetWordsTasks().execute(); // How the hell

        // I literally copy and paste from Android Developer website and it can't be done this way
//        final Observer<LiveData<List<Word>>> wordObserver = new Observer<LiveData<List<Word>>>(){
//            @Override
//            public void onChanged(@Nullable final LiveData<List<Word>> newWords) {
//                Log.d(newWords);
//            }
//        };

        // Log.d("Words", words.toString());
    }

    public void addWord(View view)
    {
        String word = "Test";
        String description = "Testing";
        String language = "English";

        Word myWord = new Word(word, description, language); // So this can't be a LiveData

        new AddWordTask().execute(myWord);
    }

    private class AddWordTask extends AsyncTask<Word, Void, Void>
    {
        @Override
        protected Void doInBackground(Word[] words) {
            try {
                for (Word word : words) {
                    wordDao.insert(word);
                }
            } catch(Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

            return null; // What is wrong with Java, why is this actually needed on a Void method this language sucks
        }
    }
}