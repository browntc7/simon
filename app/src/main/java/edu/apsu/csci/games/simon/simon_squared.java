package edu.apsu.csci.games.simon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class simon_squared extends Activity {
    private GameSounds gs;
    private Simon simon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_squared);

        TextView textView = findViewById(R.id.squaredRules_tv);
        textView.setTextSize(18);
        textView.setText("\nSimon Squared tests your memory exponentially FASTER!  There are four colored buttons, " +
                "each producing a different tone when it is pressed. A round in the game consists of " +
                "the device lighting up one or more buttons in a random order," +
                "after which the player must reproduce that order by pressing the buttons--Quickly! Good luck!");

    }

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
    }

    class buttonListener implements View.OnClickListener {

        @Override
        public void onClick(final View v) {

            GameAnimations ga = new GameAnimations(simon_squared.this, v.getId());

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
            SimonSequence ss = new SimonSequence(simon_squared.this, simon);
            ss.execute(800);
        }
    }
}
