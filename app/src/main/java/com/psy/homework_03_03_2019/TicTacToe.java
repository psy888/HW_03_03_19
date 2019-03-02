package com.psy.homework_03_03_2019;
//Крестики - нолики
public class TicTacToe
{
    private int[] mGameField; // Игровое поле
    private int mSize; //размер игрового поля mSize x mSize
    static final int PLAYER_X = 1;
    static final int PLAYER_O = -1;

    /**
     * создание новой игры
     * @param size - размер одной грани игрового поля
     */
    public TicTacToe(int size)
    {
        this.mSize = size;
        this.mGameField = new int[size*size];
    }

    /**
     * Восстановление сохраненной игры
     * @param gameField - сохраненное
     */
    public TicTacToe(byte[] gameField)
    {
        this.mSize = (int)Math.sqrt((double) gameField.length -1);
        this.mGameField = new int[gameField.length - 1];
        for (int i = 0; i < gameField.length-1; i++) {
            mGameField[i] = gameField[i+1];
        }
    }

    public int[] getGameField() {
        return mGameField;
    }



    public void clearGameField() {
        mGameField = new int[mSize*mSize];
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


    /**
     * проверка выиграша
     * @return 0 - ничего, 1 - победа, -1 ничья
     */
    public int checkResult()
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
        resultCnt =0;
        //draw check
        for (int i = 0; i < mSize*mSize; i++) {
            resultCnt+=Math.abs(mGameField[i]);
        }
        if (resultCnt == mSize*mSize)
        {
            return -1;
        }
        return 0;
    }
}
