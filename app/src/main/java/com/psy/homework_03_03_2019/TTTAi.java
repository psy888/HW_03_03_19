package com.psy.homework_03_03_2019;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;


public class TTTAi implements Serializable
{
    TicTacToe mGame;
    int aiSign;
    int mSize;
    ArrayList<int[]> mPossibilitys;

    public TTTAi (TicTacToe game)
    {
        mGame = game;
        aiSign = mGame.PLAYER_O;
        mSize = (int)Math.sqrt(mGame.getGameField().length);
    }

    int setAiMove(int curSign)
    {
        int position = 0;
        do {
            position = getNextPosition();
        } while (position == -999);

        mGame.setMove(position, curSign);
        return position;
    }

    @TargetApi(Build.VERSION_CODES.N)
    int getNextPosition()
    {
        //todo: create ranged list of next position based on check result function
        //todo: same method to get defence if there tow or more signs in the row
        //очистка не актульных возможностей
        mPossibilitys = new ArrayList<>();
        rowsCheck();
        columnsCheck();
        xline1Check();
        xline2Check();
        for (int i = 0; i < mPossibilitys.size(); i++) {

//            Log.d("POSSIBILITIES", "UNSORTED   N "+ i + "   Weight : " + mPossibilitys.get(i)[0]+ "   IndexDst : " + mPossibilitys.get(i)[1]);
//            Log.d("POSSIBILITIES", "\n\n\n\n--------------------------------------------------------------------------------");
        }

        mPossibilitys.sort(sortPos);
        for (int i = 0; i < mPossibilitys.size(); i++) {

            Log.d("POSSIBILITIES", "SORTED   N "+ i + "   Weight : " + mPossibilitys.get(i)[0]+ "   IndexDst : " + mPossibilitys.get(i)[1]);
            Log.d("POSSIBILITIES", "\n\n\n\n--------------------------------------------------------------------------------");

        }
        int[] bestPossibility = mPossibilitys.get(0); //лучший ход для ии
        int[] bestDefence = mPossibilitys.get(mPossibilitys.size()-1); // следующий лучший ход для человека

        if(Math.abs(bestPossibility[0])<bestDefence[0])
        {
            Log.d("POSSIBILITIES", "\n\nDEFENCE!!!!\n\n");

            int cntBestPossibilities = 0;
            for (int[] pos: mPossibilitys) {
                if(pos[0]==bestDefence[0])
                {
                    cntBestPossibilities++;
                }
            }
            int rand = (cntBestPossibilities>1)?(mPossibilitys.size() - 1) - (int) (Math.random()*cntBestPossibilities):(mPossibilitys.size() - 1);
//            int rand = (mPossibilitys.size() - 1) - (int) (Math.random()*cntBestPossibilities);

            return mPossibilitys.get(rand)[1];
//            return bestDefence[1];
        }
        else
        {
            Log.d("POSSIBILITIES", "\n\nPOSSIBILITY!!!!\n\n");
            int cntBestPossibilities = 0;
            for (int[] pos: mPossibilitys) {
                if(pos[0]==bestPossibility[0])
                {
                    cntBestPossibilities++;
                }
            }
            int rand = (cntBestPossibilities>1)?(int) (Math.random()*cntBestPossibilities):0;

            return mPossibilitys.get(rand)[1];
//            return bestPossibility[1];
        }

    }

    void rowsCheck() {
        //rows check
        for (int i = 0; i < mSize * mSize; i += mSize) {
            int weightHuman = 0; // знаки противника в ряду
            int weightAi = 0; // мои знаки в ряду
            int position = -999;
            //row
            for (int j = 0; j < mSize; j++) {
                switch (mGame.getGameField()[i + j]) {
                    case 0:
                        position = i + j; // запомнить пустую ячейку в ряду если есть
                        break;
                    case 1:
                        ++weightHuman;
                        break;
                    case -1:
                        --weightAi;
                        break;
                }
            }
            addPossibilities(weightHuman,weightAi,position);
        }
    }
    void columnsCheck() {
        //columns check
        for (int i = 0; i < mSize; i++)
        {
            int weightHuman = 0; // знаки противника в ряду
            int weightAi = 0; // мои знаки в ряду
            int position = -999;
            //column
            for (int j = 0; j < mSize*mSize; j+=mSize)
            {
                switch (mGame.getGameField()[i + j]) {
                    case 0:
                        position = i + j; // запомнить пустую ячейку в ряду если есть
                        break;
                    case 1:
                        ++weightHuman;
                        break;
                    case -1:
                        --weightAi;
                        break;
                }
            }
            addPossibilities(weightHuman,weightAi,position);
        }
    }
    void xline1Check() {

        int weightHuman = 0; // знаки противника в ряду
        int weightAi = 0; // мои знаки в ряду
        int position = -999;
        //x line
        for (int i = 0; i < mSize*mSize; i=i+mSize+1)
        {
            switch (mGame.getGameField()[i]) {
                case 0:
                    position = i; // запомнить пустую ячейку в ряду если есть
                    break;
                case 1:
                    ++weightHuman;
                    break;
                case -1:
                    --weightAi;
                    break;
            }
        }
        addPossibilities(weightHuman,weightAi,position);
    }

    void xline2Check() {

        int weightHuman = 0; // знаки противника в ряду
        int weightAi = 0; // мои знаки в ряду
        int position = -999;
        //x line
        for (int i = (mSize*mSize)-mSize; i > 0; i-=(mSize-1))
        {
            switch (mGame.getGameField()[i]) {
                case 0:
                    position = i; // запомнить пустую ячейку в ряду если есть
                    break;
                case 1:
                    ++weightHuman;
                    break;
                case -1:
                    --weightAi;
                    break;
            }
        }
        addPossibilities(weightHuman,weightAi,position);
    }

    /**
     * добавить возможный ход в ArrayList
     * @param weightHuman - положительный вес (кол во своих знаков в линии)
     * @param weightAi - отрицательный  вес (кол во знаков противника в линии)
     * @param position - свободная позиция в линии
     */
    void addPossibilities(int weightHuman, int weightAi, int position)
    {
        if(position!= -999&&weightHuman==0) // () () (0)
        {
            Log.d("POSSIBILITY" , "() () (0)");
            mPossibilitys.add(new int[]{-50, position});//
        }
        if(position!= -999&& weightHuman == (mSize - 1)&&weightAi==0) // X X (0)
        {
            Log.d("POSSIBILITY" , "X X (0)");
            mPossibilitys.add(new int[]{99, position});// противодействие противнику не дать выиграть
        }
        if (position != -999 && Math.abs(weightAi) == (mSize - 1) && weightHuman == 0) // 00 (0)
        {
            Log.d("POSSIBILITY" , "  0 0 (0)");

            mPossibilitys.add(new int[]{-100, position}); // 100% возможность выиграть
        }

        if (position != -999)
        {
            mPossibilitys.add(new int[]{weightAi-weightHuman, position}); // все оставшиеся ходы
        }

    }

    Comparator <int[]> sortPos = new Comparator<int[]>() {
        @Override
        public int compare(int[] o1, int[] o2) {
            if(o1[0] > o2[0])
                return 1;
            if(o1[0] < o2[0])
                return -1;
//            if(o1[0] == o2[0])
                return 0;
        }
    };
}
