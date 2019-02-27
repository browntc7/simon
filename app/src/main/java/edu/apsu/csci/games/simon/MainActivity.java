package edu.apsu.csci.games.simon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button play_react = findViewById(R.id.button_react);
        play_react.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSimonReact();
            }
        });
    }

    private void playSimonReact(){
        Log.i("Function Start ", "In Play");
        Intent intent = new Intent(getApplicationContext(), simon_react.class);
        startActivity(intent);

    }
}
