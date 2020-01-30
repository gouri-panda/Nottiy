package com.zn.notiiy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPicker;

    public static final String STRING_TITLE = "title";
    public static final String STRING_DESCRIPTION = "descripiton";
    public static final String STRING_PRIORITY = "priority";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextDescription = findViewById(R.id.edit_text_description);
        editTextTitle = findViewById(R.id.edit_text_title);
        numberPicker = findViewById(R.id.number_picker_priority);
        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(1);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Note");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
//                saveNote();
                goToBack();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }

    private void goToBack() {
        Intent intent = new Intent(AddNoteActivity.this,MainActivity.class);
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int  priority = numberPicker.getValue();
        intent.putExtra(STRING_TITLE, title);
        intent.putExtra(STRING_DESCRIPTION, description);
        intent.putExtra(STRING_PRIORITY, priority);
        Toast.makeText(AddNoteActivity.this, "putted", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK, intent);
        finish();
    }

//    public void saveNote(){
//        String title = editTextTitle.getText().toString();
//        String description = editTextDescription.getText().toString();
//        int  priority = numberPicker.getValue();
//        noteViewModel.insert(new Note(title, description, priority));
//
//
//    }
}
