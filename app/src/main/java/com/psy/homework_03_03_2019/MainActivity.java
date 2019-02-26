package com.psy.homework_03_03_2019;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int mCurrentGameResId = 0;

    tttController tttcontroller;

    TextView tvCurrentTurn;
    GridLayout gameField;
    TicTacToeClick tttcl;
    LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null)
        {
            mCurrentGameResId = savedInstanceState.getInt("CurrentGameResId");
        }
        else
        {
            mCurrentGameResId = R.layout.activity_tic_tac_toe;
        }

        setContentView(mCurrentGameResId);
        inflater = getLayoutInflater();

        if(mCurrentGameResId == R.layout.activity_tic_tac_toe)
        {
            tttcontroller = new tttController(this,4,TicTacToe.PLAYER_X);
            gameField = findViewById(R.id.gameField);
            tvCurrentTurn = findViewById(R.id.currentTurn);

            tttcl = new TicTacToeClick();

            tttcontroller.createGameField(inflater, gameField, tttcl);
        }





    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CurrentGameResId", mCurrentGameResId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    protected class TicTacToeClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            if(!tttcontroller.isEnded())
            {
                int cellId = (v.getId()) - 10000;
                tttcontroller.setMove(cellId, v, tvCurrentTurn);
            }
            else
            {
                tttcontroller.restartGame(gameField, tvCurrentTurn);
            }
        }
    }
 }
