package filipovic.football_club_crud_app.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

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
    private Date founded;

    private String logoUrl;

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

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
    public Date getFounded() {
        return founded;
    }

    public void setFounded(@NonNull Date founded) {
        this.founded = founded;
    }
}
