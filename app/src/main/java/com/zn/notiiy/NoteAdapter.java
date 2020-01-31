package com.zn.notiiy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;



    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent,false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote = notes.get(position);
        int priority = currentNote.getPriority();
        String title = currentNote.getTitle();
        String description = currentNote.getDescription();
        holder.priority.setText(String.valueOf(priority));
       holder.textViewTitle.setText(title);
       holder.textViewDescription.setText(description);

    }
    public void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }
    public Note noteAt(int position){
        return notes.get(position);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDescription;
        private  TextView priority;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            priority = itemView.findViewById(R.id.text_view_Priority);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                    listener.onItemClick( notes.get(position));
                    }
                }
            });
        }
    }
    public interface  OnItemClickListener{
        void onItemClick(Note note);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.listener = onItemClickListener;
    }

}
