package com.example.social_network_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BarActivity extends AppCompatActivity {

    //显示的图表
    public BarChart barChart;
    //保存数据的实体
    public ArrayList<BarEntry> entries = new ArrayList<>();
    //数据的集合（每组数据都需要一个数据集合存放数据实体和该组的样式）
    public BarDataSet dataset;
    //表格下方的文字
    public ArrayList<String> labels = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        barChart = (BarChart) findViewById(R.id.bar_chart);
        entries.add(new BarEntry(1, 5));
        entries.add(new BarEntry(2, 1));
        entries.add(new BarEntry(3, 2));
        entries.add(new BarEntry(4, 6));
        entries.add(new BarEntry(5, 3));



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