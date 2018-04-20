package com.trucs.slangtionary;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.ContentUris;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Cody on 10/04/2018.
 */

public class WordViewModel extends AndroidViewModel {
    private LiveData<List<Word>> words;
    private WordDao wordDao;

    public WordViewModel(@NonNull Application application) {
        super(application);
        wordDao = AppDatabase.getDB(application.getApplicationContext()).wordDao();
    }

    /**
     * Init our dataset
     * @return words from our database
     */
    public LiveData<List<Word>> getWords() {
        if(words == null) {
            words = new MutableLiveData<List<Word>>();
            loadWords();
        }
        return words;
    }

    /**
     * Load words from database
     */
    private void loadWords()
    {
        // Getting on the UI thread for now
        setWords(wordDao.getAll());
//        if(getApplication().getApplicationContext() == null)
//        {
//            Log.d("Error", "No context provided");
//        } else {
//            new LoadWordTask().execute();
//        }
    }

    /**
     * Add word to database
     * @param word
     */
    public void addWord(Word word)
    {
        // Inserting on UI thread for now
        wordDao.insert(word);
//        if(getApplication().getApplicationContext() == null)
//        {
//            Log.d("Error", "No context provided");
//        } else {
//            new AddWordTask().execute(word);
//        }
    }

    /**
     * Gets 1 word, not LiveData for now
     * @param id word id
     * @return word
     */
    public Word getWord(Long id) {
        return wordDao.get(id);
    }


    /**
     * Deletes all entries
     */
    public void deleteAllEntries()
    {
        wordDao.nukeTable();
    }

    /**
     * Loads words from database
     */
    private class LoadWordTask extends AsyncTask<Void, Void, LiveData<List<Word>>> {

        @Override
        protected LiveData<List<Word>> doInBackground(Void... voids) {
            try {
                Log.d("Async", "Load words");
                return wordDao.getAll();
            } catch (Exception e) {
                Log.d("Error", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(LiveData<List<Word>> result) {
            setWords(result);
            if(result.getValue() != null) {
                Log.d("onPostExecute", "result" + result.getValue().toString());
            }
        }
    }

    /**
     * AsyncTask to add word to our database
     */
    private class AddWordTask extends AsyncTask<Word, Void, Void>
    {
        @Override
        protected Void doInBackground(Word[] words) {
            try {
                Log.d("Async", "Add words");
                for (Word word : words) {
                    wordDao.insert(word);
                }
            } catch(Exception e) {
                Log.d("Error", e.getMessage());
            }

            return null;
        }
    }

    /**
     * Sets the words after the AsyncTask gets them from the database
     * @param resultWords
     */
    private void setWords(LiveData<List<Word>> resultWords)
    {
        words = resultWords;
    }
}
