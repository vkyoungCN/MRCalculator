package com.vkyoungcn.smartdevices.mrcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //TV和ET都需要跨方法获取和使用，所以声明为全局的
    //结果组
    private TextView tvResult_1;
    private TextView tvResult_2;
    private TextView tvResult_10;
    private TextView tvResult_30;
    private TextView tvResult_60;
    private TextView tvResult_360;//6h
    private TextView tvResult_720;//12h

    private TextView tvResult_1440;//1day
    private TextView tvResult_2880;//2days
    private TextView tvResult_4320;//3d
    private TextView tvResult_7200;//5d
    private TextView tvResult_10080;//7d
    private TextView tvResult_21600;//15d
    private TextView tvResult_43200;//30d

    private TextView tvResult_87840;//2mon(记61天)
    private TextView tvResult_131040;//3mon（记91天）
    private TextView tvResult_263520;//6mon（记183天）

    //参数组
//    private EditText etParam_a;
    private EditText etParam_alpha;
    private EditText etParam_beta;
    private EditText etParam_n;
    private EditText etParam_m;
//    private EditText etParam_gama;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult_1 = findViewById(R.id.result_aa);
        tvResult_2 = findViewById(R.id.result_ab);
        tvResult_10 = findViewById(R.id.result_ac);
        tvResult_30 = findViewById(R.id.result_ad);
        tvResult_60 = findViewById(R.id.result_ae);
        tvResult_360 = findViewById(R.id.result_af);
        tvResult_720 = findViewById(R.id.result_ag);

        tvResult_1440 = findViewById(R.id.result_ba);
        tvResult_2880 = findViewById(R.id.result_bb);
        tvResult_4320 = findViewById(R.id.result_bc);
        tvResult_7200 = findViewById(R.id.result_bd);
        tvResult_10080 = findViewById(R.id.result_be);
        tvResult_21600 =  findViewById(R.id.result_bf);
        tvResult_43200 = findViewById(R.id.result_bg);

        tvResult_87840 = findViewById(R.id.result_ca);
        tvResult_131040 = findViewById(R.id.result_cb);
        tvResult_263520 = findViewById(R.id.result_cc);

//        etParam_a = findViewById(R.id.param_a);
        etParam_alpha = findViewById(R.id.param_alpha);
        etParam_beta = findViewById(R.id.param_beta);
        etParam_n = findViewById(R.id.param_n);
        etParam_m = findViewById(R.id.param_m);
//        etParam_gama = findViewById(R.id.param_gama);


    }


    //“计算”按钮对应的点击事件；
    public void calculate(View view){

        //取各Editor的参数（如果没有，就采用默认值）（目前只需要4个参数）
        float alpha = (etParam_alpha.getText().length()==0)?0f:Float.valueOf(etParam_alpha.getText().toString());
        float beta = (etParam_beta.getText().length()==0)?0f:Float.valueOf(etParam_beta.getText().toString());
        int n = (etParam_n.getText().length()==0)?1:Integer.valueOf(etParam_n.getText().toString());
        int m = (etParam_m.getText().length()==0)?1:Integer.valueOf(etParam_m.getText().toString());

        //构造一个用于Toast消息的字串。原使用StringBuilder，但编译器提示直接用String更好。
        String strForToast = "参数：α="+String.valueOf(alpha)+", β="+String.valueOf(beta)+
                ", n="+String.valueOf(n)+", m="+String.valueOf(m)+"。";

        Toast.makeText(this, strForToast, Toast.LENGTH_SHORT).show();

        //多次调用计算函数（传入刚才确定的值，以及预设的若干个分钟数）
        int[] timeIntervals = {1,2,10,30,60,360,720,1440,2880,4320,7200,10080,21600,43200,87840,131040,263520};//预设的（需计算的）时间间隔
        ArrayList<Double> memoryRetaining = new ArrayList<>();
        for (int t :timeIntervals) {
            Double M = baseCalculate(alpha,beta,n,m,t);
            memoryRetaining.add(M);
        }

        if(memoryRetaining.size()<17){
            Toast.makeText(this, "计算结果数量错误。", Toast.LENGTH_SHORT).show();
            return;
        }

        //将同一套参数下，不同时间间隔的计算结果设置到UI上方列表。
        tvResult_1.setText(String.valueOf(memoryRetaining.get(0)));
        tvResult_2.setText(String.valueOf(memoryRetaining.get(1)));
        tvResult_10.setText(String.valueOf(memoryRetaining.get(2)));
        tvResult_30.setText(String.valueOf(memoryRetaining.get(3)));
        tvResult_60.setText(String.valueOf(memoryRetaining.get(4)));
        tvResult_360.setText(String.valueOf(memoryRetaining.get(5)));
        tvResult_720.setText(String.valueOf(memoryRetaining.get(6)));

        tvResult_1440.setText(String.valueOf(memoryRetaining.get(7)));
        tvResult_2880.setText(String.valueOf(memoryRetaining.get(8)));
        tvResult_4320.setText(String.valueOf(memoryRetaining.get(9)));
        tvResult_7200.setText(String.valueOf(memoryRetaining.get(10)));
        tvResult_10080.setText(String.valueOf(memoryRetaining.get(11)));
        tvResult_21600.setText(String.valueOf(memoryRetaining.get(12)));
        tvResult_43200.setText(String.valueOf(memoryRetaining.get(13)));

        tvResult_87840.setText(String.valueOf(memoryRetaining.get(14)));
        tvResult_131040.setText(String.valueOf(memoryRetaining.get(15)));
        tvResult_263520.setText(String.valueOf(memoryRetaining.get(16)));
    }


    private double baseCalculate(float alpha, float beta, int fakeNum, int realNum, int timeInterval){
        double m1 = 100*(Math.pow((1+alpha),fakeNum))*(Math.pow((1+beta),realNum));//分子部分
        double m2 = timeInterval+(Math.pow((1+alpha),fakeNum))*(Math.pow((1+beta),realNum))-1;//分母部分
        double retainingMemory = m1/m2;
        BigDecimal rM_BD = new BigDecimal(retainingMemory);
        return rM_BD.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public void toTimeActivity(View view){
        Intent intent = new Intent(this, TimeCalculator.class);
        startActivity(intent);
    }

}
