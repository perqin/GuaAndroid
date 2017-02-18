package com.perqin.gua.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.perqin.gua.data.models.ScoreModel;

import java.util.ArrayList;

/**
 * Author   : perqin
 * Date     : 17-2-11
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context) {
        super(context, SQLiteContract.DB_NAME, null, SQLiteContract.DB_VERSION);
    }

    public ArrayList<ScoreModel> getScores(String studentId) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(
                SQLiteContract.Score.TABLE,
                null,
                String.format("%s = ?", SQLiteContract.Score.COL_STUDENT_ID),
                new String[]{ studentId },
                null, null, String.format("%s DESC", SQLiteContract.Score.COL_REVEAL_DATE)
        );
        ArrayList<ScoreModel> scoreModels = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                scoreModels.add(ScoreModel.fromCursor(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return scoreModels;
    }

    public void createScore(ScoreModel scoreModel) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteContract.Score.COL_STUDENT_ID, scoreModel.getStudentId());
        contentValues.put(SQLiteContract.Score.COL_COURSE_ID, scoreModel.getCourseId());
        contentValues.put(SQLiteContract.Score.COL_COURSE_NAME, scoreModel.getCourseName());
        contentValues.put(SQLiteContract.Score.COL_SCORE, scoreModel.getScore());
        contentValues.put(SQLiteContract.Score.COL_REVEAL_DATE, scoreModel.getRevealDate());
        database.insert(SQLiteContract.Score.TABLE, null, contentValues);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLiteContract.Score.QUERY_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQLiteContract.Score.QUERY_DROP_TABLE);
        onCreate(db);
    }

    public void updateScore(ScoreModel scoreModel) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteContract.Score.COL_SCORE, scoreModel.getScore());
        database.update(SQLiteContract.Score.TABLE, contentValues, String.format("%s = ?", SQLiteContract.Score.COL_COURSE_ID), new String[]{ scoreModel.getCourseId() });
    }

    public void removeScore(ScoreModel scoreModel) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(SQLiteContract.Score.TABLE, String.format("%s = ?", SQLiteContract.Score.COL_COURSE_ID), new String[]{ scoreModel.getCourseId() });
    }
}
