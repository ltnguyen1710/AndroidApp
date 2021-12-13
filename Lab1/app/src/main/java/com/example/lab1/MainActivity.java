package com.example.lab1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.graphics.Color;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    static private final String URL = "https://www.youtube.com";

    SeekBar sb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Get data from previous instance if not first session

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize seek bar

        sb = (SeekBar) findViewById(R.id.see1);
        sb.setMax(100);

        //Initialize colored boxes

        final TextView red = (TextView) findViewById(R.id.red);
        final TextView yellow = (TextView) findViewById(R.id.yellow);
        final TextView blue = (TextView) findViewById(R.id.blue);
        final TextView green = (TextView) findViewById(R.id.green);
        final TextView purple = (TextView) findViewById(R.id.purple);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progChange = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                int[] redArray = {255, 0, 0};
                int[] blueArray = {0, 0, 255};
                int[] yellowArray = {255, 255, 0};
                int[] greenArray ={0, 153, 51};
                int[] purpleArray = {204, 0, 204};

                progChange = progress;

                //Make incremental color value changes

                redArray[0] = redArray[0] - (255/100)*progChange;
                redArray[1] = redArray[1] - (255/100)*progChange;
                redArray[2] = redArray[2] - (255/100)*progChange;
                blueArray[0] = blueArray[0] + (255/100)*progChange;
                blueArray[1] = blueArray[1] + (102/100)*progChange;
                blueArray[2] = blueArray[2] - (255/100)*progChange;
                yellowArray[0] = yellowArray[0] - (125/100)*progChange;
                yellowArray[1] = yellowArray[1] - (255/100)*progChange;
                yellowArray[2] = yellowArray[2] + (130/100)*progChange;
                greenArray[0] = greenArray[0] - (255/100)*progChange;
                greenArray[1] = greenArray[1] - (145/100)*progChange;
                greenArray[2] = greenArray[2] - (255/100)*progChange;
                purpleArray[0] = purpleArray[0] - (255/100)*progChange;
                purpleArray[1] = purpleArray[1] - (14/100)*progChange;
                purpleArray[2] = purpleArray[2] - (211/100)*progChange;

                //Set the boxes to new colors

                red.setBackgroundColor(Color.rgb(redArray[0],redArray[1],redArray[2]));
                blue.setBackgroundColor(Color.rgb(blueArray[0],blueArray[1],blueArray[2]));
                green.setBackgroundColor(Color.rgb(greenArray[0],greenArray[1],greenArray[2]));
                yellow.setBackgroundColor(Color.rgb(yellowArray[0],yellowArray[1],yellowArray[2]));
                purple.setBackgroundColor(Color.rgb(purpleArray[0],purpleArray[1],purpleArray[2]));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tool_bar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.s1){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Notification");
            builder.setMessage("Click visit youtube");
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent Web = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                    startActivity(Web);
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            AlertDialog dialog = builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
}