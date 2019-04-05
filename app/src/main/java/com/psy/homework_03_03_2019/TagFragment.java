package com.psy.homework_03_03_2019;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import java.io.Serializable;

public class TagFragment extends Fragment implements Serializable {

    //Tag
    TagController mTagController;
    TextView tvMovesCnt;
    GridLayout TagGameField;
    TagClick mTagClick;
    int mTagSize = 3;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(savedInstanceState!=null)
        {
            mTagSize = savedInstanceState.getInt("TagSize", 3);

            if(savedInstanceState.getByteArray("TagSavedGameRow0") != null)
            {
                int moveCountRestore = savedInstanceState.getInt("TagMoveCount");

                mTagController = new TagController(mTagSize);
                for (int i = 0; i < mTagSize; i++) {
                    mTagController.restoreGameFieldRow(i, savedInstanceState.getByteArray("TagSavedGameRow"+i));
                }
                mTagController.setMoveCount(moveCountRestore);
            }
        }
        View v = inflater.inflate(R.layout.activity_tag, container, false);

        TagGameField = v.findViewById(R.id.TagGameField);
        tvMovesCnt = v.findViewById(R.id.movesCnt);
        mTagClick = new TagClick();
        if(mTagController == null)
        {
            mTagController = new TagController(this, inflater,mTagSize, tvMovesCnt, TagGameField,mTagClick);
        }
        else {
            mTagController.setContext(this);
            mTagController.setInflater(inflater);
            mTagController.setUIGameField(TagGameField);
            mTagController.setUIMovesCnt(tvMovesCnt);
            mTagController.setClickListener(mTagClick);
        }
        mTagController.createGameField();


        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("TagSize", mTagSize);
        if(mTagController != null)
        {
            outState.putInt("TagMoveCount", mTagController.getMoveCount());
            for (int i = 0; i < mTagSize; i++) {
                outState.putByteArray("TagSavedGameRow" + i, mTagController.saveGameRow(i));
            }
        }
    }

    /**
     * Слушатель для "Пятнашки"
     */
    protected class TagClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            if(!mTagController.isEnded())
            {
                int cellId = v.getId();
                mTagController.setMove(cellId);
            }
            else
            {
                mTagController.newGame();
//
            }
        }
    }
}
