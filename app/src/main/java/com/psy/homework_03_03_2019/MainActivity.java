package com.psy.homework_03_03_2019;

import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private int mCurrentGameResId = 0;
    private Fragment mCurGameFragment;

    FragmentManager mFragmentManager = getSupportFragmentManager();
    FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

    TTTFragment mTTTFragment = new TTTFragment();
    TagFragment mTagFragment = new TagFragment();


    MenuItem tttResumeGame;
    MenuItem tagResumeGame;


//    LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if(savedInstanceState!=null)
        {
            mCurrentGameResId = savedInstanceState.getInt("CurrentGameResId");
        }
        else
        {
            mCurrentGameResId = R.layout.activity_tic_tac_toe;
//            mCurrentGameResId = R.layout.activity_tag;
            addFragment(mCurrentGameResId);
        }

//        inflater = getLayoutInflater();


    }



    void addFragment(int gameId)
    {

        mFragmentManager = getSupportFragmentManager();
//        mFragmentTransaction = mFragmentManager.beginTransaction();
        if(mFragmentManager.getFragments().size()>0) {
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.remove(mFragmentManager.findFragmentByTag("game"));
            mFragmentTransaction.commit();
        }
        switch (gameId)
        {
            case R.layout.activity_tic_tac_toe:
                mCurGameFragment = mTTTFragment;
                break;
            case R.layout.activity_tag:
                mCurGameFragment = mTagFragment;
                break;
        }
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.mainLayout, mCurGameFragment, "game").commit();
/*
        if(mFragmentManager.getFragments().size()==0)
        {
            mFragmentTransaction.add(R.id.mainLayout, mCurGameFragment, "game");
        }
        else
        {
            mFragmentTransaction.replace(R.id.mainLayout, mCurGameFragment,"game");
        }

        mFragmentTransaction.commit();*/
//        mFragmentTransaction.addToBackStack(null);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CurrentGameResId", mCurrentGameResId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        /*
        tttResumeGame = menu.findItem(R.id.TTTresumeGame);
        tagResumeGame = menu.findItem(R.id.TagResumeGame);
//        if(mCurrentGameResId == R.layout.activity_tic_tac_toe)
        if(mCurGameFragment.equals(mTTTFragment))
        {
            tttResumeGame.setVisible(false);
            tagResumeGame.setVisible(true);
        }
        else
        {
            tttResumeGame.setVisible(true);
            tagResumeGame.setVisible(false);
        }*/
        return super.onCreateOptionsMenu(menu);
    }

    void startNewGameTTT(int size)
    {
        mCurrentGameResId = R.layout.activity_tic_tac_toe;
        mTTTFragment = new TTTFragment();
        mTTTFragment.mTTTController = null;
        mTTTFragment.mTTTSize = size;
    }
    void startNewGameTag(int size)
    {
        mCurrentGameResId = R.layout.activity_tag;
        mTagFragment.mTagController = null;
        mTagFragment.mTagSize = size;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.TTTresumeGame:
                mCurrentGameResId = R.layout.activity_tic_tac_toe;
                addFragment(mCurrentGameResId);
                break;
            case R.id.TTTnewGame3x3:
                startNewGameTTT(3);
                addFragment(mCurrentGameResId);
                break;
            case R.id.TTTnewGame4x4:
                startNewGameTTT(4);
                addFragment(mCurrentGameResId);
                break;
            case R.id.TTTnewGame5x5:
                startNewGameTTT(5);
                addFragment(mCurrentGameResId);
                break;
            case R.id.TagResumeGame:
                mCurrentGameResId = R.layout.activity_tag;
                addFragment(mCurrentGameResId);
                break;
            case R.id.TagNewGame3x3:
                startNewGameTag(3);
                addFragment(mCurrentGameResId);
                break;
            case R.id.TagNewGame4x4:
                startNewGameTag(4);
                addFragment(mCurrentGameResId);
                break;
            case R.id.TagNewGame5x5:
                startNewGameTag(5);
                addFragment(mCurrentGameResId);
                break;
            case R.id.rotateScreen:
                if(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT||getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                else
                {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Слушатель для "Крестики-нолики"
     */
    /*
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
    }*/

    /**
     * Слушатель для "Пятнашки"
     */
    /*
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
    */
 }
