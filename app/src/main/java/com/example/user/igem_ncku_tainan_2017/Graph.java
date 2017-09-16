package com.example.user.igem_ncku_tainan_2017;


import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class Graph extends AppCompatActivity {

    private final Handler mHandler = new Handler();
    private Runnable runnable;
    private LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 1),
            new DataPoint(1, 5),
            new DataPoint(2, 3),
            new DataPoint(3, 2),
            new DataPoint(4, 6)
    });
    private BarGraphSeries<DataPoint> series2 = new BarGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 1),
            new DataPoint(1, 5),
            new DataPoint(2, 3),
            new DataPoint(3, 2),
            new DataPoint(4, 6)
    });
    private double graphLastXValue = 5d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.addSeries(series);
        graph.addSeries(series2);
        // activate horizontal zooming and scrolling
        graph.getViewport().setScalable(true);
        // activate horizontal scrolling
        graph.getViewport().setScrollable(true);
        // activate horizontal and vertical zooming and scrolling
        graph.getViewport().setScalableY(true);
        // activate vertical scrolling
        graph.getViewport().setScrollableY(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(40);
        /*runnable = new Runnable() {
            @Override
            public void run() {
                graphLastXValue += 1d;
                series.appendData(new DataPoint(graphLastXValue, getRandom()), true, 40);
                mHandler.postDelayed(this, 1000);
            }
        };
        mHandler.postDelayed(runnable, 1000);*/
    }

    private double getRandom() {
        Random mRand = new Random();
        return mRand.nextDouble() * 50 - 0.25;
    }
}

