package com.psy.homework_03_03_2019;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class TagController {

    private Context mContext;
    private Tag mGame;
    private int mMoveCnt = 0;
    private int  mSize;
    private boolean mIsEnded = false;
    private LayoutInflater mInflater;
    private GridLayout mGameField;
    private MainActivity.TagClick mClicklistener;

    public TagController(Context context,int size)
    {
        mContext = context;
        mSize = size;
        mGame = new Tag(size);
    }

    void createGameField(LayoutInflater inflater, GridLayout gameField, MainActivity.TagClick clicklistener)
    {
        mInflater = inflater;
        mGameField = gameField;
        mClicklistener = clicklistener;

        gameField.setRowCount(mSize);
        gameField.setColumnCount(mSize);

        updateUI();
    }


    /**
     * Сделать ход
     * @param cellId - выбоанная ячейка
     */
    void setMove(int cellId)
    {
        String id = ""+cellId;
        int row = Integer.parseInt(String.valueOf(id.charAt(3)));
        int column = Integer.parseInt(String.valueOf(id.charAt(4)));
        int[] coordinates = mGame.setMove(row, column);

        if(coordinates!=null)
        {
            updateUI();
            mIsEnded = mGame.checkResult();
            if(!mIsEnded)
            {

            }
            else
            {


            }
        }

    }

    /**
     * Обновдение игрового поля
     */
    void updateUI()
    {
        mGameField.removeAllViewsInLayout();
        //rows
        for (int j = 0; j < mSize; j++)
        {
            //columns
            for (int k = 0; k < mSize; k++)
            {
                int cellId = 200;
                int cellContent = mGame.getGameField()[j][k];
                TextView cell = (TextView) mInflater.inflate(R.layout.cell, mGameField, false);
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
                cellId = Integer.parseInt(cellId + "" + j + "" + k);
                cell.setId(cellId);
                Log.d("TAG", "CellID = " + cellId );
                if(cellContent!=0)
                {
                    cell.setText("" +cellContent);
                }
                else
                {
                    cell.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.empty_bg));
                }
                mGameField.addView(cell);
                cell.setOnClickListener(mClicklistener);
            }
        }
        /*
//        int srcResId = Integer.parseInt("200"+coordinates[0]+""+coordinates[1]);
        int dstResId = Integer.parseInt("200"+coordinates[2]+""+coordinates[3]);
        View src = srcView;
        GridLayout parent = (GridLayout) src.getParent();
        int srcIndex = parent.indexOfChild(src);
        int dstIndex = 0;
        View dst = null;
        for (int i = 0; i < mSize*mSize; i++) {
            int childId = parent.getChildAt(i).getId();
            if(childId==dstResId)
            {
                dstIndex=i;
                dst = parent.getChildAt(dstIndex);
            }
        }
        if(dst!=null) {
            parent.removeViewAt(srcIndex);
            parent.removeViewAt(dstIndex);

            parent.addView(src, dstIndex);
            parent.addView(dst, srcIndex);
        }
*/
    }

    boolean isEnded()
    {
        return mIsEnded;
    }
}
