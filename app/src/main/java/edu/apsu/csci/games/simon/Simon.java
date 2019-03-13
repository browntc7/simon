package edu.apsu.csci.games.simon;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import java.util.Random;
import java.util.Set;


public class Simon {
    private static final int MAX_ROUNDS = 31;
    private int[] simon_sequence = new int[MAX_ROUNDS];
    private int[] player_sequence = new int[MAX_ROUNDS];
    private int current_round = 0;
    private int player_round = 0;
    private Random rand = new Random();
    private Activity activity;


    Simon (Activity activity){
       this.activity = activity;
    }

    //play next round
    public int[] playNext(){
        Log.i("Simon Class", "Playing new round");
        simon_sequence[current_round] = rand.nextInt(3) + 1;
        current_round++;
        return simon_sequence;
    }

    //not sure we need this
    public int[] getSequence(){
        return simon_sequence;
    }

    //Sets players pick
    public void setPlayerPick(int p){
        //player_sequence[current_round - 1] = p;
        Log.i("Simon Class", "Setting Player Pick to " + Integer.toString(p));
    }

    //Checks to see if next round is possible
    public boolean nextRound(){
        return compareSequence();
    }

    public boolean checkForWin(){
        if(compareSequence() && current_round == MAX_ROUNDS){
            return true;
        }
        return false;
    }

    public void flashOn(ColorDrawable drawable){
        drawable.setAlpha(0);
    }

    public void flashOff(ColorDrawable drawable){
        drawable.setAlpha(255);
    }

    // don't think we need this
    private void clearPlayerSequence(){
        for(int i = 0; i < current_round; i++){
            player_sequence[i] = 0;
        }
    }

    private boolean compareSequence() {
        for(int i = 0; i < current_round; i++){
            if(simon_sequence[i] != player_sequence[i]){
                return false;
            }
        }
        return true;
    }

    public void playSimonSequence(){
        playNext();

        for(int i = 0; i < current_round; i++){
            Log.i("Simon Class", Integer.toString(simon_sequence[i]) + ", " + Integer.toString(current_round));
            switch (simon_sequence[i]){
                case 1:
                    Log.i("Simon Class", "Performing Click 1 - Green");
                    activity.findViewById(R.id.button_green).performClick();
                case 2:
                    activity.findViewById(R.id.button_red).performClick();
                case 3:
                    activity.findViewById(R.id.button_yellow).performClick();
                case 4:
                    activity.findViewById(R.id.button_blue).performClick();
            }
        }
    }

}
class GameAnamations extends AsyncTask<Integer, Integer, Void> {

    private Activity activity;
    private final static int ALPHA_MAX = 255;
    private final static int ALPHA_MIN = 32;
    private final static int FLASH_MS = 200; // is this an emulator issue; or should i not be updating the ui like this? (some flash slow, some flash fast, some not at all); however the click is being registered!

    GameAnamations (Activity activity){
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Integer... values) {
        try {
            publishProgress(values[0], ALPHA_MAX);
            Thread.sleep(FLASH_MS);
            publishProgress(values[0], ALPHA_MIN);
            Thread.sleep(FLASH_MS);
            publishProgress(values[0], ALPHA_MAX);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        activity.findViewById(values[0]).getBackground().setAlpha(values[1]);
    }
}

//class GameSounds extends AsyncTask<Integer, Integer, Void>{
//    private SoundPool soundPool; //variable for sounds
//    private Set<Integer> soundsLoaded; //variable for sounds ArrayList
//    private Activity activity;
//
//    GameSounds(Activity activity){
//
//        this.activity = activity;
//
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
//    }
//
//    @Override
//    protected Void doInBackground(Integer... values) {
//        final int soundId_1 = soundPool.load(activity, R.raw.button1, 1);
//        final int soundId_2 = soundPool.load(activity, R.raw.button2, 1);
//        final int soundId_3 = soundPool.load(activity, R.raw.button3, 1);
//        final int soundId_4 = soundPool.load(activity, R.raw.button4, 1);
//        return null;
//    }
//
//    protected void onProgressUpdate(int buttonId) {
//        //if (soundsLoaded.contains(values[0])) {
//            soundPool.play(buttonId, 12.0f, 12.0f, 0, 0, 1.0f);
//        //}
//    }
//}


class GameSounds {
    private SoundPool soundPool; //variable for sounds
    private Set<Integer> soundsLoaded; //variable for sounds ArrayList
    private Activity activity;
    private int blueId;
    private int greenId;
    private int redId;
    private int yellowId;


    GameSounds(Activity activity) {

        this.activity = activity;

        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setUsage(AudioAttributes.USAGE_GAME);

        SoundPool.Builder spBuilder = new SoundPool.Builder();
        spBuilder.setAudioAttributes(attrBuilder.build());
        spBuilder.setMaxStreams(8);
        soundPool = spBuilder.build();

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0) { // success
                    //soundsLoaded.add(sampleId);

                    Log.i("SOUND", "Sound loaded " + sampleId);
                } else {
                    Log.i("SOUND", "Error cannot load sound status = " + status);
                }
            }
        });

    }
    public void loadSounds(){
        blueId = soundPool.load(activity, R.raw.button1, 1);
        redId = soundPool.load(activity, R.raw.button2, 1);
        greenId = soundPool.load(activity, R.raw.button3, 1);
        yellowId = soundPool.load(activity, R.raw.button4, 1);
    }

    public void playSound(int id){
        int result = -1;
        Log.i("SoundID", "blue " + Integer.toString(blueId));
        Log.i("SoundID", "red " + Integer.toString(redId));
        Log.i("SoundID", "gn " + Integer.toString(greenId));
        Log.i("SoundID", "yl " + Integer.toString(yellowId));
        if(id == R.id.button_blue){
            result = soundPool.play(blueId, 12.0f, 12.0f, 0, 0, 1.0f);
        }
        else if (id == R.id.button_green) {
            result = soundPool.play(greenId, 12.0f, 12.0f, 0, 0, 1.0f);
        }
        else if (id == R.id.button_red) {
            result = soundPool.play(redId, 12.0f, 12.0f, 0, 0, 1.0f);
        }
        else if (id == R.id.button_yellow) {
            result = soundPool.play(yellowId, 12.0f, 12.0f, 0, 0, 1.0f);
        }
        Log.i("SoundResult", Integer.toString(result));
    }
}