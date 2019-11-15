package com.example.myapplication;

public class SpeakerTask {
    private boolean isNews;
    private String section;
    private int newsIndex;
    private String text;

    public SpeakerTask(boolean isNews, String section, int newsIndex, String text) {
        this.isNews = isNews;
        this.section = section;
        this.newsIndex = newsIndex;
        this.text = text;
    }

    public boolean getIsNews() {
        return this.isNews;
    }

    public String getSection() {
        return this.section;
    }

    public int getNewsIndex() {
        return this.newsIndex;
    }

    public String getText() {
        return this.text;
    }
}
