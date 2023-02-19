package filipovic.football_club_crud_app.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import filipovic.football_club_crud_app.R;
import filipovic.football_club_crud_app.model.FootballClub;

public class FootballClubAdapter extends BaseAdapter {

    private List<FootballClub> footballClubs;

    public FootballClubAdapter() {
        footballClubs = new ArrayList<>();
    }

    public List<FootballClub> getFootballClubs() {
        return footballClubs;
    }

    public void setFootballClubs(List<FootballClub> footballClubs) {
        this.footballClubs = footballClubs;
    }

    @Override
    public int getCount() {
        return footballClubs.size();
    }

    @Override
    public Object getItem(int position) {
        return footballClubs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return footballClubs.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (Objects.isNull(convertView)) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_row, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvLeague = convertView.findViewById(R.id.tvLeague);

        tvName.setText(footballClubs.get(position).getName());
        tvLeague.setText(footballClubs.get(position).getLeague().getName());

        return convertView;
    }
}
