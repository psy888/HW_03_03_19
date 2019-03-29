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

public class TagFragment extends Fragment {

    //Tag
    TagController mTagController;
    TextView tvMovesCnt;
    GridLayout TagGameField;
    TagClick mTagClick;
    int mTagSize = 3;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_tag, container);

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


        return super.onCreateView(inflater, container, savedInstanceState);
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
