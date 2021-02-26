package com.notes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.notes.database.TagNoteJoinRepository;
import com.notes.model.Note;
import com.notes.model.Tag;
import com.notes.model.TagNoteJoin;

import java.util.List;

public class TagNoteJoinViewModel extends AndroidViewModel {

    private TagNoteJoinRepository repository;

    public TagNoteJoinViewModel(@NonNull Application application) {
        super(application);
        repository = new TagNoteJoinRepository(application);
    }

    public void insert(TagNoteJoin tagNoteJoin) {
        repository.insert(tagNoteJoin);
    }

    public void delete(TagNoteJoin tagNoteJoin) {
        repository.delete(tagNoteJoin);
    }

    public LiveData<List<Tag>> selectTagsForNote(String noteId) {
        return repository.selectTagsForNote(noteId);
    }

    public LiveData<List<Note>> selectNotesForTag(String tagId) {
        return repository.selectNotesForTag(tagId);
    }

    public void deleteTagsForNote(String noteId) {
        repository.deleteTagNoteJoins(noteId);
    }

}
