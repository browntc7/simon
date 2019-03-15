package edu.apsu.csci.games.simon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.about_button).setOnClickListener(new AboutListener());

        Button play_react = findViewById(R.id.button_react);
        play_react.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSimonReact();
            }
        });

        Button play_squared = findViewById(R.id.button_squared);
        play_squared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSimonSquared();
            }
        });

        Button play_reverse = findViewById(R.id.button_reverse);
        play_reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSimonReverse();
            }
        });

        //should have loaded sounds in this activity since they are needed for multiple game. no need to load them for each game.
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = this.getSharedPreferences("PlayerHighScore", Context.MODE_PRIVATE);
        int score = prefs.getInt("HighScore", 0);

        TextView tv = findViewById(R.id.high_score_tv);
        tv.setText("High Score: " + score);
    }



    private void playSimonReact(){
        Log.i("Function Start ", "In Play");
        Intent intent = new Intent(getApplicationContext(), simon_react.class);
        startActivity(intent);

    }

    private void playSimonSquared(){
        Log.i("Function Start ", "In Play");
        Intent intent = new Intent(getApplicationContext(), simon_squared.class);
        startActivity(intent);

    }

    private void playSimonReverse(){
        Log.i("Function Start ", "In Play");
        Intent intent = new Intent(getApplicationContext(), simon_reverse.class);
        startActivity(intent);

    }
    class AboutListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String message = "<html>" +
                    "<h2>About Simon</h2>" +
                    "<p><b>Simon is a registered trademark of Hasbro Gaming.</b></p>"+
                    "<p><b>This app contains Simon React, Simon Reverse, and Simon Squared.</b></p>"+
                    "<p><b>Developers: Tyler Brown and Charity Edmonds ~ 2019</b></p>"+
                    "<p>Music Credits</p>" +
                    "<p><b>Source:</b> Casio VL-Tone VL-1, Piano G Low Short  <br>" +
                    "<b>Creator:</b> acollier123<br>" +
                    "<b>Link: </b> <a href='https://https://freesound.org/people/acollier123/sounds/122673/>source website</a><br>" +
                    "<b>License: </b> CC-BY 3.0" +
                    "</p>" +
                    "<p><b>Source:</b>  Casio VL-Tone VL-1, Piano C Hi Short <br>" +
                    "<b>Creator:</b>acollier123 <br>" +
                    "<b>Link: </b> <a href='https://freesound.org/people/acollier123/sounds/122672/'>source website</a><br>" +
                    "<b>License: </b> CC-BY 3.0" +
                    "</p>" +
                    "<p><b>Source:</b>  Casio VL-Tone VL-1, Violin C Hi Short <br>" +
                    "<b>Creator:</b>acollier123<br>" +
                    "<b>Link: </b> <a href='https://freesound.org/people/acollier123/sounds/122675/'>source website</a><br>" +
                    "<b>License: </b> CC-BY 3.0" +
                    "</p>" +
                    "<p><b>Source:</b>  Casio VL-Tone VL-1, Flute G Low Short  <br>" +
                    "<b>Creator:</b>acollier123<br>" +
                    "<b>Link: </b> <a href='https://freesound.org/people/acollier123/sounds/122666/'>source website</a><br>" +
                    "<b>License: </b> CC-BY 3.0" +
                    "</p>" +
                    "<p><b>Source:</b>Wrong Buzzer <br>" +
                    "<b>Creator:</b> KevinVG207 <br>" +
                    "<b>Link: </b> <a href='https://freesound.org/people/KevinVG207/sounds/331912/'>source website</a><br>" +
                    "<b>License: </b> CC0-BY 1.0" +
                    "</p>" +
                    "<p><b>Source:</b> retro video game sweep <br>" +
                    "<b>Creator:</b> Timbre <br>" +
                    "<b>Link: </b> <a href='https://freesound.org/people/Timbre/sounds/156522/'>source website</a><br>" +
                    "<b>License: </b> CC BY-NC 3.0" +
                    "</p></html>";
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setMessage(Html.fromHtml(message));
            builder.setPositiveButton("Ok", null);

            AlertDialog dialog = builder.create();
            dialog.show();

            // must be done after the call to show();
            // allows anchor tags to work
            TextView tv = (TextView) dialog.findViewById(android.R.id.message);
            tv.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}
