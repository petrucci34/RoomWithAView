package com.korhan34.roomwithaview;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {
    private static volatile WordRoomDatabase INSTANCE;

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase database) {
            super.onOpen(database);
            new PopulateDatabaseAsync(INSTANCE).execute();
        }
    }

    private static class PopulateDatabaseAsync extends AsyncTask<Void, Void, Void> {
        private final WordDao dao;

        PopulateDatabaseAsync(WordRoomDatabase database) {
            dao = database.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            dao.deleteAll();
            dao.insert(new Word("Hello"));
            dao.insert(new Word("World"));

            return null;
        }
    }

    public abstract WordDao wordDao();

    static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
