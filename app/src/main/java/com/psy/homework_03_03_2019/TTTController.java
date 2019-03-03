package com.psy.homework_03_03_2019;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class TTTController {

    private static final String TAG = "TTTController";
    private Context mContext;
    private TicTacToe mGame;
    private TTTAi mAi;
    private int mCurTurn;
    private int  mSize;
    private boolean mIsEnded = false;
    private boolean mIsFirstHuman = true;
    private int moveCnt = 0;
    GridLayout mGameField;


    /**
     * Конструктор
     * @param size - размер игрового поля
     */
    public TTTController(Context context, int size, int turn)
    {
        mContext = context;
        mCurTurn = turn;
        mSize = size;
        mGame = new TicTacToe(mSize);
        mAi = new TTTAi(mGame);
    }
    /**
     * Конструктор
     * @param savedGame - размер игрового поля
     */
    public TTTController(Context context, byte[] savedGame)
    {
        mContext = context;
        mCurTurn = savedGame[0];
        mSize = (int)Math.sqrt((double) savedGame.length-1);
        mGame = new TicTacToe(savedGame);
    }

    /**
     * Создание игрового поля из модели
     * @param inflater - layoutInflater
     * @param gameField - Родительский элемент игрового поля
     * @param clickListener - setOnClickListener на каждый элемент игрового поля
     */
    public void createGameField(LayoutInflater inflater,GridLayout gameField, MainActivity.TicTacToeClick clickListener)
    {
        mGameField = gameField;
        gameField.setRowCount(mSize);
        gameField.setColumnCount(mSize);
        for (int i = 0; i < mSize*mSize; i++)
        {
            TextView cell = (TextView) inflater.inflate(R.layout.cell, gameField, false);
            int cellSizePx = 0;
            switch (mSize)
            {
                case 3:
                    cellSizePx = mContext.getResources().getDimensionPixelSize(R.dimen.row_height_threeCell);
                    break;
                case 4:
                    cellSizePx = mContext.getResources().getDimensionPixelSize(R.dimen.row_height_fourCell);
                    break;
                case 5:
                    cellSizePx = mContext.getResources().getDimensionPixelSize(R.dimen.row_height_fiveCell);
                    break;
            }
            cell.setHeight(cellSizePx);
            cell.setWidth(cellSizePx);
            cell.setId(10000+i);
            cell.setText(getPlayerSign(mGame.getGameField()[i]));
            gameField.addView(cell);
            cell.setOnClickListener(clickListener);
        }
    }

    /**
     * очистка UI игрового поля
     * @param gameField - Родительский элемент игрового поля
     */
    private void clearUIGameField(GridLayout gameField)
    {
        int cnt = gameField.getChildCount();
        for (int i = 0; i < cnt; i++) {
            TextView tv = (TextView) gameField.getChildAt(i);
            tv.setText(getPlayerSign(mGame.getGameField()[i]));
        }
    }

    /**
     * Перезапус игры
     * @param gameField - Родительский элемент игрового поля
     * @param currentTurn - Текстовое поле отображения текущего игрока
     */
    void restartGame(GridLayout gameField, TextView currentTurn)
    {
        //Clear model
        mGame.clearGameField();
        //Clear UI
        clearUIGameField(gameField);
        //ResetTurn
        mCurTurn = TicTacToe.PLAYER_X;
        //Reset EndGame flag
        mIsEnded = false;
        //Update UI current turn
        currentTurn.setText(getPlayerSign(mCurTurn));
        if(mIsFirstHuman){
            mIsFirstHuman = false;
        }
        else
        {
            mIsFirstHuman = true;
        }
        if(!mIsFirstHuman)
        {
            setAiMove((TextView) gameField.getChildAt(0),currentTurn);
        }
    }

    /**
     * Получить Символ игрока
     * @param turn - Числовая константа игрока PLAYER X = 1; PLAYER O = -1;
     * @return Х или О
     */
    private String getPlayerSign(int turn)
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

    /**
     * Сделать ход
     * @param cellId - ячейка игрового поля
     * @param currentTurn - текущий игрок
     */
    void setMove(int cellId, View currentCell, TextView currentTurn, boolean isAiEnabled)
    {


        TextView curCell = null;
        try
        {
            curCell = (TextView) currentCell;
        }
        catch (ClassCastException e)
        {
            Log.e(TAG, e.getMessage());
        }



        boolean isSuccess = mGame.setMove(cellId, mCurTurn);

        if(isSuccess&&curCell!=null)
        {
            curCell.setText(getPlayerSign(mCurTurn)); // Update UI of clicked cell
            if (mGame.checkResult() == 0) {
                mCurTurn = -mCurTurn;
                currentTurn.setText(getPlayerSign(mCurTurn));
            }
            else if(mGame.checkResult() == 1) {
                mIsEnded = true;
                String winMsg =  mContext.getResources().getString(R.string.win_message) +" "+ getPlayerSign(mCurTurn);
                currentTurn.setText(winMsg);
            }
            else if(mGame.checkResult() == -1) {
                mIsEnded = true;
                String winMsg =  mContext.getResources().getString(R.string.draw_message);
                currentTurn.setText(winMsg);
            }

            if(isAiEnabled&&!mIsEnded)
            {
                setAiMove(curCell, currentTurn);

            }
        }


        Log.d("TAAAAG", "MOve cnt = " + moveCnt + ", mIsFirstHuman = " + mIsFirstHuman);
    }

    void setAiMove(TextView currentCell, TextView currentTurn)
    {

        int pos = mAi.setAiMove(mCurTurn);
        GridLayout gameField = (GridLayout) currentCell.getParent();
        TextView targetCell = (TextView) gameField.getChildAt(pos);
        targetCell.setText(getPlayerSign(mCurTurn));
        //            mCurTurn = -mCurTurn;
        if (mGame.checkResult() == 0) {
            mCurTurn = -mCurTurn;
            currentTurn.setText(getPlayerSign(mCurTurn));
        }
        else if(mGame.checkResult() == 1) {
            mIsEnded = true;
            String winMsg =  mContext.getResources().getString(R.string.win_message) +" "+ getPlayerSign(mCurTurn);
            currentTurn.setText(winMsg);
        }
        else if(mGame.checkResult() == -1) {
            mIsEnded = true;
            String winMsg =  mContext.getResources().getString(R.string.draw_message);
            currentTurn.setText(winMsg);
        }

    }

    boolean isEnded()
    {
        return mIsEnded;
    }

    /**
     * Сохранить текущее состояние игрового поля в байтовый массив
     * @return игровое поле в виде byteArray
     */
    byte[] saveGame()
    {
        byte[] curGameField = new byte[mGame.getGameField().length + 1];
        curGameField[0] = (byte) mCurTurn;
        for (int i = 1; i < mGame.getGameField().length; i++)
        {
            curGameField[i] = (byte) mGame.getGameField()[i-1];
        }
        return curGameField;
    }

    TicTacToe getGameForAi()
    {
        return mGame;
    }
}
