package com.korhan34.roomwithaview;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class WordRepository {
    private WordDao wordDao;
    private LiveData<List<Word>> allWords;

    WordRepository(Application application) {
        WordRoomDatabase database = WordRoomDatabase.getDatabase(application);
        wordDao = database.wordDao();
        allWords = wordDao.getAllWords();
    }

    LiveData<List<Word>> getAllWords() {
        return allWords;
    }

    public void insert(Word word) {
        new InsertAsyncTask(wordDao).execute(word);
    }

    private static class InsertAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao asyncTaskDao;

        InsertAsyncTask(WordDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
