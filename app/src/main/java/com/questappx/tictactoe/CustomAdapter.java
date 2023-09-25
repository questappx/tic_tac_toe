package com.questappx.tictactoe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

public class CustomAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    int totalSize;
    View mainView;
    boolean isLevelGrid;

    public CustomAdapter(Context applicationContext, int totalSize) {
        this.context = applicationContext;
        inflter = (LayoutInflater.from(applicationContext));
        this.totalSize = totalSize;
    }

    public CustomAdapter(Context applicationContext, int totalSize, boolean isLevelGrid) {
        this.context = applicationContext;
        inflter = (LayoutInflater.from(applicationContext));
        this.totalSize = totalSize;
        this.isLevelGrid = isLevelGrid;
    }



    public void isLevelGrid(boolean bool)
    {
        isLevelGrid = bool;
    }

    @Override
    public int getCount() {
        return totalSize;
    }
    @Override
    public ImageView getItem(int i) {
        ImageView imagevieww = mainView.findViewById(R.id.imageview_threeXItem);
        return imagevieww;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(totalSize == 9 && !isLevelGrid)
        {
            view = inflter.inflate(R.layout.three_x_item, null); // inflate the layout
        }
        else if(totalSize == 36 && !isLevelGrid)
        {
            view = inflter.inflate(R.layout.six_x_item, null); // inflate the layout
        }
        else if(totalSize == 81 && !isLevelGrid)
        {
            view = inflter.inflate(R.layout.nine_x_item, null); // inflate the layout
        }
        else if(totalSize == 121 && !isLevelGrid)
        {
            view = inflter.inflate(R.layout.eleven_x_item, null); // inflate the layout
        }
        else if(isLevelGrid)
        {

             if(totalSize == 16)
            {
                view = inflter.inflate(R.layout.four_x_level_iten, null); // inflate the layout
            }
            else if(totalSize == 25)
            {
                view = inflter.inflate(R.layout.five_x_level_item, null); // inflate the layout
            }
            else if(totalSize == 36)
            {
                view = inflter.inflate(R.layout.six_x_level_item, null); // inflate the layout
            }
            else if(totalSize == 49)
            {
                view = inflter.inflate(R.layout.seven_x_level_item, null); // inflate the layout
            }
        }


        mainView = view;
//        ImageView icon = (ImageView) view.findViewById(R.id.imageview_threeXItem); // get the reference of ImageView
//        icon.setImageResource(logos[i]); // set logo images
        return view;
    }

    public ImageView getImageview(View parentView)
    {
        return parentView.findViewById(R.id.imageview_threeXItem);
    }

}
