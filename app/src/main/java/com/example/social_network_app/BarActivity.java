package com.example.social_network_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.social_network_app.Basic_classes.PostDao.Post;
import com.example.social_network_app.Basic_classes.UserDao.User;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarActivity extends AppCompatActivity {

    //显示的图表
    public BarChart barChart;
    //保存数据的实体
    public ArrayList<BarEntry> entries = new ArrayList<>();
    //数据的集合（每组数据都需要一个数据集合存放数据实体和该组的样式）
    public BarDataSet dataset;
    //表格下方的文字
    public ArrayList<String> labels = new ArrayList<String>();


    List<Post> resultList = new ArrayList<>();
    Map<String,Integer> commentsMonthly = new HashMap<>();
    Map<String,Integer> likesMonthly = new HashMap<>();

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
        return map;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        float commentsCount = Float.parseFloat(getIntent().getStringExtra("commentsCount"));
        float fansCount = Float.parseFloat(getIntent().getStringExtra("fansCount"));
        float likesCount = Float.parseFloat(getIntent().getStringExtra("likesCount"));

        GlobalVariable global = (GlobalVariable) getApplication();
        List<Post> postList = global.getPostList();

        String username = getIntent().getStringExtra("username");
        Map<String, Integer> cMonthly = initMonthly(commentsMonthly);
        Map<String, Integer> lMonthly = initMonthly(likesMonthly);
        for(int i=0;i<postList.size();i++){
            if(postList.get(i).getUser(this).equals(username)){
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
        Log.e("~~~~~~",resultList.get(0).getDatetime().substring(5, 7));
        Log.e("~~~~~~",cMonthly.toString());


        barChart = (BarChart) findViewById(R.id.report);
        entries.add(new BarEntry(1, commentsCount));
        entries.add(new BarEntry(2, fansCount));
        entries.add(new BarEntry(3, likesCount/100));




        dataset = new BarDataSet(entries, "第一组数据");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataset);
        BarData data = new BarData(dataSets);
        barChart.setData(data);


        //设置显示动画效果
        barChart.animateY(2000);
        //设置图标右下放显示文字
        Description d = new Description();
        d.setText("MPandroidChart Test");
        barChart.setDescription(d);
    }
}