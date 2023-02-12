package filipovic.football_club_crud_app.model.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.Objects;

import filipovic.football_club_crud_app.model.FootballClub;

@Database(entities = {FootballClub.class}, version = 1)
public abstract class FootballClubDatabase extends RoomDatabase {

    private static FootballClubDatabase instance;

    public abstract FootballClubDAO footballClubDAO();

    public static FootballClubDatabase getInstance(Context context) {
        if (Objects.nonNull(instance)) {
            instance = Room.databaseBuilder(context.getApplicationContext(), FootballClubDatabase.class, "football_club_database")
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }
}
