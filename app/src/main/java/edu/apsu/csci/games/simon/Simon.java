package edu.apsu.csci.games.simon;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;




public class Simon {
    private static final int MAX_ROUNDS = 31;
    private int[] simon_sequence = new int[MAX_ROUNDS];
    private int[] player_sequence = new int[MAX_ROUNDS];
    private int current_round = 0;
    private boolean simonsTurn = false;
    private int player_round = 0;
    private Random rand = new Random();
    private Activity activity;
    private GameSounds gs;
    //private GameAnimations ga;
    private int highScore;

    Simon(Activity activity, GameSounds gs) {

        this.activity = activity;
        this.gs = gs;


        SharedPreferences prefs = activity.getSharedPreferences("PlayerHighScore", Context.MODE_PRIVATE);
        highScore = prefs.getInt("HighScore", 0);


    }


    //play next round
    public int[] playNext() {
        Log.i("Simon Class", "Playing new round");
        simon_sequence[current_round] = rand.nextInt(3) + 1;
        current_round++;
        return simon_sequence;
    }

    //not sure we need this
    public int[] getSequence() {
        return simon_sequence;
    }

    //Sets players pick
    public void setPlayerPick(int p) {
        //player_sequence[current_round - 1] = p;
        if (!simonsTurn) {
            Log.i("Simon Class", "Setting Player Pick to " + Integer.toString(p));

            if(simon_sequence[player_round] == p){
                player_round += 1;
                Log.i("Simon Class", "Player_Good_Job!");
            } else {
                gs.playSound(-2); // play wrong sound
                TextView tv = activity.findViewById(R.id.loser_tv);
                tv.setText("Loser! Try Again!");
                Log.i("Simon Class", "Player_Bad_Job!");
            }
        }
    }

    //Sets high score if greater than current round and checkForWin is true and returns high score
    public void setHighScore(){
        SharedPreferences prefs = activity.getSharedPreferences("PlayerHighScore", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if (current_round > highScore) {
            editor.putInt("HighScore", current_round);
            Log.i("Current Round", Integer.toString(current_round));
        } else {
            editor.putInt("HighScore", highScore);
        }
        editor.commit();
    }

    public void isSimonsTurn(){
        simonsTurn = true;
    }

    public void isPlayersTurn(){
        player_round = 0;
        simonsTurn = false;
    }


    public int getCurrentRound(){
        return current_round;
    }
} //end of simon class

class SimonSequence extends AsyncTask<Integer, Integer, Void> {

    private Activity activity;
    private Simon simon;

    SimonSequence(Activity activity, Simon simon){
        this.activity = activity;
        this.simon = simon;
    }

    @Override
    protected Void doInBackground(Integer... values) {
        simon.isSimonsTurn();
        simon.playNext();
        int current_round = simon.getCurrentRound();
        int [] simon_sequence = simon.getSequence();

        try {
            for (int i = 0; i < current_round; i++) {
                Log.i("Simon Class", Integer.toString(simon_sequence[i]) + ", " + Integer.toString(current_round));
                switch (simon_sequence[i]) {
                    case 1:
                        publishProgress(R.id.button_green);
                        Thread.sleep(values[0]);
                        break;
                    case 2:
                        publishProgress(R.id.button_red);
                        Thread.sleep(values[0]);
                        break;
                    case 3:
                        publishProgress(R.id.button_yellow);
                        Thread.sleep(values[0]);
                        break;
                    case 4:
                        publishProgress(R.id.button_blue);
                        Thread.sleep(values[0]);
                        break;
                }

            }
        } catch (InterruptedException e) {
            Log.i("playSimonSequence", "Interrupted");
        }
        simon.isPlayersTurn();
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        activity.findViewById(values[0]).performClick();
    }
}

    class GameAnimations extends AsyncTask<Void, Integer, Void> {

        private Activity activity;
        private int id;

        GameAnimations(Activity activity, int id){
            this.activity = activity;
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            //activity.findViewById(id).getBackground().setAlpha(128);
        }

        @Override
        protected Void doInBackground(Void... values) {
            try {
                publishProgress(128);
                Thread.sleep(100);
                publishProgress(255);
            } catch (InterruptedException e){

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            activity.findViewById(id).getBackground().setAlpha(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
           // super.onPostExecute(aVoid);
        }
    }

class GameSounds extends AsyncTask<Void, Void, Void>{
    private SoundPool soundPool; //variable for sounds
    private Set<Integer> soundsLoaded = new HashSet<>(); //variable for sounds ArrayList
    private Activity activity;
    private int blueId;
    private int greenId;
    private int redId;
    private int yellowId;
    private int wrongId;


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
                    soundsLoaded.add(sampleId);

                    Log.i("SOUND", "Sound loaded " + sampleId);
                } else {
                    Log.i("SOUND", "Error cannot load sound status = " + status);
                }
            }
        });
    }

    @Override
    protected Void doInBackground(Void... voids) {
        blueId = soundPool.load(activity, R.raw.button1, 1);
        greenId = soundPool.load(activity, R.raw.button2, 1);
        redId = soundPool.load(activity, R.raw.button3, 1);
        yellowId = soundPool.load(activity, R.raw.button4, 1);
        wrongId = soundPool.load(activity, R.raw.wrong, 1);


        Log.i("SoundID_Load", "blue " + Integer.toString(blueId));
        Log.i("SoundID_Load", "red " + Integer.toString(redId));
        Log.i("SoundID_Load", "gn " + Integer.toString(greenId));
        Log.i("SoundID_Load", "yl " + Integer.toString(yellowId));
        Log.i("SoundID_Load", "wr " + Integer.toString(wrongId));
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        activity.findViewById(R.id.play_button).setEnabled(true);
    }


    public void playSound(int id){
        int result = -1;
        Log.i("SoundID", "blue " + Integer.toString(blueId));
        Log.i("SoundID", "red " + Integer.toString(redId));
        Log.i("SoundID", "gn " + Integer.toString(greenId));
        Log.i("SoundID", "yl " + Integer.toString(yellowId));
        if(id == R.id.button_blue){
            result = soundPool.play(blueId, 1.0f, 1.0f, 0, 0, 1.0f);  //changed to 1.0 can hear sounds multiple time now. Thanks stack overflow user3450236!
        }
        else if (id == R.id.button_green) {
            result = soundPool.play(greenId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
        else if (id == R.id.button_red) {
            result = soundPool.play(redId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
        else if (id == R.id.button_yellow) {
            result = soundPool.play(yellowId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
        else if (id == -2){
            result = soundPool.play(wrongId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
        Log.i("SoundResult", Integer.toString(result));
    }

    public void clearSounds(){
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;

            soundsLoaded.clear();
        }

    }



}