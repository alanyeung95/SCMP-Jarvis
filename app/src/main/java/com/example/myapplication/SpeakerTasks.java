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

    public void addTask(SpeakerTask task) {
        taskQueue.add(task);
    }

    public SpeakerTask getTask() {
        return (SpeakerTask) taskQueue.poll();
    }
    public int getTaskCount(){
        return taskQueue.size();
    }
}