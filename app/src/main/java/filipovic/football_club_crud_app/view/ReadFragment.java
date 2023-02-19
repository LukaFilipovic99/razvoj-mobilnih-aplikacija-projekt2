package filipovic.football_club_crud_app.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import filipovic.football_club_crud_app.R;
import filipovic.football_club_crud_app.model.FootballClub;
import filipovic.football_club_crud_app.model.League;
import filipovic.football_club_crud_app.view.adapter.FootballClubAdapter;
import filipovic.football_club_crud_app.view_model.FootballClubViewModel;

public class ReadFragment extends Fragment {

    @BindView(R.id.listView)
    ListView listView;

    private FootballClubViewModel footballClubViewModel;

    private FootballClubAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read,
                container, false);

        ButterKnife.bind(this, view);

        adapter = new FootballClubAdapter();
        listView.setAdapter(adapter);

        footballClubViewModel = ((MainActivity) getActivity()).getFootballClubViewModel();

        readData();

        return view;
    }

    private void readData() {
        footballClubViewModel.getAll().observe(getViewLifecycleOwner(), clubs -> {
            Log.d("jdksaldakdlde", String.valueOf(clubs.size()));
            adapter.setFootballClubs(clubs);
            adapter.notifyDataSetChanged();
        });
    }

    @OnItemClick(R.id.listView)
    public void onItemClick(int position) {
        footballClubViewModel.setFootballClub(adapter.getFootballClubs().get(position));

        ((MainActivity) getActivity()).cud(true);
    }

    @OnClick(R.id.btnCreate)
    public void createFootballClub() {
        footballClubViewModel.setFootballClub(new FootballClub());
        ((MainActivity) getActivity()).cud(false);
    }

}
