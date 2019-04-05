package com.psy.homework_03_03_2019;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.TextView;

import java.io.Serializable;

public class TTTFragment extends Fragment implements Serializable {
    //TicTacToe
    TTTController mTTTController;
    TextView tvCurrentTurn;
    GridLayout TTTGameField;
    TicTacToeClick mTicTacToeClick;
    int mTTTSize = 3;
    CheckBox mCbIsAiEnabled;
    boolean isAiEnabled = false;
    boolean isInited = false;

    public TTTFragment(){
        if(getArguments()!=null)
        {

        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(!isInited) {
            if (savedInstanceState != null && savedInstanceState.getByteArray("TTTSavedGame") != null) {
                mTTTSize = savedInstanceState.getInt("TTTSize", 3);
                isAiEnabled = savedInstanceState.getBoolean("isAiEnabled", true);

                if (savedInstanceState.getByteArray("TTTSavedGame") != null) {
                    mTTTController = new TTTController(this.getContext(), savedInstanceState.getByteArray("TTTSavedGame"));
                    Log.d("TAG", "State is restored!");
                    Log.d("TAG", "savedInstanceState mTTTController hashCode" + mTTTController.hashCode());
                }

            } else {
                mTTTController = new TTTController(this, mTTTSize, TicTacToe.PLAYER_X);
                Log.d("TAG", "mTTTSize = " + mTTTSize);
                Log.d("TAG", "mTTTController hashCode" + mTTTController.hashCode());
            }
        }
        isInited = true;
        View v = inflater.inflate(R.layout.activity_tic_tac_toe,container, false);

//        if(mTTTController == null) {
//        }
        TTTGameField = v.findViewById(R.id.TTTgameField);
        tvCurrentTurn = v.findViewById(R.id.currentTurn);
        mCbIsAiEnabled = v.findViewById(R.id.aiEnabled);
        mCbIsAiEnabled.setChecked(isAiEnabled);

        mTicTacToeClick = new TicTacToeClick();

        mTTTController.createGameField(inflater, TTTGameField, mTicTacToeClick);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("TTTSize", mTTTSize);
        outState.putBoolean("isAiEnabled",isAiEnabled);
        if(mTTTController != null) {
            outState.putByteArray("TTTSavedGame", mTTTController.saveGame());
            Log.d("TAG", "State is saved!");
            Log.d("TAG", "onSaveInstanceState mTTTController hashCode" + mTTTController.hashCode());
        }

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
}
