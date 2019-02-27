package com.psy.homework_03_03_2019;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int mCurrentGameResId = 0;


    //TicTacToe
    TTTController mTTTController;
    TextView tvCurrentTurn;
    GridLayout TTTGameField;
    TicTacToeClick mTicTacToeClick;
    //Tag
    TagController mTagController;
    TextView tvMovesCnt;
    GridLayout TagGameField;
    TagClick mTagClick;

    LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null)
        {
            mCurrentGameResId = savedInstanceState.getInt("CurrentGameResId");
            mTTTController = new TTTController(this,savedInstanceState.getByteArray("savedGame"));
        }
        else
        {
//            mCurrentGameResId = R.layout.activity_tic_tac_toe;
            mCurrentGameResId = R.layout.activity_tag;
        }

        setContentView(mCurrentGameResId);
        inflater = getLayoutInflater();

        if(mCurrentGameResId == R.layout.activity_tic_tac_toe)
        {
            if(mTTTController ==null) {
                mTTTController = new TTTController(this, 3, TicTacToe.PLAYER_X);
            }
            TTTGameField = findViewById(R.id.TTTgameField);
            tvCurrentTurn = findViewById(R.id.currentTurn);

            mTicTacToeClick = new TicTacToeClick();

            mTTTController.createGameField(inflater, TTTGameField, mTicTacToeClick);
        }
        if (mCurrentGameResId == R.layout.activity_tag)
        {
            if(mTagController == null)
            {
                mTagController = new TagController(this, 3);
            }
                TagGameField = findViewById(R.id.TagGameField);
                tvMovesCnt = findViewById(R.id.movesCnt);

                mTagClick = new TagClick();

                mTagController.createGameField(inflater, TagGameField, mTagClick);

        }






    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CurrentGameResId", mCurrentGameResId);
        outState.putByteArray("savedGame", mTTTController.saveGame());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * Слушатель для "Крестики-нолики"
     */
    protected class TicTacToeClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            if(!mTTTController.isEnded())
            {
                int cellId = (v.getId()) - 10000;
                mTTTController.setMove(cellId, v, tvCurrentTurn);
            }
            else
            {
                mTTTController.restartGame(TTTGameField, tvCurrentTurn);
            }
        }
    }
    /**
     * Слушатель для "Пятнашки"
     */
    protected class TagClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            if(!mTagController.isEnded())
            {
                int cellId = v.getId();
                mTagController.setMove(cellId);
            }
            else
            {
                //todo:RestartGame
            }
        }
    }
 }
