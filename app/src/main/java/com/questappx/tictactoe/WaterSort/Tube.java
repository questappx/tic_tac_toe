package com.questappx.tictactoe.WaterSort;

import static com.questappx.tictactoe.Data.NO_COLOR;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.credentials.IdentityProviders;

import java.util.ArrayList;
import java.util.List;

public class Tube {
    private List<Integer> colors = new ArrayList<>();

    int totalSize = 4;

    int currSize = 0;

    public Tube(List<Integer> colors) {
        this.colors = colors;
    }

    public Tube()
    {

    }

    public void setTotalSize(int totalSize)
    {
        this.totalSize = totalSize;
    }

    public int getTopColor()
    {
        if(colors.isEmpty())
        {
            return NO_COLOR;
        }
        int index = 0;

        while(index<totalSize)
        {
            if(colors.get(index) == NO_COLOR)
            {
                ++index;
            }
            else
            {
                break;
            }
        }
        if(index == totalSize)
        {
            return NO_COLOR;
        }
        return colors.get(index);
    }

    public int getSize()
    {
        int temp = 0;
        for(int i=0;i<totalSize;i++)
        {
            if(colors.get(i) != NO_COLOR)
            {
                temp++;
            }

        }
        return temp;
    }


    public List<Integer> getColors() {
        return colors;
    }

    public boolean isEmpty() {
        return getSize() == 0;
    }

    public boolean isFull() {
        int tempSize = 0;
        for(int i = 0; i<totalSize; i++)
        {
            if(colors.get(i) != NO_COLOR)
            {
                tempSize++;
            }
        }
        return tempSize == totalSize;
//        int color = colors.get(0);
//        for (int i = 1; i < colors.size(); i++) {
//            if (colors.get(i) != color) {
//                return false;
//            }
//        }
    }

    public boolean isComplete()
    {
        int color = colors.get(0);
        boolean result = true;
        for(int block= 0;block < totalSize;block++)
        {
            if(colors.get(block) != color)
            {
                result = false;
            }
        }
        return result;
    }

    public boolean addColor(int color) {
        if(isFull())
        {
            return false;
        }
        if (isEmpty()) {
            colors.set(totalSize -1,color);
            return true;
        }
        else
        {
            int depth = 0;
            while((depth < totalSize))
            {
                if( (colors.get(depth) == NO_COLOR))
                {
                    depth++;
                }
                else
                {
                    break;
                }
            }


            Log.d("depth", ""+depth);
            //if color at bottom of depth is same then add it at depth Pos
            if(colors.get(depth) == color)
            {
                colors.set(depth-1, color);
                return true;
            }
        }
        return false;
    }

    public boolean removeColor() {
        if (!colors.isEmpty()) {
            int depth = 0;
            while(colors.get(depth) == NO_COLOR)
            {
                depth++;
            }
            colors.set(depth,NO_COLOR);
            return true;
        }
        return false;
    }

    public void setRandomColor(int color) {
        colors.add(color);


    }

    public void setUndoColor(int color) {
//        to get top empty position
        colors.set(totalSize - getSize()-1 ,color);


    }
}

