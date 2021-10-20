package com.example.social_network_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.social_network_app.Basic_classes.PostDao.Post;
import com.example.social_network_app.Basic_classes.UserDao.User;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BarActivity extends AppCompatActivity {

    //显示的图表
    public BarChart barChart;
    public BarChart barChart2;
    public BarChart barChart3;
    TextView title;

    List<Post> resultList = new ArrayList<>();
    Map<String,Integer> commentsMonthly = new TreeMap<>();
    Map<String,Integer> likesMonthly = new TreeMap<>();
    User user;

    public Map<String, Integer> initMonthly(Map<String,Integer> map) {
        map.put("01",0);
        map.put("02",0);
        map.put("03",0);
        map.put("04",0);
        map.put("05",0);
        map.put("06",0);
        map.put("07",0);
        map.put("08",0);
        map.put("09",0);
        map.put("10",0);
        map.put("11",0);
        map.put("12",0);
        return map;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        user = (User) getIntent().getSerializableExtra("user");

        title = findViewById(R.id.report_title);
        title.setText(user.getName()+"'s report");

        barChart = (BarChart) findViewById(R.id.report);
        barChart2 = (BarChart) findViewById(R.id.report2);
        barChart3 = (BarChart) findViewById(R.id.report3);

        int fansCount = Integer.parseInt(getIntent().getStringExtra("fansCount"));

        GlobalVariable global = (GlobalVariable) getApplication();
        List<Post> postList = global.getPostList();


        Map<String, Integer> cMonthly = initMonthly(commentsMonthly);
        Map<String, Integer> lMonthly = initMonthly(likesMonthly);
        for(int i=0;i<postList.size();i++){
            if(postList.get(i).getUser(this).equals(user)){
                resultList.add(postList.get(i));
            }
        }

        for(int i=0;i<resultList.size();i++){
            String month = resultList.get(i).getDatetime().substring(5, 7);
            int c = cMonthly.get(month);
            int l = lMonthly.get(month);
            cMonthly.put(month,c+1);
            lMonthly.put(month,l+resultList.get(i).getLikeCount());
        }



        drawComments(cMonthly);
        drawLikeCount(lMonthly);
        drawFans(fansCount);




    }

    public void drawComments(Map<String,Integer> map){
        ArrayList<BarEntry> entries = new ArrayList<>();
        BarDataSet dataset;
        int index = 1;
        Log.e("!!!!",map.toString());
        for(int cm_count : map.values()){
            entries.add(new BarEntry(index++, cm_count));
        }
        dataset = new BarDataSet(entries, "Number of comments per month");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataset);
        BarData data = new BarData(dataSets);
        barChart.setData(data);


        //设置显示动画效果
        barChart.animateY(2000);
        barChart.getDescription().setEnabled(false);
        setLayout(barChart);
    }

    public void drawLikeCount(Map<String,Integer> map){
        ArrayList<BarEntry> entries = new ArrayList<>();
        BarDataSet dataset;
        int index = 1;
        for(int lm_count : map.values()){
            entries.add(new BarEntry(index++, lm_count));
        }



        dataset = new BarDataSet(entries, "Number of likes received from comments posted per month");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        ArrayList<IBarDataSet> dataSets2 = new ArrayList<>();
        dataSets2.add(dataset);
        BarData data = new BarData(dataSets2);
        barChart2.setData(data);


        //设置显示动画效果
        barChart2.animateY(2000);
        barChart2.getDescription().setEnabled(false);
        setLayout(barChart2);
    }

    public void drawFans(int num){
        ArrayList<BarEntry> entries = new ArrayList<>();
        BarDataSet dataset;
        entries.add(new BarEntry(1,(float)num));
        dataset = new BarDataSet(entries,"Number of fans");
        dataset.setColors(Color.rgb(255,182,193));
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataset);
        BarData data = new BarData(dataSets);
        barChart3.setData(data);

        barChart3.getDescription().setEnabled(false);
        barChart3.getAxisLeft().setEnabled(false);

        XAxis xAxis=barChart3.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new ValueFormatter() {   //X轴自定义坐标
            @Override
            public String getFormattedValue(float v) {
                if (v==1) return user.getName();
                return "";
            }
        });
        xAxis.setLabelCount(1,false);

        YAxis yAxis = barChart3.getAxisRight();
        yAxis.setDrawGridLines(false);

        //设置显示动画效果
        barChart3.animateX(2000, Easing.EaseInSine);
        barChart3.getDescription().setEnabled(false);



//        yAxis.setAxisMaximum(100f);
//        yAxis.setAxisMinimum(0.5f);
//        yAxis.setGranularity(1f);
//        yAxis.setLabelCount(5,false);


    }

    public void setLayout(BarChart bar){
        //X轴
        XAxis xAxis=bar.getXAxis();
        xAxis.setDrawGridLines(false);  //是否绘制X轴上的网格线（背景里面的竖线）
        xAxis.setAxisLineWidth(2);           //X轴粗细
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);        //X轴所在位置   默认为上面

        xAxis.setValueFormatter(new ValueFormatter() {   //X轴自定义坐标
            @Override
            public String getFormattedValue(float v) {
                if (v==1) return "Jan.";
                if (v==2) return "Feb.";
                if (v==3) return "Mar.";
                if (v==4) return "Apr.";
                if (v==5) return "May.";
                if (v==6) return "Jun.";
                if (v==7) return "Jul.";
                if (v==8) return "Aug.";
                if (v==9) return "Sept.";
                if (v==10) return "Oct.";
                if (v==11) return "Nov.";
                if (v==12) return "Dec.";
                return "";//注意这里需要改成 ""
            }
        });
//        xAxis.setAxisMaximum(9);   //X轴最大数值
//        xAxis.setAxisMinimum(1);   //X轴最小数值
//        //X轴坐标的个数    第二个参数一般填false     true表示强制设置标签数 可能会导致X轴坐标显示不全等问题
          xAxis.setLabelCount(9,false);


        //Y轴
        YAxis AxisLeft=bar.getAxisLeft();
        AxisLeft.setDrawGridLines(false);  //是否绘制Y轴上的网格线（背景里面的横线）
        AxisLeft.setAxisLineWidth(2);           //Y轴粗细

        //是否隐藏右边的Y轴（不设置的话有两条Y轴 同理可以隐藏左边的Y轴）
        bar.getAxisRight().setEnabled(false);
    }
}