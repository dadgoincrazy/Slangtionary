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
    private final Context mContext; // Isn't this literally the whole point of AndroidViewModel
    private WordDao wordDao;

    public WordViewModel(@NonNull Application application) {
        super(application);

        mContext = application.getApplicationContext();
        wordDao = AppDatabase.getDB(mContext).wordDao();
    }

    public LiveData<List<Word>> getWords() {
        if(words == null) {
            words = new MutableLiveData<List<Word>>();
            loadWords();
        }
        return words;
    }

    private void loadWords()
    {
        if(mContext == null)
        {
            Log.d("Error", "No context provided");
        } else {
            new LoadWordTask().execute();
        }
    }

    private class LoadWordTask extends AsyncTask<Void, Void, LiveData<List<Word>>> {

        @Override
        protected LiveData<List<Word>> doInBackground(Void... voids) {
            try {
                return wordDao.getAll();
            } catch (Exception e) {
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(LiveData<List<Word>> result) {
            setWords(result);
            Log.d("onPostExecute", "result" + result.getValue().toString());
        }
    }

    private void setWords(LiveData<List<Word>> resultWords)
    {
        words = resultWords;
    }
}
