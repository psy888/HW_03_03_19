package com.psy.homework_03_03_2019;

public class TTTAi
{
    TicTacToe mGame;
    int aiSign;
    int mSize;

    public TTTAi (TicTacToe game)
    {
        mGame = game;
        aiSign = mGame.PLAYER_O;
        mSize = (int)Math.sqrt(mGame.getGameField().length);
    }

    boolean setAiMove()
    {
        int position = getNextPosition();
        mGame.setMove(position, aiSign);
        return true;
    }

    int getNextPosition()
    {
        //todo: create ranged list of next position based on check result function
        //todo: same method to get defence if there tow or more signs in the row
        int resultCnt = 0;
        /*
        //rows check
        for (int i = 0; i < mSize*mSize; i+=mSize)
        {
            //row
            for (int j = 0; j < mSize; j++)
            {
                //pos
                resultCnt += mGameField[i+j];
            }
            if(Math.abs(resultCnt) > 1// mSize)
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
        resultCnt =0;
        //draw check
        for (int i = 0; i < mSize*mSize; i++) {
            resultCnt+=Math.abs(mGameField[i]);
        }
        if (resultCnt == mSize*mSize)
        {
            return -1;
        }*/
        return 0;
    }
}
