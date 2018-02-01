package com.example.user.igem_ncku_tainan_2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ReportWaterQuality extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_water_quality);
        Button button = (Button) findViewById(R.id.sendreport);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Thank you for sharing this information with us.",Toast.LENGTH_LONG).show();
            }
        });
    }
}
