package com.test.myapplicationfinal.db;

import android.net.Uri;
import android.provider.BaseColumns;

/*в этом классе мы будем описывать таблицы, которые есть в нашей базе данных*/
public final class NotesContract {

    public static final String DB_NAME = "notes.db";
    public static final int DB_VERSION = 2;

    public static final String AUTHORITY = "com.test.myapplicationfinal.provider";
    public static final String URI = "content://" + AUTHORITY;

    public static final String[] CREATE_DATABASE_QUERIES = {
            Notes.CREATE_TABLE,
            Notes.CREATE_UPDATED_TS_INDEX,

            Images.CREATE_TABLE
    };

    private NotesContract() {

    }

    /**
     * Описание таблицы с конспектами
     */
    public static abstract class Notes implements BaseColumns {

        /*добавлем название таблицы и столбцы в класс Notes*/
        public static final String TABLE_NAME = "notes";

        /*хранится URI для доступа к конкретному типу данных — к конспектам*/
        public static final Uri URI = Uri.parse(NotesContract.URI + "/" + TABLE_NAME);


        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_NOTE = "note";
        public static final String COLUMN_CREATED_TS = "created_ts";
        public static final String COLUMN_UPDATED_TS = "updated_ts";

        /*строки, в которых будут содержаться запросы, создающие таблицу и индекс*/
        public static final String CREATE_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY, " +
                        "%s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, " +
                        "%s INTEGER NOT NULL, " +
                        "%s INTEGER NOT NULL);",
                TABLE_NAME,
                _ID,
                COLUMN_TITLE,
                COLUMN_NOTE,
                COLUMN_CREATED_TS,
                COLUMN_UPDATED_TS);

        public static final String CREATE_UPDATED_TS_INDEX = String.format("CREATE INDEX updated_ts_index " +
                        "ON %s (%s);",
                TABLE_NAME,
                COLUMN_UPDATED_TS);

        /**
         * Столбцы, которые будем выбирать
         */

        public static final String[] LIST_PROJECTION = {
                _ID,
                COLUMN_TITLE,
                COLUMN_CREATED_TS,
                COLUMN_UPDATED_TS
        };

        public static final String[] SINGLE_PROJECTION = {
                _ID,
                COLUMN_TITLE,
                COLUMN_NOTE,
                COLUMN_CREATED_TS,
                COLUMN_UPDATED_TS
        };

        // Список заметок
        public static final String URI_TYPE_NOTE_DIR = "vnd.android.cursor.dir/vnd.myapplicationfinal.note";

        // Одна заметка
        public static final String URI_TYPE_NOTE_ITEM = "vnd.android.cursor.item/vnd.myapplicationfinal.note";
    }

    public static abstract class Images implements BaseColumns {

        public static final String TABLE_NAME = "images";

        public static final Uri URI = Uri.parse(NotesContract.URI + "/" + TABLE_NAME);

        public static final String COLUMN_PATH = "path";
        public static final String COLUMN_NOTE_ID = "note_id";

        public static final String CREATE_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY, " +
                        "%s TEXT NOT NULL, " +
                        "%s INTEGER NOT NULL, " +
                        "FOREIGN KEY (%s) REFERENCES %s (%s) ON DELETE CASCADE);",
                TABLE_NAME,
                _ID,
                COLUMN_PATH,
                COLUMN_NOTE_ID,
                COLUMN_NOTE_ID,
                Notes.TABLE_NAME,
                Notes._ID);

        public static final String[] PROJECTION = {
                _ID,
                COLUMN_PATH,
                COLUMN_NOTE_ID
        };
        /**
         * Типы данных
         */
        public static final String URI_TYPE_IMAGE_DIR = "vnd.android.cursor.dir/vnd.myapplicationfinal.image";
        public static final String URI_TYPE_IMAGE_ITEM = "vnd.android.cursor.item/vnd.myapplicationfinal.image";
    }
}
