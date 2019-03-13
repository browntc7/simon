package edu.apsu.csci.games.simon;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import java.util.Random;


public class Simon {
    private static final int MAX_ROUNDS = 31;
    private int[] simon_sequence = new int[MAX_ROUNDS];
    private int[] player_sequence = new int[MAX_ROUNDS];
    private int current_round = 0;
    private int player_round = 0;
    private Random rand = new Random();
/*    private Activity activity;


    Simon (Activity activity){
       this.activity = activity;
    }*/

    //play next round
    public int[] playNext(){
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


}
class FlashButton extends AsyncTask<Integer, Integer, Void> {

    private Activity activity;
    private final static int ALPHA_MAX = 255;
    private final static int ALPHA_MIN = 32;
    private final static int FLASH_MS = 200; // is this an emulator issue; or should i not be updating the ui like this? (some flash slow, some flash fast, some not at all); however the click is being registered!

    FlashButton (Activity activity){
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
