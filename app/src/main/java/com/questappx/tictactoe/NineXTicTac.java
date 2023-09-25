package com.questappx.tictactoe;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NineXTicTac extends TicTac{
//    int[] boxPositions = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
//    ArrayList<ImageView> boxesArraylist = new ArrayList<>();
    int diffultyLevel;
    ProgressDialog progressDialog;
//    final int totalSize = 81;

    public NineXTicTac(Context context, int difficultyLevel, int gameMode)
    {
        super(context,81, gameMode);
        setBoxPositionsParent(new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});

        this.diffultyLevel = difficultyLevel;
        this.gamePlayMode = gameMode;

//        List<int[]> combinationList = new ArrayList<>();



        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setGrid();

                progressDialog.dismiss();
            }
        }, 1500);

        setCombination();
        setGameArea(totalBoxes);
    }

    private void setGrid() {
        if( gamePlayMode == Data.BLOCKAGE_PLAYMODE || gamePlayMode ==  Data.BARRIER_PLAYMODE)
        {
            setBlockageMode(10,5);
        }
        else if(gamePlayMode == Data.FADEAWAY_PLAYMODE)
        {
            setFadeAwayMode();
        }
    }

    private void setCombination()
    {
        combinationList = new ArrayList<>();
        combinationList.add(new int[]{0,1,2,3,4});
        combinationList.add(new int[]{1,2,3,4,5});
        combinationList.add(new int[]{2,3,4,5,6});
        combinationList.add(new int[]{3,4,5,6,7});
        combinationList.add(new int[]{4,5,6,7,8});
        combinationList.add(new int[]{9,10,11,12,13});
        combinationList.add(new int[]{10,11,12,13,14});
        combinationList.add(new int[]{11,12,13,14,15});
        combinationList.add(new int[]{12,13,14,15,16});
        combinationList.add(new int[]{13,14,15,16,17});
        combinationList.add(new int[]{18,19,20,21,22});
        combinationList.add(new int[]{19,20,21,22,23});
        combinationList.add(new int[]{20,21,22,23,24});
        combinationList.add(new int[]{21,22,23,24,25});
        combinationList.add(new int[]{27,28,29,30,31});
        combinationList.add(new int[]{28,29,30,31,32});
        combinationList.add(new int[]{29,30,31,32,33});
        combinationList.add(new int[]{30,31,32,33,34});
        combinationList.add(new int[]{31,32,33,34,35});
        combinationList.add(new int[]{36,37,38,39,40});
        combinationList.add(new int[]{37,38,39,40,41});
        combinationList.add(new int[]{38,39,40,41,42});
        combinationList.add(new int[]{39,40,41,42,43});
        combinationList.add(new int[]{40,41,42,43,44});
        combinationList.add(new int[]{45,46,47,48,49});
        combinationList.add(new int[]{46,47,48,49,50});
        combinationList.add(new int[]{47,48,49,50,51});
        combinationList.add(new int[]{48,49,50,51,52});
        combinationList.add(new int[]{49,50,51,52,53});
        combinationList.add(new int[]{54,55,56,57,58});
        combinationList.add(new int[]{55,56,57,58,59});
        combinationList.add(new int[]{56,57,58,59,60});
        combinationList.add(new int[]{57,58,59,60,61});
        combinationList.add(new int[]{58,59,60,61,62});
        combinationList.add(new int[]{63,64,65,66,67});
        combinationList.add(new int[]{64,65,66,67,68});
        combinationList.add(new int[]{65,66,67,68,69});
        combinationList.add(new int[]{66,67,68,69,70});
        combinationList.add(new int[]{67,68,69,70,71});
        combinationList.add(new int[]{72,73,74,75,76});
        combinationList.add(new int[]{73,74,75,76,77});
        combinationList.add(new int[]{74,75,76,77,78});
        combinationList.add(new int[]{75,76,77,78,79});
        combinationList.add(new int[]{76,77,78,79,80});
        combinationList.add(new int[]{36,46,56,66,76});
        combinationList.add(new int[]{27,37,47,57,67});
        combinationList.add(new int[]{37,47,57,67,77});
        combinationList.add(new int[]{18,28,38,48,58});
        combinationList.add(new int[]{28,38,48,58,68});
        combinationList.add(new int[]{38,48,58,68,78});
        combinationList.add(new int[]{9,19,29,39,49});
        combinationList.add(new int[]{19,29,39,49,59});
        combinationList.add(new int[]{29,39,49,59,69});
        combinationList.add(new int[]{39,49,59,69,79});
        combinationList.add(new int[]{0,10,20,30,40});
        combinationList.add(new int[]{10,20,30,40,50});
        combinationList.add(new int[]{20,30,40,50,60});
        combinationList.add(new int[]{30,40,50,60,70});
        combinationList.add(new int[]{40,50,60,70,80});
        combinationList.add(new int[]{1,11,21,31,41});
        combinationList.add(new int[]{11,21,31,41,51});
        combinationList.add(new int[]{21,31,41,51,61});
        combinationList.add(new int[]{31,41,51,61,71});
        combinationList.add(new int[]{2,12,22,32,42});
        combinationList.add(new int[]{12,22,32,42,52});
        combinationList.add(new int[]{22,32,42,52,62});
        combinationList.add(new int[]{3,13,23,33,43});
        combinationList.add(new int[]{13,23,33,43,53});
        combinationList.add(new int[]{4,14,24,34,44});
        combinationList.add(new int[]{76,69,60,52,44});
        combinationList.add(new int[]{75,67,59,51,43});
        combinationList.add(new int[]{67,59,51,43,35});
        combinationList.add(new int[]{74,66,58,50,42});
        combinationList.add(new int[]{66,58,50,42,34});
        combinationList.add(new int[]{58,50,42,34,26});
        combinationList.add(new int[]{73,65,57,49,41});
        combinationList.add(new int[]{65,57,49,41,33});
        combinationList.add(new int[]{57,49,41,33,25});
        combinationList.add(new int[]{49,41,33,25,17});
        combinationList.add(new int[]{72,64,56,48,40});
        combinationList.add(new int[]{64,56,48,40,32});
        combinationList.add(new int[]{56,48,40,32,24});
        combinationList.add(new int[]{48,40,32,24,16});
        combinationList.add(new int[]{40,32,24,16,8});
        combinationList.add(new int[]{63,55,47,39,31});
        combinationList.add(new int[]{55,47,39,31,23});
        combinationList.add(new int[]{47,39,31,23,15});
        combinationList.add(new int[]{39,31,23,15,7});
        combinationList.add(new int[]{54,46,38,30,22});
        combinationList.add(new int[]{46,38,30,22,14});
        combinationList.add(new int[]{38,30,22,14,6});
        combinationList.add(new int[]{45,37,29,21,13});
        combinationList.add(new int[]{37,29,21,13,5});
        combinationList.add(new int[]{36,28,20,12,4});
        combinationList.add(new int[]{0,9,18,27,36});
        combinationList.add(new int[]{9,18,27,36,45});
        combinationList.add(new int[]{18,27,36,45,54});
        combinationList.add(new int[]{27,36,45,54,63});
        combinationList.add(new int[]{36,45,54,63,72});
        combinationList.add(new int[]{1,10,19,28,37});
        combinationList.add(new int[]{10,19,28,37,46});
        combinationList.add(new int[]{19,28,37,46,55});
        combinationList.add(new int[]{28,37,46,55,64});
        combinationList.add(new int[]{37,46,55,64,73});
        combinationList.add(new int[]{2,11,20,29,38});
        combinationList.add(new int[]{11,20,29,38,47});
        combinationList.add(new int[]{20,29,38,47,56});
        combinationList.add(new int[]{29,38,47,56,65});
        combinationList.add(new int[]{38,47,56,65,74});
        combinationList.add(new int[]{3,12,21,30,39});
        combinationList.add(new int[]{12,21,30,39,48});
        combinationList.add(new int[]{21,30,39,48,57});
        combinationList.add(new int[]{30,39,48,57,66});
        combinationList.add(new int[]{39,48,57,66,75});
        combinationList.add(new int[]{4,13,22,31,40});
        combinationList.add(new int[]{13,22,31,40,49});
        combinationList.add(new int[]{22,31,40,49,58});
        combinationList.add(new int[]{31,40,49,58,67});
        combinationList.add(new int[]{40,49,58,67,76});
        combinationList.add(new int[]{5,14,23,32,41});
        combinationList.add(new int[]{14,23,32,41,50});
        combinationList.add(new int[]{23,32,41,50,59});
        combinationList.add(new int[]{32,41,50,59,68});
        combinationList.add(new int[]{41,50,59,68,77});
        combinationList.add(new int[]{6,15,24,33,42});
        combinationList.add(new int[]{15,24,33,42,51});
        combinationList.add(new int[]{24,33,42,51,60});
        combinationList.add(new int[]{33,42,51,60,69});
        combinationList.add(new int[]{42,51,60,69,78});
        combinationList.add(new int[]{7,15,24,33,42});
        combinationList.add(new int[]{16,25,34,43,52});
        combinationList.add(new int[]{25,34,43,52,61});
        combinationList.add(new int[]{34,43,52,61,70});
        combinationList.add(new int[]{43,52,61,70,79});
        combinationList.add(new int[]{8,17,26,35,44});
        combinationList.add(new int[]{17,26,35,44,53});
        combinationList.add(new int[]{26,35,44,53,62});
        combinationList.add(new int[]{35,44,53,62,71});
        combinationList.add(new int[]{44,53,62,71,80});


    }


    @Override
    protected boolean checkPlayerWin(int[] boxPositions) {
        boolean response = false;
        for(int i=0;i<combinationList.size();i++)
        {
            final int[] combination = combinationList.get(i);
            if((boxPositions[combination[0]] ==  this.playerTurns) && (boxPositions[combination[1]] ==  playerTurns) && (boxPositions[combination[2]] ==  playerTurns) && (boxPositions[combination[3]] ==  playerTurns) && (boxPositions[combination[4]] ==  playerTurns))
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
        this.boxPositionsParent = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        for (int i = 0; i<gridView.getChildCount();i++)
        {
            ImageView tempImage = customAdapter.getImageview(gridView.getChildAt(i));
//            imageViewArrayList.get(i).setBackgroundResource(R.drawable.box_bg);
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

            if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == opponentPlayerTurn && boxPositionsParent[combination[4]] == 0)
            {
                if(recentPickedBox != combination[4])
                {
                    return combination[4];
                }

            }
            if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == 0 && boxPositionsParent[combination[4]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[3])
                {
                    return combination[3];
                }
            }
            if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == 0 && boxPositionsParent[combination[3]] == opponentPlayerTurn && boxPositionsParent[combination[4]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
            if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == 0) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == opponentPlayerTurn && boxPositionsParent[combination[4]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[1])
                {
                    return combination[1];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == opponentPlayerTurn && boxPositionsParent[combination[4]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[0])
                {
                    return combination[0];
                }
            }
        }



        return result;
    }



//    11001
//    10011
//    00111
//    01011
//    01101
//    01110
//    10110
//    11010
//    11100
//    10101
    public int getAITurnNum2(int opponentPlayerTurn)
    {
        int result = -1;
        for (int i=0;i<combinationList.size();i++)
        {
            final int[] combination = combinationList.get(i);

            if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == 0 && boxPositionsParent[combination[4]] == 0)
            {
                if(recentPickedBox != combination[3])
                {
                    return combination[3];
                }
            }
            if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == 0 && boxPositionsParent[combination[3]] == 0 && boxPositionsParent[combination[4]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[3])
                {
                    return combination[3];
                }
            }
            if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == 0 && boxPositionsParent[combination[3]] == opponentPlayerTurn && boxPositionsParent[combination[4]] == 0)
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
            if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == 0) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == opponentPlayerTurn && boxPositionsParent[combination[4]] == 0)
            {
                if(recentPickedBox != combination[1])
                {
                    return combination[1];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == opponentPlayerTurn && boxPositionsParent[combination[4]] == 0)
            {
                if(recentPickedBox != combination[0])
                {
                    return combination[0];
                }
            }
            if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == 0) && boxPositionsParent[combination[2]] == 0 && boxPositionsParent[combination[3]] == opponentPlayerTurn && boxPositionsParent[combination[4]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == 0) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == opponentPlayerTurn && boxPositionsParent[combination[4]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[1])
                {
                    return combination[1];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == 0 && boxPositionsParent[combination[3]] == opponentPlayerTurn && boxPositionsParent[combination[4]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == 0 && boxPositionsParent[combination[4]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[3])
                {
                    return combination[3];
                }
            }
            if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == 0) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == 0 && boxPositionsParent[combination[4]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[3])
                {
                    return combination[3];
                }
            }
        }
        return result;
    }

    public int getAITurnNum3(int opponentPlayerTurn)
    {
        int result = -1;

        //11000
        for (int i=0;i<combinationList.size();i++)
        {
            final int[] combination = combinationList.get(i);

            if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == 0 && boxPositionsParent[combination[3]] == 0 && boxPositionsParent[combination[4]] == 0)
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == 0 && boxPositionsParent[combination[3]] == opponentPlayerTurn && boxPositionsParent[combination[4]] == 0)
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == 0 && boxPositionsParent[combination[4]] == 0)
            {
                if(recentPickedBox != combination[0])
                {
                    return combination[0];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == 0) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == 0 && boxPositionsParent[combination[4]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[3])
                {
                    return combination[3];
                }
            }

            if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == 0) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == 0 && boxPositionsParent[combination[4]] == 0)
            {
                if(recentPickedBox != combination[1])
                {
                    return combination[1];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == 0 && boxPositionsParent[combination[3]] == 0 && boxPositionsParent[combination[4]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[3])
                {
                    return combination[3];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == 0) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == opponentPlayerTurn && boxPositionsParent[combination[4]] == 0)
            {
                if(recentPickedBox != combination[1])
                {
                    return combination[1];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == opponentPlayerTurn && boxPositionsParent[combination[4]] == 0)
            {
                if(recentPickedBox != combination[0])
                {
                    return combination[0];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == 0) && boxPositionsParent[combination[2]] == 0 && boxPositionsParent[combination[3]] == opponentPlayerTurn && boxPositionsParent[combination[4]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == 0) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == opponentPlayerTurn && boxPositionsParent[combination[4]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[1]) {
                    return combination[1];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == 0 && boxPositionsParent[combination[3]] == opponentPlayerTurn && boxPositionsParent[combination[4]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == 0 && boxPositionsParent[combination[4]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[3])
                {
                    return combination[3];
                }
            }
            if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == 0) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == 0 && boxPositionsParent[combination[4]] == opponentPlayerTurn)
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
        // try to win
        int rand = -1;

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
            if(diffultyLevel == Data.EASY_TTTLEVEL)
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
            else if(diffultyLevel == Data.MEDIUM_TTTLEVEL)
            {
                //Medium
                rand = getAITurnNum1(playerTurns);
                if(rand == -1)
                {
                    rand = getAITurnNum1(getInversePlayerTurns());
                    if(rand == -1)
                    {
                        rand = getAITurnNum2(getInversePlayerTurns());
                        if(rand == -1)
                        {
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
            else //Hard
            {
                rand = getAITurnNum1(playerTurns);
                if(rand == -1)
                {
                    //try to prevent
                    rand = getAITurnNum1(getInversePlayerTurns());
                    if(rand == -1)
                    {
                        rand = getAITurnNum2(getInversePlayerTurns());
                        if(rand == -1)
                        {
                            rand = getAITurnNum2(playerTurns);
                            if(rand == -1)
                            {
                                rand = getAITurnNum3(playerTurns);
                                if(rand == -1)
                                {
                                    rand = getAITurnNum3(getInversePlayerTurns());
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
