package com.questappx.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicTac {
    protected List<int[]> combinationList;
    protected Context context;
    protected int playerTurns = 1;
    int recentPickedBox = -1;
    protected int totalSelectedBoxes = 0;
    protected int totalBoxes;
    protected int player1Clicks = 0, player2Clicks = 0;
    protected boolean isAiPlaying;
    protected ArrayList<Integer> recentClickedBoxes;
    protected int[] boxPositionsParent = {};
    protected onGameListener listener;
    protected ArrayList<Integer> blockedBoxesArray;
    int removeAtNumModulus, howManyBoxesToLock;
    protected int gamePlayMode;
    MediaPlayer mediaPlayerClick;
    protected GridView gridView;
    protected CustomAdapter customAdapter;


    public TicTac(Context context, int totalBoxes, int gamePlayMode)
    {
        this.context = context;
        this.totalBoxes = totalBoxes;
        this.gamePlayMode = gamePlayMode;

        recentClickedBoxes = new ArrayList<>();
        if(mediaPlayerClick == null) {
            mediaPlayerClick = MediaPlayer.create(getContext(), R.raw.click);
        }

    }


    public void setCombinationList(List<int[]> combinationList) {
        this.combinationList = combinationList;
    }

    protected void setBlockageMode(int blockBoxes, int removeAtClicks)
    {
        blockedBoxesArray = new ArrayList<>();
        howManyBoxesToLock = blockBoxes;
        removeAtNumModulus = removeAtClicks;

        if(gamePlayMode == Data.BLOCKAGE_PLAYMODE)
        {
            setRandomBoxesToBlock();
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    protected void setRandomBoxesToBlock()
    {
        Random random = new Random();

        int rand;
        int j = blockedBoxesArray.size();
        while(j < howManyBoxesToLock)
        {
            rand = random.nextInt(totalBoxes);
            if(!inArrayorNot(rand, blockedBoxesArray))
            {
                if(isBoxEmpty(boxPositionsParent, rand))
                {
                    blockedBoxesArray.add(rand);
                    totalSelectedBoxes++;
                    j = blockedBoxesArray.size();
                }
            }
        }


        for(int i = 0; i< blockedBoxesArray.size(); i++)
        {
            int boxtoBlock = blockedBoxesArray.get(i);
            boxPositionsParent[boxtoBlock] = Data.BOX_IS_LOCKED;
//            imageViewArrayList.get(boxtoBlock).setImageResource(R.drawable.lock);
            Glide.with(context).load(R.drawable.lock).into(customAdapter.getImageview(gridView.getChildAt(boxtoBlock)));
//            Glide.with(context).load(R.drawable.lock).into(imageViewArrayList.get(boxtoBlock));
        }
    }

    private boolean inArrayorNot(int number, ArrayList<Integer> arrayList)
    {
        boolean result = false;

        for(int i=0;i<arrayList.size();i++)
        {
            if(arrayList.get(i) == number)
            {
                return true;
            }
        }


        return result;
    }


    public void setGameListener(onGameListener listener)
    {
        this.listener = listener;
    }

    public void setBoxPositionsParent(int[] boxPositionsParent) {
        this.boxPositionsParent = boxPositionsParent;
    }




    //return reverse;
    protected boolean isBoxEmpty(int[] boxPositions, int boxPosition)
    {
        boolean response = false;

        if(boxPositions[boxPosition] == 0)
        {
            response = true;
        }

        return response;
    }

    protected void setGameArea(int gridSize)
    {
        ImageView gridDesign = ((Activity)context).findViewById(R.id.designGrid);
        if(gridSize == 9)
        {
            gridDesign.setImageResource(R.drawable.design_bg_gamelayot);
            gridView = ((Activity)context).findViewById(R.id.grid_layout);
            gridView.setNumColumns(3);
            customAdapter = new CustomAdapter(context, gridSize);
            gridView.setAdapter(customAdapter);
        }
        else if(gridSize == 36)
        {
            gridDesign.setImageResource(R.drawable.bg_design_gamelayout_four);
            gridView = ((Activity)context).findViewById(R.id.grid_layout);
            customAdapter = new CustomAdapter(context, gridSize);
            gridView.setNumColumns(6);
            gridView.setAdapter(customAdapter);
        }
        else if(gridSize == 81)
        {
            gridDesign.setImageResource(R.drawable.bg_design_gamelayout_five);
            gridView = ((Activity)context).findViewById(R.id.grid_layout);
            customAdapter = new CustomAdapter(context, gridSize);
            gridView.setNumColumns(9);
            gridView.setVerticalSpacing(2);
            gridView.setHorizontalSpacing(2);
            gridView.setPadding(gridView.getPaddingRight(), gridView.getPaddingRight(), gridView.getPaddingRight(), gridView.getPaddingRight());
            gridView.setAdapter(customAdapter);
        }
        else if(gridSize == 121)
        {
            gridDesign.setImageResource(R.drawable.bg_design_gamelayout_six);
            gridView = ((Activity)context).findViewById(R.id.grid_layout);
            customAdapter = new CustomAdapter(context, gridSize);
            gridView.setNumColumns(11);
            gridView.setVerticalSpacing(2);
            gridView.setHorizontalSpacing(2);
            gridView.setPadding(gridView.getPaddingRight(), gridView.getPaddingRight(), gridView.getPaddingRight(), gridView.getPaddingRight());
            gridView.setAdapter(customAdapter);
        }


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

                    //if player uses all his turns, then he can only readjust
                    if(gamePlayMode == Data.PICKAGE_PLAYMODE && playerTurns == 1 && player1Clicks == combinationList.get(0).length)
                    {
                        Toast.makeText(context, "You used all your turns. Pick one & Replace it", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(gamePlayMode == Data.PICKAGE_PLAYMODE && playerTurns == 2 && player2Clicks == combinationList.get(0).length)
                    {
                        Toast.makeText(context, "You used all your turns. Pick one & Replace it", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        performAction(boxPositionsParent, imageView, position);
                    }
                }
                else
                {
                    if(gamePlayMode == Data.PICKAGE_PLAYMODE)
                    {
                        PickageModeWorking(position);
                    }
                }
            }
        });
    }

    protected void PickageModeWorking(int position) {

        if(boxPositionsParent[position] == playerTurns && recentPickedBox != position)
        {
            boxPositionsParent[position] = 0;
            totalSelectedBoxes--;
            recentClickedBoxes.remove((Object)position);
            recentPickedBox = position;
            ImageView temp = customAdapter.getImageview(gridView.getChildAt(position));
            int padding = 2 * (customAdapter.getImageview(gridView.getChildAt(recentClickedBoxes.get(0))).getPaddingRight());
            temp.setPadding(padding, padding, padding, padding);
            if(playerTurns == 1)
            {
                player1Clicks--;
                MainActivity.playerOneClick.setText(player1Clicks+"");
            }
            else
            {
                player2Clicks--;
                MainActivity.playerTwoClick.setText(player2Clicks+"");
            }
        }
    }

    public void performAction(int[] boxPositions, ImageView imageView, int selectedBoxPosition)
    {
        if(recentPickedBox == selectedBoxPosition && gamePlayMode == Data.PICKAGE_PLAYMODE)
        {
            Toast.makeText(context, "You can't place here : " + selectedBoxPosition, Toast.LENGTH_SHORT).show();
            return;
        }

        if(recentPickedBox != -1 && gamePlayMode == Data.PICKAGE_PLAYMODE)
        {
            int padding = customAdapter.getImageview(gridView.getChildAt(recentClickedBoxes.get(0))).getPaddingRight();
            ImageView tempImage = customAdapter.getImageview(gridView.getChildAt(recentPickedBox));
            Glide.with(context).load(android.R.drawable.screen_background_light_transparent).into(tempImage);
            tempImage.setPadding(padding, padding, padding, padding);
            tempImage.invalidate();
            recentPickedBox = -1;

        }

        recentClickedBoxes.add(selectedBoxPosition);
        playClickSound();
        boxPositions[selectedBoxPosition] = playerTurns;
        if(playerTurns == 1)
        {
//            imageView.setImageResource(R.drawable.x);
            Glide.with(context).load(R.drawable.x).into(imageView);

            if(gamePlayMode == Data.FADEAWAY_PLAYMODE)
            {
                fadeAwayBoxWorking();
            }

            if(checkPlayerWin(boxPositions))
            {
//                WinDialog winDialog = new WinDialog(context,this,  "Player One has won the match!");
//                winDialog.show();
                listener.onGameEnd(1, gamePlayMode, totalBoxes);
                playerWinSound();

            }
            else if(totalSelectedBoxes == totalBoxes-1)
            {
//                WinDialog winDialog = new WinDialog(context, this, "It is draw!");
//                winDialog.show();
                listener.onGameEnd(0, gamePlayMode, totalBoxes);
                playerWinSound();
            }
            else
            {
                changePlayerTurn(2);
                ++totalSelectedBoxes;
                player1Clicks++;
                MainActivity.playerOneClick.setText(player1Clicks+"");

                if(gamePlayMode == Data.BLOCKAGE_PLAYMODE || gamePlayMode == Data.BARRIER_PLAYMODE)
                {
                    LockBoxWorking();
                }
                else if(gamePlayMode == Data.PICKAGE_PLAYMODE)
                {
                    if(checkPlayerWin(boxPositions))
                    {
                        listener.onGameEnd(2, gamePlayMode, totalBoxes);
                        playerWinSound();
                    }
                }
            }
        }
        else {
//            imageView.setImageResource(R.drawable.o);
            Glide.with(context).load(R.drawable.o).into(imageView);

            if (gamePlayMode == Data.FADEAWAY_PLAYMODE) {
                fadeAwayBoxWorking();
            }

            if (checkPlayerWin(boxPositions)) {
//                WinDialog winDialog = new WinDialog(context, this, "Player Two has won the match!");
//                winDialog.show();
                listener.onGameEnd(2, gamePlayMode, totalBoxes);
                playerWinSound();
            } else if (totalSelectedBoxes == totalBoxes-1) {
//                WinDialog winDialog = new WinDialog(context,this,  "It is draw!");
//                winDialog.show();
                listener.onGameEnd(0, gamePlayMode, totalBoxes);
                playerWinSound();
            } else {
                changePlayerTurn(1);
                ++totalSelectedBoxes;
                player2Clicks++;
                MainActivity.playerTwoClick.setText(player2Clicks + "");

                if (gamePlayMode == Data.BLOCKAGE_PLAYMODE || gamePlayMode == Data.BARRIER_PLAYMODE) {
                    LockBoxWorking();
                }
                else if(gamePlayMode == Data.PICKAGE_PLAYMODE)
                {
                    if(checkPlayerWin(boxPositions))
                    {
                        listener.onGameEnd(1, gamePlayMode, totalBoxes);
                        playerWinSound();
                    }
                }
            }
        }
    }

    public void setFadeAwayMode()
    {
        gamePlayMode = Data.FADEAWAY_PLAYMODE;
    }

    protected void fadeAwayBoxWorking() {
        if(recentClickedBoxes.size() == 0)
        {
//            if no box is clicked yet
            return;
        }

        int boxToFadePos = recentClickedBoxes.get(0);
        TextView boxToFadeTv = gridView.getChildAt(boxToFadePos).findViewById(R.id.boxFadeTv);
        if(boxToFadeTv.getVisibility() == View.GONE)
        {
            boxToFadeTv.setVisibility(View.VISIBLE);
        }

        int remainingFadeClicks = Integer.parseInt(boxToFadeTv.getText().toString());
        if(remainingFadeClicks == 1)
        {
            boxToFadeTv.setVisibility(View.GONE);
            boxToFadeTv.setText(""+3);
            recentClickedBoxes.remove(0);
            Glide.with(context).load(android.R.color.transparent).into(customAdapter.getImageview(gridView.getChildAt(boxToFadePos)));
//            Glide.with(context).load(android.R.color.transparent).into(imageViewArrayList.get(boxToFadePos));
            boxPositionsParent[boxToFadePos] = 0;
            totalSelectedBoxes--;

            int newBoxTofade = recentClickedBoxes.get(0);
            //error
            TextView newBoxToFadeTv = gridView.getChildAt(newBoxTofade).findViewById(R.id.boxFadeTv);
//            TextView newBoxToFadeTv = findViewById(context.getResources().getIdentifier("box"+(newBoxTofade+1)+"fadeTv", "id", context.getPackageName()));
            if(newBoxToFadeTv.getVisibility() == View.GONE)
            {
                newBoxToFadeTv.setVisibility(View.VISIBLE);
            }

        }
        else
        {
            boxToFadeTv.setText(String.valueOf(remainingFadeClicks-1));
        }



    }

    protected void LockBoxWorking() {
        int userClickedBoxes = totalSelectedBoxes - blockedBoxesArray.size();
        if(gamePlayMode == Data.BLOCKAGE_PLAYMODE)
        {
            //will remove locks
            if(blockedBoxesArray.size() == 0)
            {
                return;
            }
            //        4 % 2 == 0
            if((userClickedBoxes) % removeAtNumModulus == 0)
            {
                //first blocked box
                int blockedBox = blockedBoxesArray.get(0);
                boxPositionsParent[blockedBox] = 0;
                Glide.with(context).load(android.R.color.transparent).into(customAdapter.getImageview(gridView.getChildAt(blockedBox)));
                blockedBoxesArray.remove(0);
                playLockSound(0);
                totalSelectedBoxes--;
            }
        }
        else if(gamePlayMode == Data.BARRIER_PLAYMODE)
        {
//            will add locks

            if((userClickedBoxes % removeAtNumModulus) == 0)
            {
                Random random = new Random();
                int rand = random.nextInt(totalBoxes);
                while(!isBoxEmpty(boxPositionsParent,rand))
                {
                    rand = random.nextInt(totalBoxes);
                }
                if(isBoxEmpty(boxPositionsParent, rand))
                {
                    boxPositionsParent[rand] = Data.BOX_IS_LOCKED;
//                    imageViewArrayList.get(rand).setImageResource(R.drawable.lock);
                    Glide.with(context).load(R.drawable.lock).into(customAdapter.getImageview(gridView.getChildAt(rand)));
                    playLockSound(1);
                    blockedBoxesArray.add(rand);
                    ++totalSelectedBoxes;

                    if(totalSelectedBoxes == totalBoxes)
                    {
                        listener.onGameEnd(0, gamePlayMode, totalBoxes);
                        playerWinSound();
                    }
                }
            }
        }

    }

    protected void aiTurnToPlay(boolean getRandNum) {

    }

    protected boolean checkPlayerWin(int[] boxPositions)
    {
        boolean response = false;


        return response;
    }

    protected void addCross(int[] combination) {


//        for(int i=0;i<combination.length;i++)
//        {
//            imageViewArrayList.get(combination[i]).setPadding(0,0,0,0);
//            imageViewArrayList.get(combination[i]).setScaleType(ImageView.ScaleType.CENTER_CROP);
//        }

        for(int i=0;i<combination.length;i++)
        {
//            imageViewArrayList.get(combination[i]).setBackgroundResource(R.drawable.win_bg);
            customAdapter.getImageview(gridView.getChildAt(combination[i])).setBackgroundResource(R.drawable.win_bg);
        }

    }

//    public void drawLine( int startX, int startY, int endX, int endY) {
//        ImageView lineView = ((Activity) context).findViewById(R.id.canvasLineView);
//        int width = lineView.getWidth();
//        int height = lineView.getHeight();
//        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        Paint paint = new Paint();
//        paint.setColor(Color.WHITE);
//        paint.setStrokeWidth(10);
//        canvas.drawLine(startX, startY, endX, endY, paint);
//
//        lineView.setImageBitmap(bitmap);
////        lineView.setImageDrawable(new BitmapDrawable(context.getResources(), bitmap));
//
//    }



    public void changePlayerTurn(int currentPlayerTurn)
    {
        playerTurns = currentPlayerTurn;
        if(playerTurns == 1)
        {
            ((Activity)context).findViewById(R.id.playerOneLevelIv).setAlpha(1);
            ((Activity)context).findViewById(R.id.playerTwoLevelIv).setAlpha(0.5f);

            Glide.with(context).load(R.drawable.player_one_glow).into((ImageView) ((Activity)context).findViewById(R.id.playerOneIv));
            Glide.with(context).load(R.drawable.player_two_dim).into((ImageView) ((Activity)context).findViewById(R.id.playerTwoIv));
//            Glide.with(context).load(R.drawable.player_one_glow).into(MainActivity.playerOneIv);
//            Glide.with(context).load(R.drawable.player_two_dim).into(MainActivity.playerTwoIv);

        }
        else if(playerTurns == 2)
        {
//            MainActivity.playerOneIv.setImageResource(R.drawable.player_one_dim);
//            MainActivity.playerTwoIv.setImageResource(R.drawable.player_two_glow);
            ((Activity)context).findViewById(R.id.playerOneLevelIv).setAlpha(0.5f);
            ((Activity)context).findViewById(R.id.playerTwoLevelIv).setAlpha(1);

            Glide.with(context).load(R.drawable.player_one_dim).into((ImageView) ((Activity)context).findViewById(R.id.playerOneIv));
            Glide.with(context).load(R.drawable.player_two_glow).into((ImageView) ((Activity)context).findViewById(R.id.playerTwoIv));

//            Glide.with(context).load(R.drawable.player_one_dim).into(MainActivity.playerOneIv);
//            Glide.with(context).load(R.drawable.player_two_glow).into(MainActivity.playerTwoIv);

            //if ai is playing then
            if(isAiPlaying)
            {
                if(gamePlayMode == Data.PICKAGE_PLAYMODE)
                {
                    // if ai used all his turns
                    if(player2Clicks == combinationList.get(0).length)
                    {
                        int i=0;
                        //while not we find players 2 clicked box
                        while(!((boxPositionsParent[recentClickedBoxes.get(i)] == 2) && (recentPickedBox != recentClickedBoxes.get(i))))
                        {
                            i++;
                        }
                        int player2BoxPos = recentClickedBoxes.get(i);
                        ImageView temp = customAdapter.getImageview(gridView.getChildAt(player2BoxPos));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                boxPositionsParent[player2BoxPos] = 0;
                                player2Clicks--;
                                recentClickedBoxes.remove((Object)player2BoxPos);
                                recentPickedBox = player2BoxPos;
                                totalSelectedBoxes--;
                                Glide.with(context).load(R.drawable.box_bg).into(temp);
                            }
                        }, 100);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                              aiTurnToPlay(false);
                            }
                        }, 200);
                    }
                    else
                    {
                        aiTurnToPlay(false);
                    }
//                    Random random = new Random();
//                    int rand = random.nextInt(1);
//                    if(rand == 1)
//                    {
//                        //use previous turns instead of new
//                    }
                }
                else
                {
                    //just play
                    aiTurnToPlay(false);
                }
            }
        }


    }

    protected boolean isAiPlayed()
    {
        Toast.makeText(context, "isAIPlayerd", Toast.LENGTH_SHORT).show();
        final boolean[] response = {false};
        if(isAiPlaying && playerTurns == 2)
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(playerTurns == 1)
                    {
                        Toast.makeText(context, "Player 1 turn now", Toast.LENGTH_SHORT).show();
                        response[0] = true;
                    }
                }
            }, 1000);
        }
        else{
            response[0] = true;
        }
        return response[0];

    }

    protected void restartMatch()
    {
        if(isAiPlaying && playerTurns == 2)
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    restartMatch();
                }
            }, 200);
        }

        player1Clicks = 0;
        player2Clicks = 0;
        MainActivity.playerOneClick.setText(0+"");
        MainActivity.playerTwoClick.setText(0+"");

        totalSelectedBoxes = 0;
        playerTurns = 1;


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
        else if(gamePlayMode == Data.PICKAGE_PLAYMODE)
        {
            if(recentPickedBox != -1)
            {
                int padding = customAdapter.getImageview(gridView.getChildAt(recentClickedBoxes.get(1))).getPaddingRight();

            }
        }
    }

    protected void playClickSound()
    {
        if(FirstActivity.IS_SOUND_ENABLED && mediaPlayerClick != null)
        {
            mediaPlayerClick.seekTo(0);
            mediaPlayerClick.start();
        }
    }

    protected void playLockSound(int sound)
    {
        if(FirstActivity.IS_SOUND_ENABLED)
        {
            MediaPlayer mediaPlayer;
            if(sound == 1)
            {
                mediaPlayer =  MediaPlayer.create(getContext(), R.raw.lock);
            }
            else {
                mediaPlayer =  MediaPlayer.create(getContext(), R.raw.unlock);
            }
            mediaPlayer.start();
            if(!mediaPlayer.isPlaying())
            {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
    }
    protected void playerWinSound()
    {
        if(FirstActivity.IS_SOUND_ENABLED)
        {
            MediaPlayer mediaPlayer =  MediaPlayer.create(getContext(), R.raw.win);
            mediaPlayer.start();
            if(!mediaPlayer.isPlaying())
            {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
    }

    public void playMultiPlayer()
    {
        isAiPlaying = false;
    }

    public void playSingleMode()
    {
        isAiPlaying = true;
    }


    public void DeallocateMemory()
    {
        recentClickedBoxes = null;
        blockedBoxesArray  = null;
        combinationList = null;
        boxPositionsParent = null;



        if(mediaPlayerClick != null)
        {
            mediaPlayerClick.stop();
            mediaPlayerClick.release();
            mediaPlayerClick = null;
        }

//        Glide.get(context).clearDiskCache();
//        Glide.get(context).clearMemory();
    }
}
