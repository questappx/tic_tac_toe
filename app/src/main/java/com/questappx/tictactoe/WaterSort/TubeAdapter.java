package com.questappx.tictactoe.WaterSort;

import static android.content.Context.MODE_PRIVATE;
import static com.questappx.tictactoe.Data.DEFAULT_UNDOS_LEFTS;
import static com.questappx.tictactoe.Data.NO_COLOR;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.questappx.tictactoe.Data;
import com.questappx.tictactoe.FirstActivity;
import com.questappx.tictactoe.R;

import java.util.ArrayList;
import java.util.List;

public class TubeAdapter extends BaseAdapter {
    private Context context;
    private List<Tube> tubes;
    GridView gridView;

    int undoLefts = DEFAULT_UNDOS_LEFTS;

    onSortEndListener listener;

    int pickedTubePos = -1;
    MediaPlayer mediaPlayerClick;
    int levelNo;

    int undoLength = 0;
    int undoToPos;
    int undoFromPos;

    public TubeAdapter(Context context, GridView gridView, List<Tube> tubes, int levelNo, onSortEndListener listener) {
        this.context = context;
        this.tubes = tubes;
        this.gridView = gridView;
        this.levelNo = levelNo;
        this.listener = listener;

        if(mediaPlayerClick == null) {
            mediaPlayerClick = MediaPlayer.create(context, R.raw.click);
        }
    }

    public TubeAdapter(Context context)
    {
        this.context = context;
    }

    @Override
    public int getCount() {
        return tubes.size();
    }

    @Override
    public Tube getItem(int position) {
        return tubes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tube tube = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.tube_item, parent, false);
        }
        ImageView colorView1 = convertView.findViewById(R.id.color1);
        ImageView colorView2 = convertView.findViewById(R.id.color2);
        ImageView colorView3 = convertView.findViewById(R.id.color3);
        ImageView colorView4 = convertView.findViewById(R.id.color4);


        List<Integer> colors = tube.getColors();
        if (colors.size() > 0) {
            if(colors.get(0) == NO_COLOR)
            {
                colorView1.setBackgroundColor(android.R.color.transparent);
            }
            else
            {
                colorView1.setBackgroundColor(ContextCompat.getColor(context, colors.get(0)));
            }
        } else {
            colorView2.setBackgroundColor(0);
        }
        if (colors.size() > 1) {
            if(colors.get(1) == NO_COLOR)
            {
                colorView2.setBackgroundColor(android.R.color.transparent);
            }
            else {
                colorView2.setBackgroundColor(ContextCompat.getColor(context, colors.get(1)));
            }
        } else {
            colorView2.setBackgroundColor(0);
        }
        if (colors.size() > 2) {
            if(colors.get(2) == NO_COLOR)
            {
                colorView3.setBackgroundColor(android.R.color.transparent);
            }
            else {

                colorView3.setBackgroundColor(ContextCompat.getColor(context, colors.get(2)));
            }
        } else {
            colorView3.setBackgroundColor(0);
        }
        if (colors.size() > 3) {
            if(colors.get(3) == NO_COLOR)
            {
                colorView4.setBackgroundColor(android.R.color.transparent);
            }
            else {

                colorView4.setBackgroundColor(ContextCompat.getColor(context, colors.get(3)));
            }
        } else {
            colorView4.setBackgroundColor(0);
        }


        View finalConvertView = convertView;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tubeWorking(finalConvertView, position);
            }
        });

        return convertView;
    }

    private void tubeWorking(View parentView, int clickedTubePos) {
//        no tube is picked yet then pick clickedTube
        undoLength = 0;
        if(pickedTubePos == -1)
        {

            if(tubes.get(clickedTubePos).isEmpty())
            {
                return;
            }
            pickedTubePos = clickedTubePos;
            animateTubeUp(parentView);
        }
        else if(pickedTubePos == clickedTubePos)
        {//Same Tube is clicked again, so animate down the tube
            animateTubeDown(parentView);
        }
        else
        {//another tube is clicked check is we can add color
//            if(!tubes.get(clickedTubePos).isFull())
//            {

                boolean isColorAdded = false;
                while(tubes.get(clickedTubePos).addColor(tubes.get(pickedTubePos).getTopColor()))
                {
                    undoLength++;
                    playClickSound();
                    undoFromPos = clickedTubePos;
                    undoToPos = pickedTubePos;
                    isColorAdded = true;
                    tubes.get(pickedTubePos).removeColor();
                }
                if(isColorAdded)
                {
                    animateTubeDown(gridView.getChildAt(pickedTubePos));
                    notifyDataSetChanged();
                    if(checkWin())
                    {
                        listener.onGameEnd();
                        playerWinSound();
                        unlockNextLevel();
                    }
                }
//            }

        }
    }

    private boolean checkWin()
    {
        for(int i=0;i<tubes.size();i++)
        {
            if(!tubes.get(i).isComplete())
            {
                return false;
            }
        }
        return true;
    }





    public void setTubes(List<Tube> tubes) {
        this.tubes = tubes;
    }

    public ImageView getTubeView(View parentView)
    {
        return parentView.findViewById(R.id.tubeItem);
    }

    public void animateTubeUp(View view)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0f, -50f);
        animator.setDuration(300); // Set the duration of the animation in milliseconds
        animator.start(); // Start the animation
    }

    public void animateTubeDown(View view)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0f, 0f);
        animator.setDuration(300); // Set the duration of the animation in milliseconds
        animator.start(); // Start the animation
        pickedTubePos = -1;
    }

    public boolean goToNextLevel(int currLevel)
    {
        SharedPreferences getshared = context.getSharedPreferences(context.getString(R.string.shared_preference_filename), MODE_PRIVATE);
        int level = Integer.parseInt(getshared.getString(Data.TAG_WATERSORT_LEVELSINFO,"0"));

        undoLefts = DEFAULT_UNDOS_LEFTS;

        int nextLevel = currLevel+1;

        if(nextLevel < WaterSortLevelActivity.tubeColors.length-1)
        {
             levelNo = nextLevel;
        }

//        if user is going to play this level for first time then update database
        if((nextLevel > level) && (nextLevel < WaterSortLevelActivity.tubeColors.length-1))
        {
            SharedPreferences.Editor editor = getshared.edit();
            String levels = ""+(nextLevel);
            editor.putString(Data.TAG_WATERSORT_LEVELSINFO, levels);
            editor.apply();
        }
        if(nextLevel <= level)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void unlockNextLevel()
    {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.shared_preference_filename), MODE_PRIVATE);
        int level = Integer.parseInt(preferences.getString(Data.TAG_WATERSORT_LEVELSINFO,"0"));
        if((levelNo < WaterSortLevelActivity.tubeColors.length-1) && (levelNo>=level))
        {

            SharedPreferences.Editor editor = preferences.edit();
            String levels = ""+(levelNo+1);
            editor.putString(Data.TAG_WATERSORT_LEVELSINFO, levels);
            editor.apply();
        }
    }

    public void undoMoves()
    {
        if(undoLefts == 0)
        {
            Toast.makeText(context, "No Undo Left", Toast.LENGTH_SHORT).show();
            return;
        }
        if(undoLength != 0 && undoFromPos != -1 && undoToPos != -1)
        {
            for(int i=0;i<undoLength;i++)
            {
                tubes.get(undoToPos).setUndoColor(tubes.get(undoFromPos).getTopColor());
                tubes.get(undoFromPos).removeColor();
            }
            notifyDataSetChanged();
            undoFromPos = -1;
            undoToPos = -1;
            undoLefts--;
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

    protected void playerWinSound()
    {
        if(FirstActivity.IS_SOUND_ENABLED)
        {
            MediaPlayer mediaPlayer =  MediaPlayer.create(context, R.raw.win);
            mediaPlayer.start();
            if(!mediaPlayer.isPlaying())
            {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
    }
}
