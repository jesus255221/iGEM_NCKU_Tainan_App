package com.example.user.igem_ncku_tainan_2017;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private String[] drawerTitles;
    private ListView drawerListview;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Drawer processing
        drawerTitles = getResources().getStringArray(R.array.titles);
        drawerListview = (ListView) findViewById(R.id.drawer);//List item bind
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListview.setAdapter(new ArrayAdapter<>(//Adapter Setting
                this,
                android.R.layout.simple_list_item_1,
                drawerTitles
        ));
        drawerListview.setOnItemClickListener(new DrawerItemClickListener());
        drawerToggle = new ActionBarDrawerToggle(//Setting Toggle
                this,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
        );
        drawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Enable back arrow
        getSupportActionBar().setHomeButtonEnabled(true);//Enable back arrow
        //Drawer processing done
        if (savedInstanceState == null) {//Default fragment setting
            selectItem(0);
        } else {
            currentPosition = savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition);
        }

        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {//Add return stack
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = fragmentManager.findFragmentByTag("visible_fragment");
                if (fragment instanceof HomeFragment) {
                    currentPosition = 0;
                } else if (fragment instanceof RegulatorFragment) {
                    currentPosition = 1;
                } else if (fragment instanceof BoatFragment) {
                    currentPosition = 2;
                }
                setActionBarTitle(currentPosition);
                drawerListview.setItemChecked(currentPosition, true);
                //Toast.makeText(getApplicationContext(), drawerTitles[currentPosition], Toast.LENGTH_LONG).show();
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("DATE", null) == null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String date = simpleDateFormat.format(calendar.getTime());
            editor.putString("DATE", date);
            editor.commit();
            Toast.makeText(this, "Not exist", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, NotificationService.class);
        startService(intent);
        if (sharedPreferences.getString("DATE", null) != null) {
            //Toast.makeText(this, sharedPreferences.getString("DATE", null), Toast.LENGTH_SHORT).show();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        //Toast.makeText(this, simpleDateFormat.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//Notify the activity that an item
        if (drawerToggle.onOptionsItemSelected(item)) {//has been selected
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {//synchronize the status
        super.onPostCreate(savedInstanceState);//of the toggle and drawer
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {//pass any configuration change
        super.onConfigurationChanged(newConfig);//to toggle
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentPosition);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override//Drawer Item onClick listener
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    public void selectItem(int position) {//Item selected call fragment
        currentPosition = position;//Synchornize the position of the current position
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new RegulatorFragment();
                break;
            case 2:
                fragment = new BoatFragment();
                break;
            default:
                fragment = new HomeFragment();
        }
        //Fragment trade
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "visible_fragment");
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        setActionBarTitle(position);
        drawerLayout.closeDrawer(drawerListview);
    }

    private void setActionBarTitle(int position) {//Change of title of the action bar
        String string;
        if (position == 0) {
            string = getResources().getString(R.string.app_name);
        } else {
            string = drawerTitles[position];
        }
        getSupportActionBar().setTitle(string);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
