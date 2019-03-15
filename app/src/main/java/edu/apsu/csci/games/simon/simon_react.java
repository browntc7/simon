package edu.apsu.csci.games.simon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class simon_react extends Activity {
    private GameSounds gs;
    private Simon simon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_react);


        findViewById(R.id.play_button).setEnabled(false);

    }

    class buttonListener implements View.OnClickListener {

        @Override
        public void onClick(final View v) {

            GameAnimations ga = new GameAnimations(simon_react.this, v.getId());

            gs.playSound(v.getId());
            ga.execute();

            Button button = (Button) v;
            int playerPick = Integer.parseInt(button.getText().toString());

            simon.setPlayerPick(playerPick);

        }

    }

    class playListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            SimonSequence ss = new SimonSequence(simon_react.this, simon);
            ss.execute(1000);
        }
    }

    //SoundPool code for button sounds
    @Override
    protected void onResume() {
        super.onResume();

        gs = new GameSounds(this);
        simon = new Simon(this, gs);

        findViewById(R.id.button_blue).setOnClickListener(new buttonListener());
        findViewById(R.id.button_green).setOnClickListener(new buttonListener());
        findViewById(R.id.button_red).setOnClickListener(new buttonListener());
        findViewById(R.id.button_yellow).setOnClickListener(new buttonListener());

        findViewById(R.id.play_button).setOnClickListener(new playListener());

        gs.execute();

    }

    @Override
    protected void onPause() {
        super.onPause();
        gs.clearSounds();
        gs = null;
        simon.setHighScore();
    }
}


