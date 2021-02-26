package com.notes.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.notes.dao.NoteDAO;
import com.notes.dao.TagDAO;
import com.notes.dao.TagNoteJoinDAO;
import com.notes.model.Note;
import com.notes.model.Tag;
import com.notes.model.TagNoteJoin;

@Database(entities = {Note.class, Tag.class, TagNoteJoin.class}, version = 4)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDAO noteDAO();

    public abstract TagDAO tagDAO();

    public abstract TagNoteJoinDAO tagNoteJoinDAO();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    NoteDatabase.class,
                    "note_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
