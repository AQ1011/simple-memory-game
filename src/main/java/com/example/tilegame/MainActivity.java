package com.example.tilegame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int tileLeft;
    int level;
    Boolean gameover;
    int point;
    int increment;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeGameBoard();
        tv = (TextView) findViewById(R.id.textView);
        gameover = false;
        tileLeft = 3;
        increment =3;
        level = 3;
        clearTile();
        RandomTile(level);
        point = 0;
        tv.setText(String.valueOf(point));
    }

    void makeGameBoard(){
        LinearLayout bigboi = (LinearLayout)findViewById(R.id.bigboi);
        int id = 0;
        for(int i = 0;i<6;i++) {
            LinearLayout row = new LinearLayout(this);
            row.setLayoutParams(new LinearLayout
                    .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            row.setId(i);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setPadding(5,5,5,5);
            bigboi.addView(row);
            for(int j = 0;j<6;j++) {
                Button btn = new Button(this);
                btn.setId(100+id);
                id+=1;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(90,90);
                params.setMargins(10,5,10,5);
                btn.setLayoutParams(params);
                btn.setBackgroundColor(getColor(R.color.teal_200));
                btn.setPadding(5,5,5,5);
                row.addView(btn);
            }
        }
    }
    void RandomTile(int numberOfTile){
        List<Integer> generated = new ArrayList<Integer>();
        Random random = new Random();
        for(int i = 0; i<numberOfTile; i++) {
            while(true)
            {
                int num = random.nextInt((135 - 100) + 1) + 100;
                if (!generated.contains(num))
                {
                    Button btn = (Button) findViewById(num);
                    btn.setBackgroundColor(getColor(R.color.purple_500));
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btn.setBackgroundColor(getColor(R.color.teal_200));
                            btn.setOnClickListener(v -> {
                                tileLeft -= 1;
                                btn.setBackgroundColor(getColor(R.color.purple_200));
                                if(tileLeft==0)
                                {
                                    point += level*100;
                                    tv.setText(String.valueOf(point));
                                    increment -= 1;
                                    if(increment == 0) {
                                        level += 1;
                                        increment = 3;
                                    }
                                    tileLeft = level;
                                    clearTile();
                                    RandomTile(level);
                                }
                            });
                        }
                    }, 3000);

                    generated.add(num);
                    break;
                }
            }


        }
    }
    void clearTile()
    {
        for(int i = 100; i < 136; i++){
            Button btn = (Button) findViewById(i);
            btn.setBackgroundColor(getColor(R.color.teal_200));
            btn.setOnClickListener(v -> {
                ColorDrawable viewColor = (ColorDrawable) btn.getBackground();
                if(viewColor.getColor() != getColor(R.color.teal_200))
                    return;
                gameover = true;
                level = 3;
                AlertDialog al = new AlertDialog.Builder(this).setTitle("LOSER")
                        .setMessage("Play again?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            gameover = false;
                            tileLeft = 3;
                            level = 3;
                            clearTile();
                            RandomTile(level);
                            point = 0;
                            tv.setText(String.valueOf(point));
                        })
                        .setNegativeButton("No i'm dumb", (dialog, which) -> {
                            finishAndRemoveTask();
                        }).create();
                al.show();
            });
        }
    }
}