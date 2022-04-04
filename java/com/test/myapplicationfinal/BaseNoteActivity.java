package com.test.myapplicationfinal;

import android.content.ContentUris;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;

import com.test.myapplicationfinal.db.NotesContract;
import com.test.myapplicationfinal.ui.NoteImagesAdapter;
import com.test.myapplicationfinal.ui.NotesAdapter;

public abstract class BaseNoteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    protected static final int LOADER_NOTE = 0;
    protected static final int LOADER_IMAGES = 1;

    protected long noteId = -1;

    protected NoteImagesAdapter noteImagesAdapter;
    protected NotesAdapter noteAdapter;

    /**
     * Инициализируем загрузчик конспекта
     */
    protected void initNoteLoader() {
        /*константу для инициализации лоадера конспекта*/
        getLoaderManager().initLoader(
                LOADER_NOTE, // Идентификатор загрузчика
                null, // Аргументы
                this // Callback для событий загрузчика
        );
    }

    /**
     * Инициализируем загрузчик изображений для конспекта
     */
    protected void initImagesLoader() {
        getLoaderManager().initLoader(
                LOADER_IMAGES,
                null,
                this
        );
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == LOADER_NOTE) {
            return new CursorLoader(
                    this,  // Контекст
                    ContentUris.withAppendedId(NotesContract.Notes.URI, noteId), // URI
                    NotesContract.Notes.SINGLE_PROJECTION, // Столбцы
                    null, // Параметры выборки
                    null, // Аргументы выборки
                    null // Сортировка по умолчанию
            );
        } else {
            return new CursorLoader(
                    this,
                    NotesContract.Images.URI,
                    NotesContract.Images.PROJECTION,
                    NotesContract.Images.COLUMN_NOTE_ID + " = ?",
                    new String[]{String.valueOf(noteId)},
                    null
            );
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (loader.getId() == LOADER_NOTE) {
            cursor.setNotificationUri(getContentResolver(), NotesContract.Notes.URI);
            displayNote(cursor);
        } else {
            cursor.setNotificationUri(getContentResolver(), NotesContract.Images.URI);

            noteImagesAdapter.swapCursor(cursor);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /**
     * Отображаем заметку. Этот метод реализован в Activity
     */
    protected abstract void displayNote(Cursor cursor);
}
