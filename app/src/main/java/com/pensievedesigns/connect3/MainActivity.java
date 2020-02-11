package com.pensievedesigns.connect3;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // 0 = yellow, 1 = red
    int activePlayer = 0;

    boolean gameIsActive = true;

    /** 06. 2 = un-played. We have an array of Integers (gameState)
      We have assigned every imageView a tag to know which one has been tagged
     starting from 0-8 so we can know the position
     then we have made an Array of Arrays to decide which positions qualify as
     winningPositions*/

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    /** This is a set of arrays within an Array we've made to store winning positions*/
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8},
                                {0,3,6}, {1,4,7}, {2,5,8},
                                {0,4,8}, {2,4,6} };

    public void dropIn(View view){

        /** 01. When user taps on an empty space or ImageView
        Since view is just a view, we cast it into an ImageView
        this gets the image they have tapped on */


        ImageView counter = (ImageView) view;

        //06-1 we use counter.getTag() to get the exact position of the
        // tappedCounter and store it in an Integer variable called tappedCounter

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        /**  We check if the tappedCounter is equals to 2, if it is it means its already tapped
         * we then set the game state to activePlayer*/

        if (gameState[tappedCounter] == 2 && gameIsActive) {

            gameState[tappedCounter] = activePlayer;

            /** 02. then will move the image a 1000 pixels up to the top of the screen
              since it is hidden */

            counter.setTranslationY(-1000f);

            /** 05 If activePlayer is 0(yellow),
             * the if statement changes the active player to 1 (red) */

            if (activePlayer == 0) {

                /** 03. then will set the yellow.png image to the imageView
                The R is for Resources */

                counter.setImageResource(R.drawable.yellow);

                activePlayer = 1;

                /** 05-1 Else If activePlayer is 1(red),
                 * the if statement changes the active player to 0 (Yellow) */

            } else {

                counter.setImageResource(R.drawable.red);

                activePlayer = 0;

            }

            /** 04. Then it will animate the ImageView back down to the intended grid space where
             the user has clicked using the translationYBy(1000f), rotating it 360 degrees for 300
                milliseconds */

            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);

            /** We'll loop through the winning positions and check if any in the gamesState has the
             * same value, use the for command to loop through arrays */

            for (int[] winningPosition : winningPositions) {

                if (gameState[winningPosition[0]] == gameState[winningPosition[1]]
                        && gameState[winningPosition[1]] == gameState[winningPosition[2]]
                        && gameState[winningPosition[0]] != 2) {

                    //someone has won!

                    gameIsActive = false;

                    String winner = "Red";

                    if (gameState[winningPosition[0]] == 0) {

                        winner = "Yellow";
                    }

                    TextView winnerMessage = findViewById(R.id.winnerMessage);

                    winnerMessage.setText(winner + " has won!");

                    LinearLayout layout = findViewById(R.id.playAgainLayout);

                    layout.setVisibility(View.VISIBLE);

                } else {

                    boolean gameIsOver = true;

                    for (int counterState : gameState) {

                        if (counterState == 2) gameIsOver = false;

                    }

                    if(gameIsOver) {

                        TextView winnerMessage = findViewById(R.id.winnerMessage);

                        winnerMessage.setText("It's a draw");

                        LinearLayout layout = findViewById(R.id.playAgainLayout);

                        layout.setVisibility(View.VISIBLE);

                    }

                }
            }
        }

    }

    public void playAgain(View view) {

        gameIsActive = true;

        LinearLayout layout = findViewById(R.id.playAgainLayout);

        layout.setVisibility(View.INVISIBLE);

        activePlayer = 0;

        for(int i = 0; i < gameState.length; i++) {

            gameState[i] = 2;

        }

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++ ) {

            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);

        }


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
