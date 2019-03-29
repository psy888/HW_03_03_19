package com.psy.homework_03_03_2019;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

public class TagController {

    private Context mContext;
    private Tag mGame;
    private int mMoveCnt = 0;
    private int mSize;
    private boolean mIsEnded = false;
    private LayoutInflater mInflater;
    private GridLayout mGameField;
    private TextView mTvMovesCnt;
    private TagFragment.TagClick mClicklistener;

    public TagController(TagFragment context, LayoutInflater inflater,int size, TextView tvMovesCnt, GridLayout TagGameField,TagFragment.TagClick clicklistener)
    {
        mContext = context.getContext();
        mInflater = inflater;
        mClicklistener = clicklistener;
        mSize = size;
        mGame = new Tag(size);
        mTvMovesCnt = tvMovesCnt;
        mGameField = TagGameField;
    }
    public TagController(int size)
    {
        mSize = size;
        mGame = new Tag(size);
    }

    /**
     * Создание игрового поля
     */
    void createGameField()
    {
        mGameField.setRowCount(mSize);
        mGameField.setColumnCount(mSize);

        updateUI();
    }
    void newGame()
    {
        mGame = new Tag(mSize);
        mMoveCnt = 0;
        mIsEnded = false;
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
            Log.d("TAG", "COORDINATES = " + coordinates.toString());
            mMoveCnt++;
            mIsEnded = mGame.checkResult();
            if(mIsEnded)
            {
                mTvMovesCnt.setText(mMoveCnt + ", " + mContext.getResources().getString(R.string.endgame_message));
            }
            else
            {
                mTvMovesCnt.setText(String.valueOf(mMoveCnt));
            }
//            int[]{srcRow,srcColumn,dstCoordinates[0],dstCoordinates[1]};
//            updateUI();
            swapCells(coordinates);
        }
        else
        {
            Log.d("TAG", "COORDINATES = NULL");
        }

    }

    /**
     * Обновдение игрового поля
     */
    void updateUI()
    {
        mGameField.removeAllViewsInLayout();
        if(!mIsEnded) {
            mTvMovesCnt.setText(String.valueOf(mMoveCnt));
        }
        //rows
        for (int j = 0; j < mSize; j++)
        {
            //columns
            for (int k = 0; k < mSize; k++)
            {
                int cellId = 200;
                int cellContent = mGame.getGameField()[j][k];
                TextView cell = (TextView) mInflater.inflate(R.layout.cell, mGameField, false);
                int cellSizePx = getCellSize();

                cell.setHeight(cellSizePx);
                cell.setWidth(cellSizePx);
                cellId = Integer.parseInt(cellId + "" + j + "" + k);
                cell.setId(cellId);
//                Log.d("TAG", "CellID = " + cellId );
                if(cellContent!=0)
                {
                    cell.setText("" +cellContent);
                }
//                else
//                {
//                    cell.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.empty_bg));
//                }
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
    void swapCells(int[] coordinates)
    {
        //            int[]{srcRow,srcColumn,dstCoordinates[0],dstCoordinates[1]};
        int srcIndex = getIndexInParent(coordinates[0],coordinates[1]);
        int dstIndex = getIndexInParent(coordinates[2],coordinates[3]);

        TextView src = (TextView) mGameField.getChildAt(srcIndex);
        TextView dst = (TextView) mGameField.getChildAt(dstIndex);
//        int srcId = src.getId();
//        int dstId = dst.getId();

        String srcTxt = String.valueOf(mGame.getGameField()[coordinates[0]][coordinates[1]]);
        srcTxt = (srcTxt.contentEquals("0"))?"":srcTxt;
        src.setText(srcTxt);
//        src.setId(dstId);
        Log.d("TAG", "SRC has on clickListener? = " +src.hasOnClickListeners());
        String dstTxt = String.valueOf(mGame.getGameField()[coordinates[2]][coordinates[3]]);
        dstTxt = (dstTxt.contentEquals("0"))?"":dstTxt;
        dst.setText(dstTxt);
//        dst.setId(srcId);
        Log.d("TAG", "DST has on clickListener? = " +dst.hasOnClickListeners());

        /*
        TextView newSrc = (TextView) mInflater.inflate(mContext.getResources().getLayout(R.layout.cell), mGameField, false);
        int newSrcCellId = Integer.parseInt(200 + "" + coordinates[0] + "" + coordinates[1]);
        newSrc.setId(newSrcCellId);
        newSrc.setWidth(getCellSize());
        newSrc.setHeight(getCellSize());
        String newSrcTxt = String.valueOf(mGame.getGameField()[coordinates[0]][coordinates[1]]);
        newSrcTxt = (newSrcTxt.contentEquals("0"))?"":newSrcTxt;
        newSrc.setText(newSrcTxt);

        TextView newDst = (TextView) mInflater.inflate(mContext.getResources().getLayout(R.layout.cell), mGameField, false);
        int newDstCellId = Integer.parseInt(200 + "" + coordinates[2] + "" + coordinates[3]);
        newDst.setId(newDstCellId);
        newDst.setWidth(getCellSize());
        newDst.setHeight(getCellSize());
        String newDstTxt = String.valueOf(mGame.getGameField()[coordinates[2]][coordinates[3]]);
        newDstTxt = (newDstTxt.contentEquals("0"))?"":newDstTxt;
        newDst.setText(newDstTxt);

        mGameField.clearFocus();
        src.clearFocus();
        mGameField.removeViewAt(srcIndex);
        mGameField.addView(newDst, dstIndex);
        dst.clearFocus();
        mGameField.removeViewAt(dstIndex);
        mGameField.addView(newSrc, srcIndex);
        newSrc.setOnClickListener(mClicklistener);
        newDst.setOnClickListener(mClicklistener);
*/

    }

    /**
     * Оприделение индекса виджета в родительском элементе по координатам игрового поля
     * @param row - ряд игрового поля
     * @param column - столбец игрового поля
     * @return - индекс
     */
    int getIndexInParent(int row, int column)
    {
        return row * mSize + column;
    }

    /**
     * Получить размер ячейки игрового поля в зависимости от размера поля
     * @return размер в px
     */

    int getCellSize()
    {
        switch (mSize)
        {
            case 3:
                return  mContext.getResources().getDimensionPixelSize(R.dimen.row_height_threeCell);
            case 4:
                return mContext.getResources().getDimensionPixelSize(R.dimen.row_height_fourCell);
            case 5:
                return mContext.getResources().getDimensionPixelSize(R.dimen.row_height_fiveCell);
            default:
                return 0;
        }
    }

    /**
     * Окончание игры
     * @return true / false
     */
    boolean isEnded()
    {
        return mIsEnded;
    }

    /**
     * Сохранить текущее состояние одного ряда игрового поля в байтовый массив
     * @return игровое поле в виде byteArray
     */
    byte[] saveGameRow(int row)
    {
        byte[] curGameFieldRow = new byte[mGame.getGameField().length];
        //rows
        for (int i = 0; i < curGameFieldRow.length; i++)
        {
                curGameFieldRow[i] = (byte) mGame.getGameField()[row][i];
        }
        return curGameFieldRow;
    }

    /**
     * получить текущее кол-во ходов
     * @return текущее кол-во ходов
     */
    int getMoveCount()
    {
        return mMoveCnt;
    }
    /**
     * установить текущее кол-во ходов
     */
    void setMoveCount(int moveCnt)
    {
        mMoveCnt = moveCnt;
    }

    void restoreGameFieldRow(int row, byte[] rowData)
    {
        for (int i = 0; i < rowData.length; i++) {
            mGame.getGameField()[row][i] = (int) rowData[i];
        }
    }

    void setContext(TagFragment context)
    {
        mContext = context.getContext();
    }
    void setInflater(LayoutInflater inflater)
    {
        mInflater = inflater;
    }
    void setUIGameField (GridLayout gameField)
    {
        mGameField = gameField;
    }
    void setUIMovesCnt (TextView movesCnt)
    {
        mTvMovesCnt = movesCnt;
    }
    void setClickListener (TagFragment.TagClick clicklistener)
    {
        mClicklistener = clicklistener;
    }
}
