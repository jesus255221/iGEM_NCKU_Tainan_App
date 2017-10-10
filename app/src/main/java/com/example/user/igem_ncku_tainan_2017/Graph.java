package com.example.user.igem_ncku_tainan_2017;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.android.gms.maps.model.LatLng;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class Graph extends AppCompatActivity {

    public interface Service {
        @GET("/nitrates/mobile")
        Call<NitrateResponses> GetData();
    }

    private final Handler mHandler = new Handler();
    private Runnable runnable;
    /*private LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
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
    });*/
    //private double graphLastXValue = 5d;
    private ArrayList<Double> longitude = new ArrayList<>();
    private ArrayList<Double> latitude = new ArrayList<>();
    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<Double> ph = new ArrayList<>();
    private ArrayList<Double> temperature = new ArrayList<>();
    private ArrayList<Double> concentration = new ArrayList<>();
    private ArrayList<Date> sensing_date = new ArrayList<>();
    private String dataSetName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("http://jia.ee.ncku.edu.tw")
                .addConverterFactory(GsonConverterFactory.create()).build();
        Graph.Service service = retrofit.create(Graph.Service.class);
        Call<NitrateResponses> get = service.GetData();
        get.enqueue(new Callback<NitrateResponses>() {
            @Override
            public void onResponse(Call<NitrateResponses> call, Response<NitrateResponses> response) {

                for (int i = 0; i < response.body().getNitrates().size(); i++) {
                    date.add(response.body().getNitrates().get(i).getDate());
                    longitude.add(response.body().getNitrates().get(i).getLongitude());
                    latitude.add(response.body().getNitrates().get(i).getLatitude());
                    ph.add(response.body().getNitrates().get(i).getPh());
                    temperature.add(response.body().getNitrates().get(i).getTemp());
                    concentration.add(response.body().getNitrates().get(i).getConcentration());
                }
                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (int i = 0; i < date.size(); i++) {
                    try {
                        String dateFormat = date.get(i).substring(0, 19);
                        dateFormat = dateFormat.replace('T', ' ');
                        Date date_1 = simpleDateFormat.parse(dateFormat);
                        sensing_date.add(date_1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                ArrayList<Double> DataSet = new ArrayList<>();
                Intent intent = getIntent();
                switch (intent.getIntExtra("Activity_number", -1)) {
                    case 1:
                        DataSet = ph;
                        dataSetName = "PH Value";
                        break;
                    case 2:
                        DataSet = temperature;
                        dataSetName = "water temperature(celsius)";
                        break;
                    case 3:
                        DataSet = concentration;
                        dataSetName = "nitrate concentration(ppm)";
                        break;
                }
                LineChart lineChart = (LineChart) findViewById(R.id.chart);
                ArrayList<Entry> entries = new ArrayList<>();
                for (int i = 0; i < DataSet.size(); i++) {
                    entries.add(new Entry(i, DataSet.get(i).floatValue()));
                }
                LineDataSet dataSet = new LineDataSet(entries, dataSetName);
                dataSet.setValueTextSize(12f);
                //dataSet.setColor();
                XAxis xAxis = lineChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawAxisLine(false);
                xAxis.setAxisMinimum(1);
                xAxis.setValueFormatter(new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("M/d H:m");
                        return simpleDateFormat1.format(sensing_date.get((int) value));
                    }
                });
                LineData data = new LineData(dataSet);
                lineChart.setData(data);
                /*DataPoint[] dataPoints = new DataPoint[DataSet.size()];
                for (int i = 0; i < DataSet.size(); i++) {
                    dataPoints[i] = new DataPoint(sensing_date.get(i), DataSet.get(i));
                }
                Toast.makeText(getApplicationContext(),Double.toString(DataSet.get(0)),Toast.LENGTH_LONG).show();
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
                GraphView graph = (GraphView) findViewById(R.id.graph);
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("\nMM-dd\nHH:mm:ss");
                graph.getViewport().setYAxisBoundsManual(true);
                graph.getViewport().setMinY(0);
                graph.getViewport().setScalableY(true);
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setScalable(true);
                graph.getViewport().setScrollable(true);
                graph.addSeries(series);
                graph.getGridLabelRenderer().setNumHorizontalLabels(3);
                graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext(),simpleDateFormat1));*/
            }

            @Override
            public void onFailure(Call<NitrateResponses> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Sorry! something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        //Toast.makeText(getApplicationContext(), Double.toString(ph.get(1)), Toast.LENGTH_SHORT).show();
        //graph.addSeries(series2);
        // activate horizontal zooming and scrolling
        //graph.getViewport().setScalable(true);
        // activate horizontal scrolling
        //graph.getViewport().setScrollable(true);
        // activate horizontal and vertical zooming and scrolling
        //graph.getViewport().setScalableY(true);
        // activate vertical scrolling
        /*graph.getViewport().setScrollableY(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(40);*/
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

    /*private double getRandom() {
        Random mRand = new Random();
        return mRand.nextDouble() * 50 - 0.25;
    }*/

}

