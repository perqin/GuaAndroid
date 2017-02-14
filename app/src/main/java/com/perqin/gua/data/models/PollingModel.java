package com.perqin.gua.data.models;

/**
 * Author   : perqin
 * Date     : 17-2-11
 */

public class PollingModel {
    private String studentId;
    private boolean started;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
