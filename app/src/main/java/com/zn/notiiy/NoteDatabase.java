package com.zn.notiiy;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Note.class,version =  1)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract  NoteDao noteDao();
    public  static  NoteDatabase instance;

    public static synchronized NoteDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,
                    "Note_database")
                    .addCallback(callback)
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDataAsyncTask(instance).execute();
        }
    };
    private static class PopulateDataAsyncTask extends AsyncTask<Void, Void, Void> {
            private NoteDao noteDao;
            private PopulateDataAsyncTask(NoteDatabase noteDatabase) {
                this.noteDao = noteDatabase.noteDao();
            }
            @Override
            protected Void doInBackground(Void... voids) {
                noteDao.insert(new Note("title1djfjdkfjkdklfjjfjjdkjfkdjjdkfjdljfkldfjkldjfkldjfkldjkjfkldklfkldjkfjjdkf", "description1", 1));
                noteDao.insert(new Note("title2", "description2", 2));
                noteDao.insert(new Note("title3", "description3", 3));
                noteDao.insert(new Note("title4", "description4", 4));
                noteDao.insert(new Note("title5", "description5", 5));
                return null;



        }
    }

}
