package com.psy.homework_03_03_2019;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private int mCurrentGameResId = 0;


    //TicTacToe
    TTTController mTTTController;
    TextView tvCurrentTurn;
    GridLayout TTTGameField;
    TicTacToeClick mTicTacToeClick;
    int mTTTSize = 3;
    //Tag
    TagController mTagController;
    TextView tvMovesCnt;
    GridLayout TagGameField;
    TagClick mTagClick;
    int mTagSize = 3;


    LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null)
        {
            mCurrentGameResId = savedInstanceState.getInt("CurrentGameResId");
            if(savedInstanceState.getByteArray("savedGame")!=null)
                mTTTController = new TTTController(this, savedInstanceState.getByteArray("savedGame"));
            if(savedInstanceState.getByteArray("TagSavedGameRow0") != null)
            {
                mTagSize = savedInstanceState.getInt("TagMoveCount");

                TagGameField = findViewById(R.id.TagGameField);
                tvMovesCnt = findViewById(R.id.movesCnt);
                mTagClick = new TagClick();

                mTagController = new TagController(this,inflater, mTagSize, tvMovesCnt, TagGameField, mTagClick );
                for (int i = 0; i < mTagSize; i++) {
                    mTagController.restoreGameFieldRow(i, savedInstanceState.getByteArray("TagSavedGameRow"+i));
                }
            }
        }
        else
        {
            mCurrentGameResId = R.layout.activity_tic_tac_toe;
//            mCurrentGameResId = R.layout.activity_tag;
        }

        setContentView(mCurrentGameResId);

        inflater = getLayoutInflater();

        if(mCurrentGameResId == R.layout.activity_tic_tac_toe)
        {
            if(mTTTController ==null) {
                mTTTController = new TTTController(this, mTTTSize, TicTacToe.PLAYER_X);
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
                TagGameField = findViewById(R.id.TagGameField);
                tvMovesCnt = findViewById(R.id.movesCnt);
                mTagClick = new TagClick();
                mTagController = new TagController(this, inflater,mTagSize, tvMovesCnt, TagGameField,mTagClick);
            }
            mTagController.createGameField();
        }






    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CurrentGameResId", mCurrentGameResId);
        if(mTTTController!=null) {
            outState.putByteArray("TTTSavedGame", mTTTController.saveGame());
        }
        if(mTagController != null)
        {
            outState.putInt("TagMoveCount", mTagController.getMoveCount());
            for (int i = 0; i < mTagSize; i++) {
                outState.putByteArray("TagSavedGameRow" + i, mTagController.saveGameRow(i));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        MenuItem tttResumeGame = menu.findItem(R.id.TTTresumeGame);
        MenuItem tagResumeGame = menu.findItem(R.id.TagResumeGame);
        if(mCurrentGameResId == R.layout.activity_tic_tac_toe)
        {
            tttResumeGame.setVisible(false);
//            menu.removeItem(R.id.TTTresumeGame);
//            tttResumeGame.set
        }
        else
        {
            tagResumeGame.setVisible(false);
//            menu.removeItem(R.id.TagResumeGame);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.TTTresumeGame:
                mCurrentGameResId = R.layout.activity_tic_tac_toe;
                recreate();
                break;
            case R.id.TTTnewGame3x3:
                mCurrentGameResId = R.layout.activity_tic_tac_toe;
                mTTTSize = 3;
                recreate();
                break;
            case R.id.TTTnewGame4x4:
                mCurrentGameResId = R.layout.activity_tic_tac_toe;
                mTTTSize = 4;
                recreate();
                break;
            case R.id.TTTnewGame5x5:
                mCurrentGameResId = R.layout.activity_tic_tac_toe;
                mTTTSize = 5;
                recreate();
                break;
            case R.id.TagResumeGame:
                mCurrentGameResId = R.layout.activity_tag;
                recreate();
                break;
            case R.id.TagNewGame3x3:
                mCurrentGameResId = R.layout.activity_tag;
                mTagSize = 3;
                recreate();
                break;
            case R.id.TagNewGame4x4:
                mCurrentGameResId = R.layout.activity_tag;
                mTagSize = 4;
                recreate();
                break;
            case R.id.TagNewGame5x5:
                mCurrentGameResId = R.layout.activity_tag;
                mTagSize = 5;
                recreate();
                break;

        }

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
                Log.d("TAG", "Clicked view ID = " + cellId);
                mTagController.setMove(cellId);
            }
            else
            {
                mTagController.newGame();
                int cellId = v.getId();
                Log.d("TAG", "ENDED GAME. Clicked view ID = " + cellId);
                //todo:RestartGame
            }
        }
    }
 }
