package com.mine.edgr.dice;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.DialogInterface;

public class player2 extends ActionBarActivity {

    private FrameLayout die1, die2;
    private Button roll, hold;
    private int p2Score, p1Score, turnScore, round;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player2);
        getInfo();
        displayScores();
        TextView roundInfo = (TextView)findViewById(R.id.round);
        roundInfo.setText("Round: " + round);
        die1 = (FrameLayout)findViewById(R.id.die1);
        die2 = (FrameLayout)findViewById(R.id.die2);
        roll = (Button)findViewById(R.id.button);
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                rollDice();
                TextView roundScore = (TextView)findViewById(R.id.round);
                roundScore.setText("Round score: " + turnScore);
            }
        });
        hold = (Button)findViewById(R.id.hold);
        hold.setOnClickListener(new View.OnClickListener() {


//            This is tp show the winner
//            @Override
//            public void onClick(View view){
//                AlertDialog alertDialog = new AlertDialog.Builder(player2.this).create();
//                alertDialog.setTitle("You Won!");
//                alertDialog.setMessage("Yabadabadoo");
//                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which){
//                                dialog.dismiss();
//                            }
//                        });
//                alertDialog.show();
//            }

            @Override
            public void onClick(View view) {
                p2Score += turnScore;
                sendInfo();
            }
        });
    }

    public void getInfo(){
        Intent intent = getIntent();
        p1Score = intent.getIntExtra("p1Score", 0);
        p2Score = intent.getIntExtra("p2Score", 0);
        round = intent.getIntExtra("round", 0);
    }
    //get two random numbers
    public void rollDice(){
        int roll_1, roll_2;
        roll_1 = (int)(Math.random() * 6) + 1;
        roll_2 = (int)(Math.random() * 6) + 1;
        setDie(roll_1, die1);
        setDie(roll_2, die2);
        addScore(roll_1);
        addScore(roll_2);

        if ((roll_1 == 1) || (roll_2 == 1)){
            Toast.makeText(this, "Looks like a 1 came up. \nYou lose your turn points. \nPlayer 1 is up!", Toast.LENGTH_LONG).show();
            sendInfo();
        }

//        if (turnScore > 20){
//            Toast.makeText(this, "Your turn score is: " + turnScore + "\nYou went over! \nPlayer 1 is up! " , Toast.LENGTH_LONG).show();
//            sendInfo();
//        }
        if((p2Score + turnScore) > 100){
            win();
        }
    }

    public void win() {
        AlertDialog.Builder builder = new AlertDialog.Builder(player2.this);
        builder.setTitle("Player 2 Wins!");
        builder.setMessage("Would you like to play again?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                p1Score = 0;
                p2Score = 0;
                round = 0;
                turnScore = 0;
                sendInfo();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.show();
    }

    public void addScore(int dice){

        turnScore += dice;
    }

    public void sendInfo(){
        round++;
        Intent intent = new Intent(player2.this, MainActivity.class);
        intent.putExtra("p1Score", p1Score);
        intent.putExtra("p2Score", p2Score);
        intent.putExtra("round", round);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void displayScores(){
        TextView p1TotalScore = (TextView)findViewById(R.id.p1);
        p1TotalScore.setText("P1: " + p1Score);
        TextView p2TotalScore = (TextView)findViewById(R.id.p2);
        p2TotalScore.setText("P2: " + p2Score);
    }
    //set appropriate image to FrameView for an int
    public void setDie(int value, FrameLayout die){
        Drawable pic = null;

        switch(value){
            case 1: pic = getResources().getDrawable(R.drawable.die_face_1);
                break;
            case 2: pic = getResources().getDrawable(R.drawable.die_face_2);
                break;
            case 3: pic = getResources().getDrawable(R.drawable.die_face_3);
                break;
            case 4: pic = getResources().getDrawable(R.drawable.die_face_4);
                break;
            case 5: pic = getResources().getDrawable(R.drawable.die_face_5);
                break;
            case 6: pic = getResources().getDrawable(R.drawable.die_face_6);
                break;
        }

        die.setBackground(pic);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
