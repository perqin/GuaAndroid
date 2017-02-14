package com.perqin.gua.data.models;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;
import com.perqin.gua.data.sqlite.SQLiteContract;

/**
 * Author   : perqin
 * Date     : 17-2-11
 */

public class ScoreModel {
    @SerializedName("student_id")
    private String studentId;
    @SerializedName("course_id")
    private String courseId;
    @SerializedName("course_name")
    private String courseName;
    @SerializedName("score")
    private String score;
    @SerializedName("reveal_date")
    private long revealDate;

    public static ScoreModel fromCursor(Cursor cursor) {
        ScoreModel scoreModel = new ScoreModel();
        scoreModel.setStudentId(cursor.getString(cursor.getColumnIndex(SQLiteContract.Score.COL_STUDENT_ID)));
        scoreModel.setCourseId(cursor.getString(cursor.getColumnIndex(SQLiteContract.Score.COL_COURSE_ID)));
        scoreModel.setCourseName(cursor.getString(cursor.getColumnIndex(SQLiteContract.Score.COL_COURSE_NAME)));
        scoreModel.setScore(cursor.getString(cursor.getColumnIndex(SQLiteContract.Score.COL_SCORE)));
        scoreModel.setRevealDate(cursor.getLong(cursor.getColumnIndex(SQLiteContract.Score.COL_REVEAL_DATE)));
        return scoreModel;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public long getRevealDate() {
        return revealDate;
    }

    public void setRevealDate(long revealDate) {
        this.revealDate = revealDate;
    }
}
