package edu.apsu.csci.games.simon;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.HashSet;
import java.util.Set;


public class simon_react extends Activity {

    private Simon simon = new Simon();
    private SoundPool soundPool; //variable for sounds
    private Set<Integer> soundsLoaded; //variable for sounds ArrayList


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_react);

        soundsLoaded = new HashSet<Integer>(); //ArrayList of sounds

        findViewById(R.id.button_blue).setOnClickListener(new buttonListener());
        findViewById(R.id.button_green).setOnClickListener(new buttonListener());
        findViewById(R.id.button_red).setOnClickListener(new buttonListener());
        findViewById(R.id.button_yellow).setOnClickListener(new buttonListener());

    }
    class buttonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            FlashButton fb = new FlashButton(simon_react.this);
            fb.execute(v.getId());

            Button button = (Button) v;
            int playerPick = Integer.parseInt(button.getText().toString());

            simon.setPlayerPick(playerPick);
        }
    }

    //SoundPool code for button sounds
    @Override
    protected void onResume() {
        super.onResume();

        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setUsage(AudioAttributes.USAGE_GAME);

        SoundPool.Builder spBuilder = new SoundPool.Builder();
        spBuilder.setAudioAttributes(attrBuilder.build());
        spBuilder.setMaxStreams(1);
        soundPool = spBuilder.build();

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0) { // success
                    soundsLoaded.add(sampleId);
                    Log.i("SOUND", "Sound loaded " + sampleId);
                } else {
                    Log.i("SOUND", "Error cannot load sound status = " + status);
                }
            }
        });

        final int blueId = soundPool.load(this, R.raw.button1, 1);
        findViewById(R.id.button_blue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(blueId);
            }
        });

        final int greenId = soundPool.load(this, R.raw.button2, 1);
        findViewById(R.id.button_green).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(greenId);
            }
        });

        final int redId = soundPool.load(this, R.raw.button3, 1);
        findViewById(R.id.button_red).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(redId);
            }
        });

        final int yellowId = soundPool.load(this, R.raw.button4, 1);
        findViewById(R.id.button_yellow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(yellowId);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;

            soundsLoaded.clear();
        }
    }

    private void playSound(int soundId) {
        if (soundsLoaded.contains(soundId)) {
            soundPool.play(soundId, 12.0f, 12.0f, 0, 0, 1.0f);
        }
    }
}


