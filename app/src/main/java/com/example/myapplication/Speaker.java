package com.example.myapplication;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;




import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.speech.tts.UtteranceProgressListener;
import android.widget.Toast;
import android.util.Log;


import java.util.HashMap;
import java.util.Locale;


public class Speaker {

    public ttsUtteranceListener getSpeakEndHandler(){
        return new ttsUtteranceListener();
    }

    private class ttsUtteranceListener extends UtteranceProgressListener {
        @Override
        public void onStart(String utteranceId) {

        }
        @Override
        public void onDone(String utteranceId) {
            Log.e("TTS", "fooooooooooooooooooooooooooo");

        }
        @Override
        public void onError(String utteranceId) {

        }
    }
}
