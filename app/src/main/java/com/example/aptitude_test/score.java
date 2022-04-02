package com.example.aptitude_test;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

public class score extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scores);
        ArrayList<String> arr2 = getIntent().getExtras().getStringArrayList("ans2");
        int a1 = getIntent().getExtras().getInt("ans1", -1);
        int a3 = getIntent().getExtras().getInt("ans3", -1);
        TextView score = findViewById(R.id.scoreboard);
        int result =check_score(a1, arr2, a3);
        score.setText(" "+result);


    }

    protected int check_score(int a1, ArrayList<String> arr, int a3){
        int score=0;
        if(a1==1){
            score+=1;
        }
        if(arr.contains("1000") && arr.contains("500")){
            score+=1;
        }

        if (a3==1) {
            score += 1;
        }


        return score;
    }
}
