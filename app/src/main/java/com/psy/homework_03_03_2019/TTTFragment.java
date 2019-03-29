package com.psy.homework_03_03_2019;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.TextView;

public class TTTFragment extends Fragment {
    //TicTacToe
    TTTController mTTTController;
    TextView tvCurrentTurn;
    GridLayout TTTGameField;
    TicTacToeClick mTicTacToeClick;
    int mTTTSize = 3;
    CheckBox mCbIsAiEnabled;
    boolean isAiEnabled = false;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(savedInstanceState!=null)
        {
            mTTTSize = savedInstanceState.getInt("TTTSize", 3);
            isAiEnabled = savedInstanceState.getBoolean("isAiEnabled",true);

            if(savedInstanceState.getByteArray("TTTSavedGame")!=null)
                mTTTController = new TTTController(this.getContext(), savedInstanceState.getByteArray("TTTSavedGame"));
        }
        View v = inflater.inflate(R.layout.activity_tic_tac_toe,container);

        if(mTTTController == null) {
//                mTTTSize = 3;
            mTTTController = new TTTController(this, mTTTSize, TicTacToe.PLAYER_X);
            Log.d("TAG", "mTTTSize = " + mTTTSize );
        }
        TTTGameField = v.findViewById(R.id.TTTgameField);
        tvCurrentTurn = v.findViewById(R.id.currentTurn);
        mCbIsAiEnabled = v.findViewById(R.id.aiEnabled);
        mCbIsAiEnabled.setChecked(isAiEnabled);

        mTicTacToeClick = new TicTacToeClick();

        mTTTController.createGameField(inflater, TTTGameField, mTicTacToeClick);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("TTTSize", mTTTSize);
        outState.putBoolean("isAiEnabled",isAiEnabled);

        if(mTTTController != null) {
            outState.putByteArray("TTTSavedGame", mTTTController.saveGame());
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
