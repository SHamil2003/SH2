package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView textTest;
    private ImageView imMain;
    private TextToSpeech textToSpeech;
    private MediaPlayer catSound, dverSound, ogonSound, obyvSound;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();




    }

    private void init() {
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });
        imMain = findViewById(R.id.imMain);
        catSound = MediaPlayer.create(this, R.raw.cats);
        dverSound = MediaPlayer.create(this, R.raw.dvers);

        imMain.setOnClickListener(
                view -> {
                    if(name.equals("дверь"))

                        soundPlay (dverSound);
                    if(name.equals("кот"))

                        soundPlay (catSound);
                    if(name.equals("зажигалка"))

                        soundPlay (ogonSound);
                    if(name.equals("обувь"))

                        soundPlay (obyvSound);
                }
        );
    }

    public void onClickMic(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        startActivityForResult(intent, 10);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 10:
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textCommand(text.get(0));
                    break;

            }
        }
    }

    public void soundPlay (MediaPlayer sound){
        sound.start();
    }
    private void textCommand(String text) {
        switch (text) {
            case "кот":
                imMain.setImageResource(R.drawable.cat);
                name = "кот";
                break;
            case "дверь":
                imMain.setImageResource(R.drawable.dver);
                name = "дверь";
                break;
            case "обувь":
                imMain.setImageResource(R.drawable.obyv);
                name = "обувь";
                break;
            case "зажигалка":
                imMain.setImageResource(R.drawable.ogon);
                name = "зажигалка";
                break;
        }
    }
}
