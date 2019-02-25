package com.psy.homework_03_03_2019;
//Крестики - нолики
public class TicTacToe
{
    private int[] mGameField;
    private int mSize;
    public static final int PLAYER_X = 1;
    public static final int PLAYER_O = -1;

    public int[] getGameField() {
        return mGameField;
    }

    public void clearGameField() {
        mGameField = new int[mSize*mSize];
    }

    public TicTacToe(int size)
    {
        this.mSize = size;
        this.mGameField = new int[size*size];
    }

    public boolean setMove(int position, int player)
    {
        if(this.mGameField[position]==0)
        {
            this.mGameField[position] = player;
            return true;
        }
        else
        {
            return false;
        }
    }

    public int checkResult(int player)
    {
        int resultCnt = 0;
        //rows check
        for (int i = 0; i < mSize*mSize; i+=mSize)
        {
            //row
            for (int j = 0; j < mSize; j++)
            {
                //pos
                resultCnt += mGameField[i+j];
            }
            if(Math.abs(resultCnt) == mSize)
            {
                return 1;
            }
            resultCnt = 0;
        }
        //columns check
        for (int i = 0; i < mSize; i++)
        {
            //column
            for (int j = 0; j < mSize*mSize; j+=mSize)
            {
                //pos
                resultCnt += mGameField[i+j];
            }
            if(Math.abs(resultCnt) == mSize)
            {
                return 1;
            }
            resultCnt = 0;

        }

        //x lines check
        for (int i = 0; i < mSize*mSize; i=i+mSize+1)
        {
            resultCnt += mGameField[i];
        }
        if(Math.abs(resultCnt) == mSize)
        {
            return 1;
        }
        resultCnt = 0;
        //x lines2 check
        for (int i = (mSize*mSize)-mSize; i > 0; i-=(mSize-1))
        {
            resultCnt += mGameField[i];
        }
        if(Math.abs(resultCnt) == mSize)
        {
            return 1;
        }
        resultCnt = 0;
//todo: if won reset game field
        return 0;
    }
}
