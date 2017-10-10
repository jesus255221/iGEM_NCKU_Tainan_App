package com.example.user.igem_ncku_tainan_2017;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.igem_ncku_tainan_2017.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public interface Service {
        /*@FormUrlEncoded
        @POST("/users")
        Call<JsonResponse> Create(
                @Field("name") String name,
                @Field("glucose") String glucose
        );

        @FormUrlEncoded
        @PUT("/users/{id}")
        Call<JsonResponse> Update(
                @Path("id") String id,
                @Field("name") String name,
                @Field("glucose") String glucose
        );*/

        /*@POST("/.json")
        Call<String> PostArray(
                @Body List<GlucoseData> data
        );

        @GET("/users")
        Call<List<UsersResponses>> GetUsersInformations();*/

        @GET("/locations/mobile")
        Call<locationResponses> GetLocations();

        @GET("/nitrates/mobile")
        Call<NitrateResponses> GetData();
    }


    private ArrayList<Double> longitude = new ArrayList<>();
    private ArrayList<Double> latituude = new ArrayList<>();
    private ArrayList<LatLng> latLngs = new ArrayList<>();
    private ArrayList<String> date = new ArrayList<>();
    private Runnable runnable;
    private Handler handler = new Handler();
    private GoogleMap mMap;
    private final int MY_LOCATION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        /*Button Update_Button = (Button) findViewById(R.id.update_button);
        Update_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetData();
            }
        });*/
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Toast.makeText(getApplicationContext(), "Ready", Toast.LENGTH_SHORT).show();
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_REQUEST_CODE);
        }
        /*LatLng sydney = new LatLng(22.995571, 120.221539);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker of my home")
                .snippet("Welcome to tainan"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        runnable = new Runnable() {
            @Override
            public void run() {
                if (isNetworkConnected()) {
                    Retrofit retrofit = new Retrofit
                            .Builder()
                            .baseUrl("http://jia.ee.ncku.edu.tw")
                            .addConverterFactory(GsonConverterFactory.create()).build();
                    MapsActivity.Service service = retrofit.create(MapsActivity.Service.class);
                    Intent intent = getIntent();
                    if (intent.getIntExtra("Activity_number", -1) == 4) {
                        Call<locationResponses> get = service.GetLocations();
                        get.enqueue(new Callback<locationResponses>() {
                            @Override
                            public void onResponse(Call<locationResponses> call, Response<locationResponses> response) {
                                for (int i = 0; i < response.body().getLocations().size(); i++) {
                                    latituude.add(i, response.body().getLocations().get(i).getLatitude() / 100);
                                    longitude.add(i, response.body().getLocations().get(i).getLongitude() / 100);
                                    latLngs.add(i, new LatLng(latituude.get(i), longitude.get(i)));
                                    date.add(response.body().getLocations().get(i).getDate());
                                    mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(latituude.get(i), longitude.get(i)))
                                            .title(date.get(i))
                                    );
                                }
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(latituude.get(0),longitude.get(0)),18));
                            }

                            @Override
                            public void onFailure(Call<locationResponses> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                        PolylineOptions rectOptions = new PolylineOptions();
                        rectOptions.addAll(latLngs);
                        mMap.addPolyline(rectOptions);
                        latituude.clear();
                        longitude.clear();
                        latLngs.clear();
                    } else {
                        Call<NitrateResponses> get = service.GetData();
                        get.enqueue(new Callback<NitrateResponses>() {
                            @Override
                            public void onResponse(Call<NitrateResponses> call, Response<NitrateResponses> response) {
                                //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < response.body().getNitrates().size(); i++) {
                                    mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(response.body().getNitrates().get(i).getLatitude(),
                                                    response.body().getNitrates().get(i).getLongitude()))
                                            .title(response.body().getNitrates().get(i).getDate())
                                            .snippet(Double.toString(response.body().getNitrates().get(i).getConcentration()) + " ppm"));
                                }
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(response.body().getNitrates().get(0).getLatitude(),
                                                response.body().getNitrates().get(0).getLongitude()),18));
                            }

                            @Override
                            public void onFailure(Call<NitrateResponses> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "NetWorkERROR", Toast.LENGTH_SHORT).show();
                }
                handler.postDelayed(this, 10000);
            }
        };
        handler.post(runnable);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_LOCATION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Location permissions ERROR", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    public void GetData() {
        if (isNetworkConnected()) {
            Retrofit retrofit = new Retrofit
                    .Builder()
                    .baseUrl("http://jia.ee.ncku.edu.tw")
                    .addConverterFactory(GsonConverterFactory.create()).build();
            MapsActivity.Service service = retrofit.create(MapsActivity.Service.class);
            Call<locationResponses> get = service.GetLocations();
            get.enqueue(new Callback<locationResponses>() {
                @Override
                public void onResponse(Call<locationResponses> call, Response<locationResponses> response) {
                    for (int i = 0; i < response.body().getLocations().size(); i++) {
                        latituude.add(i, response.body().getLocations().get(i).getLatitude() / 100);
                        longitude.add(i, response.body().getLocations().get(i).getLongitude() / 100);
                        latLngs.add(i, new LatLng(latituude.get(i), longitude.get(i)));
                    }
                }

                @Override
                public void onFailure(Call<locationResponses> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
            PolylineOptions rectOptions = new PolylineOptions();
            rectOptions.addAll(latLngs);
            mMap.addPolyline(rectOptions);
            latituude.clear();
            longitude.clear();
        } else {
            Toast.makeText(this, "NetWorkERROR", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
