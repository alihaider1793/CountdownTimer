package com.example.haide.countdowntimer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView1, textView2;
    ImageView image;
    Button btnStart,btnCancel;
    CountDownTimer countDownTimer;
    SeekBar seekBar;
    int counter=10;

    //doing this to test github only
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = (TextView)findViewById(R.id.textView_timer);
        textView2 = (TextView)findViewById(R.id.textView_result);
        btnStart = (Button)findViewById(R.id.button_start);
        btnCancel = (Button)findViewById(R.id.button_cancel);
        image = (ImageView)findViewById(R.id.imageView);
        image.setVisibility(View.GONE);
        btnCancel.setEnabled(false);
        setTimer();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image.setImageResource(R.drawable.one);
                btnStart.setEnabled(false);
                btnCancel.setEnabled(true);
                countDownTimer= new CountDownTimer(counter*1000, 1000) {
                    @Override
                    public void onTick(long l) {
                        int time = (int)l/1000;
                        String remainingTime = String.valueOf(time);
                        textView1.setText(remainingTime);
                        image.setVisibility(View.VISIBLE);
                        if(time == counter/3)
                            changeImage();
                    }

                    @Override
                    public void onFinish() {

                        image.setImageResource(R.drawable.three);
                        //image.setVisibility(View.GONE);
                        Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                        vibrator.vibrate(2000);
                        textView1.setText("0");
                        textView2.setText("SUCCESS!");
                        btnStart.setEnabled(true);
                        btnCancel.setEnabled(false);
                    }
                }.start();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(countDownTimer!=null){
                    countDownTimer.cancel();
                    textView1.setText("");
                    textView2.setText("");
                    btnStart.setEnabled(true);
                    btnCancel.setEnabled(false);
                }
            }
        });
    }


    //Function to set Timer for countdown

    public void setTimer(){
        image.setImageResource(R.drawable.one);
        image.setVisibility(View.GONE);
        seekBar = (SeekBar)findViewById(R.id.seekBar_setTime);
        seekBar.setMax(105);    //because max time is 120 mins and starting time is 10 mins
//      textView2.setText("Covered: "+seekBar.getProgress()+"/"+seekBar.getMax());
        textView2.setText("TIMER : "+counter);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress_value=0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress_value = i;
                textView2.setText("TIMER : "+counter);
//              textView2.setText("Covered: "+progress_value+"/"+seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textView2.setText("TIMER : "+counter);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//              textView2.setText("Covered: "+progress_value+"/"+seekBar.getMax());
                if(progress_value == 0){
                    counter = 10;
                    textView2.setText("TIMER : "+counter);
                }
                else{
                    int temp = progress_value;
                    counter = 15+(5*(temp/5));
                    textView2.setText("TIMER : "+counter);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (countDownTimer != null) {

            final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("STOP!");
            alert.setMessage("Do you want exit?");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    countDownTimer.cancel();
                    dialogInterface.dismiss();
                    finish();
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alert.show();
        }
        //super.onBackPressed();
    }

    public void changeImage(){
        image.setImageResource(R.drawable.two);

    }
}
