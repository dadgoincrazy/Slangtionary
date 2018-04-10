package com.trucs.slangtionary;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    WordDao wordDao;
    LiveData<List<Word>> words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordDao = AppDatabase.getDB(getApplicationContext()).wordDao();

        new GetWordsTasks().execute(); // How the hell

        // Log.d("Words", words.toString());
    }

    public void addWord(View view)
    {
        String word = "Test";
        String description = "Testing";
        String language = "English";

        Word myWord = new Word(word, description, language); // Help

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

    private class GetWordsTasks extends AsyncTask<Void, Void, LiveData<List<Word>>>
    {
        @Override
        protected LiveData<List<Word>> doInBackground(Void... voids) {
            // Can I use observer here or do I have to go elsewhere

            try {
                return wordDao.getAll();
            } catch(Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(LiveData<List<Word>> result)
        {
            getWords(result);
        }
    }

    private void getWords(LiveData<List<Word>> result)
    {
        Log.d("getWords", result.toString());
        words = result;
    }
}