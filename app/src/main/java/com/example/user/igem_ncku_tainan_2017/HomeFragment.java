package com.example.user.igem_ncku_tainan_2017;


import android.app.FragmentTransaction;
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
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_home, container, false);
        String[] cardview_titles = new String[4];
        cardview_titles[0] = "Regulator";
        cardview_titles[1] = "Boat";
        cardview_titles[2] = "Official WebSite";
        cardview_titles[3] = "Report water quality";
        int[] icon = new int[4];
        icon[0] = R.drawable.device;
        icon[1] = R.drawable.boat;
        icon[2] = R.drawable.ic_web_black_24dp;
        icon[3] = R.drawable.ic_reply_all_black_24dp;

        MyAdapter adapter = new MyAdapter(cardview_titles, icon);
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter.setListener(new MyAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Fragment fragment = new HomeFragment();
                switch (position) {
                    case 0:
                        fragment = new RegulatorFragment();
                        break;
                    case 1:
                        fragment = new BoatFragment();
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), WebView_Activity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getActivity(), ReportWaterQuality.class));
                        break;
                    //default:
                        //fragment = new HomeFragment();
                }
                if (!(fragment instanceof HomeFragment)) {
                    //Fragment trade
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.addToBackStack(null);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }
            }
        });
        // Inflate the layout for this fragment
        return recyclerView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView.setAdapter(null);
    }

}
