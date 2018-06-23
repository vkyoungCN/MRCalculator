package com.vkyoungcn.smartdevices.mrcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TimeCalculator extends AppCompatActivity {

    private TextView[] times = new TextView[13];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_calculator);

        times[0] = findViewById(R.id.tv_0_3_TC);
        times[1] = findViewById(R.id.tv_1_3_TC);
        times[2] = findViewById(R.id.tv_2_3_TC);
        times[3] = findViewById(R.id.tv_3_3_TC);
        times[4] = findViewById(R.id.tv_4_3_TC);
        times[5] = findViewById(R.id.tv_5_3_TC);
        times[6] = findViewById(R.id.tv_6_3_TC);
        times[7] = findViewById(R.id.tv_7_3_TC);
        times[8] = findViewById(R.id.tv_8_3_TC);
        times[9] = findViewById(R.id.tv_9_3_TC);
        times[10] = findViewById(R.id.tv_10_3_TC);
        times[11] = findViewById(R.id.tv_11_3_TC);
        times[12] = findViewById(R.id.tv_12_3_TC);

        //以i作为MS。
        for(byte i=0;i<13;i++){
            times[i].setText(formatFromMinutes(minutesRemainTillThreshold(i)));
        }

    }

    /*
    * 指定MS下计算从100到某（暂时固定的）RMA之间的总时限
    * */
    private int minutesRemainTillThreshold( byte memoryStage){
        float alpha = 2.5f;
        float beta =2.05f;
        int fakeNum = 3;

        int target_RM ;
        switch (memoryStage){
            case 0:
            case 1:
                target_RM =10;
                break;
            case 2:
                target_RM =12;
                break;
            case 3:
                target_RM =16;
                break;
            case 4:
                target_RM=25;
                break;
            case 5:
                target_RM=39;
                break;
            case 6:
                target_RM=59;
                break;
            case 7:
                target_RM=78;
                break;
            case 8:
                target_RM=89;
                break;
            case 9:
                target_RM=95;
                break;
            case 10:
                target_RM=98;
                break;
            default:
                target_RM = 99;

        }

        double block_1 = (Math.pow((1+alpha),fakeNum))*(Math.pow((1+beta),memoryStage));
        return (int)((100*block_1)/target_RM+1-block_1);
    }

    /*
    * 分钟换算成更易读的单位
    * */
    private String formatFromMinutes(int minutes){
        int days = minutes/(60*24);
        int hours = (minutes%(60*24))/60;
        int min = (minutes%60);
        if(days!=0) {
            return days + "d " + hours + "h " + min + "min";
        }else if(hours!=0){
            return hours + "h " + min + "min";
        }else {
            return min + "min";
        }
    }
}
