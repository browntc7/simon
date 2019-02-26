package edu.apsu.csci.games.simon;
import java.util.Random;


public class simon {
    private static final int MAX_ROUNDS = 31;
    private int[] simon_sequence = new int[MAX_ROUNDS];
    private int[] player_sequence = new int[MAX_ROUNDS];
    private int current_round = 0;
    private Random rand = new Random();


    //play next round
    public void playNext(){
        simon_sequence[current_round] = rand.nextInt(3) + 1;
        current_round++;
    }

    public int[] getSequence(){
        return simon_sequence;
    }

    //Sets players pick
    public void setPlayerPick(int p){
        player_sequence[current_round] = p;
    }

    //Checks to see if next round is possible
    public boolean nextRound(){
        if(current_round < MAX_ROUNDS){
            return false;
        }
        return compareSequence();
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
