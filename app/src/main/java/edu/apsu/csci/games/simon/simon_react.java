package edu.apsu.csci.games.simon;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;
import java.util.SimpleTimeZone;


public class simon_react extends Activity {

    private Simon simon = new Simon(this);
    //private SoundPool soundPool; //variable for sounds
    //private Set<Integer> soundsLoaded; //variable for sounds ArrayList


   private GameSounds gs = new GameSounds(this);
   private int hs;
   //final Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_react);

        //soundsLoaded = new HashSet<Integer>(); //ArrayList of sounds

        findViewById(R.id.play_button).setEnabled(false);



    }
    class buttonListener implements View.OnClickListener {

        @Override
        public void onClick(final View v) {

            GameAnimations ga = new GameAnimations(simon_react.this, v.getId());


            gs.playSound(v.getId());
            ga.execute();

            //simon.flash(v.getBackground());


            Button button = (Button) v;
            int playerPick = Integer.parseInt(button.getText().toString());

            simon.setPlayerPick(playerPick);

        }





}

    class playListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            SimonSequence ss = new SimonSequence(simon_react.this, simon);
            ss.execute();
        }
    }

    //SoundPool code for button sounds
    @Override
    protected void onResume() {
        super.onResume();

        findViewById(R.id.button_blue).setOnClickListener(new buttonListener());
        findViewById(R.id.button_green).setOnClickListener(new buttonListener());
        findViewById(R.id.button_red).setOnClickListener(new buttonListener());
        findViewById(R.id.button_yellow).setOnClickListener(new buttonListener());

        findViewById(R.id.play_button).setOnClickListener(new playListener());

        gs.execute();

//        while(!gs.soundsAreLoaded()) {
//            Log.i("main ui sleep" , "sleeping");
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        //simon.playSimonSequence();
        //simon.checkForWin();
        //hs = simon.setHighScore();
        //Toast.makeText (getBaseContext(),"Congratulations! You Broke The High Score Record with: "+ hs, Toast.LENGTH_LONG).show();

//        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
//        attrBuilder.setUsage(AudioAttributes.USAGE_GAME);
//
//        SoundPool.Builder spBuilder = new SoundPool.Builder();
//        spBuilder.setAudioAttributes(attrBuilder.build());
//        spBuilder.setMaxStreams(4);
//        soundPool = spBuilder.build();
//
//        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
//            @Override
//            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
//                if (status == 0) { // success
//                    soundsLoaded.add(sampleId);
//
//                    Log.i("SOUND", "Sound loaded " + sampleId);
//                } else {
//                    Log.i("SOUND", "Error cannot load sound status = " + status);
//                }
//            }
//        });
//
//        final int blueId = soundPool.load(this, R.raw.button1, 1);
//        findViewById(R.id.button_blue).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                playButton(blueId, v);
//                Log.i("Simon OnClick", " blue loaded");
//            }
//        });
//
//        final int greenId = soundPool.load(this, R.raw.button2, 1);
//        findViewById(R.id.button_green).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                playButton(greenId, v);
//                Log.i("Simon OnClick", " green loaded");
//            }
//        });
//
//        final int redId = soundPool.load(this, R.raw.button3, 1);
//        findViewById(R.id.button_red).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                playButton(redId, v);
//                Log.i("Simon OnClick", " red loaded");
//            }
//        });
//
//        final int yellowId = soundPool.load(this, R.raw.button4, 1);
//        findViewById(R.id.button_yellow).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                playButton(yellowId, v);
//                Log.i("Simon OnClick", " yellow loaded");
//            }
//
//        });
//        Log.i("Sounds Loaded Size", Integer.toString(soundsLoaded.size()));

        //simon.playSimonSequence();
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (soundPool != null) {
//            soundPool.release();
//            soundPool = null;
//
//            soundsLoaded.clear();
//        }
//    }

//    private void playButton(int soundId, View buttonView) {
//        if (soundsLoaded.contains(soundId)) {
//            soundPool.play(soundId, 12.0f, 12.0f, 0, 0, 1.0f);
//        }
//        FlashButton fb = new FlashButton(simon_react.this);
//        fb.execute(buttonView.getId());
//
//        Button button = (Button) buttonView;
//        int playerPick = Integer.parseInt(button.getText().toString());
//
//        simon.setPlayerPick(playerPick);
//    }
//
//
}


