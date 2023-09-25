package com.questappx.tictactoe.Extras;

import static android.content.Context.MODE_PRIVATE;

import static com.questappx.tictactoe.LevelsActivity.levelBlockedBoxes;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.questappx.tictactoe.CustomAdapter;
import com.questappx.tictactoe.Data;
import com.questappx.tictactoe.MainActivity;
import com.questappx.tictactoe.R;
import com.questappx.tictactoe.TicTac;

import java.util.ArrayList;
import java.util.Random;

public class LevelsTicTac extends TicTac {

    int diffultyLevel;
    int gridOf;
    int levelNo;

    private int[] levelGamePlayModes = {
            1,
            2,
            3,
            2,
            3,
            1,
            2,
            2,
            1,
            2,
            3,
            2,
            2,
            3,
            3,
            2,
            3,
            1,
            1,
            3,
            1,
            2,
            3,
            1,
            2,
            3,
            1,
            2,
            3,
            1,
            3,
            1,
            2,
            1,
            3,
            1,
            2,
            3,
            2,
            1,
            3,
            1,
            2,
            1,
            3,
            1,
            3,
            2,
            3,
            1,
            2,
            3,
            2,
            1,
            1,
            3,
            3,
            1,
            3,
            1,
            1,
            3,
            2,
            1,
            3,
            1,
            3,
            1,
            3,
            1,
            3,
            1,
            2,
            1,
            3,
            1,
            2,
            1,
            1,
            2,
            1,
            3,
            1,
            2,
            2,
            1,
            1,
            2,
            1,
            1,
            1,
            3,
            1,
            3,
            1,
            3,
            1,
            2,
            1,
            3,
            1,
            2,
            1,
            2,
            1,
            1,
            2,
            1,
            1,
            2,
            1,
            2,
            3,
            1,
            1,
            2,
            1,
            1,
            1,
            1,
            3,
            1,
            1,
            2,
            1,
            3,
            1,
            1,
            2,
            1,
            1,
            1,
            3,
            1,
            3,
            2,
            1,
            2,
            1,
            3,
            3
    };

    ProgressDialog progressDialog;

    public LevelsTicTac(Context context, int gridOf, int levelNo) {
        super(context, gridOf * gridOf, Data.ORGINAL_PLAYMODE);

        this.gridOf = gridOf;
        this.diffultyLevel = Data.HARD_TTTLEVEL;
        this.levelNo = levelNo;

        setTotalBoxes();

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        setCombinationsForGrid();
    }

    private void getGamePlayMode() {
        gamePlayMode = levelGamePlayModes[levelNo];
        if(gamePlayMode == Data.BLOCKAGE_PLAYMODE || gamePlayMode == Data.BARRIER_PLAYMODE)
        {
            if(totalBoxes == 16)
            {
                setBlockageMode(2, 3);
            }
            else if(totalBoxes == 25)
            {
                setBlockageMode(3, 3);
            }
            else if(totalBoxes == 36)
            {
                setBlockageMode(5, 4);
            }
            else if(totalBoxes == 49)
            {
                setBlockageMode(6, 4);
            }
            else
            {
                setBlockageMode(totalBoxes/5, totalBoxes/5);
            }
        }
        else if(gamePlayMode == Data.FADEAWAY_PLAYMODE)
        {
            setFadeAwayMode();
        }
    }

    private void setTotalBoxes() {
        if (levelNo <= 9) {
            gridOf = 4;

        } else if (levelNo <= 21) {
            gridOf = 5;
        } else if (levelNo <= 34) {
            gridOf = 6;
        } else if (levelNo <= 140) {
            gridOf = 7;
        }


        totalBoxes = gridOf * gridOf;
        int[] temp = new int[totalBoxes];
        for (int i = 0; i < totalBoxes; i++) {
            temp[i] = 0;
        }
        boxPositionsParent = null;
        setBoxPositionsParent(temp);
        setGameArea(totalBoxes);


    }

    @Override
    protected void setGameArea(int gridSize) {
        TextView gamePlayModeTv = ((Activity)context).findViewById(R.id.gamePlayModeTv);
        gamePlayModeTv.setVisibility(View.GONE);
        ImageView gridDesign = ((Activity) context).findViewById(R.id.designGrid);
        gridDesign.setVisibility(View.GONE);
        TextView levelTextview = ((Activity)context).findViewById(R.id.levelNoTv);
        levelTextview.setText(""+levelNo);
        gridView = ((Activity) context).findViewById(R.id.grid_layout);
        gridView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
////            gridView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        gridView.setHorizontalSpacing(10);
//
        if(levelNo <=9) {

            gridView.setNumColumns(4);
        }
        else if(levelNo <= 21) {
            gridView.setNumColumns(5);
        }
        else if(levelNo <= 34)
        {
            gridView.setNumColumns(6);
        }
        else if(levelNo <= 140)
        {
            gridView.setHorizontalSpacing(10);
            gridView.setVerticalSpacing(10);
            gridView.setNumColumns(7);
            gridView.setPadding(gridView.getPaddingRight(), gridView.getPaddingRight(), gridView.getPaddingRight(), gridView.getPaddingRight());

        }

        customAdapter = new CustomAdapter(context, gridSize, true);
        gridView.setAdapter(customAdapter);




        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageView imageView = view.findViewById(R.id.imageview_threeXItem);
                if(isBoxEmpty(boxPositionsParent, position))
                {
                    if(isAiPlaying && playerTurns == 2)
                    {
                        return;
                    }
                    performAction(boxPositionsParent, imageView, position);
                }
            }
        });
//
//
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setGameLevelInfo();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getGamePlayMode();
                        progressDialog.dismiss();
                    }
                }, 500);
            }
        },1500);
//


    }

    private void setGameLevelInfo() {
        gridView.invalidate();
            int[] tempLevel = levelBlockedBoxes[levelNo];
            int tempVal;
            for(int i=0;i<tempLevel.length;i++)
            {
                 tempVal = tempLevel[i];
                if(tempVal == 3)
                {
                    customAdapter.getImageview(gridView.getChildAt(i)).setVisibility(View.INVISIBLE);
                    boxPositionsParent[i] = tempVal;
                    totalSelectedBoxes++;
                }
            }
    }

    private void setCombinationsForGrid() {
        combinationList = new ArrayList<>();

        addHorizontalCombinations(gridOf);
        addVerticalCombinations(gridOf);
        addDiagonalCombinations(gridOf);
        addAntiDiagonalCombinations(gridOf);

        for(int i=0;i<combinationList.size();i++)
        {
            Log.d("gridinfo", combinationList.get(i)[0] + ", " + combinationList.get(i)[1] + "," +  combinationList.get(i)[2] + "\n");
        }
    }
    private void addAntiDiagonalCombinations(int gridX) {
        int endPos;
        int nextPos = gridX - 1;

        if(gridOf <= 4)
        {
            for (int start=0;start<(gridX*gridX);start++)
            {
                endPos = start + (2 * (nextPos));
                if(endPos <= ((gridX * gridX) - 1))
                {
                    if((((start)/gridX) + 2) == ((endPos)/gridX))
                    {
//                    Log.d("gridinfo", ""+start + "," + (start+(1*(nextPos))) + "," + (start+(2*(nextPos))) +"\n");
                        combinationList.add(new int[]{start, (start+(1*nextPos)), (start+(2*nextPos))});
                    }
                }
            }
        }
        else
        {
            for (int start=0;start<(gridX*gridX);start++)
            {
                endPos = start + (3 * (nextPos));
                if(endPos <= ((gridX * gridX) - 1))
                {
                    if((((start)/gridX) + 3) == ((endPos)/gridX))
                    {
//                    Log.d("gridinfo", ""+start + "," + (start+(1*(nextPos))) + "," + (start+(2*(nextPos))) +"\n");
                        combinationList.add(new int[]{start, (start+(1*nextPos)), (start+(2*nextPos)),(start+(3*nextPos))});
                    }
                }
            }
        }
    }

    private void addHorizontalCombinations(int gridX) {
//        for(int i=0;i<gridX;i++)
//        {

        if(gridOf <= 4)
        {
            for (int start=0;start<(gridX*gridX);start++)
            {
                if((start/gridX) == ((start+2)/gridX))
                {
//                Log.d("gridinfo", ""+start + "," + (start+1) + "," + (start+2) +"\n");
                    combinationList.add(new int[]{start, (start+1), (start+2)});
                }
            }
        }
        else
        {
            for (int start=0;start<(gridX*gridX);start++)
            {
                if((start/gridX) == ((start+3)/gridX))
                {
//                Log.d("gridinfo", ""+start + "," + (start+1) + "," + (start+2) +"\n");
                    combinationList.add(new int[]{start, (start+1), (start+2), (start+3)});
                }
            }
        }
    }

    private void addVerticalCombinations(int gridX) {

        if(gridOf <= 4)
        {
            for (int start=0;start<(gridX*gridX);start++)
            {
                if((start/gridX) <= ((gridX-3)))
                {
//                Log.d("gridinfo", ""+start + "," + (start+(1*gridX)) + "," + (start+(2*gridX)) +"\n");
                    combinationList.add(new int[]{start, (start+(1*gridX)), (start+(2*gridX))});
                }
            }
        }
        else
        {
            for (int start=0;start<(gridX*gridX);start++)
            {
                if((start/gridX) <= ((gridX-4)))
                {
//                Log.d("gridinfo", ""+start + "," + (start+(1*gridX)) + "," + (start+(2*gridX)) +"\n");
                    combinationList.add(new int[]{start, (start+(1*gridX)), (start+(2*gridX)), (start+(3*gridX))});
                }
            }
        }
    }

    public int getAITurnNum2(int opponentPlayerTurn)
    {
        int result = -1;
        for (int i=0;i<combinationList.size();i++)
        {
            final int[] combination = combinationList.get(i);
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == 0)
            {
                return combination[3];
            }
            if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == 0) && boxPositionsParent[combination[2]] == 0 && boxPositionsParent[combination[3]] == opponentPlayerTurn)
            {
                return combination[2];
            }
            if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == 0) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == 0)
            {
                return combination[1];
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == 0 && boxPositionsParent[combination[3]] == opponentPlayerTurn)
            {
                return combination[2];
            }
            if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == 0 && boxPositionsParent[combination[3]] == 0)
            {
                return combination[2];
            }
            if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == 0) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == opponentPlayerTurn)
            {
                return combination[1];
            }
        }
        return result;
    }

    // will stop opponent to win & try to win by itself
    //opponentPlayerTurn == can be both to check whether space is empty or not
    public int getAITurnNum(int opponentPlayerTurn)
    {
        int result = -1;
        if(gridOf <= 4)
        {
            for (int i=0;i<combinationList.size();i++)
            {
                final int[] combination = combinationList.get(i);
                if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == 0)
                {
                    return combination[2];
                }
                if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == 0) && boxPositionsParent[combination[2]] == opponentPlayerTurn)
                {
                    return combination[1];
                }
                if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == opponentPlayerTurn)
                {
                    return combination[0];
                }
            }
        }
        else //5x, 6x, 7x
        {
            for (int i=0;i<combinationList.size();i++)
            {
                final int[] combination = combinationList.get(i);
                if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == 0)
                {
                    return combination[3];
                }
                if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == 0 && boxPositionsParent[combination[3]] == opponentPlayerTurn)
                {
                    return combination[2];
                }
                if((boxPositionsParent[combination[0]] == opponentPlayerTurn) && (boxPositionsParent[combination[1]] == 0) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == opponentPlayerTurn)
                {
                    return combination[1];
                }
                if((boxPositionsParent[combination[0]] == 0) && (boxPositionsParent[combination[1]] == opponentPlayerTurn) && boxPositionsParent[combination[2]] == opponentPlayerTurn && boxPositionsParent[combination[3]] == opponentPlayerTurn)
                {
                    return combination[0];
                }

            }
        }
        return result;
    }





    @Override
    protected void restartMatch() {
        progressDialog.setMessage("Restarting Match");
        progressDialog.show();

           this.boxPositionsParent = null;
           int[] temp = new int[totalBoxes];
           for(int i=0;i<totalBoxes;i++) {
               temp[i] = 0;
           }
           setBoxPositionsParent(temp);

           player1Clicks = 0;
           player2Clicks = 0;
           MainActivity.playerOneClick.setText(0+"");
           MainActivity.playerTwoClick.setText(0+"");

           totalSelectedBoxes = 0;
           playerTurns = 1;

           for (int i = 0; i<gridView.getChildCount();i++)
           {
               ImageView tempImage = customAdapter.getImageview(gridView.getChildAt(i));
               Glide.with(context)
                       .asDrawable()
                       .load(R.drawable.box_bg_level)
                       .into(new SimpleTarget<Drawable>() {
                           @Override
                           public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                               tempImage.setBackground(resource);
                           }
                       });
               Glide.with(context).load(android.R.drawable.screen_background_light_transparent).into(tempImage);
           }

           setGameLevelInfo();


           Glide.with(context).load(R.drawable.player_one_glow).into((ImageView) ((Activity)context).findViewById(R.id.playerOneIv));
           Glide.with(context).load(R.drawable.player_two_dim).into((ImageView) ((Activity)context).findViewById(R.id.playerTwoIv));

           ((Activity)context).findViewById(R.id.playerOneLevelIv).setAlpha(1);
           ((Activity)context).findViewById(R.id.playerTwoLevelIv).setAlpha(0.5f);


           if(gamePlayMode == Data.BLOCKAGE_PLAYMODE)
           {
               blockedBoxesArray.clear();
               blockedBoxesArray.trimToSize();
               blockedBoxesArray = new ArrayList<>();

               setRandomBoxesToBlock();
           }
           else if(gamePlayMode == Data.BARRIER_PLAYMODE)
           {
               blockedBoxesArray.clear();
               blockedBoxesArray.trimToSize();
               blockedBoxesArray = new ArrayList<>();
           }
           else if(gamePlayMode == Data.FADEAWAY_PLAYMODE && recentClickedBoxes.size() != 0)
           {
               int recentFadedPos = recentClickedBoxes.get(0);
//            TextView recentFadedTv = inflatedView_Grid.findViewById(context.getResources().getIdentifier("box"+(recentFadedPos+1)+"fadeTv", "id", context.getPackageName()));
               TextView recentFadedTv = gridView.getChildAt(recentFadedPos).findViewById(R.id.boxFadeTv);
               recentFadedTv.setText(""+3);
               if(recentFadedTv.getVisibility() == View.VISIBLE)
               {
                   recentFadedTv.setVisibility(View.GONE);
               }
               recentClickedBoxes.clear();
               recentClickedBoxes = new ArrayList<>();
           }

           progressDialog.dismiss();


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

    @Override
    protected void aiTurnToPlay(boolean getRandNum) {

        Random random = new Random();
        // to try to win
        int rand = -1;

        if(diffultyLevel == Data.EASY_TTTLEVEL || getRandNum)
        {
            rand = random.nextInt(totalBoxes);
        }
        else
        {
            //4x4
            if(gridOf<=4)
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
                    }
                }
            }
            else
            {
                rand = getAITurnNum(playerTurns);
                if(rand == -1)
                {
                    rand = getAITurnNum(getInversePlayerTurns());
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

    public void performAction(int[] boxPositions, ImageView imageView, int selectedBoxPosition)
    {
        playClickSound();
        boxPositions[selectedBoxPosition] = playerTurns;

        recentClickedBoxes.add(selectedBoxPosition);
        if(gamePlayMode == Data.FADEAWAY_PLAYMODE)
        {
            fadeAwayBoxWorking();
        }
        if(playerTurns == 1)
        {
            Glide.with(context).load(R.drawable.x).into(imageView);


            if(checkPlayerWin(boxPositions))
            {
                listener.onGameEnd(1, levelNo, totalBoxes);
                playerWinSound();
                unlockNextLevel();

            }
            else if(totalSelectedBoxes == totalBoxes-1)
            {
                listener.onGameEnd(0, levelNo, totalBoxes);
                playerWinSound();
            }
            else
            {
                changePlayerTurn(2);
                ++totalSelectedBoxes;
                player1Clicks++;
                MainActivity.playerOneClick.setText(player1Clicks+"");

            }
        }
        else {
            Glide.with(context).load(R.drawable.o).into(imageView);

            if (checkPlayerWin(boxPositions)) {
                listener.onGameEnd(2, levelNo, totalBoxes);
                playerWinSound();
            } else if (totalSelectedBoxes == totalBoxes-1) {
                listener.onGameEnd(0, levelNo, totalBoxes);
                playerWinSound();
            } else {
                changePlayerTurn(1);
                ++totalSelectedBoxes;
                player2Clicks++;
                MainActivity.playerTwoClick.setText(player2Clicks + "");
            }

        }

        if (gamePlayMode == Data.BLOCKAGE_PLAYMODE || gamePlayMode == Data.BARRIER_PLAYMODE) {
            LockBoxWorking();
        }


    }

    @Override
    protected void addCross(int[] combination) {
        for(int i=0;i<combination.length;i++)
        {
            customAdapter.getImageview(gridView.getChildAt(combination[i])).setBackgroundResource(R.drawable.win_bg_levels);
        }
    }

    @Override
    protected boolean checkPlayerWin(int[] boxPositions) {
        boolean response = false;
        if(gridOf <= 4)
        {
            for(int i=0;i<combinationList.size();i++)
            {
                final int[] combination = combinationList.get(i);
                if((boxPositions[combination[0]] ==  this.playerTurns) && (boxPositions[combination[1]] ==  playerTurns) && (boxPositions[combination[2]] ==  playerTurns))
                {
                    response = true;
                    addCross(combination);
                    return response;
                }
            }
        }
        else
        {
            for(int i=0;i<combinationList.size();i++)
            {
                final int[] combination = combinationList.get(i);
                if((boxPositions[combination[0]] ==  this.playerTurns) && (boxPositions[combination[1]] ==  playerTurns) && (boxPositions[combination[2]] ==  playerTurns) && (boxPositions[combination[3]] ==  playerTurns))
                {
                    response = true;
                    this.addCross(combination);
                    return response;
                }
            }
        }
        return response;
    }

    private void addDiagonalCombinations(int gridX) {
        int endPos;
        int nextPos = gridX + 1;
        if(gridOf <= 4)
        {
            for (int start=0;start<(gridX*gridX);start++)
            {
                endPos = start + (2 * (nextPos));
                if(endPos <= ((gridX * gridX) - 1))
                {
                    if((((start)/gridX) + 2) == ((endPos )/gridX))
                    {
//                    Log.d("gridinfo", ""+start + "," + (start+(1*(gridX+1))) + "," + (start+(2*(gridX+1))) +"\n");
                        combinationList.add(new int[]{start, (start+(1*(gridX+1))), (start+(2*(gridX+1)))});
                    }
                }
            }
        }
        else
        {
            for (int start=0;start<(gridX*gridX);start++)
            {
                endPos = start + (3 * (nextPos));
                if(endPos <= ((gridX * gridX) - 1))
                {
                    if((((start)/gridX) + 3) == ((endPos )/gridX))
                    {
//                    Log.d("gridinfo", ""+start + "," + (start+(1*(gridX+1))) + "," + (start+(2*(gridX+1))) +"\n");
                        combinationList.add(new int[]{start, (start+(1*(gridX+1))), (start+(2*(gridX+1))), (start+(3*(gridX+1)))});
                    }
                }
            }
        }

    }

    public void goToNextLevel(int currLevel)
    {
        progressDialog.setMessage("Loading Next Level");
        progressDialog.show();
        SharedPreferences getshared = context.getSharedPreferences(context.getString(R.string.shared_preference_filename), MODE_PRIVATE);
        int level = Integer.parseInt(getshared.getString(Data.TAG_TTT_LEVELSINFO,"0"));

        int nextLevel = currLevel+1;

        if(nextLevel < 140)
        {
            levelNo = nextLevel;
        }

        if((nextLevel > level) && (nextLevel < 140))
        {
            SharedPreferences.Editor editor = getshared.edit();
            String levels = ""+(nextLevel);
            editor.putString(Data.TAG_TTT_LEVELSINFO, levels);
            editor.apply();
        }
//        if((nextLevel < 140) && (nextLevel >= level))
        if(nextLevel <= level)
        {

            resetMatchDataNextLevel();
            ///////////// Main Activity Function Code
//            finish();
//            Toast.makeText(MainActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
//            Intent newIntent = new Intent(MainActivity.this, MainActivity.class);
//            newIntent.putExtra("grid", Data.LEVELS_GRID);
//            newIntent.putExtra("difficulty",nextLevel);
//            newIntent.putExtra("ai",1);
//            startActivity(newIntent);
        }
        else
        {
            progressDialog.dismiss();
            Toast.makeText(context, "More Levels Coming Soon...", Toast.LENGTH_SHORT).show();
        }
    }

    private void unlockNextLevel()
    {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.shared_preference_filename), MODE_PRIVATE);
        int level = Integer.parseInt(preferences.getString(Data.TAG_TTT_LEVELSINFO,"0"));
        if((levelNo < 140) && (levelNo>=level))
        {

            SharedPreferences.Editor editor = preferences.edit();
            String levels = ""+(levelNo+1);
            editor.putString(Data.TAG_TTT_LEVELSINFO, levels);
            editor.apply();
        }
    }

    private void resetMatchDataNextLevel()
    {
        totalSelectedBoxes = 0;
        recentClickedBoxes.clear();
        recentClickedBoxes = new ArrayList<>();

        player1Clicks = 0;
        player2Clicks = 0;
        playerTurns = 1;


        gridView = null;
        customAdapter = null;
        setTotalBoxes();
        setCombinationsForGrid();

//        setGameArea(totalBoxes);
//        for(int i=0;i<boxPositionsParent.length;i++)
//        {
//            boxPositionsParent[i] = 0;
//        }


        ((Activity)context).findViewById(R.id.playerOneLevelIv).setAlpha(1);
        ((Activity)context).findViewById(R.id.playerTwoLevelIv).setAlpha(0.5f);


//        setGameArea(totalBoxes);
    }
}
