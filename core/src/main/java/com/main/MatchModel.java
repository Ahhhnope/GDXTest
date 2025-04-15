package com.main;

public class MatchModel {
    private String time;
    private String date;
    private float score;

    public MatchModel(String time, String date, float score) {
        this.time = time;
        this.date = date;
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
