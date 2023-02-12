package filipovic.football_club_crud_app.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import filipovic.football_club_crud_app.model.FootballClub;
import filipovic.football_club_crud_app.model.dao.FootballClubDAO;
import filipovic.football_club_crud_app.model.dao.FootballClubDatabase;

public class FootballClubViewModel extends AndroidViewModel {

    private FootballClubDAO footballClubDAO;
    private FootballClub footballClub;
    private LiveData<List<FootballClub>> footballClubs;


    public FootballClubViewModel(@NonNull Application application) {
        super(application);
        footballClubDAO = FootballClubDatabase.getInstance(application.getApplicationContext()).footballClubDAO();
    }

    public LiveData<List<FootballClub>> getAll() {
        return footballClubDAO.getAll();
    }

    public void create() {
        footballClubDAO.create(footballClub);
    }

    public void update() {
        footballClubDAO.update(footballClub);
    }

    public void delete() {
        footballClubDAO.delete(footballClub);
    }

    public FootballClub getFootballClub() {
        return footballClub;
    }

    public void setFootballClub(FootballClub footballClub) {
        this.footballClub = footballClub;
    }
}
