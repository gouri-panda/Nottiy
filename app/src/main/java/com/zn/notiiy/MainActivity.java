package com.zn.notiiy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    NoteViewModel noteViewModel;
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    FloatingActionButton createNote;
    public static final int REQUEST_CODE = 1;
    public static final int REQUEST_EDIT_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView =findViewById(R.id.recycler_view);
        createNote = findViewById(R.id.floating_action_button);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setHasFixedSize(true);
        noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);
        //Added animation 😃
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.recycler_view_animation);
        recyclerView.setAnimation(animation);

        noteViewModel = ViewModelProviders.of(this)
                .get(NoteViewModel.class);
        noteViewModel.getAllNotes.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
//                Toast.makeText(MainActivity.this, "Changed", Toast.LENGTH_LONG).show();
                noteAdapter.setNotes(notes) ;

            }
        });
        createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddNoteActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.ANIMATION_TYPE_DRAG,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(noteAdapter.noteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "item deleted", Toast.LENGTH_LONG).show();

            }
        }).attachToRecyclerView(recyclerView);
        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this,AddNoteActivity.class);
                intent.putExtra( AddNoteActivity.STRING_TITLE, note.getTitle());
                intent.putExtra(AddNoteActivity.STRING_DESCRIPTION, note.getDescription());
                intent.putExtra(AddNoteActivity.STRING_PRIORITY, note.getPriority());
                intent.putExtra(AddNoteActivity.STRING_ID, note.getId());
                startActivityForResult(intent, REQUEST_EDIT_CODE);

            }
        });


    }
    public synchronized   void setNoteViewModel(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_all_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_note:
                noteViewModel.deleteAll();
                Toast.makeText(MainActivity.this, "All message deleted", Toast.LENGTH_LONG).show();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE  && resultCode == RESULT_OK  ){
            String title = data.getStringExtra(AddNoteActivity.STRING_TITLE);
            String description = data.getStringExtra(AddNoteActivity.STRING_DESCRIPTION);
            int priority = data.getIntExtra(AddNoteActivity.STRING_PRIORITY, 0);
            noteViewModel.insert(new Note(title, description, priority));
            Toast.makeText(MainActivity.this, "Note saved", Toast.LENGTH_LONG).show();
        }else if ( requestCode == REQUEST_EDIT_CODE && resultCode  == RESULT_OK){
            int id = data.getIntExtra(AddNoteActivity.STRING_ID, -1);
            if (id == -1){
                Toast.makeText(MainActivity.this, "Can n't update note", Toast.LENGTH_LONG).show();
            }
            String title = data.getStringExtra(AddNoteActivity.STRING_TITLE);
            String descripiton = data.getStringExtra(AddNoteActivity.STRING_DESCRIPTION);
            int priority = data.getIntExtra(AddNoteActivity.STRING_PRIORITY, 1);
            Note note  = new Note(title, descripiton, priority);
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(MainActivity.this, "Updated Successful", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Note not saved", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
