package com.example.user.igem_ncku_tainan_2017;


import android.content.Intent;
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
        titles[0] = "pH值";
        titles[1] = "水溫";
        titles[2] = "硝酸鹽濃度";
        titles[3] = "路徑紀錄";
        titles[4] = "水質地圖";
        int[] icons = new int[5];
        icons[0] = R.drawable.ic_straighten_black_24dp;
        icons[1] = R.drawable.ic_insert_chart_black_24dp;
        icons[2] = R.drawable.ic_show_chart_black_24dp;
        icons[3] = R.drawable.ic_my_location_black_24dp;
        icons[4] = R.drawable.ic_map_black_24dp;
        MyAdapter adapter = new MyAdapter(titles, icons);
        adapter.setListener(new MyAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(getActivity(), Graph.class);
                        intent.putExtra("Activity_number", 1);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getActivity(), Graph.class);
                        intent.putExtra("Activity_number", 2);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getActivity(), Graph.class);
                        intent.putExtra("Activity_number", 3);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(getActivity(), MapsActivity.class);
                        intent.putExtra("Activity_number", 4);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(getActivity(), MapsActivity.class);
                        intent.putExtra("Activity_number", 5);
                        startActivity(intent);
                        break;
                }
            }
        });
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