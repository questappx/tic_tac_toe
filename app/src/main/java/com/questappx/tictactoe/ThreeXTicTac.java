package com.questappx.tictactoe;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.Random;

public class ThreeXTicTac extends TicTac{

//    private List<int[]> combinationList = new ArrayList<>();
//    ArrayList<ImageView> boxesArraylist = new ArrayList<>();
//    final int totalsize = 9;
    protected int difficultyLevel;
     protected ProgressDialog progressDialog;

//    int gameMode;

    public ThreeXTicTac(Context context, int difficultyLevel, int gameMode)
    {
        super(context, 9, gameMode);

        setBoxPositionsParent(new int[]{0,0,0,0,0,0,0,0,0});

        this.difficultyLevel = difficultyLevel;
        this.gamePlayMode = gameMode;


        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setGameData();


                progressDialog.dismiss();
            }
        }, 1000);

        setGameArea(totalBoxes);
    }

    private void setGameData() {
        combinationList = new ArrayList<>();
        combinationList.add(new int[]{0, 1, 2});
        combinationList.add(new int[]{3, 4, 5});
        combinationList.add(new int[]{6, 7, 8});
        combinationList.add(new int[]{0, 3, 6});
        combinationList.add(new int[]{1, 4, 7});
        combinationList.add(new int[]{2, 5, 8});
        combinationList.add(new int[]{2, 4, 6});
        combinationList.add(new int[]{0, 4, 8});


        if( gamePlayMode == Data.BLOCKAGE_PLAYMODE || gamePlayMode ==  Data.BARRIER_PLAYMODE)
        {
            setBlockageMode(3,2);
        }
        else if(gamePlayMode == Data.FADEAWAY_PLAYMODE)
        {
            setFadeAwayMode();
        }
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

    // will stop opponent to win & try to win by itself
    //opponentPlayerTurn == can be both to check whether space is empty or not
    public int getAITurnNum(int opponentPlayerTurn)
    {
        int result = -1;
        for (int i=0;i<combinationList.size();i++)
        {
            final int[] combination = combinationList.get(i);
            if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == 0)
            {
                if(recentPickedBox != combination[2])
                {
                    return combination[2];
                }
            }
            if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == 0) && boxPositionsParent[combination[2]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[1])
                {
                    return combination[1];
                }
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == opponentPlayerTurn)
            {
                if(recentPickedBox != combination[0])
                {
                    return combination[0];
                }
            }
        }
        return result;
    }



    @Override
    protected boolean checkPlayerWin(int[] boxPositions) {
        boolean response = false;
        for(int i=0;i<combinationList.size();i++)
        {
            final int[] combination = combinationList.get(i);
            if((boxPositions[combination[0]] ==  this.playerTurns) && (boxPositions[combination[1]] ==  playerTurns) && (boxPositions[combination[2]] ==  playerTurns))
            {
                response = true;
                this.addCross(combination);
                return response;
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

    protected void addCross(int[] combination) {

        for(int i=0;i<combination.length;i++)
        {
            View child = gridView.getChildAt(combination[i]);
            ImageView imageviewBg = child.findViewById(R.id.imageview_threeXItem);
            imageviewBg.setBackgroundResource(R.drawable.win_bg);
        }

    }

    @Override
    protected void restartMatch() {
        this.boxPositionsParent = new int[]{0,0,0,0,0,0,0,0,0};

        for (int i = 0; i<gridView.getChildCount();i++)
        {
            ImageView temp = gridView.getChildAt(i).findViewById(R.id.imageview_threeXItem);
            int finalI = i;
            Glide.with(context)
                    .asDrawable()
                    .load(R.drawable.box_bg)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            temp.setBackground(resource);
                        }
                    });
            Glide.with(context).load(R.drawable.box_bg).into(temp);

        }
        super.restartMatch();
    }



    @Override
    protected void aiTurnToPlay(boolean getRandNum) {
        Random random = new Random();
        // to try to win
        int rand = -1;

        if(difficultyLevel == Data.EASY_TTTLEVEL || getRandNum)
        {
            rand = random.nextInt(totalBoxes);
            while(boxPositionsParent[rand] != 0)
            {
                rand = random.nextInt(totalBoxes);
            }
        }
        else
        {
            //Medium + Hard
            rand = getAITurnNum(playerTurns);
            if(rand == -1)
            {
                //to try to prevent from opponent winning
                rand = getAITurnNum(getInversePlayerTurns());
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

        if(!this.isBoxEmpty(this.boxPositionsParent, rand))
        {
            aiTurnToPlay(true);
        }
        else
        {
            //if clicked by ai then wait 1 sec for user turn
            int finalRand = rand;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(playerTurns == 2)
                    {
                        performAction(boxPositionsParent, customAdapter.getImageview(gridView.getChildAt(finalRand)), finalRand);
                    }
//                    performAction(boxPositionsParent, boxesArraylist.get(finalRand), finalRand);
//                    changePlayerTurn(1);
                }
            },1000);
        }
    }


}
