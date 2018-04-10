package com.trucs.slangtionary;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by owner on 10/04/2018.
 */

public class WordViewModel extends AndroidViewModel {
    private LiveData<List<Word>> words;

    public WordViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Word>> getWords() {
        return words;
    }
}
