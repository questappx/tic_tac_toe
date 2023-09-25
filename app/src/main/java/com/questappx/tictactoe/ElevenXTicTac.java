package com.questappx.tictactoe;

import static kotlinx.coroutines.JobKt.Job;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
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
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ElevenXTicTac extends TicTac {
//    int[] boxPositions = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    ProgressDialog progressDialog;
    int difficultyLevel;

//    final int totalBoxes = 121;

    public ElevenXTicTac(Context context, int difficultyLevel,int gameMode)
    {
        super(context, 121, gameMode);
        setBoxPositionsParent(new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0});

        this.difficultyLevel = difficultyLevel;
        this.gamePlayMode = gameMode;


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

        setCombinations();
        setGameArea(totalBoxes);



    }

    private void setGrid() {
        if( gamePlayMode == Data.BLOCKAGE_PLAYMODE || gamePlayMode ==  Data.BARRIER_PLAYMODE)
        {
            setBlockageMode(15,6);
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
            if((boxPositions[combination[0]] ==  this.playerTurns) && (boxPositions[combination[1]] ==  playerTurns) && (boxPositions[combination[2]] ==  playerTurns) && (boxPositions[combination[3]] ==  playerTurns) && (boxPositions[combination[4]] ==  playerTurns) && (boxPositions[combination[5]] ==  playerTurns))
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
        this.boxPositionsParent = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        for (int i = 0; i<gridView.getChildCount();i++)
        {
//            imageViewArrayList.get(i).setBackgroundResource(R.drawable.box_bg);
            ImageView temp = customAdapter.getImageview(gridView.getChildAt(i));
            Glide.with(context)
                    .asDrawable()
                    .load(R.drawable.box_bg)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            temp.setBackground(resource);
                        }
                    });
//            imageViewArrayList.get(i).setImageResource(R.drawable.box_bg);
            Glide.with(context).load(R.drawable.box_bg).into(temp);

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

    public int getAITurnNum(int player)
    {
        int result = -1;

        //111110
        for (int i=0;i<combinationList.size();i++)
        {
            final int[] combination = combinationList.get(i);

            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == player) && (boxPositionsParent[combination[3]] == player) && (boxPositionsParent[combination[4]] == player) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[0])
                {
                    return combination[0];
                }
            }
             if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == 0) && (boxPositionsParent[combination[2]] == player) && (boxPositionsParent[combination[3]] == player) && (boxPositionsParent[combination[4]] == player) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[4])
                {
                    return combination[4];
                }
            }
             if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == 0) && (boxPositionsParent[combination[3]] == player) && (boxPositionsParent[combination[4]] == player) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
             if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == player) && (boxPositionsParent[combination[3]] == 0) && (boxPositionsParent[combination[4]] == player) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[3])
                {
                    return combination[3];
                }
            }
             if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == player) && (boxPositionsParent[combination[3]] == player) && (boxPositionsParent[combination[4]] == 0) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[4])
                {
                    return combination[4];
                }
            }
             if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == player) && (boxPositionsParent[combination[3]] == player) && (boxPositionsParent[combination[4]] == player) && (boxPositionsParent[combination[5]] == 0))
            {
                if(recentPickedBox != combination[5]) {
                    return combination[5];
                }
            }

        }


        return result;
    }



//    111100
//    111001
//    110011
//    100111
//    001111
//    010111
//    011011
//    011101
//    011110
//    101110
//    110110
//    111010
//    110101
//    101011
//    101101

    public int getAITurnNum2(int player)
    {
        int result = -1;
        //111100
        for (int i=0;i<combinationList.size();i++)
        {
            final int[] combination = combinationList.get(i);

            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == 0) && (boxPositionsParent[combination[2]] == player) && (boxPositionsParent[combination[3]] == player) && (boxPositionsParent[combination[4]] == player) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[1])
                {
                    return combination[1];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == 0) && (boxPositionsParent[combination[3]] == player) && (boxPositionsParent[combination[4]] == player) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
            if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == player) && (boxPositionsParent[combination[3]] == player) && (boxPositionsParent[combination[4]] == 0) && (boxPositionsParent[combination[5]] == 0))
            {
                if(recentPickedBox != combination[4])
                {
                    return combination[4];
                }
            }
            if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == player) && (boxPositionsParent[combination[3]] == 0) && (boxPositionsParent[combination[4]] == 0) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[3])
                {
                    return combination[3];
                }
            }
            if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == 0) && (boxPositionsParent[combination[3]] == 0) && (boxPositionsParent[combination[4]] == player) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
            if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == 0) && (boxPositionsParent[combination[2]] == 0) && (boxPositionsParent[combination[3]] == player) && (boxPositionsParent[combination[4]] == player) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }

            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == player) && (boxPositionsParent[combination[3]] == 0) && (boxPositionsParent[combination[4]] == player) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[3])
                {
                    return combination[3];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == player) && (boxPositionsParent[combination[3]] == player) && (boxPositionsParent[combination[4]] == 0) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[4])
                {
                    return combination[4];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == player) && (boxPositionsParent[combination[3]] == player) && (boxPositionsParent[combination[4]] == player) && (boxPositionsParent[combination[5]] == 0))
            {
                if(recentPickedBox != combination[5])
                {
                    return combination[5];
                }
            }
            if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == 0) && (boxPositionsParent[combination[2]] == player) && (boxPositionsParent[combination[3]] == player) && (boxPositionsParent[combination[4]] == player) && (boxPositionsParent[combination[5]] == 0))
            {
                if(recentPickedBox != combination[1])
                {
                    return combination[1];
                }
            }
            if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == 0) && (boxPositionsParent[combination[3]] == player) && (boxPositionsParent[combination[4]] == player) && (boxPositionsParent[combination[5]] == 0))
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }

            if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == player) && (boxPositionsParent[combination[3]] == 0) && (boxPositionsParent[combination[4]] == player) && (boxPositionsParent[combination[5]] == 0))
            {
                if(recentPickedBox != combination[3])
                {
                    return combination[3];
                }
            }
            if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == 0) && (boxPositionsParent[combination[3]] == player) && (boxPositionsParent[combination[4]] == 0) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
            if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == 0) && (boxPositionsParent[combination[2]] == player) && (boxPositionsParent[combination[3]] == 0) && (boxPositionsParent[combination[4]] == player) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[4])
                {
                    return combination[4];
                }
            }
            if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == 0) && (boxPositionsParent[combination[2]] == player) && (boxPositionsParent[combination[3]] == player) && (boxPositionsParent[combination[4]] == 0) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[4]) {
                    return combination[4];
                }
            }

        }
        return result;
    }

    protected int getAITurnNum3(int player)
    {
        int result = -1;

        //111000
        for (int i=0;i<combinationList.size();i++)
        {
            final int[] combination = combinationList.get(i);
            if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == player) && (boxPositionsParent[combination[3]] == 0) && (boxPositionsParent[combination[4]] == 0) && (boxPositionsParent[combination[5]] == 0))
            {
                if(recentPickedBox != combination[3])
                {
                    return combination[3];
                }
            }
            if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == 0) && (boxPositionsParent[combination[3]] == player) && (boxPositionsParent[combination[4]] == 0) && (boxPositionsParent[combination[5]] == 0))
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
            if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == 0) && (boxPositionsParent[combination[3]] == 0) && (boxPositionsParent[combination[4]] == player) && (boxPositionsParent[combination[5]] == 0))
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
            if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == 0) && (boxPositionsParent[combination[3]] == 0) && (boxPositionsParent[combination[4]] == 0) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
            if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == 0) && (boxPositionsParent[combination[2]] == player) && (boxPositionsParent[combination[3]] == 0) && (boxPositionsParent[combination[4]] == 0) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[1])
                {
                    return combination[1];
                }
            }
            if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == 0) && (boxPositionsParent[combination[2]] == 0) && (boxPositionsParent[combination[3]] == player) && (boxPositionsParent[combination[4]] == 0) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[4])
                {
                    return combination[4];
                }
            }
            if((boxPositionsParent[combination[0]] == player) && (boxPositionsParent[combination[1]] == 0) && (boxPositionsParent[combination[2]] == 0) && (boxPositionsParent[combination[3]] == 0) && (boxPositionsParent[combination[4]] == player) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[3])
                {
                    return combination[3];
                }
            }
        }

            return result;
    }

    protected int getAITurnNum4(int player)
    {
        int result = -1;
        //110000
        for (int i=0;i<combinationList.size();i++)
        {
            final int[] combination = combinationList.get(i);
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == player) && (boxPositionsParent[combination[3]] == 0) && (boxPositionsParent[combination[4]] == 0) && (boxPositionsParent[combination[5]] == 0))
            {
                if(recentPickedBox != combination[3])
                {
                    return combination[3];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == 0) && (boxPositionsParent[combination[3]] == player) && (boxPositionsParent[combination[4]] == 0) && (boxPositionsParent[combination[5]] == 0))
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == 0) && (boxPositionsParent[combination[3]] == 0) && (boxPositionsParent[combination[4]] == player) && (boxPositionsParent[combination[5]] == 0))
            {
                if(recentPickedBox != combination[3])
                {
                    return combination[3];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == player) && (boxPositionsParent[combination[2]] == 0) && (boxPositionsParent[combination[3]] == 0) && (boxPositionsParent[combination[4]] == 0) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[4])
                {
                    return combination[4];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == 0) && (boxPositionsParent[combination[2]] == player) && (boxPositionsParent[combination[3]] == 0) && (boxPositionsParent[combination[4]] == 0) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[3])
                {
                    return combination[3];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == 0) && (boxPositionsParent[combination[2]] == 0) && (boxPositionsParent[combination[3]] == player) && (boxPositionsParent[combination[4]] == 0) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[3])
                {
                    return combination[4];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == 0) && (boxPositionsParent[combination[2]] == 0) && (boxPositionsParent[combination[3]] == 0) && (boxPositionsParent[combination[4]] == player) && (boxPositionsParent[combination[5]] == player))
            {
                if(recentPickedBox != combination[3])
                {
                    return combination[3];
                }
            }
        }

        return result;
    }


    @Override
    protected void aiTurnToPlay(boolean getRandNum) {
        Random random = new Random();

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
            if(difficultyLevel == Data.EASY_TTTLEVEL)
            {
                rand = getAITurnNum(playerTurns);
                if(rand == -1)
                {
                    rand = getAITurnNum(getInversePlayerTurns());
                    if(rand == -1)
                    {
                        rand = getAITurnNum2(getInversePlayerTurns());
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
            else if(difficultyLevel == Data.MEDIUM_TTTLEVEL)
            {
                rand = getAITurnNum(playerTurns);
                if(rand == -1)
                {
                    rand = getAITurnNum(getInversePlayerTurns());
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
            else
            {//Hard
                rand = getAITurnNum(playerTurns);
                if(rand == -1)
                {
                    //try to prevent
                    rand = getAITurnNum(getInversePlayerTurns());
                    if(rand == -1)
                    {
                        //try to stop combinations
                        rand = getAITurnNum2(getInversePlayerTurns());
                        if(rand == -1)
                        {
                            //try to make own combinations
                            rand = getAITurnNum2(playerTurns);
                            if(rand == -1)
                            {
                                rand = getAITurnNum3(playerTurns);
                                if(rand == -1)
                                {
                                    rand = getAITurnNum3(getInversePlayerTurns());
                                    if(rand == -1)
                                    {
                                        rand = getAITurnNum4(playerTurns);
                                        if(rand == -1)
                                        {
                                            rand = getAITurnNum4(getInversePlayerTurns());
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
            }
        }

        //try to win

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

    private void setCombinations() {
        combinationList = new ArrayList<>();
        combinationList.add(new int[]{0, 1, 2, 3, 4, 5});
        combinationList.add(new int[]{1, 2, 3, 4, 5, 6});
        combinationList.add(new int[]{2, 3, 4, 5, 6, 7});
        combinationList.add(new int[]{3, 4, 5, 6, 7, 8});
        combinationList.add(new int[]{4, 5, 6, 7, 8, 9});
        combinationList.add(new int[]{5, 6, 7, 8, 9, 10});
        combinationList.add(new int[]{11, 12, 13, 14, 15, 16});
        combinationList.add(new int[]{12, 13, 14, 15, 16, 17});
        combinationList.add(new int[]{13, 14, 15, 16, 17, 18});
        combinationList.add(new int[]{14, 15, 16, 17, 18, 19});
        combinationList.add(new int[]{15, 16, 17, 18, 19, 20});
        combinationList.add(new int[]{16, 17, 18, 19, 20, 21});
        combinationList.add(new int[]{22, 23, 24, 25, 26, 27});
        combinationList.add(new int[]{23, 24, 25, 26, 27, 28});
        combinationList.add(new int[]{24, 25, 26, 27, 28, 29});
        combinationList.add(new int[]{25, 26, 27, 28, 29, 30});
        combinationList.add(new int[]{26, 27, 28, 29, 30, 31});
        combinationList.add(new int[]{27, 28, 29, 30, 31, 32});
        combinationList.add(new int[]{33, 34, 35, 36, 37, 38});
        combinationList.add(new int[]{34, 35, 36, 37, 38, 39});
        combinationList.add(new int[]{35, 36, 37, 38, 39, 40});
        combinationList.add(new int[]{36, 37, 38, 39, 40, 41});
        combinationList.add(new int[]{37, 38, 39, 40, 41, 42});
        combinationList.add(new int[]{38, 39, 40, 41, 42, 43});
        combinationList.add(new int[]{44, 45, 46, 47, 48, 49});
        combinationList.add(new int[]{45, 46, 47, 48, 49, 50});
        combinationList.add(new int[]{46, 47, 48, 49, 50, 51});
        combinationList.add(new int[]{47, 48, 49, 50, 51, 52});
        combinationList.add(new int[]{48, 40, 50, 51, 52, 53});
        combinationList.add(new int[]{49, 50, 51, 52, 53, 54});
        combinationList.add(new int[]{55, 56, 57, 58, 59, 60});
        combinationList.add(new int[]{56, 57, 58, 59, 60, 61});
        combinationList.add(new int[]{57, 58, 59, 60, 61, 62});
        combinationList.add(new int[]{58, 59, 60, 61, 62, 63});
        combinationList.add(new int[]{59, 60, 61, 62, 63, 64});
        combinationList.add(new int[]{60, 61, 62, 63, 64, 65});
        combinationList.add(new int[]{66, 67, 68, 69, 70, 71});
        combinationList.add(new int[]{67, 68, 69, 70, 71, 72});
        combinationList.add(new int[]{68, 69, 70, 71, 72, 73});
        combinationList.add(new int[]{69, 70, 71, 72, 73, 74});
        combinationList.add(new int[]{70, 71, 72, 73, 74, 75});
        combinationList.add(new int[]{71, 72, 73, 74, 75, 76});
        combinationList.add(new int[]{77, 78, 79, 80, 81, 82});
        combinationList.add(new int[]{78, 79, 80, 81, 82, 83});
        combinationList.add(new int[]{79, 80, 81, 82, 83, 84});
        combinationList.add(new int[]{80, 81, 82, 83, 84, 85});
        combinationList.add(new int[]{81, 82, 83, 84, 85, 86});
        combinationList.add(new int[]{82, 83, 84, 85, 86, 87});
        combinationList.add(new int[]{88, 89, 90, 91, 92, 93});
        combinationList.add(new int[]{89, 90, 91, 92, 93, 94});
        combinationList.add(new int[]{90, 91, 92, 93, 94, 95});
        combinationList.add(new int[]{91, 92, 93, 94, 95, 96});
        combinationList.add(new int[]{92, 93, 94, 95, 96, 97});
        combinationList.add(new int[]{93, 94, 95, 96, 97, 98});
        combinationList.add(new int[]{99, 100, 101, 102, 103, 104});
        combinationList.add(new int[]{100, 101, 102, 103, 104, 105});
        combinationList.add(new int[]{101, 102, 103, 104, 105, 106});
        combinationList.add(new int[]{102, 103, 104, 105, 106, 107});
        combinationList.add(new int[]{103, 104, 105, 106, 107, 108});
        combinationList.add(new int[]{104, 105, 106, 107, 108, 109});
        combinationList.add(new int[]{110, 111, 112, 113, 114, 115});
        combinationList.add(new int[]{111, 112, 113, 114, 115, 116});
        combinationList.add(new int[]{112, 113, 114, 115, 116, 117});
        combinationList.add(new int[]{113, 114, 115, 116, 117, 118});
        combinationList.add(new int[]{114, 115, 116, 117, 118, 119});
        combinationList.add(new int[]{115, 116, 117, 118, 119, 120});
        combinationList.add(new int[]{55, 67, 79, 91, 103, 115});
        combinationList.add(new int[]{44, 56, 68, 80, 92, 104});
        combinationList.add(new int[]{56, 68, 80, 92, 104, 116});
        combinationList.add(new int[]{33, 45, 57, 69, 81, 93});
        combinationList.add(new int[]{45, 57, 69, 81, 93, 105});
        combinationList.add(new int[]{57, 69, 81, 93, 105, 117});
        combinationList.add(new int[]{22, 34, 46, 58, 70, 82});
        combinationList.add(new int[]{34, 46, 58, 70, 82, 94});
        combinationList.add(new int[]{46, 58, 70, 82, 94, 106});
        combinationList.add(new int[]{58, 70, 82, 94, 106, 118});
        combinationList.add(new int[]{11, 23, 35, 47, 59, 71});
        combinationList.add(new int[]{23, 35, 47, 59, 71, 83});
        combinationList.add(new int[]{35, 47, 59, 71, 83, 95});
        combinationList.add(new int[]{47, 59, 71, 83, 95, 107});
        combinationList.add(new int[]{59, 71, 83, 95, 107, 119});
        combinationList.add(new int[]{0, 12, 24, 36, 48, 60});
        combinationList.add(new int[]{12, 24, 36, 48, 60, 72});
        combinationList.add(new int[]{24, 36, 48, 60, 72, 84});
        combinationList.add(new int[]{36, 48, 60, 72, 84, 96});
        combinationList.add(new int[]{48, 60, 72, 84, 96, 108});
        combinationList.add(new int[]{60, 72, 84, 96, 108, 120});
        combinationList.add(new int[]{1, 13, 25, 37, 49, 61});
        combinationList.add(new int[]{13, 25, 37, 49, 61, 73});
        combinationList.add(new int[]{25, 37, 49, 61, 73, 85});
        combinationList.add(new int[]{37, 49, 61, 73, 85, 97});
        combinationList.add(new int[]{49, 61, 73, 85, 97, 109});
        combinationList.add(new int[]{2, 14, 26, 38, 50, 62});
        combinationList.add(new int[]{14, 26, 38, 50, 62, 74});
        combinationList.add(new int[]{26, 38, 50, 62, 74, 86});
        combinationList.add(new int[]{38, 50, 62, 74, 86, 98});
        combinationList.add(new int[]{3, 15, 27, 39, 51, 63});
        combinationList.add(new int[]{15, 27, 39, 51, 63, 75});
        combinationList.add(new int[]{27, 39, 51, 63, 75, 87});
        combinationList.add(new int[]{4, 16, 28, 40, 52, 64});
        combinationList.add(new int[]{16, 28, 40, 52, 64, 76});
        combinationList.add(new int[]{5, 17, 29, 41, 53, 65});
        combinationList.add(new int[]{115, 105, 95, 85, 75, 65});
        combinationList.add(new int[]{114, 104, 94, 84, 74, 64});
        combinationList.add(new int[]{104, 94, 84, 74, 64, 54});
        combinationList.add(new int[]{113, 103, 93, 83, 73, 63});
        combinationList.add(new int[]{103, 93, 83, 73, 63, 53});
        combinationList.add(new int[]{93, 83, 73, 63, 53, 43});
        combinationList.add(new int[]{112, 102, 92, 82, 72, 62});
        combinationList.add(new int[]{102, 92, 82, 72, 62, 52});
        combinationList.add(new int[]{92, 82, 72, 62, 52, 42});
        combinationList.add(new int[]{82, 72, 62, 52, 42, 32});
        combinationList.add(new int[]{111, 101, 91, 81, 71, 61});
        combinationList.add(new int[]{101, 91, 81, 71, 61, 51});
        combinationList.add(new int[]{91, 81, 71, 61, 51, 41});
        combinationList.add(new int[]{81, 71, 61, 51, 41, 31});
        combinationList.add(new int[]{71, 61, 51, 41, 31, 21});
        combinationList.add(new int[]{110, 100, 90, 80, 70, 60});
        combinationList.add(new int[]{100, 90, 80, 70, 60, 50});
        combinationList.add(new int[]{90, 80, 70, 60, 50, 40});
        combinationList.add(new int[]{80, 70, 60, 50, 40, 30});
        combinationList.add(new int[]{70, 60, 50, 40, 30, 20});
        combinationList.add(new int[]{60, 50, 40, 30, 20, 10});
        combinationList.add(new int[]{99, 89, 79, 69, 59, 49});
        combinationList.add(new int[]{89, 79, 69, 59, 49, 39});
        combinationList.add(new int[]{79, 69, 59, 49, 39, 29});
        combinationList.add(new int[]{69, 59, 49, 39, 29, 19});
        combinationList.add(new int[]{59, 49, 39, 29, 19, 9});
        combinationList.add(new int[]{88, 78, 68, 58, 48, 38});
        combinationList.add(new int[]{78, 68, 58, 48, 38, 28});
        combinationList.add(new int[]{68, 58, 48, 38, 28, 18});
        combinationList.add(new int[]{58, 48, 38, 28, 18, 8});
        combinationList.add(new int[]{77, 67, 57, 47, 37, 27});
        combinationList.add(new int[]{67, 57, 47, 37, 27, 17});
        combinationList.add(new int[]{57, 47, 37, 27, 17, 7});
        combinationList.add(new int[]{55, 45, 35, 25, 15, 5});
        combinationList.add(new int[]{56, 46, 36, 26, 16, 6});
        combinationList.add(new int[]{66, 56, 46, 36, 26, 16});
        combinationList.add(new int[]{0, 11, 22, 33, 44, 55});
        combinationList.add(new int[]{11, 22, 33, 44, 55, 66});
        combinationList.add(new int[]{22, 33, 44, 55, 66, 77});
        combinationList.add(new int[]{33, 44, 55, 66, 77, 88});
        combinationList.add(new int[]{44, 55, 66, 77, 88, 99});
        combinationList.add(new int[]{55, 66, 77, 88, 99, 110});
        combinationList.add(new int[]{1, 12, 23, 34, 45, 56});
        combinationList.add(new int[]{12, 23, 34, 45, 56, 67});
        combinationList.add(new int[]{23, 34, 45, 56, 67, 78});
        combinationList.add(new int[]{34, 45, 56, 67, 78, 89});
        combinationList.add(new int[]{45, 56, 67, 78, 89, 100});
        combinationList.add(new int[]{56, 67, 78, 89, 100, 111});
        combinationList.add(new int[]{2, 13, 24, 35, 46, 57});
        combinationList.add(new int[]{13, 24, 35, 46, 57, 68});
        combinationList.add(new int[]{24, 35, 46, 57, 68, 79});
        combinationList.add(new int[]{35, 46, 57, 68, 79, 90});
        combinationList.add(new int[]{46, 57, 68, 79, 90, 101});
        combinationList.add(new int[]{57, 68, 79, 90, 101, 112});
        combinationList.add(new int[]{3, 14, 25, 36, 47, 58});
        combinationList.add(new int[]{14, 25, 36, 47, 58, 69});
        combinationList.add(new int[]{25, 36, 47, 58, 69, 80});
        combinationList.add(new int[]{36, 47, 58, 69, 80, 91});
        combinationList.add(new int[]{47, 58, 69, 80, 91, 102});
        combinationList.add(new int[]{58, 69, 80, 91, 102, 113});
        combinationList.add(new int[]{4, 15, 26, 37, 48, 59});
        combinationList.add(new int[]{15, 26, 37, 48, 59, 70});
        combinationList.add(new int[]{26, 37, 48, 59, 70, 81});
        combinationList.add(new int[]{37, 48, 59, 70, 81, 92});
        combinationList.add(new int[]{48, 59, 70, 81, 92, 103});
        combinationList.add(new int[]{59, 70, 81, 92, 103, 114});
        combinationList.add(new int[]{5, 16, 27, 38, 49, 60});
        combinationList.add(new int[]{16, 27, 38, 49, 60, 71});
        combinationList.add(new int[]{27, 38, 49, 60, 71, 82});
        combinationList.add(new int[]{38, 49, 60, 71, 82, 93});
        combinationList.add(new int[]{49, 60, 71, 82, 93, 104});
        combinationList.add(new int[]{60, 71, 82, 93, 104, 115});
        combinationList.add(new int[]{6, 17, 28, 39, 50, 61});
        combinationList.add(new int[]{17, 28, 39, 50, 61, 72});
        combinationList.add(new int[]{28, 39, 50, 61, 72, 83});
        combinationList.add(new int[]{39, 50, 61, 72, 83, 94});
        combinationList.add(new int[]{50, 61, 72, 83, 94, 105});
        combinationList.add(new int[]{61, 72, 83, 94, 105, 116});
        combinationList.add(new int[]{7, 18, 29, 40, 51, 62});
        combinationList.add(new int[]{18, 29, 40, 51, 62, 73});
        combinationList.add(new int[]{29, 40, 51, 62, 73, 84});
        combinationList.add(new int[]{40, 51, 62, 73, 84, 95});
        combinationList.add(new int[]{51, 62, 73, 84, 95, 106});
        combinationList.add(new int[]{62, 73, 84, 95, 106, 117});
        combinationList.add(new int[]{8, 19, 30, 41, 52, 63});
        combinationList.add(new int[]{19, 30, 41, 52, 63, 74});
        combinationList.add(new int[]{30, 41, 52, 63, 74, 85});
        combinationList.add(new int[]{41, 52, 63, 74, 85, 96});
        combinationList.add(new int[]{52, 63, 74, 85, 96, 107});
        combinationList.add(new int[]{63, 74, 85, 96, 107, 118});
        combinationList.add(new int[]{9, 20, 31, 42, 53, 64});
        combinationList.add(new int[]{20, 31, 42, 53, 64, 75});
        combinationList.add(new int[]{31, 42, 53, 64, 75, 86});
        combinationList.add(new int[]{42, 53, 64, 75, 86, 97});
        combinationList.add(new int[]{53, 64, 75, 86, 97, 108});
        combinationList.add(new int[]{64, 75, 86, 97, 108, 119});
        combinationList.add(new int[]{10, 21, 32, 43, 54, 65});
        combinationList.add(new int[]{21, 32, 43, 54, 65, 76});
        combinationList.add(new int[]{32, 43, 54, 65, 76, 87});
        combinationList.add(new int[]{43, 54, 65, 76, 87, 98});
        combinationList.add(new int[]{54, 65, 76, 87, 98, 109});
        combinationList.add(new int[]{65, 76, 87, 98, 109, 120});

    }

    @Override
    public void DeallocateMemory() {
        super.DeallocateMemory();
        boxPositionsParent = null;
        combinationList = null;
    }


}
