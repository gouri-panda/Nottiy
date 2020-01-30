package com.zn.notiiy;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    NoteRepository noteRepository;
    LiveData<List<Note>> getAllNotes;
    public NoteViewModel(@NonNull Application application){
        super(application);
        this.noteRepository =  new NoteRepository(application);
        getAllNotes = noteRepository.getAllNotes();
    }
    public void insert(Note note){
        noteRepository.insert(note);
    }
    public void update(Note note){
        noteRepository.update(note);
    }
    public void delete(Note note){
        noteRepository.delete(note);
    }
    public void deletAll(){
        noteRepository.deleteAll();
    }
    public LiveData<List<Note>> getGetAllNotes(){
        return getAllNotes;
    }
}
