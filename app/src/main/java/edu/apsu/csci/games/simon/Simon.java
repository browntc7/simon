package edu.apsu.csci.games.simon;
import java.util.Random;


public class Simon {
    private static final int MAX_ROUNDS = 31;
    private int[] simon_sequence = new int[MAX_ROUNDS];
    private int[] player_sequence = new int[MAX_ROUNDS];
    private int current_round = 0;
    private Random rand = new Random();


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
        player_sequence[current_round - 1] = p;
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

    public int flashSquare(int color){
        return color - 44000000;
    }


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
