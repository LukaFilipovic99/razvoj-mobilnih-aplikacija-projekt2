package filipovic.football_club_crud_app.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import filipovic.football_club_crud_app.R;
import filipovic.football_club_crud_app.model.FootballClub;

public class FootballClubAdapter extends ArrayAdapter<FootballClub> {
    private Activity activity;
    private List<FootballClub> footballClubs;
    private static LayoutInflater inflater = null;

    public FootballClubAdapter(Activity activity, int textViewResourceId, List<FootballClub> footballClubs) {
        super(activity, textViewResourceId, footballClubs);

        this.activity = activity;
        this.footballClubs = footballClubs;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    public int getCount() {
        return Objects.nonNull(footballClubs) ? footballClubs.size() : 0;
    }

    public FootballClub getItem(FootballClub position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView tvName;
        public TextView tvLeague;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;
        try {
            if (Objects.nonNull(convertView)) {
                view = inflater.inflate(R.layout.fragment_read, null);
                holder = new ViewHolder();

                holder.tvName = view.findViewById(R.id.tvName);
                holder.tvLeague = view.findViewById(R.id.tvLeague);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.tvName.setText(footballClubs.get(position).getName());
            holder.tvLeague.setText(footballClubs.get(position).getLeague().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }
}
