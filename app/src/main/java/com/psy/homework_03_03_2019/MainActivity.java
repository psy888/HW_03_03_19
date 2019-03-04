package com.psy.homework_03_03_2019;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int mCurrentGameResId = 0;


    //TicTacToe
    TTTController mTTTController;
    TextView tvCurrentTurn;
    GridLayout TTTGameField;
    TicTacToeClick mTicTacToeClick;
    int mTTTSize = 3;
    CheckBox mCbIsAiEnabled;
    boolean isAiEnabled = false;
    //Tag
    TagController mTagController;
    TextView tvMovesCnt;
    GridLayout TagGameField;
    TagClick mTagClick;
    int mTagSize = 3;

    MenuItem tttResumeGame;
    MenuItem tagResumeGame;


    LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!=null)
        {
            mTTTSize = savedInstanceState.getInt("TTTSize", 3);
            isAiEnabled = savedInstanceState.getBoolean("isAiEnabled",true);
            mTagSize = savedInstanceState.getInt("TagSize", 3);
            mCurrentGameResId = savedInstanceState.getInt("CurrentGameResId");

            if(savedInstanceState.getByteArray("TTTSavedGame")!=null)
                mTTTController = new TTTController(this, savedInstanceState.getByteArray("TTTSavedGame"));
            if(savedInstanceState.getByteArray("TagSavedGameRow0") != null)
            {
                int moveCountRestore = savedInstanceState.getInt("TagMoveCount");

                mTagController = new TagController(mTagSize);
                for (int i = 0; i < mTagSize; i++) {
                    mTagController.restoreGameFieldRow(i, savedInstanceState.getByteArray("TagSavedGameRow"+i));
                }
                mTagController.setMoveCount(moveCountRestore);
            }
        }
        else
        {
            mCurrentGameResId = R.layout.activity_tic_tac_toe;
        }

        setContentView(mCurrentGameResId);

        inflater = getLayoutInflater();

        if(mCurrentGameResId == R.layout.activity_tic_tac_toe)
        {
//            invalidateOptionsMenu();
//            Log.d("TAG", "mTTTSize = " + mTTTSize );
            if(mTTTController == null) {
//                mTTTSize = 3;
                mTTTController = new TTTController(this, mTTTSize, TicTacToe.PLAYER_X);
                Log.d("TAG", "mTTTSize = " + mTTTSize );
            }
            TTTGameField = findViewById(R.id.TTTgameField);
            tvCurrentTurn = findViewById(R.id.currentTurn);
            mCbIsAiEnabled = findViewById(R.id.aiEnabled);
            mCbIsAiEnabled.setChecked(isAiEnabled);

            mTicTacToeClick = new TicTacToeClick();

            mTTTController.createGameField(inflater, TTTGameField, mTicTacToeClick);
        }

        if (mCurrentGameResId == R.layout.activity_tag)
        {
            invalidateOptionsMenu();
            TagGameField = findViewById(R.id.TagGameField);
            tvMovesCnt = findViewById(R.id.movesCnt);
            mTagClick = new TagClick();
            if(mTagController == null)
            {
                mTagController = new TagController(this, inflater,mTagSize, tvMovesCnt, TagGameField,mTagClick);
            }
            else {
                mTagController.setContext(this);
                mTagController.setInflater(inflater);
                mTagController.setUIGameField(TagGameField);
                mTagController.setUIMovesCnt(tvMovesCnt);
                mTagController.setClickListener(mTagClick);
            }
            mTagController.createGameField();
        }






    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CurrentGameResId", mCurrentGameResId);
        outState.putInt("TagSize", mTagSize);
        outState.putInt("TTTSize", mTTTSize);
        outState.putBoolean("isAiEnabled",isAiEnabled);

        if(mTTTController != null) {
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
        tttResumeGame = menu.findItem(R.id.TTTresumeGame);
        tagResumeGame = menu.findItem(R.id.TagResumeGame);
        if(mCurrentGameResId == R.layout.activity_tic_tac_toe)
        {
            tttResumeGame.setVisible(false);
            tagResumeGame.setVisible(true);
        }
        else
        {
            tttResumeGame.setVisible(true);
            tagResumeGame.setVisible(false);
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
                mTTTController = null;
                mTTTSize = 3;
                recreate();
                break;
            case R.id.TTTnewGame4x4:
                mCurrentGameResId = R.layout.activity_tic_tac_toe;
                mTTTController = null;
                recreate();
                this.mTTTSize = 4;
                break;
            case R.id.TTTnewGame5x5:
                mCurrentGameResId = R.layout.activity_tic_tac_toe;
                mTTTController = null;
                mTTTSize = 5;
                recreate();
                break;
            case R.id.TagResumeGame:
                mCurrentGameResId = R.layout.activity_tag;
                recreate();
                break;
            case R.id.TagNewGame3x3:
                mCurrentGameResId = R.layout.activity_tag;
                mTagController = null;
                mTagSize = 3;
                recreate();
                break;
            case R.id.TagNewGame4x4:
                mCurrentGameResId = R.layout.activity_tag;
                mTagController = null;
                mTagSize = 4;
                recreate();
                break;
            case R.id.TagNewGame5x5:
                mCurrentGameResId = R.layout.activity_tag;
                mTagController = null;
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
                mTTTController.setMove(cellId, v, tvCurrentTurn, mCbIsAiEnabled.isChecked());


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
                mTagController.newGame();
//
            }
        }
    }
 }
