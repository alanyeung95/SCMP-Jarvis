package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;

public class SpeakerTasks {
    Queue taskQueue;

    public SpeakerTasks() {
        this.taskQueue = new LinkedList();
    }

    public void addTask(String text) {
        taskQueue.add(text);
    }

    public String getTask() {
        return taskQueue.poll().toString();
    }
    public int getTaskCount(){
        return taskQueue.size();
    }
}