package com.psy.homework_03_03_2019;

import android.util.Log;


//Пятнашки
public class Tag
{
    private int mSize; // размер грани игрового поля
    private int[][] mGameField; //игровое поле

    public Tag(int size)
    {
        mSize = size;
        mGameField = new int[mSize][mSize];
        fillGameField();
        shuffleGameField();
    }

    /**
     * Заполнение игрового поля по порядку значениями от 0 до ..
     */
    private void fillGameField()
    {
        int value = 0;
        //Rows
        for (int i = 0; i < mSize; i++) {
            //Columns
            for (int j = 0; j < mSize; j++) {
                mGameField[i][j] = value;
                value++;
            }

        }
    }


    /**
     * Перемешать значения на игровом поле
     */
    private void shuffleGameField()
    {
        //rows
        for (int i = 0; i < mSize; i++) {
            //columns
            for (int j = 0; j < mSize; j++) {
                int random1 = (int) (Math.random() * mSize);
                int random2 =(int) (Math.random() * mSize);
                swapCells(i,j,random1,random2);
                Log.d("Tag class", "random1 = " + random1 + ", random2 = " + random2);
            }
        }
    }
    /**
     * Обмен значениями в ячейках игрового поля
     * @param r1 - ряд первого значения
     * @param c1 - колнка первого значения
     * @param r2 - ряд второго значения
     * @param c2 - колнка второго значения
     */
    void swapCells (int r1, int c1, int r2, int c2)
    {
        int tmp = mGameField[r1][c1];
        mGameField[r1][c1] = mGameField[r2][c2];
        mGameField[r2][c2] = tmp;
    }

    /**
     * Проверка результата игры
     * @return true - победа, false - продолжаем
     */
    boolean checkResult()
    {
        int val = 1;
        //rows
        for (int i = 0; i < mSize; i++) {
            //columns
            for (int j = 0; j < mSize; j++) {
                if(mGameField[i][j] == val)
                {
                    val++;
                }
                else
                {
                    return mSize * mSize - 1 == val;
                }
            }

        }
        return false;
    }
/*
    /**
     * Сделать ход
     * @param rowSrc - ряд текущей
     * @param columnSrc - колонка текущей
     * @return - true успешно / false нет свободной ячейки рядом

    boolean setMove(int rowSrc, int columnSrc)
    {
        int[] rc = checkDirrection(rowSrc,columnSrc);
        if(rc[0]!=-1)
        {
            swapCells(rowSrc,columnSrc,rc[0],rc[1]);
            return true;
        }
        return false;
    }
    */
    /**
     * Сделать ход
     * @param srcRow - ряд текущей
     * @param srcColumn - колонка текущей
     * @return - int[]{srcRow,srcColumn,dstRow,dstColumn}координыты источника и назначения/ null - нет свободной ячейки рядом
     */
    int[] setMove(int srcRow, int srcColumn)
    {
        int[] dstCoordinates = checkDirrection(srcRow,srcColumn);
        if(dstCoordinates[0]!=-1)
        {
            swapCells(srcRow,srcColumn,dstCoordinates[0],dstCoordinates[1]);
            return new int[]{srcRow,srcColumn,dstCoordinates[0],dstCoordinates[1]};
        }
        return null;
    }

    /**
     * Проверка наличия свободной(0) ячейки по соседству
     * @param rowSrc - ряд текущей
     * @param columnSrc - колонка текущей
     * @return - координаты свободной ячейки или int[]{-1,-1}
     */
    int[] checkDirrection(int rowSrc, int columnSrc)
    {
        int rowDst = -1;
        int columnDst = -1;
        try
        {
            if(mGameField[rowSrc-1][columnSrc] == 0)
            {
                rowDst = rowSrc-1;
                columnDst = columnSrc;
            }
        }
        catch(ArrayIndexOutOfBoundsException e){/*Ignore*/}
        try
        {
            if(mGameField[rowSrc+1][columnSrc] == 0)
            {
                rowDst = rowSrc+1;
                columnDst = columnSrc;
            }
        }
        catch(ArrayIndexOutOfBoundsException e){/*Ignore*/}
        try
        {
            if(mGameField[rowSrc][columnSrc-1] == 0)
            {
                rowDst = rowSrc;
                columnDst = columnSrc-1;
            }
        }
        catch(ArrayIndexOutOfBoundsException e){/*Ignore*/}
        try
        {
            if(mGameField[rowSrc][columnSrc+1] == 0)
            {
                rowDst = rowSrc;
                columnDst = columnSrc+1;
            }
        }
        catch(ArrayIndexOutOfBoundsException e){/*Ignore*/}

        return new int[]{rowDst,columnDst};
    }

    public int[][] getGameField() {
        return mGameField;
    }

    public void setGameField(int[][] gameField) {
        mGameField = gameField;
    }
}
