package filipovic.football_club_crud_app.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "football_club")
public class FootballClub {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;

    @NonNull
    private String name;

    private String coach;

    private String stadium;

    @NonNull
    private League league;

    @NonNull
    private LocalDate founded;


    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    @NonNull
    public League getLeague() {
        return league;
    }

    public void setLeague(@NonNull League league) {
        this.league = league;
    }

    @NonNull
    public LocalDate getFounded() {
        return founded;
    }

    public void setFounded(@NonNull LocalDate founded) {
        this.founded = founded;
    }
}
