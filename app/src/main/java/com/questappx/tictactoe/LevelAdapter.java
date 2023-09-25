package com.questappx.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.RecyclerHolder>{

    Context context;
    int totalSize;
    int maxUnlockedLevels;
    LevelClickListener listener;



    public LevelAdapter(Context context, int totalSize, int maxUnlockedLevels, LevelClickListener listener) {
        this.context = context;
        this.totalSize = totalSize;
        this.listener = listener;
        this.maxUnlockedLevels = maxUnlockedLevels;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.level_item, null, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, @SuppressLint("RecyclerView") int position) {

        if(isLevelUnlocked(position))
        {
            holder.imageviewLocked.setVisibility(View.GONE);
        }
        else
        {
            holder.imageviewLocked.setVisibility(View.VISIBLE);
        }

        holder.textLevel.setText(""+ (position ));

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });

        holder.imageviewLocked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Play Previous Levels to Unlock it", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean isLevelUnlocked(int position) {
        if (position <= maxUnlockedLevels) {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void updateMaxUnlockedLevel(int maxUnlockedLevels)
    {
        this.maxUnlockedLevels = maxUnlockedLevels;
    }


    @Override
    public int getItemCount() {
        return totalSize;
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {

        ImageView imageView, imageviewLocked;
        TextView textLevel;

        public RecyclerHolder(View view) {
            super(view);

            imageView = view.findViewById(R.id.imageview_level);
            imageviewLocked = view.findViewById(R.id.imageview_locked);
            textLevel = view.findViewById(R.id.textlevel);
        }
    }

}
