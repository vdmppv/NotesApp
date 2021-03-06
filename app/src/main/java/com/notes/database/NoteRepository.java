package com.notes.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.notes.dao.NoteDAO;
import com.notes.model.Note;
import com.notes.viewmodel.TagNoteJoinViewModel;

import java.util.List;

public class NoteRepository {

    private NoteDAO noteDAO;

    private LiveData<List<Note>> notes;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDAO = database.noteDAO();
        notes = noteDAO.getNotes();
    }

    public void insert(Note note) {
        new InsertNoteAsyncTask(noteDAO).execute(note);
    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(noteDAO).execute(note);
    }

    public void delete(Note note, TagNoteJoinViewModel tagNoteJoinViewModel) {
        new DeleteNoteAsyncTask(noteDAO).execute(new NoteExtendedParams(note, tagNoteJoinViewModel));
    }

    public void clear() {
        new ClearNoteAsyncTask(noteDAO).execute();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    public LiveData<List<Note>> getNotesSortedByDate() {
        return noteDAO.getNotesSortedByDate();
    }

    public LiveData<List<Note>> getNotesSortedByTitle() {
        return noteDAO.getNotesSortedByTitle();
    }


    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDAO noteDAO;

        private InsertNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDAO noteDAO;

        private UpdateNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDAO.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<NoteExtendedParams, Void, Void> {
        private NoteDAO noteDAO;

        private DeleteNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(NoteExtendedParams... params) {
            noteDAO.delete(params[0].note);
            params[0].tagNoteJoinViewModel.deleteTagsForNote(params[0].note.getId());
            return null;
        }
    }

    private static class ClearNoteAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDAO noteDAO;

        private ClearNoteAsyncTask(NoteDAO noteDAO) {
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.clear();
            return null;
        }
    }

    private class NoteExtendedParams {
        public Note note;
        public TagNoteJoinViewModel tagNoteJoinViewModel;

        public NoteExtendedParams(Note note, TagNoteJoinViewModel tagNoteJoinViewModel) {
            this.note = note;
            this.tagNoteJoinViewModel = tagNoteJoinViewModel;
        }
    }

}
