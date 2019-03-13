package edu.apsu.csci.games.simon;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class simon_react extends Activity {

    private Simon simon = new Simon();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_react);



        final Button button = findViewById(R.id.button_blue);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*               //ColorDrawable buttonBackground = (ColorDrawable) v.getBackground();
                //simon.flashOff(buttonBackground);
                //simon.flashOn(buttonBackground);
                v.getBackground().setAlpha(255);
                //buttonBackground.setAlpha(128);
                Log.i("Click", "Alpha 255");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               // buttonBackground.setAlpha(255);
                v.getBackground().setAlpha(0);
                Log.i("Click", "Alpha 0");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                v.getBackground().setAlpha(255);
                //buttonBackground.setAlpha(128);
                Log.i("Click", "Alpha 255");*/

                FlashButton fb = new FlashButton();
                fb.execute(R.id.button_blue);

            }
        });
    }

    class FlashButton extends AsyncTask<Integer, Integer, Void>{

        @Override
        protected Void doInBackground(Integer... values) {
            try {
                    publishProgress(values[0], 255);
                    Thread.sleep(100);
                    publishProgress(values[0], 128);
                    Thread.sleep(100);
                    publishProgress(values[0], 255);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //findViewById(R.id.button_blue).getBackground().setAlpha(values[0]);
            findViewById(values[0]).getBackground().setAlpha(values[1]);
        }
    }
}
