package com.questappx.tictactoe.WaterSort;

import static com.questappx.tictactoe.Data.BLUE_COLOR;
import static com.questappx.tictactoe.Data.DARKRED_COLOR;
import static com.questappx.tictactoe.Data.GREEN_COLOR;
import static com.questappx.tictactoe.Data.GREY_COLOR;
import static com.questappx.tictactoe.Data.NO_COLOR;
import static com.questappx.tictactoe.Data.ORANGE_COLOR;
import static com.questappx.tictactoe.Data.PURPLE_COLOR;
import static com.questappx.tictactoe.Data.RED_COLOR;
import static com.questappx.tictactoe.Data.SKYBLUE_COLOR;
import static com.questappx.tictactoe.Data.YELLOW_COLOR;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.ProgressUpdater;
import androidx.work.impl.background.systemalarm.ConstraintProxyUpdateReceiver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.lang.UProperty;
import android.os.Bundle;
import android.text.method.DateKeyListener;
import android.view.RoundedCorner;
import android.view.contentcapture.DataRemovalRequest;

import com.android.volley.toolbox.DiskBasedCache;
import com.questappx.tictactoe.Data;
import com.questappx.tictactoe.LevelAdapter;
import com.questappx.tictactoe.LevelClickListener;
import com.questappx.tictactoe.R;

import java.util.EventListener;

public class WaterSortLevelActivity extends AppCompatActivity {

    public static int[][][] tubeColors = {
            {//1
                    {NO_COLOR, NO_COLOR, NO_COLOR, SKYBLUE_COLOR},
                    {NO_COLOR, SKYBLUE_COLOR,SKYBLUE_COLOR, SKYBLUE_COLOR}
            },
            {//2
                    {RED_COLOR, SKYBLUE_COLOR, RED_COLOR, SKYBLUE_COLOR},
                    {SKYBLUE_COLOR, RED_COLOR,SKYBLUE_COLOR, RED_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//3
                    {SKYBLUE_COLOR, YELLOW_COLOR, RED_COLOR, SKYBLUE_COLOR},
                    {SKYBLUE_COLOR, YELLOW_COLOR,RED_COLOR, RED_COLOR},
                    {YELLOW_COLOR, RED_COLOR, SKYBLUE_COLOR, YELLOW_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//4
                    {RED_COLOR, RED_COLOR, YELLOW_COLOR, SKYBLUE_COLOR},
                    {YELLOW_COLOR, SKYBLUE_COLOR,YELLOW_COLOR, SKYBLUE_COLOR},
                    {RED_COLOR, YELLOW_COLOR, SKYBLUE_COLOR, RED_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//5
                    {BLUE_COLOR, SKYBLUE_COLOR, RED_COLOR, GREEN_COLOR},
                    {BLUE_COLOR, SKYBLUE_COLOR,GREEN_COLOR, RED_COLOR},
                    {YELLOW_COLOR, RED_COLOR, YELLOW_COLOR, BLUE_COLOR},
                    {SKYBLUE_COLOR, YELLOW_COLOR, BLUE_COLOR, RED_COLOR},
                    {SKYBLUE_COLOR, YELLOW_COLOR, GREEN_COLOR, GREEN_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//6
                    {GREEN_COLOR, GREEN_COLOR, GREEN_COLOR, YELLOW_COLOR},
                    {GREEN_COLOR, BLUE_COLOR,YELLOW_COLOR, RED_COLOR},
                    {RED_COLOR, YELLOW_COLOR, RED_COLOR, BLUE_COLOR},
                    {BLUE_COLOR, RED_COLOR, BLUE_COLOR, SKYBLUE_COLOR},
                    {YELLOW_COLOR, SKYBLUE_COLOR, SKYBLUE_COLOR, SKYBLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//7
                    {RED_COLOR, YELLOW_COLOR, RED_COLOR, GREEN_COLOR},
                    {RED_COLOR, YELLOW_COLOR,SKYBLUE_COLOR, SKYBLUE_COLOR},
                    {RED_COLOR, SKYBLUE_COLOR, BLUE_COLOR, BLUE_COLOR},
                    {SKYBLUE_COLOR, YELLOW_COLOR, BLUE_COLOR, GREEN_COLOR},
                    {BLUE_COLOR, GREEN_COLOR, YELLOW_COLOR, GREEN_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//8
                    {SKYBLUE_COLOR, GREEN_COLOR, RED_COLOR, GREEN_COLOR},
                    {RED_COLOR, BLUE_COLOR,BLUE_COLOR, RED_COLOR},
                    {YELLOW_COLOR, SKYBLUE_COLOR, YELLOW_COLOR, BLUE_COLOR},
                    {BLUE_COLOR, GREEN_COLOR, YELLOW_COLOR, SKYBLUE_COLOR},
                    {RED_COLOR, YELLOW_COLOR, GREEN_COLOR, SKYBLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//9
                    {BLUE_COLOR, SKYBLUE_COLOR, YELLOW_COLOR, BLUE_COLOR},
                    {RED_COLOR, GREEN_COLOR,GREEN_COLOR, YELLOW_COLOR},
                    {BLUE_COLOR, RED_COLOR, RED_COLOR, YELLOW_COLOR},
                    {SKYBLUE_COLOR, GREEN_COLOR, YELLOW_COLOR, BLUE_COLOR},
                    {SKYBLUE_COLOR, SKYBLUE_COLOR, RED_COLOR, GREEN_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//10
                    {SKYBLUE_COLOR, GREEN_COLOR, SKYBLUE_COLOR, BLUE_COLOR},
                    {YELLOW_COLOR, BLUE_COLOR,ORANGE_COLOR, RED_COLOR},
                    {GREEN_COLOR, GREY_COLOR, GREY_COLOR, SKYBLUE_COLOR},
                    {GREEN_COLOR, RED_COLOR, RED_COLOR, BLUE_COLOR},
                    {YELLOW_COLOR, GREEN_COLOR, ORANGE_COLOR, ORANGE_COLOR},
                    {GREY_COLOR, GREY_COLOR, YELLOW_COLOR, SKYBLUE_COLOR},
                    {ORANGE_COLOR, RED_COLOR, BLUE_COLOR, YELLOW_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//11
                    {GREY_COLOR, SKYBLUE_COLOR, BLUE_COLOR, SKYBLUE_COLOR},
                    {GREY_COLOR, GREEN_COLOR,BLUE_COLOR, BLUE_COLOR},
                    {RED_COLOR, ORANGE_COLOR, RED_COLOR, RED_COLOR},
                    {RED_COLOR, BLUE_COLOR, YELLOW_COLOR, GREY_COLOR},
                    {ORANGE_COLOR, YELLOW_COLOR, GREEN_COLOR, SKYBLUE_COLOR},
                    {YELLOW_COLOR, YELLOW_COLOR, SKYBLUE_COLOR, ORANGE_COLOR},
                    {GREEN_COLOR, GREEN_COLOR, GREY_COLOR, ORANGE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//12
                    {GREEN_COLOR, GREEN_COLOR, BLUE_COLOR, SKYBLUE_COLOR},
                    {SKYBLUE_COLOR, BLUE_COLOR,SKYBLUE_COLOR, RED_COLOR},
                    {GREEN_COLOR, YELLOW_COLOR, RED_COLOR, SKYBLUE_COLOR},
                    {BLUE_COLOR, YELLOW_COLOR, GREEN_COLOR, RED_COLOR},
                    {BLUE_COLOR, YELLOW_COLOR, RED_COLOR, YELLOW_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//13
                    {YELLOW_COLOR, GREEN_COLOR, BLUE_COLOR, SKYBLUE_COLOR},
                    {GREY_COLOR, GREEN_COLOR,GREEN_COLOR, RED_COLOR},
                    {YELLOW_COLOR, RED_COLOR, ORANGE_COLOR, BLUE_COLOR},
                    {GREY_COLOR, SKYBLUE_COLOR, YELLOW_COLOR, GREEN_COLOR},
                    {GREY_COLOR, ORANGE_COLOR, BLUE_COLOR, GREY_COLOR},
                    {ORANGE_COLOR, SKYBLUE_COLOR, RED_COLOR, BLUE_COLOR},
                    {RED_COLOR, SKYBLUE_COLOR, YELLOW_COLOR, ORANGE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//14
                    {SKYBLUE_COLOR, SKYBLUE_COLOR, SKYBLUE_COLOR, ORANGE_COLOR},
                    {RED_COLOR, BLUE_COLOR,ORANGE_COLOR, SKYBLUE_COLOR},
                    {GREEN_COLOR, ORANGE_COLOR, RED_COLOR, ORANGE_COLOR},
                    {BLUE_COLOR, GREEN_COLOR, RED_COLOR, YELLOW_COLOR},
                    {YELLOW_COLOR, BLUE_COLOR, GREY_COLOR, YELLOW_COLOR},
                    {GREY_COLOR, GREEN_COLOR, GREY_COLOR, RED_COLOR},
                    {YELLOW_COLOR, GREY_COLOR, GREEN_COLOR, BLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//15
                    {RED_COLOR, GREEN_COLOR, GREEN_COLOR, SKYBLUE_COLOR},
                    {BLUE_COLOR, YELLOW_COLOR,SKYBLUE_COLOR, BLUE_COLOR},
                    {RED_COLOR, BLUE_COLOR, SKYBLUE_COLOR, BLUE_COLOR},
                    {SKYBLUE_COLOR, RED_COLOR, YELLOW_COLOR, YELLOW_COLOR},
                    {GREEN_COLOR, YELLOW_COLOR, GREEN_COLOR, RED_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//16
                    {YELLOW_COLOR, BLUE_COLOR, BLUE_COLOR, YELLOW_COLOR},
                    {YELLOW_COLOR, SKYBLUE_COLOR,YELLOW_COLOR, GREY_COLOR},
                    {RED_COLOR, RED_COLOR, GREEN_COLOR, RED_COLOR},
                    {ORANGE_COLOR, GREEN_COLOR, SKYBLUE_COLOR, ORANGE_COLOR},
                    {RED_COLOR, ORANGE_COLOR, GREY_COLOR, GREEN_COLOR},
                    {GREY_COLOR, BLUE_COLOR, ORANGE_COLOR, GREY_COLOR},
                    {GREEN_COLOR, SKYBLUE_COLOR, SKYBLUE_COLOR, BLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//17
                    {GREY_COLOR, SKYBLUE_COLOR, ORANGE_COLOR, GREY_COLOR},
                    {GREEN_COLOR, YELLOW_COLOR,SKYBLUE_COLOR, BLUE_COLOR},
                    {SKYBLUE_COLOR, GREEN_COLOR, BLUE_COLOR, SKYBLUE_COLOR},
                    {RED_COLOR, ORANGE_COLOR, YELLOW_COLOR, GREY_COLOR},
                    {GREEN_COLOR, GREY_COLOR, RED_COLOR, GREEN_COLOR},
                    {YELLOW_COLOR, RED_COLOR, BLUE_COLOR, YELLOW_COLOR},
                    {ORANGE_COLOR, ORANGE_COLOR, BLUE_COLOR, RED_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//18
                    {YELLOW_COLOR, SKYBLUE_COLOR, RED_COLOR, GREEN_COLOR},
                    {GREEN_COLOR, YELLOW_COLOR,BLUE_COLOR, RED_COLOR},
                    {GREEN_COLOR, YELLOW_COLOR, SKYBLUE_COLOR, RED_COLOR},
                    {GREEN_COLOR, SKYBLUE_COLOR, SKYBLUE_COLOR, BLUE_COLOR},
                    {RED_COLOR, YELLOW_COLOR, BLUE_COLOR, BLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//19
                    {BLUE_COLOR, BLUE_COLOR, YELLOW_COLOR, GREEN_COLOR},
                    {GREY_COLOR, BLUE_COLOR,GREY_COLOR, GREEN_COLOR},
                    {ORANGE_COLOR, ORANGE_COLOR, RED_COLOR, SKYBLUE_COLOR},
                    {ORANGE_COLOR, GREEN_COLOR, BLUE_COLOR, RED_COLOR},
                    {YELLOW_COLOR, GREY_COLOR, GREEN_COLOR, SKYBLUE_COLOR},
                    {ORANGE_COLOR, GREY_COLOR, SKYBLUE_COLOR, SKYBLUE_COLOR},
                    {YELLOW_COLOR, YELLOW_COLOR, RED_COLOR, RED_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//20
                    {BLUE_COLOR, RED_COLOR, SKYBLUE_COLOR, SKYBLUE_COLOR},
                    {BLUE_COLOR, GREY_COLOR,BLUE_COLOR, RED_COLOR},
                    {GREEN_COLOR, ORANGE_COLOR, YELLOW_COLOR, ORANGE_COLOR},
                    {ORANGE_COLOR, GREY_COLOR, GREEN_COLOR, YELLOW_COLOR},
                    {GREEN_COLOR, GREY_COLOR, SKYBLUE_COLOR, SKYBLUE_COLOR},
                    {GREY_COLOR, YELLOW_COLOR, RED_COLOR, ORANGE_COLOR},
                    {BLUE_COLOR, RED_COLOR, YELLOW_COLOR, GREEN_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//21
                    {SKYBLUE_COLOR, YELLOW_COLOR, YELLOW_COLOR, RED_COLOR},
                    {RED_COLOR, YELLOW_COLOR,GREEN_COLOR, BLUE_COLOR},
                    {YELLOW_COLOR, GREEN_COLOR, SKYBLUE_COLOR, SKYBLUE_COLOR},
                    {SKYBLUE_COLOR, BLUE_COLOR, RED_COLOR, GREEN_COLOR},
                    {BLUE_COLOR, GREEN_COLOR, BLUE_COLOR, RED_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//22
                    {GREEN_COLOR, SKYBLUE_COLOR, GREY_COLOR, GREY_COLOR},
                    {BLUE_COLOR, RED_COLOR,YELLOW_COLOR, ORANGE_COLOR},
                    {SKYBLUE_COLOR, RED_COLOR, GREEN_COLOR, YELLOW_COLOR},
                    {BLUE_COLOR, YELLOW_COLOR, SKYBLUE_COLOR, ORANGE_COLOR},
                    {BLUE_COLOR, BLUE_COLOR, RED_COLOR, ORANGE_COLOR},
                    {GREY_COLOR, SKYBLUE_COLOR, GREY_COLOR, GREEN_COLOR},
                    {RED_COLOR, YELLOW_COLOR, ORANGE_COLOR, GREEN_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//23
                    {RED_COLOR, BLUE_COLOR, YELLOW_COLOR, GREEN_COLOR},
                    {GREY_COLOR, SKYBLUE_COLOR,YELLOW_COLOR, BLUE_COLOR},
                    {GREY_COLOR, RED_COLOR, ORANGE_COLOR, RED_COLOR},
                    {GREY_COLOR, SKYBLUE_COLOR, GREEN_COLOR, BLUE_COLOR},
                    {GREY_COLOR, YELLOW_COLOR, ORANGE_COLOR, BLUE_COLOR},
                    {YELLOW_COLOR, GREEN_COLOR, GREEN_COLOR, ORANGE_COLOR},
                    {ORANGE_COLOR, RED_COLOR, SKYBLUE_COLOR, SKYBLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//24
                    {SKYBLUE_COLOR, SKYBLUE_COLOR, SKYBLUE_COLOR, YELLOW_COLOR},
                    {RED_COLOR, RED_COLOR,GREEN_COLOR, GREEN_COLOR},
                    {RED_COLOR, BLUE_COLOR, YELLOW_COLOR, GREEN_COLOR},
                    {BLUE_COLOR, RED_COLOR, GREEN_COLOR, YELLOW_COLOR},
                    {SKYBLUE_COLOR, BLUE_COLOR, YELLOW_COLOR, BLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//25
                    {BLUE_COLOR, ORANGE_COLOR, SKYBLUE_COLOR, SKYBLUE_COLOR},
                    {BLUE_COLOR, ORANGE_COLOR,YELLOW_COLOR, GREY_COLOR},
                    {SKYBLUE_COLOR, GREEN_COLOR, YELLOW_COLOR, YELLOW_COLOR},
                    {RED_COLOR, GREY_COLOR, GREY_COLOR, GREEN_COLOR},
                    {RED_COLOR, ORANGE_COLOR, RED_COLOR, GREEN_COLOR},
                    {YELLOW_COLOR, ORANGE_COLOR, BLUE_COLOR, RED_COLOR},
                    {GREEN_COLOR, BLUE_COLOR, GREY_COLOR, SKYBLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//26
                    {GREY_COLOR, GREY_COLOR, YELLOW_COLOR, RED_COLOR},
                    {GREEN_COLOR, BLUE_COLOR,BLUE_COLOR, GREEN_COLOR},
                    {GREEN_COLOR, RED_COLOR, SKYBLUE_COLOR, ORANGE_COLOR},
                    {YELLOW_COLOR, ORANGE_COLOR, SKYBLUE_COLOR, BLUE_COLOR},
                    {GREEN_COLOR, BLUE_COLOR, ORANGE_COLOR, RED_COLOR},
                    {GREY_COLOR, RED_COLOR, YELLOW_COLOR, YELLOW_COLOR},
                    {ORANGE_COLOR, SKYBLUE_COLOR, SKYBLUE_COLOR, GREY_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//27
                    {BLUE_COLOR, YELLOW_COLOR, RED_COLOR, RED_COLOR},
                    {SKYBLUE_COLOR, GREEN_COLOR,GREEN_COLOR, YELLOW_COLOR},
                    {BLUE_COLOR, BLUE_COLOR, YELLOW_COLOR, RED_COLOR},
                    {GREEN_COLOR, SKYBLUE_COLOR, SKYBLUE_COLOR, BLUE_COLOR},
                    {RED_COLOR, GREEN_COLOR, YELLOW_COLOR, SKYBLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//28
                    {SKYBLUE_COLOR, GREY_COLOR, ORANGE_COLOR, RED_COLOR},
                    {BLUE_COLOR, YELLOW_COLOR,BLUE_COLOR, BLUE_COLOR},
                    {ORANGE_COLOR, GREEN_COLOR, ORANGE_COLOR, RED_COLOR},
                    {YELLOW_COLOR, BLUE_COLOR, SKYBLUE_COLOR, GREEN_COLOR},
                    {ORANGE_COLOR, GREY_COLOR, SKYBLUE_COLOR, SKYBLUE_COLOR},
                    {GREY_COLOR, RED_COLOR, RED_COLOR, YELLOW_COLOR},
                    {YELLOW_COLOR, GREEN_COLOR, GREEN_COLOR, GREY_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//29
                    {RED_COLOR, BLUE_COLOR, GREY_COLOR, ORANGE_COLOR},
                    {BLUE_COLOR, GREEN_COLOR,GREEN_COLOR, GREY_COLOR},
                    {RED_COLOR, SKYBLUE_COLOR, RED_COLOR, SKYBLUE_COLOR},
                    {SKYBLUE_COLOR, GREEN_COLOR, GREY_COLOR, BLUE_COLOR},
                    {SKYBLUE_COLOR, GREEN_COLOR, YELLOW_COLOR, ORANGE_COLOR},
                    {YELLOW_COLOR, ORANGE_COLOR, ORANGE_COLOR, GREY_COLOR},
                    {BLUE_COLOR, YELLOW_COLOR, RED_COLOR, YELLOW_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//30
                    {BLUE_COLOR, BLUE_COLOR, BLUE_COLOR, RED_COLOR},
                    {GREEN_COLOR, GREY_COLOR,SKYBLUE_COLOR, SKYBLUE_COLOR},
                    {BLUE_COLOR, SKYBLUE_COLOR, YELLOW_COLOR, RED_COLOR},
                    {GREEN_COLOR, RED_COLOR, ORANGE_COLOR, YELLOW_COLOR},
                    {ORANGE_COLOR, GREY_COLOR, ORANGE_COLOR, YELLOW_COLOR},
                    {GREY_COLOR, GREEN_COLOR, GREY_COLOR, SKYBLUE_COLOR},
                    {GREEN_COLOR, RED_COLOR, YELLOW_COLOR, ORANGE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//31
                    {RED_COLOR, BLUE_COLOR, GREEN_COLOR, SKYBLUE_COLOR},
                    {SKYBLUE_COLOR, PURPLE_COLOR,RED_COLOR, GREY_COLOR},
                    {DARKRED_COLOR, GREEN_COLOR, ORANGE_COLOR, SKYBLUE_COLOR},
                    {GREY_COLOR, DARKRED_COLOR, DARKRED_COLOR, BLUE_COLOR},
                    {BLUE_COLOR, YELLOW_COLOR, YELLOW_COLOR, ORANGE_COLOR},
                    {GREEN_COLOR, PURPLE_COLOR, DARKRED_COLOR, SKYBLUE_COLOR},
                    {GREY_COLOR, RED_COLOR, YELLOW_COLOR, GREEN_COLOR},
                    {PURPLE_COLOR, YELLOW_COLOR, PURPLE_COLOR, ORANGE_COLOR},
                    {RED_COLOR, ORANGE_COLOR, GREY_COLOR, BLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//32
                    {DARKRED_COLOR, BLUE_COLOR, DARKRED_COLOR, GREEN_COLOR },
                    {ORANGE_COLOR, RED_COLOR,SKYBLUE_COLOR, DARKRED_COLOR},
                    {BLUE_COLOR, YELLOW_COLOR, ORANGE_COLOR, RED_COLOR},
                    {YELLOW_COLOR, ORANGE_COLOR, PURPLE_COLOR, GREY_COLOR},
                    {PURPLE_COLOR, YELLOW_COLOR, SKYBLUE_COLOR, GREEN_COLOR},
                    {YELLOW_COLOR, PURPLE_COLOR, BLUE_COLOR,ORANGE_COLOR},
                    {GREEN_COLOR, DARKRED_COLOR, GREEN_COLOR, GREY_COLOR},
                    {RED_COLOR, GREY_COLOR, GREY_COLOR, SKYBLUE_COLOR},
                    {SKYBLUE_COLOR, BLUE_COLOR, RED_COLOR, PURPLE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//33
                    {YELLOW_COLOR, GREEN_COLOR, GREEN_COLOR, YELLOW_COLOR },
                    {BLUE_COLOR, RED_COLOR,SKYBLUE_COLOR, GREY_COLOR},
                    {SKYBLUE_COLOR, BLUE_COLOR, ORANGE_COLOR, ORANGE_COLOR},
                    {RED_COLOR, GREY_COLOR, GREEN_COLOR, GREY_COLOR},
                    {SKYBLUE_COLOR, GREY_COLOR, RED_COLOR, GREEN_COLOR},
                    {YELLOW_COLOR, ORANGE_COLOR, ORANGE_COLOR,BLUE_COLOR},
                    {YELLOW_COLOR, RED_COLOR, SKYBLUE_COLOR, BLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//34
                    {PURPLE_COLOR, SKYBLUE_COLOR, YELLOW_COLOR, RED_COLOR },
                    {DARKRED_COLOR, DARKRED_COLOR,SKYBLUE_COLOR, ORANGE_COLOR},
                    {GREEN_COLOR, ORANGE_COLOR, GREEN_COLOR, ORANGE_COLOR},
                    {RED_COLOR, GREY_COLOR, PURPLE_COLOR, RED_COLOR},
                    {GREY_COLOR, YELLOW_COLOR, YELLOW_COLOR, GREY_COLOR},
                    {BLUE_COLOR, ORANGE_COLOR, BLUE_COLOR,SKYBLUE_COLOR},
                    {SKYBLUE_COLOR, BLUE_COLOR, YELLOW_COLOR, DARKRED_COLOR},
                    {PURPLE_COLOR, GREY_COLOR, BLUE_COLOR, DARKRED_COLOR},
                    {GREEN_COLOR, RED_COLOR, GREEN_COLOR, PURPLE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//35
                    {BLUE_COLOR, YELLOW_COLOR, BLUE_COLOR, GREEN_COLOR },
                    {RED_COLOR, BLUE_COLOR,GREEN_COLOR, SKYBLUE_COLOR},
                    {SKYBLUE_COLOR, PURPLE_COLOR, RED_COLOR, GREY_COLOR},
                    {YELLOW_COLOR, PURPLE_COLOR, DARKRED_COLOR, GREY_COLOR},
                    {ORANGE_COLOR, YELLOW_COLOR, DARKRED_COLOR, GREY_COLOR},
                    {PURPLE_COLOR, RED_COLOR, DARKRED_COLOR,ORANGE_COLOR},
                    {DARKRED_COLOR, RED_COLOR, GREY_COLOR, SKYBLUE_COLOR},
                    {BLUE_COLOR, SKYBLUE_COLOR, YELLOW_COLOR, PURPLE_COLOR},
                    {ORANGE_COLOR, GREEN_COLOR, ORANGE_COLOR, GREEN_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//36
                    {SKYBLUE_COLOR, ORANGE_COLOR, YELLOW_COLOR, GREY_COLOR },
                    {GREEN_COLOR, SKYBLUE_COLOR,YELLOW_COLOR, GREEN_COLOR},
                    {BLUE_COLOR, GREY_COLOR, SKYBLUE_COLOR, GREEN_COLOR},
                    {BLUE_COLOR, GREEN_COLOR, YELLOW_COLOR, YELLOW_COLOR},
                    {BLUE_COLOR, RED_COLOR, GREY_COLOR, ORANGE_COLOR},
                    {GREY_COLOR, BLUE_COLOR, RED_COLOR,ORANGE_COLOR},
                    {ORANGE_COLOR, SKYBLUE_COLOR, RED_COLOR, RED_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//37
                    {PURPLE_COLOR, SKYBLUE_COLOR, GREEN_COLOR, ORANGE_COLOR },
                    {GREEN_COLOR, GREEN_COLOR,RED_COLOR, ORANGE_COLOR},
                    {BLUE_COLOR, YELLOW_COLOR, ORANGE_COLOR, DARKRED_COLOR},
                    {PURPLE_COLOR, RED_COLOR, GREY_COLOR, GREY_COLOR},
                    {GREY_COLOR, YELLOW_COLOR, SKYBLUE_COLOR, PURPLE_COLOR},
                    {YELLOW_COLOR, DARKRED_COLOR, SKYBLUE_COLOR,YELLOW_COLOR},
                    {ORANGE_COLOR, GREY_COLOR, RED_COLOR, BLUE_COLOR},
                    {PURPLE_COLOR, DARKRED_COLOR, DARKRED_COLOR, BLUE_COLOR},
                    {SKYBLUE_COLOR, RED_COLOR, BLUE_COLOR, GREEN_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//38
                    {GREY_COLOR, PURPLE_COLOR, GREY_COLOR, SKYBLUE_COLOR },
                    {RED_COLOR, PURPLE_COLOR,YELLOW_COLOR, SKYBLUE_COLOR},
                    {SKYBLUE_COLOR, SKYBLUE_COLOR, RED_COLOR, ORANGE_COLOR},
                    {GREEN_COLOR, DARKRED_COLOR, BLUE_COLOR, YELLOW_COLOR},
                    {YELLOW_COLOR, BLUE_COLOR, GREEN_COLOR, ORANGE_COLOR},
                    {PURPLE_COLOR, GREEN_COLOR, GREEN_COLOR,DARKRED_COLOR},
                    {YELLOW_COLOR, RED_COLOR, ORANGE_COLOR, PURPLE_COLOR},
                    {DARKRED_COLOR, BLUE_COLOR, ORANGE_COLOR, RED_COLOR},
                    {BLUE_COLOR, GREY_COLOR, GREY_COLOR, DARKRED_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//39
                    {GREEN_COLOR, BLUE_COLOR, GREY_COLOR, SKYBLUE_COLOR },
                    {BLUE_COLOR, ORANGE_COLOR,DARKRED_COLOR, YELLOW_COLOR},
                    {DARKRED_COLOR, SKYBLUE_COLOR, SKYBLUE_COLOR, GREY_COLOR},
                    {YELLOW_COLOR, GREY_COLOR, DARKRED_COLOR, BLUE_COLOR},
                    {DARKRED_COLOR, GREEN_COLOR, SKYBLUE_COLOR, BLUE_COLOR},
                    {YELLOW_COLOR, GREY_COLOR, GREEN_COLOR,ORANGE_COLOR},
                    {ORANGE_COLOR, YELLOW_COLOR, ORANGE_COLOR, GREEN_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//40
                    {DARKRED_COLOR, ORANGE_COLOR, BLUE_COLOR, PURPLE_COLOR },
                    {RED_COLOR, GREY_COLOR,GREEN_COLOR, DARKRED_COLOR},
                    {GREEN_COLOR,SKYBLUE_COLOR,PURPLE_COLOR,BLUE_COLOR},
                    {ORANGE_COLOR, GREY_COLOR,GREY_COLOR,BLUE_COLOR},
                    {SKYBLUE_COLOR,BLUE_COLOR, PURPLE_COLOR, SKYBLUE_COLOR},
                    {YELLOW_COLOR, SKYBLUE_COLOR, RED_COLOR,YELLOW_COLOR},
                    {ORANGE_COLOR, RED_COLOR,GREEN_COLOR, GREY_COLOR},
                    {PURPLE_COLOR, YELLOW_COLOR,GREEN_COLOR, ORANGE_COLOR},
                    {RED_COLOR, DARKRED_COLOR,YELLOW_COLOR, DARKRED_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//41
                    {BLUE_COLOR, RED_COLOR, PURPLE_COLOR, DARKRED_COLOR },
                    {YELLOW_COLOR, SKYBLUE_COLOR,GREEN_COLOR,RED_COLOR},
                    {SKYBLUE_COLOR,GREY_COLOR,GREEN_COLOR,SKYBLUE_COLOR},
                    {GREY_COLOR, YELLOW_COLOR, YELLOW_COLOR,DARKRED_COLOR},
                    {RED_COLOR,ORANGE_COLOR, RED_COLOR, BLUE_COLOR},
                    {ORANGE_COLOR, GREY_COLOR, SKYBLUE_COLOR,PURPLE_COLOR},
                    {GREEN_COLOR,PURPLE_COLOR,DARKRED_COLOR,ORANGE_COLOR},
                    {BLUE_COLOR,PURPLE_COLOR,BLUE_COLOR, YELLOW_COLOR},
                    {DARKRED_COLOR, ORANGE_COLOR,GREEN_COLOR,GREY_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//42
                    {YELLOW_COLOR, GREEN_COLOR, RED_COLOR, BLUE_COLOR },
                    {BLUE_COLOR, SKYBLUE_COLOR,GREY_COLOR,RED_COLOR},
                    {BLUE_COLOR,GREEN_COLOR,ORANGE_COLOR,RED_COLOR},
                    {GREEN_COLOR,YELLOW_COLOR,ORANGE_COLOR,BLUE_COLOR},
                    {GREY_COLOR,ORANGE_COLOR,ORANGE_COLOR,GREY_COLOR},
                    {RED_COLOR,SKYBLUE_COLOR,YELLOW_COLOR,GREEN_COLOR},
                    {SKYBLUE_COLOR,YELLOW_COLOR,GREY_COLOR,SKYBLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//43
                    {GREY_COLOR,GREEN_COLOR,DARKRED_COLOR,YELLOW_COLOR },
                    {PURPLE_COLOR,ORANGE_COLOR,RED_COLOR,BLUE_COLOR},
                    {BLUE_COLOR,GREEN_COLOR,ORANGE_COLOR,GREY_COLOR},
                    {GREEN_COLOR,DARKRED_COLOR,DARKRED_COLOR,PURPLE_COLOR},
                    {YELLOW_COLOR,RED_COLOR,DARKRED_COLOR,YELLOW_COLOR},
                    {RED_COLOR,RED_COLOR,SKYBLUE_COLOR,BLUE_COLOR},
                    {ORANGE_COLOR,SKYBLUE_COLOR,GREY_COLOR,PURPLE_COLOR},
                    {BLUE_COLOR,GREY_COLOR,YELLOW_COLOR,SKYBLUE_COLOR},
                    {GREEN_COLOR,PURPLE_COLOR,SKYBLUE_COLOR,ORANGE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//44
                    {PURPLE_COLOR,GREY_COLOR,BLUE_COLOR,GREEN_COLOR },
                    {SKYBLUE_COLOR,GREY_COLOR,GREY_COLOR,BLUE_COLOR},
                    {YELLOW_COLOR,YELLOW_COLOR,DARKRED_COLOR,YELLOW_COLOR},
                    {PURPLE_COLOR,ORANGE_COLOR,RED_COLOR,ORANGE_COLOR},
                    {GREEN_COLOR,PURPLE_COLOR,BLUE_COLOR,GREY_COLOR},
                    {GREEN_COLOR,DARKRED_COLOR,RED_COLOR,RED_COLOR},
                    {PURPLE_COLOR,ORANGE_COLOR,DARKRED_COLOR,YELLOW_COLOR},
                    {DARKRED_COLOR,SKYBLUE_COLOR,SKYBLUE_COLOR,GREEN_COLOR},
                    {ORANGE_COLOR,RED_COLOR,BLUE_COLOR,SKYBLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//45
                    {GREEN_COLOR,ORANGE_COLOR,GREEN_COLOR,GREY_COLOR },
                    {SKYBLUE_COLOR,DARKRED_COLOR,YELLOW_COLOR,BLUE_COLOR},
                    {GREEN_COLOR,ORANGE_COLOR,DARKRED_COLOR,SKYBLUE_COLOR},
                    {SKYBLUE_COLOR,GREY_COLOR,SKYBLUE_COLOR,BLUE_COLOR},
                    {GREY_COLOR,BLUE_COLOR,YELLOW_COLOR,YELLOW_COLOR},
                    {YELLOW_COLOR,ORANGE_COLOR,DARKRED_COLOR,DARKRED_COLOR},
                    {GREY_COLOR,BLUE_COLOR, ORANGE_COLOR,GREEN_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//46
                    {DARKRED_COLOR, GREEN_COLOR,RED_COLOR, ORANGE_COLOR },
                    {ORANGE_COLOR, SKYBLUE_COLOR,GREY_COLOR,PURPLE_COLOR},
                    {GREY_COLOR,SKYBLUE_COLOR,PURPLE_COLOR,DARKRED_COLOR},
                    {YELLOW_COLOR,PURPLE_COLOR, ORANGE_COLOR,GREEN_COLOR},
                    {BLUE_COLOR,RED_COLOR,GREEN_COLOR,RED_COLOR},
                    {RED_COLOR,YELLOW_COLOR,GREY_COLOR,BLUE_COLOR},
                    {BLUE_COLOR,YELLOW_COLOR,GREY_COLOR,BLUE_COLOR},
                    {PURPLE_COLOR,DARKRED_COLOR,ORANGE_COLOR,GREEN_COLOR},
                    {SKYBLUE_COLOR,SKYBLUE_COLOR,DARKRED_COLOR,YELLOW_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//47
                    {PURPLE_COLOR, SKYBLUE_COLOR, RED_COLOR, GREEN_COLOR },
                    {SKYBLUE_COLOR,YELLOW_COLOR,DARKRED_COLOR,PURPLE_COLOR},
                    {BLUE_COLOR,ORANGE_COLOR,PURPLE_COLOR,GREY_COLOR},
                    {DARKRED_COLOR,YELLOW_COLOR,GREEN_COLOR,BLUE_COLOR},
                    {DARKRED_COLOR,GREEN_COLOR,GREY_COLOR,GREY_COLOR},
                    {RED_COLOR,DARKRED_COLOR,ORANGE_COLOR,ORANGE_COLOR},
                    {YELLOW_COLOR,SKYBLUE_COLOR,SKYBLUE_COLOR,GREEN_COLOR},
                    {RED_COLOR,BLUE_COLOR,GREY_COLOR,PURPLE_COLOR},
                    {BLUE_COLOR,RED_COLOR,YELLOW_COLOR,ORANGE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//48
                    {DARKRED_COLOR,GREEN_COLOR,YELLOW_COLOR,GREY_COLOR },
                    {SKYBLUE_COLOR,SKYBLUE_COLOR,GREEN_COLOR,GREY_COLOR},
                    {GREY_COLOR,YELLOW_COLOR,DARKRED_COLOR,ORANGE_COLOR},
                    {SKYBLUE_COLOR,BLUE_COLOR,BLUE_COLOR,GREEN_COLOR},
                    {DARKRED_COLOR,ORANGE_COLOR,YELLOW_COLOR, BLUE_COLOR},
                    {DARKRED_COLOR,GREY_COLOR,ORANGE_COLOR,YELLOW_COLOR},
                    {BLUE_COLOR,ORANGE_COLOR,GREEN_COLOR,SKYBLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//49
                    {BLUE_COLOR, YELLOW_COLOR,GREY_COLOR,GREEN_COLOR },
                    {GREEN_COLOR,SKYBLUE_COLOR,SKYBLUE_COLOR,SKYBLUE_COLOR},
                    {GREEN_COLOR,DARKRED_COLOR,YELLOW_COLOR,SKYBLUE_COLOR},
                    {GREY_COLOR,DARKRED_COLOR,YELLOW_COLOR,DARKRED_COLOR},
                    {ORANGE_COLOR,ORANGE_COLOR, BLUE_COLOR, GREY_COLOR},
                    {DARKRED_COLOR,RED_COLOR,PURPLE_COLOR,PURPLE_COLOR},
                    {GREY_COLOR,GREEN_COLOR,PURPLE_COLOR,ORANGE_COLOR},
                    {RED_COLOR,BLUE_COLOR,BLUE_COLOR, PURPLE_COLOR},
                    {ORANGE_COLOR,YELLOW_COLOR,RED_COLOR,RED_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//50
                    {DARKRED_COLOR,GREEN_COLOR,RED_COLOR, ORANGE_COLOR },
                    {BLUE_COLOR,BLUE_COLOR,BLUE_COLOR,YELLOW_COLOR},
                    {GREEN_COLOR,RED_COLOR,GREEN_COLOR,GREY_COLOR},
                    {ORANGE_COLOR, DARKRED_COLOR, GREY_COLOR,SKYBLUE_COLOR},
                    {SKYBLUE_COLOR,RED_COLOR,GREY_COLOR, ORANGE_COLOR},
                    {BLUE_COLOR,PURPLE_COLOR,GREY_COLOR,SKYBLUE_COLOR},
                    {GREEN_COLOR,RED_COLOR,YELLOW_COLOR,DARKRED_COLOR},
                    {PURPLE_COLOR,ORANGE_COLOR,PURPLE_COLOR,YELLOW_COLOR},
                    {PURPLE_COLOR,DARKRED_COLOR,SKYBLUE_COLOR,YELLOW_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//51
                    {GREEN_COLOR,DARKRED_COLOR,BLUE_COLOR,SKYBLUE_COLOR },
                    {GREY_COLOR,GREEN_COLOR,YELLOW_COLOR,BLUE_COLOR},
                    {GREY_COLOR,BLUE_COLOR,GREY_COLOR,YELLOW_COLOR},
                    {GREEN_COLOR,ORANGE_COLOR,SKYBLUE_COLOR,DARKRED_COLOR},
                    {YELLOW_COLOR,DARKRED_COLOR,ORANGE_COLOR,DARKRED_COLOR},
                    {YELLOW_COLOR,SKYBLUE_COLOR,ORANGE_COLOR,ORANGE_COLOR},
                    {SKYBLUE_COLOR,GREEN_COLOR,BLUE_COLOR,GREY_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//52
                    {GREEN_COLOR,BLUE_COLOR,BLUE_COLOR,YELLOW_COLOR },
                    {SKYBLUE_COLOR,ORANGE_COLOR,GREY_COLOR,GREY_COLOR},
                    {ORANGE_COLOR,ORANGE_COLOR,RED_COLOR,PURPLE_COLOR},
                    {GREY_COLOR,PURPLE_COLOR,SKYBLUE_COLOR,YELLOW_COLOR},
                    {GREEN_COLOR,DARKRED_COLOR,YELLOW_COLOR,SKYBLUE_COLOR},
                    {GREEN_COLOR,PURPLE_COLOR,RED_COLOR,GREEN_COLOR},
                    {RED_COLOR,RED_COLOR,DARKRED_COLOR,YELLOW_COLOR},
                    {DARKRED_COLOR,GREY_COLOR,DARKRED_COLOR,SKYBLUE_COLOR},
                    {PURPLE_COLOR,BLUE_COLOR,ORANGE_COLOR,BLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//53
                    {SKYBLUE_COLOR,GREEN_COLOR,PURPLE_COLOR,GREEN_COLOR },
                    {RED_COLOR,GREY_COLOR,ORANGE_COLOR,GREEN_COLOR},
                    {RED_COLOR,RED_COLOR,BLUE_COLOR,GREEN_COLOR},
                    {PURPLE_COLOR, GREY_COLOR, GREY_COLOR,ORANGE_COLOR},
                    {SKYBLUE_COLOR,ORANGE_COLOR,SKYBLUE_COLOR,BLUE_COLOR},
                    {YELLOW_COLOR,YELLOW_COLOR,ORANGE_COLOR,YELLOW_COLOR},
                    {DARKRED_COLOR,RED_COLOR,BLUE_COLOR,YELLOW_COLOR},
                    {BLUE_COLOR,SKYBLUE_COLOR,DARKRED_COLOR,PURPLE_COLOR},
                    {GREY_COLOR,DARKRED_COLOR,DARKRED_COLOR,PURPLE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//54
                    {GREEN_COLOR,SKYBLUE_COLOR,GREEN_COLOR,GREY_COLOR },
                    {DARKRED_COLOR,YELLOW_COLOR,SKYBLUE_COLOR,GREEN_COLOR},
                    {SKYBLUE_COLOR,ORANGE_COLOR,ORANGE_COLOR,BLUE_COLOR},
                    {BLUE_COLOR,GREEN_COLOR,GREY_COLOR,DARKRED_COLOR},
                    {ORANGE_COLOR,SKYBLUE_COLOR,BLUE_COLOR,YELLOW_COLOR},
                    {DARKRED_COLOR,BLUE_COLOR,GREY_COLOR,DARKRED_COLOR},
                    {ORANGE_COLOR,GREY_COLOR,YELLOW_COLOR,YELLOW_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//55
                    {YELLOW_COLOR,SKYBLUE_COLOR,YELLOW_COLOR,ORANGE_COLOR },
                    {SKYBLUE_COLOR,DARKRED_COLOR,SKYBLUE_COLOR,RED_COLOR},
                    {BLUE_COLOR,GREEN_COLOR,GREY_COLOR,GREY_COLOR},
                    {GREEN_COLOR,BLUE_COLOR,ORANGE_COLOR,GREY_COLOR},
                    {RED_COLOR,PURPLE_COLOR,YELLOW_COLOR,YELLOW_COLOR},
                    {ORANGE_COLOR,PURPLE_COLOR,BLUE_COLOR,ORANGE_COLOR},
                    {RED_COLOR,DARKRED_COLOR,SKYBLUE_COLOR,RED_COLOR},
                    {BLUE_COLOR,DARKRED_COLOR,PURPLE_COLOR,GREEN_COLOR},
                    {DARKRED_COLOR,GREEN_COLOR,GREY_COLOR,PURPLE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//56
                    {DARKRED_COLOR,GREY_COLOR,BLUE_COLOR,YELLOW_COLOR },
                    {BLUE_COLOR,YELLOW_COLOR,RED_COLOR,ORANGE_COLOR},
                    {SKYBLUE_COLOR,GREEN_COLOR,PURPLE_COLOR,BLUE_COLOR},
                    {DARKRED_COLOR,SKYBLUE_COLOR,SKYBLUE_COLOR,GREEN_COLOR},
                    {ORANGE_COLOR,DARKRED_COLOR,GREY_COLOR,RED_COLOR},
                    {PURPLE_COLOR,GREY_COLOR,RED_COLOR,ORANGE_COLOR},
                    {YELLOW_COLOR,ORANGE_COLOR,BLUE_COLOR,GREY_COLOR},
                    {RED_COLOR,PURPLE_COLOR,GREEN_COLOR,DARKRED_COLOR},
                    {YELLOW_COLOR,SKYBLUE_COLOR,PURPLE_COLOR,GREEN_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//57
                    {ORANGE_COLOR,GREEN_COLOR,BLUE_COLOR,ORANGE_COLOR },
                    {SKYBLUE_COLOR,YELLOW_COLOR,GREY_COLOR,BLUE_COLOR},
                    {YELLOW_COLOR, DARKRED_COLOR,SKYBLUE_COLOR,SKYBLUE_COLOR},
                    {DARKRED_COLOR,BLUE_COLOR,GREEN_COLOR,DARKRED_COLOR},
                    {DARKRED_COLOR,YELLOW_COLOR,GREEN_COLOR,GREEN_COLOR},
                    {GREY_COLOR,GREY_COLOR,ORANGE_COLOR,YELLOW_COLOR},
                    {BLUE_COLOR,GREY_COLOR,SKYBLUE_COLOR,ORANGE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//58
                    {RED_COLOR,SKYBLUE_COLOR,SKYBLUE_COLOR,PURPLE_COLOR },
                    {ORANGE_COLOR,RED_COLOR,BLUE_COLOR,YELLOW_COLOR},
                    {ORANGE_COLOR,YELLOW_COLOR,GREY_COLOR,GREEN_COLOR},
                    {BLUE_COLOR,YELLOW_COLOR,BLUE_COLOR,GREY_COLOR},
                    {PURPLE_COLOR,DARKRED_COLOR,SKYBLUE_COLOR,GREEN_COLOR},
                    {RED_COLOR,PURPLE_COLOR,DARKRED_COLOR,GREEN_COLOR},
                    {ORANGE_COLOR,ORANGE_COLOR,SKYBLUE_COLOR,DARKRED_COLOR},
                    {DARKRED_COLOR,BLUE_COLOR, YELLOW_COLOR,RED_COLOR},
                    {GREY_COLOR,GREY_COLOR,PURPLE_COLOR,GREEN_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//59
                    {ORANGE_COLOR,PURPLE_COLOR,GREY_COLOR,DARKRED_COLOR },
                    {SKYBLUE_COLOR,GREY_COLOR,PURPLE_COLOR,GREEN_COLOR},
                    {GREY_COLOR,RED_COLOR,YELLOW_COLOR,PURPLE_COLOR},
                    {DARKRED_COLOR,YELLOW_COLOR,DARKRED_COLOR,YELLOW_COLOR},
                    {BLUE_COLOR,GREEN_COLOR,ORANGE_COLOR,ORANGE_COLOR},
                    {SKYBLUE_COLOR,GREY_COLOR,BLUE_COLOR,RED_COLOR},
                    {GREEN_COLOR,PURPLE_COLOR,GREEN_COLOR,SKYBLUE_COLOR},
                    {BLUE_COLOR,ORANGE_COLOR,SKYBLUE_COLOR,BLUE_COLOR},
                    {RED_COLOR,YELLOW_COLOR,RED_COLOR,DARKRED_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//60
                    {GREEN_COLOR,YELLOW_COLOR,SKYBLUE_COLOR, YELLOW_COLOR},
                    {GREEN_COLOR,BLUE_COLOR,GREEN_COLOR,GREEN_COLOR},
                    {DARKRED_COLOR,BLUE_COLOR,SKYBLUE_COLOR,GREY_COLOR},
                    {YELLOW_COLOR,ORANGE_COLOR,GREY_COLOR,ORANGE_COLOR},
                    {YELLOW_COLOR,BLUE_COLOR,GREY_COLOR,SKYBLUE_COLOR},
                    {ORANGE_COLOR,SKYBLUE_COLOR,DARKRED_COLOR,GREY_COLOR},
                    {ORANGE_COLOR,DARKRED_COLOR,DARKRED_COLOR,BLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//61
                    {PURPLE_COLOR,GREEN_COLOR,GREEN_COLOR,SKYBLUE_COLOR},
                    {GREY_COLOR,ORANGE_COLOR,PURPLE_COLOR,DARKRED_COLOR},
                    {PURPLE_COLOR,GREEN_COLOR,BLUE_COLOR,SKYBLUE_COLOR},
                    {GREY_COLOR,GREEN_COLOR,YELLOW_COLOR,ORANGE_COLOR},
                    {RED_COLOR,SKYBLUE_COLOR,DARKRED_COLOR,DARKRED_COLOR},
                    {ORANGE_COLOR,RED_COLOR,RED_COLOR,GREY_COLOR},
                    {SKYBLUE_COLOR,PURPLE_COLOR,YELLOW_COLOR,YELLOW_COLOR},
                    {BLUE_COLOR,GREY_COLOR,RED_COLOR,YELLOW_COLOR},
                    {BLUE_COLOR,ORANGE_COLOR,BLUE_COLOR,DARKRED_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//62
                    {DARKRED_COLOR,GREEN_COLOR,PURPLE_COLOR,RED_COLOR},
                    {RED_COLOR,PURPLE_COLOR, RED_COLOR,YELLOW_COLOR},
                    {GREEN_COLOR,DARKRED_COLOR,YELLOW_COLOR,YELLOW_COLOR},
                    {RED_COLOR,GREY_COLOR,BLUE_COLOR,PURPLE_COLOR},
                    {GREEN_COLOR,SKYBLUE_COLOR,BLUE_COLOR,YELLOW_COLOR},
                    {ORANGE_COLOR,ORANGE_COLOR,SKYBLUE_COLOR,PURPLE_COLOR},
                    {DARKRED_COLOR,SKYBLUE_COLOR,GREY_COLOR,BLUE_COLOR},
                    {DARKRED_COLOR,SKYBLUE_COLOR,ORANGE_COLOR,GREY_COLOR},
                    {GREEN_COLOR,BLUE_COLOR,ORANGE_COLOR,GREY_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//63
                    {BLUE_COLOR,SKYBLUE_COLOR,RED_COLOR,GREEN_COLOR},
                    {ORANGE_COLOR,YELLOW_COLOR,SKYBLUE_COLOR,ORANGE_COLOR},
                    {GREEN_COLOR,ORANGE_COLOR,GREY_COLOR,GREY_COLOR},
                    {YELLOW_COLOR,SKYBLUE_COLOR,YELLOW_COLOR,GREY_COLOR},
                    {BLUE_COLOR,GREY_COLOR,ORANGE_COLOR,BLUE_COLOR},
                    {YELLOW_COLOR,BLUE_COLOR,GREEN_COLOR,SKYBLUE_COLOR},
                    {GREEN_COLOR,RED_COLOR,RED_COLOR,RED_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//64
                    {BLUE_COLOR,BLUE_COLOR,ORANGE_COLOR,ORANGE_COLOR},
                    {YELLOW_COLOR,DARKRED_COLOR,PURPLE_COLOR,RED_COLOR},
                    {GREEN_COLOR,YELLOW_COLOR,ORANGE_COLOR,PURPLE_COLOR},
                    {PURPLE_COLOR,YELLOW_COLOR,RED_COLOR,DARKRED_COLOR},
                    {BLUE_COLOR,RED_COLOR,PURPLE_COLOR,SKYBLUE_COLOR},
                    {SKYBLUE_COLOR,RED_COLOR,SKYBLUE_COLOR,GREY_COLOR},
                    {GREEN_COLOR,GREEN_COLOR,BLUE_COLOR,GREY_COLOR},
                    {DARKRED_COLOR,SKYBLUE_COLOR,GREEN_COLOR,GREY_COLOR},
                    {DARKRED_COLOR,ORANGE_COLOR,YELLOW_COLOR,GREY_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//65
                    {BLUE_COLOR,DARKRED_COLOR,GREEN_COLOR,DARKRED_COLOR},
                    {ORANGE_COLOR,SKYBLUE_COLOR,SKYBLUE_COLOR,GREEN_COLOR},
                    {RED_COLOR,YELLOW_COLOR,GREY_COLOR,BLUE_COLOR},
                    {GREEN_COLOR,ORANGE_COLOR,YELLOW_COLOR, PURPLE_COLOR},
                    {DARKRED_COLOR,PURPLE_COLOR,BLUE_COLOR,ORANGE_COLOR},
                    {PURPLE_COLOR,GREY_COLOR,RED_COLOR,RED_COLOR},
                    {YELLOW_COLOR,YELLOW_COLOR,GREY_COLOR, ORANGE_COLOR},
                    {RED_COLOR,PURPLE_COLOR,BLUE_COLOR,DARKRED_COLOR},
                    {SKYBLUE_COLOR,GREEN_COLOR,GREY_COLOR,SKYBLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//66
                    {YELLOW_COLOR,GREY_COLOR,BLUE_COLOR,ORANGE_COLOR},
                    {SKYBLUE_COLOR,GREY_COLOR,BLUE_COLOR,ORANGE_COLOR},
                    {YELLOW_COLOR,BLUE_COLOR,GREEN_COLOR,DARKRED_COLOR},
                    {GREY_COLOR,SKYBLUE_COLOR,GREEN_COLOR,ORANGE_COLOR},
                    {GREEN_COLOR,DARKRED_COLOR,SKYBLUE_COLOR,GREY_COLOR},
                    {DARKRED_COLOR,DARKRED_COLOR,YELLOW_COLOR,BLUE_COLOR},
                    {YELLOW_COLOR,SKYBLUE_COLOR,ORANGE_COLOR,GREEN_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//67
                    {ORANGE_COLOR,YELLOW_COLOR,PURPLE_COLOR,GREY_COLOR},
                    {SKYBLUE_COLOR,PURPLE_COLOR,GREEN_COLOR,BLUE_COLOR},
                    {GREY_COLOR,PURPLE_COLOR,YELLOW_COLOR,BLUE_COLOR},
                    {DARKRED_COLOR,DARKRED_COLOR,ORANGE_COLOR,YELLOW_COLOR},
                    {RED_COLOR,YELLOW_COLOR,GREEN_COLOR,BLUE_COLOR},
                    {SKYBLUE_COLOR,ORANGE_COLOR,GREEN_COLOR,RED_COLOR},
                    {ORANGE_COLOR,GREY_COLOR,RED_COLOR,DARKRED_COLOR},
                    {GREEN_COLOR,BLUE_COLOR,PURPLE_COLOR,DARKRED_COLOR},
                    {GREY_COLOR,RED_COLOR,SKYBLUE_COLOR,SKYBLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//68
                    {GREEN_COLOR,BLUE_COLOR,DARKRED_COLOR,BLUE_COLOR},
                    {PURPLE_COLOR,YELLOW_COLOR,GREY_COLOR,GREY_COLOR},
                    {PURPLE_COLOR,BLUE_COLOR,SKYBLUE_COLOR,RED_COLOR},
                    {SKYBLUE_COLOR,RED_COLOR,GREY_COLOR,YELLOW_COLOR},
                    {SKYBLUE_COLOR,YELLOW_COLOR,YELLOW_COLOR,DARKRED_COLOR},
                    {GREEN_COLOR,ORANGE_COLOR,RED_COLOR,ORANGE_COLOR},
                    {GREEN_COLOR,PURPLE_COLOR,PURPLE_COLOR,SKYBLUE_COLOR},
                    {DARKRED_COLOR,ORANGE_COLOR,DARKRED_COLOR,BLUE_COLOR},
                    {RED_COLOR,GREY_COLOR,GREEN_COLOR,ORANGE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//69
                    {SKYBLUE_COLOR,BLUE_COLOR,DARKRED_COLOR,YELLOW_COLOR},
                    {GREEN_COLOR,DARKRED_COLOR,DARKRED_COLOR,BLUE_COLOR},
                    {GREEN_COLOR,ORANGE_COLOR,ORANGE_COLOR,SKYBLUE_COLOR},
                    {BLUE_COLOR,GREY_COLOR,BLUE_COLOR,GREEN_COLOR},
                    {YELLOW_COLOR,GREEN_COLOR,SKYBLUE_COLOR,ORANGE_COLOR},
                    {YELLOW_COLOR,YELLOW_COLOR,GREY_COLOR,GREY_COLOR},
                    {DARKRED_COLOR,ORANGE_COLOR,GREY_COLOR,SKYBLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//70
                    {GREY_COLOR,BLUE_COLOR,YELLOW_COLOR,GREEN_COLOR},
                    {GREEN_COLOR,GREY_COLOR,SKYBLUE_COLOR,SKYBLUE_COLOR},
                    {ORANGE_COLOR,GREY_COLOR,BLUE_COLOR,SKYBLUE_COLOR},
                    {GREY_COLOR,RED_COLOR,RED_COLOR,BLUE_COLOR},
                    {DARKRED_COLOR,PURPLE_COLOR,PURPLE_COLOR,GREEN_COLOR},
                    {PURPLE_COLOR,SKYBLUE_COLOR,RED_COLOR,RED_COLOR},
                    {YELLOW_COLOR,DARKRED_COLOR,GREEN_COLOR,BLUE_COLOR},
                    {DARKRED_COLOR,ORANGE_COLOR,ORANGE_COLOR,YELLOW_COLOR},
                    {YELLOW_COLOR,PURPLE_COLOR,DARKRED_COLOR,ORANGE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//71
                    {ORANGE_COLOR,GREEN_COLOR,ORANGE_COLOR,YELLOW_COLOR},
                    {SKYBLUE_COLOR,RED_COLOR,DARKRED_COLOR,RED_COLOR},
                    {BLUE_COLOR,RED_COLOR,BLUE_COLOR,PURPLE_COLOR},
                    {SKYBLUE_COLOR,YELLOW_COLOR,DARKRED_COLOR,DARKRED_COLOR},
                    {PURPLE_COLOR,PURPLE_COLOR,SKYBLUE_COLOR,SKYBLUE_COLOR},
                    {BLUE_COLOR,GREY_COLOR,GREY_COLOR,GREEN_COLOR},
                    {ORANGE_COLOR,RED_COLOR,DARKRED_COLOR,GREEN_COLOR},
                    {PURPLE_COLOR,YELLOW_COLOR,GREY_COLOR,ORANGE_COLOR},
                    {GREEN_COLOR,GREY_COLOR,BLUE_COLOR,YELLOW_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//72
                    {DARKRED_COLOR,BLUE_COLOR,ORANGE_COLOR,YELLOW_COLOR},
                    {BLUE_COLOR,ORANGE_COLOR,GREY_COLOR,DARKRED_COLOR},
                    {GREEN_COLOR,SKYBLUE_COLOR,DARKRED_COLOR,GREEN_COLOR},
                    {SKYBLUE_COLOR,YELLOW_COLOR,GREY_COLOR,SKYBLUE_COLOR},
                    {GREEN_COLOR,ORANGE_COLOR,YELLOW_COLOR,GREY_COLOR},
                    {DARKRED_COLOR,BLUE_COLOR,BLUE_COLOR,SKYBLUE_COLOR},
                    {ORANGE_COLOR,YELLOW_COLOR,GREEN_COLOR,GREY_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//73
                    {BLUE_COLOR,PURPLE_COLOR,SKYBLUE_COLOR,DARKRED_COLOR},
                    {YELLOW_COLOR,RED_COLOR,DARKRED_COLOR,ORANGE_COLOR},
                    {GREEN_COLOR,ORANGE_COLOR,GREEN_COLOR,YELLOW_COLOR},
                    {BLUE_COLOR,GREEN_COLOR,SKYBLUE_COLOR,SKYBLUE_COLOR},
                    {GREY_COLOR,YELLOW_COLOR,PURPLE_COLOR,DARKRED_COLOR},
                    {GREY_COLOR,ORANGE_COLOR,GREY_COLOR,PURPLE_COLOR},
                    {RED_COLOR,YELLOW_COLOR,RED_COLOR,ORANGE_COLOR},
                    {GREEN_COLOR,SKYBLUE_COLOR,BLUE_COLOR,GREY_COLOR},
                    {RED_COLOR,DARKRED_COLOR,PURPLE_COLOR,BLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//74
                    {GREY_COLOR,SKYBLUE_COLOR,GREY_COLOR,DARKRED_COLOR},
                    {DARKRED_COLOR,PURPLE_COLOR,GREEN_COLOR,BLUE_COLOR},
                    {BLUE_COLOR,BLUE_COLOR,ORANGE_COLOR,GREY_COLOR},
                    {SKYBLUE_COLOR,RED_COLOR,GREEN_COLOR,PURPLE_COLOR},
                    {YELLOW_COLOR,ORANGE_COLOR,PURPLE_COLOR,GREY_COLOR},
                    {BLUE_COLOR,ORANGE_COLOR,RED_COLOR,GREEN_COLOR},
                    {YELLOW_COLOR,RED_COLOR,PURPLE_COLOR,DARKRED_COLOR},
                    {DARKRED_COLOR,GREEN_COLOR,YELLOW_COLOR,SKYBLUE_COLOR},
                    {SKYBLUE_COLOR,ORANGE_COLOR,YELLOW_COLOR,RED_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//75
                    {SKYBLUE_COLOR,GREY_COLOR,BLUE_COLOR,ORANGE_COLOR},
                    {GREEN_COLOR,GREEN_COLOR,DARKRED_COLOR,GREY_COLOR},
                    {BLUE_COLOR,SKYBLUE_COLOR,YELLOW_COLOR,GREY_COLOR},
                    {DARKRED_COLOR,GREEN_COLOR,GREEN_COLOR,ORANGE_COLOR},
                    {SKYBLUE_COLOR,GREY_COLOR,ORANGE_COLOR,DARKRED_COLOR},
                    {YELLOW_COLOR,DARKRED_COLOR,SKYBLUE_COLOR,ORANGE_COLOR},
                    {YELLOW_COLOR,BLUE_COLOR,BLUE_COLOR,YELLOW_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//76
                    {GREY_COLOR,GREEN_COLOR,GREEN_COLOR,BLUE_COLOR},
                    {GREY_COLOR,SKYBLUE_COLOR,RED_COLOR,RED_COLOR},
                    {PURPLE_COLOR,GREY_COLOR,YELLOW_COLOR,ORANGE_COLOR},
                    {ORANGE_COLOR,GREY_COLOR,RED_COLOR,GREEN_COLOR},
                    {PURPLE_COLOR,BLUE_COLOR,YELLOW_COLOR,ORANGE_COLOR},
                    {PURPLE_COLOR,YELLOW_COLOR,YELLOW_COLOR,SKYBLUE_COLOR},
                    {DARKRED_COLOR,BLUE_COLOR,ORANGE_COLOR,SKYBLUE_COLOR},
                    {DARKRED_COLOR,BLUE_COLOR,DARKRED_COLOR,DARKRED_COLOR},
                    {GREEN_COLOR,SKYBLUE_COLOR,RED_COLOR,PURPLE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//77
                    {SKYBLUE_COLOR,GREY_COLOR,ORANGE_COLOR,GREY_COLOR},
                    {SKYBLUE_COLOR,GREEN_COLOR,YELLOW_COLOR,ORANGE_COLOR},
                    {GREEN_COLOR,GREEN_COLOR,PURPLE_COLOR,DARKRED_COLOR},
                    {PURPLE_COLOR,YELLOW_COLOR,PURPLE_COLOR,BLUE_COLOR},
                    {BLUE_COLOR,BLUE_COLOR,DARKRED_COLOR,RED_COLOR},
                    {ORANGE_COLOR,DARKRED_COLOR,DARKRED_COLOR,GREY_COLOR},
                    {GREY_COLOR,SKYBLUE_COLOR,YELLOW_COLOR,YELLOW_COLOR},
                    {RED_COLOR,RED_COLOR,PURPLE_COLOR,GREEN_COLOR},
                    {ORANGE_COLOR,RED_COLOR,SKYBLUE_COLOR,BLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//78
                    {GREY_COLOR,ORANGE_COLOR,DARKRED_COLOR,SKYBLUE_COLOR},
                    {SKYBLUE_COLOR,ORANGE_COLOR,SKYBLUE_COLOR,GREEN_COLOR},
                    {BLUE_COLOR,BLUE_COLOR,GREY_COLOR,DARKRED_COLOR},
                    {GREY_COLOR,GREEN_COLOR,SKYBLUE_COLOR,GREEN_COLOR},
                    {YELLOW_COLOR,DARKRED_COLOR,BLUE_COLOR,YELLOW_COLOR},
                    {DARKRED_COLOR,YELLOW_COLOR,GREEN_COLOR,GREY_COLOR},
                    {ORANGE_COLOR,BLUE_COLOR,YELLOW_COLOR,ORANGE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//79
                    {YELLOW_COLOR,RED_COLOR,GREY_COLOR, RED_COLOR},
                    {YELLOW_COLOR,SKYBLUE_COLOR,GREEN_COLOR,RED_COLOR},
                    {ORANGE_COLOR,PURPLE_COLOR,SKYBLUE_COLOR,SKYBLUE_COLOR},
                    {DARKRED_COLOR,GREY_COLOR,ORANGE_COLOR,DARKRED_COLOR},
                    {YELLOW_COLOR,BLUE_COLOR,GREY_COLOR,RED_COLOR},
                    {BLUE_COLOR,BLUE_COLOR,DARKRED_COLOR,PURPLE_COLOR},
                    {PURPLE_COLOR,GREEN_COLOR,DARKRED_COLOR,PURPLE_COLOR},
                    {GREEN_COLOR,ORANGE_COLOR,GREY_COLOR,BLUE_COLOR},
                    {SKYBLUE_COLOR,YELLOW_COLOR,ORANGE_COLOR,GREEN_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//80
                    {RED_COLOR,ORANGE_COLOR,RED_COLOR,DARKRED_COLOR},
                    {GREEN_COLOR,GREY_COLOR,GREY_COLOR,RED_COLOR},
                    {YELLOW_COLOR,ORANGE_COLOR,GREEN_COLOR,GREEN_COLOR},
                    {YELLOW_COLOR,SKYBLUE_COLOR,ORANGE_COLOR,SKYBLUE_COLOR},
                    {BLUE_COLOR,BLUE_COLOR,RED_COLOR,DARKRED_COLOR},
                    {SKYBLUE_COLOR,SKYBLUE_COLOR,PURPLE_COLOR,GREY_COLOR},
                    {DARKRED_COLOR,BLUE_COLOR,YELLOW_COLOR,GREY_COLOR},
                    {DARKRED_COLOR,YELLOW_COLOR,GREEN_COLOR,ORANGE_COLOR},
                    {BLUE_COLOR,PURPLE_COLOR,PURPLE_COLOR,PURPLE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//81
                    {DARKRED_COLOR,GREEN_COLOR,YELLOW_COLOR,YELLOW_COLOR},
                    {GREEN_COLOR,GREY_COLOR,GREY_COLOR,DARKRED_COLOR},
                    {DARKRED_COLOR,YELLOW_COLOR,SKYBLUE_COLOR,SKYBLUE_COLOR},
                    {GREY_COLOR,BLUE_COLOR,DARKRED_COLOR,GREY_COLOR},
                    {ORANGE_COLOR,SKYBLUE_COLOR,ORANGE_COLOR,ORANGE_COLOR},
                    {BLUE_COLOR,GREEN_COLOR,YELLOW_COLOR,ORANGE_COLOR},
                    {BLUE_COLOR,GREEN_COLOR,SKYBLUE_COLOR,BLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//82
                    {GREY_COLOR,PURPLE_COLOR,PURPLE_COLOR,PURPLE_COLOR},
                    {ORANGE_COLOR,DARKRED_COLOR,RED_COLOR,YELLOW_COLOR},
                    {GREEN_COLOR,RED_COLOR,SKYBLUE_COLOR,GREY_COLOR},
                    {GREEN_COLOR,RED_COLOR,RED_COLOR,YELLOW_COLOR},
                    {YELLOW_COLOR,BLUE_COLOR,SKYBLUE_COLOR,GREEN_COLOR},
                    {BLUE_COLOR,GREY_COLOR,BLUE_COLOR,SKYBLUE_COLOR},
                    {GREEN_COLOR,ORANGE_COLOR,DARKRED_COLOR,PURPLE_COLOR},
                    {DARKRED_COLOR,SKYBLUE_COLOR,YELLOW_COLOR,ORANGE_COLOR},
                    {ORANGE_COLOR,BLUE_COLOR,GREY_COLOR,DARKRED_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//83
                    {GREY_COLOR,PURPLE_COLOR,ORANGE_COLOR,RED_COLOR},
                    {RED_COLOR,YELLOW_COLOR,GREY_COLOR,GREEN_COLOR},
                    {YELLOW_COLOR,SKYBLUE_COLOR,PURPLE_COLOR,ORANGE_COLOR},
                    {GREY_COLOR,ORANGE_COLOR,BLUE_COLOR,YELLOW_COLOR},
                    {PURPLE_COLOR,RED_COLOR,BLUE_COLOR,PURPLE_COLOR},
                    {SKYBLUE_COLOR,YELLOW_COLOR,ORANGE_COLOR,DARKRED_COLOR},
                    {BLUE_COLOR,GREEN_COLOR,BLUE_COLOR,DARKRED_COLOR},
                    {GREEN_COLOR,GREEN_COLOR,SKYBLUE_COLOR,SKYBLUE_COLOR},
                    {DARKRED_COLOR,DARKRED_COLOR,RED_COLOR,GREY_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//84
                    {GREEN_COLOR,YELLOW_COLOR,GREY_COLOR,DARKRED_COLOR},
                    {YELLOW_COLOR,DARKRED_COLOR,BLUE_COLOR,ORANGE_COLOR},
                    {GREEN_COLOR,BLUE_COLOR,SKYBLUE_COLOR,BLUE_COLOR},
                    {BLUE_COLOR,ORANGE_COLOR,YELLOW_COLOR,YELLOW_COLOR},
                    {GREEN_COLOR,SKYBLUE_COLOR,GREY_COLOR,ORANGE_COLOR},
                    {DARKRED_COLOR,GREY_COLOR,ORANGE_COLOR,GREEN_COLOR},
                    {SKYBLUE_COLOR,GREY_COLOR,SKYBLUE_COLOR,DARKRED_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//85
                    {BLUE_COLOR,YELLOW_COLOR,GREEN_COLOR,DARKRED_COLOR},
                    {BLUE_COLOR,GREY_COLOR,SKYBLUE_COLOR,SKYBLUE_COLOR},
                    {GREY_COLOR,GREEN_COLOR,RED_COLOR,YELLOW_COLOR},
                    {ORANGE_COLOR,DARKRED_COLOR,ORANGE_COLOR,BLUE_COLOR},
                    {BLUE_COLOR,PURPLE_COLOR,ORANGE_COLOR,GREY_COLOR},
                    {ORANGE_COLOR,GREEN_COLOR,DARKRED_COLOR,YELLOW_COLOR},
                    {DARKRED_COLOR,RED_COLOR, SKYBLUE_COLOR,YELLOW_COLOR},
                    {RED_COLOR,PURPLE_COLOR,RED_COLOR,SKYBLUE_COLOR},
                    {PURPLE_COLOR,PURPLE_COLOR,GREEN_COLOR,GREY_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//86
                    {RED_COLOR,BLUE_COLOR,SKYBLUE_COLOR,GREY_COLOR},
                    {PURPLE_COLOR, PURPLE_COLOR,YELLOW_COLOR,BLUE_COLOR},
                    {RED_COLOR,GREEN_COLOR,ORANGE_COLOR,DARKRED_COLOR},
                    {YELLOW_COLOR,SKYBLUE_COLOR,GREY_COLOR,ORANGE_COLOR},
                    {DARKRED_COLOR,GREEN_COLOR,ORANGE_COLOR,YELLOW_COLOR},
                    {RED_COLOR,YELLOW_COLOR,BLUE_COLOR,DARKRED_COLOR},
                    {GREY_COLOR,PURPLE_COLOR,GREEN_COLOR,BLUE_COLOR},
                    {DARKRED_COLOR,RED_COLOR,PURPLE_COLOR,GREEN_COLOR},
                    {GREY_COLOR,SKYBLUE_COLOR,ORANGE_COLOR,SKYBLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//87
                    {YELLOW_COLOR,SKYBLUE_COLOR,BLUE_COLOR,SKYBLUE_COLOR},
                    {BLUE_COLOR,ORANGE_COLOR,SKYBLUE_COLOR,ORANGE_COLOR},
                    {SKYBLUE_COLOR,GREEN_COLOR,BLUE_COLOR,GREY_COLOR},
                    {GREY_COLOR,DARKRED_COLOR,DARKRED_COLOR,GREEN_COLOR},
                    {YELLOW_COLOR,YELLOW_COLOR,ORANGE_COLOR,GREY_COLOR},
                    {BLUE_COLOR,YELLOW_COLOR,GREEN_COLOR,DARKRED_COLOR},
                    {ORANGE_COLOR,GREY_COLOR,DARKRED_COLOR,GREEN_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//88
                    {DARKRED_COLOR,YELLOW_COLOR,GREY_COLOR,BLUE_COLOR},
                    {GREY_COLOR,RED_COLOR,DARKRED_COLOR,PURPLE_COLOR},
                    {GREY_COLOR,ORANGE_COLOR,PURPLE_COLOR,BLUE_COLOR},
                    {SKYBLUE_COLOR,ORANGE_COLOR,RED_COLOR,PURPLE_COLOR},
                    {RED_COLOR,DARKRED_COLOR,GREEN_COLOR,YELLOW_COLOR},
                    {SKYBLUE_COLOR,PURPLE_COLOR,GREEN_COLOR,ORANGE_COLOR},
                    {RED_COLOR,YELLOW_COLOR,YELLOW_COLOR,GREY_COLOR},
                    {DARKRED_COLOR,ORANGE_COLOR,GREEN_COLOR,BLUE_COLOR},
                    {SKYBLUE_COLOR,GREEN_COLOR,BLUE_COLOR,SKYBLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//89
                    {BLUE_COLOR,PURPLE_COLOR,DARKRED_COLOR,DARKRED_COLOR},
                    {PURPLE_COLOR,ORANGE_COLOR,SKYBLUE_COLOR,RED_COLOR},
                    {BLUE_COLOR,PURPLE_COLOR,ORANGE_COLOR,GREY_COLOR},
                    {GREEN_COLOR,GREY_COLOR,GREEN_COLOR,YELLOW_COLOR},
                    {SKYBLUE_COLOR,DARKRED_COLOR,RED_COLOR,RED_COLOR},
                    {GREY_COLOR,BLUE_COLOR,SKYBLUE_COLOR,PURPLE_COLOR},
                    {SKYBLUE_COLOR,DARKRED_COLOR,GREEN_COLOR,ORANGE_COLOR},
                    {GREEN_COLOR,YELLOW_COLOR,BLUE_COLOR,RED_COLOR},
                    {YELLOW_COLOR,YELLOW_COLOR,GREY_COLOR,ORANGE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//90
                    {BLUE_COLOR,YELLOW_COLOR,DARKRED_COLOR,YELLOW_COLOR},
                    {BLUE_COLOR,GREY_COLOR,SKYBLUE_COLOR,SKYBLUE_COLOR},
                    {ORANGE_COLOR,DARKRED_COLOR,GREEN_COLOR,GREY_COLOR},
                    {GREY_COLOR,DARKRED_COLOR,YELLOW_COLOR,ORANGE_COLOR},
                    {GREEN_COLOR,BLUE_COLOR,GREY_COLOR,GREEN_COLOR},
                    {DARKRED_COLOR,GREEN_COLOR,SKYBLUE_COLOR,YELLOW_COLOR},
                    {BLUE_COLOR,ORANGE_COLOR,ORANGE_COLOR,SKYBLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//91
                    {YELLOW_COLOR,SKYBLUE_COLOR,YELLOW_COLOR,DARKRED_COLOR},
                    {DARKRED_COLOR,BLUE_COLOR,PURPLE_COLOR,YELLOW_COLOR},
                    {YELLOW_COLOR,ORANGE_COLOR,GREY_COLOR,ORANGE_COLOR},
                    {GREY_COLOR,GREEN_COLOR,GREEN_COLOR,PURPLE_COLOR},
                    {RED_COLOR,DARKRED_COLOR,PURPLE_COLOR,BLUE_COLOR},
                    {SKYBLUE_COLOR,GREY_COLOR,RED_COLOR,RED_COLOR},
                    {GREEN_COLOR,BLUE_COLOR,BLUE_COLOR,DARKRED_COLOR},
                    {GREEN_COLOR,RED_COLOR,SKYBLUE_COLOR,PURPLE_COLOR},
                    {ORANGE_COLOR,GREY_COLOR,SKYBLUE_COLOR,ORANGE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//92
                    {GREEN_COLOR,YELLOW_COLOR,ORANGE_COLOR,DARKRED_COLOR},
                    {ORANGE_COLOR,GREY_COLOR,BLUE_COLOR,BLUE_COLOR},
                    {ORANGE_COLOR,DARKRED_COLOR,DARKRED_COLOR,RED_COLOR},
                    {YELLOW_COLOR,SKYBLUE_COLOR,GREEN_COLOR,GREY_COLOR},
                    {RED_COLOR,GREEN_COLOR,SKYBLUE_COLOR,RED_COLOR},
                    {ORANGE_COLOR,BLUE_COLOR,PURPLE_COLOR,SKYBLUE_COLOR},
                    {PURPLE_COLOR,YELLOW_COLOR,GREY_COLOR,SKYBLUE_COLOR},
                    {RED_COLOR,GREY_COLOR,DARKRED_COLOR,PURPLE_COLOR},
                    {PURPLE_COLOR,BLUE_COLOR,GREEN_COLOR,YELLOW_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//93
                    {YELLOW_COLOR,BLUE_COLOR,BLUE_COLOR,YELLOW_COLOR},
                    {GREEN_COLOR,GREEN_COLOR,ORANGE_COLOR,YELLOW_COLOR},
                    {YELLOW_COLOR,GREY_COLOR,DARKRED_COLOR,GREY_COLOR},
                    {SKYBLUE_COLOR,SKYBLUE_COLOR,GREEN_COLOR,BLUE_COLOR},
                    {ORANGE_COLOR,ORANGE_COLOR,DARKRED_COLOR,SKYBLUE_COLOR},
                    {BLUE_COLOR,DARKRED_COLOR,ORANGE_COLOR,GREEN_COLOR},
                    {SKYBLUE_COLOR,DARKRED_COLOR,GREY_COLOR,GREY_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//94
                    {SKYBLUE_COLOR,GREEN_COLOR,DARKRED_COLOR,PURPLE_COLOR},
                    {SKYBLUE_COLOR,YELLOW_COLOR,GREEN_COLOR,RED_COLOR},
                    {PURPLE_COLOR,YELLOW_COLOR,RED_COLOR,GREY_COLOR},
                    {RED_COLOR,BLUE_COLOR,YELLOW_COLOR,GREY_COLOR},
                    {SKYBLUE_COLOR,PURPLE_COLOR,BLUE_COLOR,GREY_COLOR},
                    {RED_COLOR,ORANGE_COLOR,GREEN_COLOR,ORANGE_COLOR},
                    {DARKRED_COLOR,GREY_COLOR,BLUE_COLOR,PURPLE_COLOR},
                    {DARKRED_COLOR,GREEN_COLOR,ORANGE_COLOR,SKYBLUE_COLOR},
                    {BLUE_COLOR,ORANGE_COLOR,DARKRED_COLOR,YELLOW_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//95
                    {DARKRED_COLOR,ORANGE_COLOR,YELLOW_COLOR,RED_COLOR},
                    {DARKRED_COLOR,GREY_COLOR,GREEN_COLOR,RED_COLOR},
                    {GREY_COLOR,GREEN_COLOR,ORANGE_COLOR,YELLOW_COLOR},
                    {YELLOW_COLOR,ORANGE_COLOR,PURPLE_COLOR,SKYBLUE_COLOR},
                    {BLUE_COLOR,DARKRED_COLOR,SKYBLUE_COLOR,SKYBLUE_COLOR},
                    {BLUE_COLOR,RED_COLOR,YELLOW_COLOR,SKYBLUE_COLOR},
                    {PURPLE_COLOR,BLUE_COLOR,ORANGE_COLOR,PURPLE_COLOR},
                    {GREEN_COLOR,DARKRED_COLOR,GREY_COLOR,PURPLE_COLOR},
                    {GREY_COLOR,GREEN_COLOR,BLUE_COLOR,RED_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//96
                    {SKYBLUE_COLOR,GREEN_COLOR,BLUE_COLOR,GREEN_COLOR},
                    {GREEN_COLOR,YELLOW_COLOR,DARKRED_COLOR,DARKRED_COLOR},
                    {DARKRED_COLOR,ORANGE_COLOR,YELLOW_COLOR,ORANGE_COLOR},
                    {ORANGE_COLOR,SKYBLUE_COLOR,GREY_COLOR,BLUE_COLOR},
                    {GREY_COLOR,ORANGE_COLOR,SKYBLUE_COLOR,BLUE_COLOR},
                    {GREY_COLOR,YELLOW_COLOR,BLUE_COLOR,DARKRED_COLOR},
                    {GREY_COLOR,SKYBLUE_COLOR,GREEN_COLOR,YELLOW_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//97
                    {RED_COLOR,BLUE_COLOR,GREEN_COLOR,PURPLE_COLOR},
                    {SKYBLUE_COLOR,BLUE_COLOR,ORANGE_COLOR,ORANGE_COLOR},
                    {YELLOW_COLOR,RED_COLOR,DARKRED_COLOR,PURPLE_COLOR},
                    {SKYBLUE_COLOR,GREY_COLOR,SKYBLUE_COLOR,ORANGE_COLOR},
                    {YELLOW_COLOR,GREEN_COLOR,GREY_COLOR,SKYBLUE_COLOR},
                    {GREY_COLOR,DARKRED_COLOR,YELLOW_COLOR,BLUE_COLOR},
                    {YELLOW_COLOR,DARKRED_COLOR,GREEN_COLOR,GREY_COLOR},
                    {PURPLE_COLOR,ORANGE_COLOR,BLUE_COLOR,GREEN_COLOR},
                    {DARKRED_COLOR,RED_COLOR,PURPLE_COLOR,RED_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//98
                    {ORANGE_COLOR,PURPLE_COLOR,GREEN_COLOR,SKYBLUE_COLOR},
                    {RED_COLOR,BLUE_COLOR,ORANGE_COLOR,GREY_COLOR},
                    {GREY_COLOR,RED_COLOR,RED_COLOR,GREEN_COLOR},
                    {BLUE_COLOR,GREEN_COLOR,PURPLE_COLOR,RED_COLOR},
                    {PURPLE_COLOR,SKYBLUE_COLOR,DARKRED_COLOR,GREY_COLOR},
                    {BLUE_COLOR,ORANGE_COLOR,GREY_COLOR,SKYBLUE_COLOR},
                    {SKYBLUE_COLOR,PURPLE_COLOR,DARKRED_COLOR,ORANGE_COLOR},
                    {DARKRED_COLOR,YELLOW_COLOR,YELLOW_COLOR,DARKRED_COLOR},
                    {YELLOW_COLOR,GREEN_COLOR,YELLOW_COLOR,BLUE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//99
                    {GREY_COLOR,ORANGE_COLOR,SKYBLUE_COLOR,GREY_COLOR},
                    {DARKRED_COLOR,YELLOW_COLOR,GREEN_COLOR,BLUE_COLOR},
                    {GREEN_COLOR,GREY_COLOR,BLUE_COLOR,ORANGE_COLOR},
                    {DARKRED_COLOR,GREY_COLOR,ORANGE_COLOR,DARKRED_COLOR},
                    {GREEN_COLOR,YELLOW_COLOR,SKYBLUE_COLOR,DARKRED_COLOR},
                    {BLUE_COLOR,GREEN_COLOR,SKYBLUE_COLOR,YELLOW_COLOR},
                    {YELLOW_COLOR,SKYBLUE_COLOR,BLUE_COLOR,ORANGE_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },
            {//100
                    {GREEN_COLOR,RED_COLOR,DARKRED_COLOR,BLUE_COLOR},
                    {BLUE_COLOR,GREY_COLOR,ORANGE_COLOR,GREY_COLOR},
                    {GREEN_COLOR,DARKRED_COLOR,BLUE_COLOR,SKYBLUE_COLOR},
                    {YELLOW_COLOR,YELLOW_COLOR,PURPLE_COLOR,BLUE_COLOR},
                    {YELLOW_COLOR,ORANGE_COLOR,RED_COLOR,YELLOW_COLOR},
                    {ORANGE_COLOR,ORANGE_COLOR,PURPLE_COLOR,PURPLE_COLOR},
                    {GREEN_COLOR,SKYBLUE_COLOR,GREY_COLOR,SKYBLUE_COLOR},
                    {DARKRED_COLOR,SKYBLUE_COLOR,DARKRED_COLOR,RED_COLOR},
                    {GREY_COLOR,PURPLE_COLOR,RED_COLOR,GREEN_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR},
                    {NO_COLOR, NO_COLOR, NO_COLOR, NO_COLOR}
            },











    };

    RecyclerView recyclerViewLevels;
    int maxUnlockedLevels;
    LevelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_sort_level);
        writeLevelInfoToPreference();
        linkXml();
    }

    private void linkXml() {
        recyclerViewLevels = findViewById(R.id.recyclerviewLevels);
        recyclerViewLevels.setLayoutManager(new GridLayoutManager(WaterSortLevelActivity.this, 3 , RecyclerView.VERTICAL, false));
        recyclerViewLevels.setHasFixedSize(true);
        adapter = new LevelAdapter(WaterSortLevelActivity.this, tubeColors.length, maxUnlockedLevels, new LevelClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(WaterSortLevelActivity.this, WaterSortMainActivity.class);
                intent.putExtra(Data.TAG_WATERSORT_LEVELSINFO,position);
                startActivity(intent);
            }
        });
        recyclerViewLevels.setAdapter(adapter);
    }

    private void writeLevelInfoToPreference()
    {
        SharedPreferences getshared = getSharedPreferences(getString(R.string.shared_preference_filename), MODE_PRIVATE);
        String s = getshared.getString(Data.TAG_WATERSORT_LEVELSINFO,"0");

//        String s = String.valueOf(140);


        loadLevelsFromString(s);

    }

    private void loadLevelsFromString(String fileString) {
        maxUnlockedLevels = Integer.parseInt(fileString);
//        maxUnlockedLevels = 140;
//        if (fileString != null && fileString != "")
//        {
//            unlockedLevels = fileString.split(",");
//            ArrayList<Integer> levelList = new ArrayList<>();
//            for (String level : unlockedLevels) {
//                levelList.add(Integer.parseInt(level.trim()));
//            }
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter != null)
        {
            writeLevelInfoToPreference();
            adapter.updateMaxUnlockedLevel(maxUnlockedLevels);
            adapter.notifyDataSetChanged();
        }
    }
}
