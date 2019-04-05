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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if(savedInstanceState!=null)
        {
            mCurrentGameResId = savedInstanceState.getInt("CurrentGameResId");
//            if(savedInstanceState.getSerializable("tttFragment")!=null)
                mTTTFragment = (TTTFragment) savedInstanceState.getSerializable("tttFragment");
//            if(savedInstanceState.getSerializable("tagFragment")!=null)
                mTagFragment = (TagFragment) savedInstanceState.getSerializable("tagFragment");

            /*
            if(getSupportFragmentManager().getFragment(savedInstanceState, "tttFragment")!=null)
                mTTTFragment = (TTTFragment) getSupportFragmentManager().getFragment(savedInstanceState, "tttFragment");
            if(getSupportFragmentManager().getFragment(savedInstanceState, "tagFragment") !=null)
                mTagFragment = (TagFragment) getSupportFragmentManager().getFragment(savedInstanceState, "tagFragment");
            mTTTFragment.setArguments(savedInstanceState.getBundle("tttSaved"));
            */
        }
        else
        {
            mCurrentGameResId = R.layout.activity_tic_tac_toe;
            addFragment(mCurrentGameResId);
        }

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

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CurrentGameResId", mCurrentGameResId);
//        if(!mTTTFragment.isAdded())
            outState.putSerializable("tttFragment", mTTTFragment);
//        if(!mTagFragment.isAdded())
            outState.putSerializable("tagFragment", mTagFragment);

        /*
        if(mTTTFragment.isAdded())
            getSupportFragmentManager().putFragment(outState, "tttFragment", mTTTFragment);


        if(mTagFragment.isAdded())
            getSupportFragmentManager().putFragment(outState,"tagFragment", mTagFragment);

        outState.putBundle("tttSaved",mTTTFragment.getArguments());
*/
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

 }
