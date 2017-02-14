package com.perqin.gua.data.sqlite;

import android.provider.BaseColumns;

/**
 * Author   : perqin
 * Date     : 17-2-11
 */

public interface SQLiteContract {
    String DB_NAME = "app.db";
    int DB_VERSION = 2;

    interface Score extends BaseColumns {
        String TABLE = "scores";
        String COL_STUDENT_ID = "student_id";
        String COL_COURSE_ID = "course_id";
        String COL_COURSE_NAME = "course_name";
        String COL_SCORE = "score";
        String COL_REVEAL_DATE = "reveal_date";
        String QUERY_CREATE_TABLE = String.format(
                "CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER)",
                TABLE, _ID, COL_STUDENT_ID, COL_COURSE_ID, COL_COURSE_NAME, COL_SCORE, COL_REVEAL_DATE
        );
        String QUERY_DROP_TABLE = String.format(
                "DROP TABLE IF EXISTS %s",
                TABLE
        );
    }
}
