package com.example.scarnedice;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int userOverallScore;
    private int userTurnScore;
    private int computerOverallScore;
    private int computerTurnScore;

    private ImageView diceView;
    private ArrayList<Drawable> diceImages;
    private TextView scoreView;

    private Button rollButt;
    private Button holdButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreView = findViewById(R.id.textView);
        diceView = findViewById(R.id.diceImage);
        rollButt = findViewById(R.id.rollButton);
        holdButt = findViewById(R.id.buttonHold);

        diceImages = new ArrayList<Drawable>();
        diceImages.add(getResources().getDrawable(R.drawable.dice1));
        diceImages.add(getResources().getDrawable(R.drawable.dice2));
        diceImages.add(getResources().getDrawable(R.drawable.dice3));
        diceImages.add(getResources().getDrawable(R.drawable.dice4));
        diceImages.add(getResources().getDrawable(R.drawable.dice5));
        diceImages.add(getResources().getDrawable(R.drawable.dice6));

        userOverallScore = 0;
        userTurnScore = 0;
        computerOverallScore = 0;
        computerTurnScore = 0;
    }

    public void rollPressed(View view) {
        Random random = new Random();
        int roll = random.nextInt(6);
        diceView.setImageDrawable(diceImages.get(roll));

        if(roll == 0) {
            userTurnScore = 0;
            scoreView.setText("Your score: " + userOverallScore + " Computer score: " + computerOverallScore + " Your turn score: X");
            computerTurn();
        }
        else {
            userTurnScore += roll + 1;
            scoreView.setText("Your score: " + userOverallScore + " Computer score: " + computerOverallScore + " Your turn score: " + userTurnScore);
        }
    }

    public void holdPressed(View view) {
        if(userTurnScore != 0) {
            userOverallScore += userTurnScore;
            userTurnScore = 0;
            scoreView.setText("Your score: " + userOverallScore + " Computer score: " + computerOverallScore + " Your turn score: " + userTurnScore);
            computerTurn();
        }
    }

    public void resetPressed(View view) {
        userTurnScore = 0;
        userOverallScore = 0;
        computerTurnScore = 0;
        computerOverallScore = 0;
        scoreView.setText("Your score: " + userOverallScore + " Computer score: " + computerOverallScore + " Your turn score: " + userTurnScore);
    }

    private void computerTurn() {
        rollButt.setEnabled(false);
        holdButt.setEnabled(false);

        while(computerTurnScore < 20) {
            Random random = new Random();
            int roll = random.nextInt(6);
            diceView.setImageDrawable(diceImages.get(roll));

            if(roll == 0) {
                computerTurnScore = 0;
                scoreView.setText("Your score: " + userOverallScore + " Computer score: " + computerOverallScore + " Computer's turn score: X");
                rollButt.setEnabled(true);
                holdButt.setEnabled(true);
                return;
            }
            else {
                computerTurnScore += roll + 1;
                scoreView.setText("Your score: " + userOverallScore + " Computer score: " + computerOverallScore + " Computer's turn score: " + computerTurnScore);
            }
        }

        computerOverallScore += computerTurnScore;
        scoreView.setText("Your score: " + userOverallScore + " Computer score: " + computerOverallScore + " Your turn score: " + userTurnScore);

        rollButt.setEnabled(true);
        holdButt.setEnabled(true);
    }
}
