package com.questappx.tictactoe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SixXTicTac extends TicTac{
    int difficultyLevel;
    ProgressDialog progressDialog;

    public SixXTicTac(Context context, int difficultyLevel, int gameMode)
    {
        super(context,36, gameMode);
        setBoxPositionsParent(new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});

        this.difficultyLevel = difficultyLevel;
        this.gamePlayMode = gameMode;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                setGameLayout();

                progressDialog.dismiss();
            }
        }, 1000);

        setGameArea(totalBoxes);

    }

    private void setGameLayout() {

        combinationList = new ArrayList<>();
        combinationList.add(new int[]{0, 1, 2, 3});
        combinationList.add(new int[]{1, 2, 3, 4});
        combinationList.add(new int[]{2, 3, 4, 5});
        combinationList.add(new int[]{6, 7, 8, 9});
        combinationList.add(new int[]{7, 8, 9, 10});
        combinationList.add(new int[]{8, 9, 10, 11});
        combinationList.add(new int[]{12, 13, 14, 15});
        combinationList.add(new int[]{13, 14, 15, 16});
        combinationList.add(new int[]{14, 15, 16, 17});
        combinationList.add(new int[]{18, 19, 20, 21});
        combinationList.add(new int[]{19, 20, 21, 22});
        combinationList.add(new int[]{20,21,22,23});
        combinationList.add(new int[]{24,25,26,27});
        combinationList.add(new int[]{25,26,27,28});
        combinationList.add(new int[]{26,27,28,29});
        combinationList.add(new int[]{30,31,32,33});
        combinationList.add(new int[]{31,32,33,34});
        combinationList.add(new int[]{32,33,34,35});
        combinationList.add(new int[]{0,6,12,18});
        combinationList.add(new int[]{6,12,18,24});
        combinationList.add(new int[]{12,18,24,30});
        combinationList.add(new int[]{1,7,13,19});
        combinationList.add(new int[]{7,13,19,25});
        combinationList.add(new int[]{13,19,25,31});
        combinationList.add(new int[]{13,20,27,34});
        combinationList.add(new int[]{0,7,14,21});
        combinationList.add(new int[]{7,14,21,28});
        combinationList.add(new int[]{14,21,28,35});
        combinationList.add(new int[]{1,8,15,22});
        combinationList.add(new int[]{8,15,22,29});
        combinationList.add(new int[]{2,9,16,23});
        combinationList.add(new int[]{10,16,22,28});
        combinationList.add(new int[]{16,22,28,34});
        combinationList.add(new int[]{5,11,17,23});
        combinationList.add(new int[]{11,17,23,29});
        combinationList.add(new int[]{17,23,29,35});
        combinationList.add(new int[]{12,19,26,33});
        combinationList.add(new int[]{6,13,20,27});
        combinationList.add(new int[]{2,8,14,20});
        combinationList.add(new int[]{8,14,20,26});
        combinationList.add(new int[]{14,20,26,32});
        combinationList.add(new int[]{3,9,15,21});
        combinationList.add(new int[]{9,15,21,27});
        combinationList.add(new int[]{15,21,27,33});
        combinationList.add(new int[]{4,10,16,22});
        combinationList.add(new int[]{17,22,27,32});
        combinationList.add(new int[]{11,16,21,26});
        combinationList.add(new int[]{16,21,26,31});
        combinationList.add(new int[]{5,10,15,20});
        combinationList.add(new int[]{10,15,20,25});
        combinationList.add(new int[]{15,20,25,30});
        combinationList.add(new int[]{4,9,14,19});
        combinationList.add(new int[]{9,14,19,24});
        combinationList.add(new int[]{3,8,13,18});


        if( gamePlayMode == Data.BLOCKAGE_PLAYMODE || gamePlayMode ==  Data.BARRIER_PLAYMODE)
        {
            setBlockageMode(5,4);
        }
        else if(gamePlayMode == Data.FADEAWAY_PLAYMODE)
        {
            setFadeAwayMode();
        }

    }

    @Override
    protected boolean checkPlayerWin(int[] boxPositions) {
        boolean response = false;
        for(int i=0;i<combinationList.size();i++)
        {
            final int[] combination = combinationList.get(i);
            if((boxPositions[combination[0]] ==  this.playerTurns) && (boxPositions[combination[1]] ==  playerTurns) && (boxPositions[combination[2]] ==  playerTurns) && (boxPositions[combination[3]] ==  playerTurns))
            {
                response = true;
                this.addCross(combination);
            }
        }
//        if(gamePlayMode == Data.PICKAGE_PLAYMODE)
//        {
//            //player uses all his boxes
//            if(player1Clicks == combinationList.get(0).length ||player2Clicks == combinationList.get(0).length)
//            {
//                response = true;
//                return response;
//            }
//        }
        return response;
    }

    @Override
    protected void restartMatch() {
        this.boxPositionsParent = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        for (int i = 0; i<gridView.getChildCount();i++)
        {
//            imageViewArrayList.get(i).setBackgroundResource(R.drawable.box_bg);
            ImageView tempImage = customAdapter.getImageview(gridView.getChildAt(i));
            Glide.with(context)
                    .asDrawable()
                    .load(R.drawable.box_bg)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            tempImage.setBackground(resource);
                        }
                    });
//            imageViewArrayList.get(i).setImageResource(R.drawable.box_bg);
            Glide.with(context).load(R.drawable.box_bg).into(tempImage);

        }
        super.restartMatch();
    }

    private int getInversePlayerTurns()
    {
        if(playerTurns == 1)
        {
            return 2;
        }
        else
        {
            return 1;
        }
    }

    public int getAITurnNum1(int opponentPlayerTurn)
    {
        int result = -1;
        for (int i=0;i<combinationList.size();i++)
        {
            final int[] combination = combinationList.get(i);
            if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == 0)
            {
                if(recentPickedBox != combination[3])
                {
                    return combination[3];
                }
            }
             if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == 0 && boxPositionsParent[combination[3]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
             if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == 0) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[1])
                {
                    return combination[1];
                }
            }
             if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[0])
                {
                    return combination[0];
                }
            }
        }
        return result;
    }



//    6x6
//    1100
//    1010
//    1001
//    0101
//    0011
//    0110
    public int getAITurnNum2(int opponentPlayerTurn)
    {
        int result = -1;
        for (int i=0;i<combinationList.size();i++)
        {
            final int[] combination = combinationList.get(i);
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == 0)
            {
                if(recentPickedBox != combination[3])
                {
                    return combination[3];
                }
            }
             if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == 0) && boxPositionsParent[combination[2]] == 0 && boxPositionsParent[combination[3]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
             if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == 0) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == 0)
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
             if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == 0 && boxPositionsParent[combination[3]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
             if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == 0 && boxPositionsParent[combination[3]] == 0)
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
             if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == 0) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[1])
                {
                    return combination[1];
                }
            }
        }
        return result;
    }

    @Override
    protected void aiTurnToPlay(boolean getRandNum) {
        Random random = new Random();

        int rand = -1;

//        if ai checked all numbers but choosing randum number in recursion then direct choose random
        if(getRandNum)
        {
            rand = random.nextInt(totalBoxes);
            while(boxPositionsParent[rand] != 0)
            {

                rand = random.nextInt(totalBoxes);
            }
        }
        else
        {
            if(difficultyLevel == Data.EASY_TTTLEVEL)
            {
                rand = getAITurnNum1(playerTurns);
                if(rand == -1)
                {
                    rand = getAITurnNum1(getInversePlayerTurns());
                    if(rand == -1)
                    {
                        rand = random.nextInt(totalBoxes);
                        while(boxPositionsParent[rand] != 0)
                        {
                            rand = random.nextInt(totalBoxes);
                        }
                    }
                }
            }
            else
            {
                //Medium + Hard
                rand = getAITurnNum1(playerTurns);
                if(rand == -1)
                {
                    rand = getAITurnNum1(getInversePlayerTurns());
                    if(rand == -1)
                    {
                        //preventing
                        rand = getAITurnNum2(getInversePlayerTurns());
                        if(rand == -1)
                        {
                            //completing your own combinations
                            rand = getAITurnNum2(playerTurns);
                            if(rand == -1)
                            {
                                rand = random.nextInt(totalBoxes);
                                while(boxPositionsParent[rand] != 0)
                                {
                                    rand = random.nextInt(totalBoxes);
                                }
                            }
                        }
                    }
                }
            }
        }



        if(!this.isBoxEmpty(this.boxPositionsParent, rand))
        {
            aiTurnToPlay(true);
        }
        else
        {
            int finalRand = rand;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(playerTurns == 2)
                    {
                        performAction(boxPositionsParent, customAdapter.getImageview(gridView.getChildAt(finalRand)), finalRand);
                    }
//                    changePlayerTurn(1);
                }
            },1000);
        }
    }

}