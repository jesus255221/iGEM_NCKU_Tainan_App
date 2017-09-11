package com.example.user.igem_ncku_tainan_2017;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class BoatFragment extends Fragment {

    private RecyclerView recyclerView;


    public BoatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_boat, container, false);
        String[] titles = new String[5];
        titles[0] = "Linkit";
        titles[1] = "PH meter";
        titles[2] = "temp";
        titles[3] = "NO2";
        titles[4] = "GPS Data";
        int[] icons = new int[5];
        icons[0] = R.drawable.linkit_two;
        icons[1] = R.drawable.ph_meter;
        icons[2] = R.drawable.thermometer;
        icons[3] = R.drawable.bubbles;
        icons[4] = R.drawable.worldwide;
        MyAdapter adapter = new MyAdapter(titles, icons);
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        return recyclerView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView.setAdapter(null);
    }
}
