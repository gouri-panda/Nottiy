package com.zn.notiiy;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NoteRepository {
    List<String> stringList= new ArrayList<>();
    LinkedList<String> list = new LinkedList<>();
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;
    public NoteRepository(Application application){
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application.getApplicationContext());
        noteDao = noteDatabase.noteDao();
        allNotes = noteDao.getAllNotes();
    }
    public void insert(Note note){
        new InsertAsyncTask(noteDao).execute(note);
    }
    public void update(Note note){
        new UpdateAsyncTask(noteDao).execute(note);
    }
    public void delete(Note note){
        new DeleteAsyncTask(noteDao).execute(note);
    }
    public void deleteAll(){
      new DeleteAllAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }
    private static class InsertAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;
        private InsertAsyncTask(NoteDao noteDao){
             this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }
    private static class UpdateAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;
        private UpdateAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }
    private static class DeleteAsyncTask extends  AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;
        private DeleteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }
    private static class DeleteAllAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;
        private DeleteAllAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.deleteAll();
            return null;
        }
    }


}
