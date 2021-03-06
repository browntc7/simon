package edu.apsu.csci.games.simon;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class simon_reverse extends Activity {
    private GameSounds gs;
    private Simon simon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_reverse);

        TextView textView = findViewById(R.id.reverseRules_tv);
        textView.setTextSize(18);
        textView.setText("\nSimon Reverse has four colored buttons, each producing a different " +
                "tone when it is pressed. A round in the game consists of " +
                "the device lighting up one or more buttons in a random order," +
                "after which the player must reproduce the OPPOSITE order by pressing the buttons. Good luck!");

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
        Log.i("in onpause", "setting high score");
        simon.setHighScore();
    }

    class buttonListener implements View.OnClickListener {

        @Override
        public void onClick(final View v) {

            GameAnimations ga = new GameAnimations(simon_reverse.this, v.getId());

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
            SimonSequence ss = new SimonSequence(simon_reverse.this, simon);
            ss.execute(1000);
        }
    }
}

