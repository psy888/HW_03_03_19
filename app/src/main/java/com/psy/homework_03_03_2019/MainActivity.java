package com.psy.homework_03_03_2019;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int mCurrentGameResId = 0;
    private int mMoveCnt = 0;
    private int mCurTurn = TicTacToe.PLAYER_X;
    private int  mSize = 3;
    private boolean mIsEnded = false;

    TextView tvCurrentTurn;
    TextView tvCurCell;
    GridLayout gameField;
    TicTacToeClick tttcl;
    LayoutInflater inflater;

    TicTacToe ttt;

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




        tvCurrentTurn = findViewById(R.id.currentTurn);
        ttt = new TicTacToe(mSize);
        tttcl = new TicTacToeClick();
        inflater = getLayoutInflater();
        gameField = findViewById(R.id.gameField);
        for (int i = 0; i < mSize*mSize; i++)
        {
            TextView cell = (TextView) inflater.inflate(R.layout.cell_tictactoe, gameField, false);
            cell.setId(10000+i);
            cell.setText(playerSign(ttt.getGameField()[i]));
            gameField.addView(cell);
            cell.setOnClickListener(tttcl);
        }

    }

    private void clrGameField()
    {

        int cnt = gameField.getChildCount();
        for (int i = 0; i < cnt; i++) {
            TextView tv = (TextView) gameField.getChildAt(i);
            tv.setText(playerSign(ttt.getGameField()[i]));
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

    private class TicTacToeClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            Log.d("Tag", "Click on " + v.getId());
            if(!mIsEnded)
            {
                int cellId = (v.getId()) - 10000;
                if (ttt.setMove(cellId, mCurTurn)) {
                    Log.d("Tag", "Click on " + playerSign(mCurTurn));
//                int id = v.getId();
//                TextView tv = findViewById(id);
                    tvCurCell = (TextView) v;
                    tvCurCell.setText(playerSign(mCurTurn));
                    int result = ttt.checkResult(mCurTurn);
                    Log.d("Tag", "Result  " + result);
                    if (result == 0) {
                        mCurTurn = -mCurTurn;
                        tvCurrentTurn.setText(playerSign(mCurTurn));
                    } else {
                        mIsEnded = true;
                        tvCurrentTurn.setText("победа игрока" + playerSign(mCurTurn));
                    }
                }
            }
            else
            {
                restartGame();
            }

        }
    }

    private void restartGame()
    {
        ttt.clearGameField();
        mCurTurn = 1;
        clrGameField();
        mIsEnded = false;
        tvCurrentTurn.setText(playerSign(mCurTurn));
    }
    private String playerSign(int turn)
    {
        switch (turn)
        {
            case TicTacToe.PLAYER_X:
                return "X";
            case TicTacToe.PLAYER_O:
                return "O";
            default:
                return "";
        }
    }
 }
