package edu.apsu.csci.games.simon;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class simon_react extends Activity {

    private Simon simon = new Simon();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_react);



        Button button = findViewById(R.id.button_blue);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int buttonBackground = ((ColorDrawable) v.getBackground()).getColor();
                v.setBackgroundColor(simon.flashSquare(buttonBackground));
            }
        });
    }
}
