package filipovic.football_club_crud_app.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import filipovic.football_club_crud_app.model.FootballClub;

@Dao
public interface FootballClubDAO {

    @Query("SELECT * from football_club")
    LiveData<List<FootballClub>> getAll();

    @Insert
    void create(FootballClub footballClub);

    @Update
    void update(FootballClub footballClub);

    @Delete
    void delete(FootballClub footballClub);

}
