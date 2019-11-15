package com.example.myapplication;

public class SpeakerTask {
    private String type;
    private String section;
    private int newsIndex;
    private String text;

    public SpeakerTask(String type, String section, int newsIndex, String text) {
        this.type = type;
        this.section = section;
        this.newsIndex = newsIndex;
        this.text = text;
    }

    public String getType() {
        return this.type;
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
