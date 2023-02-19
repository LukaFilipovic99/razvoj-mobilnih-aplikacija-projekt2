package filipovic.football_club_crud_app.view;

import android.os.Bundle;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import filipovic.football_club_crud_app.R;
import filipovic.football_club_crud_app.model.FootballClub;
import filipovic.football_club_crud_app.view.adapter.FootballClubAdapter;
import filipovic.football_club_crud_app.view_model.FootballClubViewModel;

public class ReadFragment extends Fragment {

    @BindView(R.id.listView)
    ListView listView;

    private FootballClubViewModel footballClubViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read,
                container, false);

        ButterKnife.bind(this, view);

        footballClubViewModel = ((MainActivity) getActivity()).getFootballClubViewModel();

        readData();

        return view;
    }

    private void readData() {
        List<FootballClub> footballClubs = footballClubViewModel.getAll().getValue();

        FootballClubAdapter footballClubAdapter = new FootballClubAdapter(getActivity(), 0, footballClubs);
        listView.setAdapter(footballClubAdapter);
    }

}
