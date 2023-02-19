package filipovic.football_club_crud_app.model.converters;

import androidx.room.TypeConverter;

import java.util.Date;
import java.util.Objects;

import filipovic.football_club_crud_app.model.League;

public class Converters {

    @TypeConverter
    public static Long dateToLong(Date date) {
        return Objects.nonNull(date) ? date.getTime() : null;
    }

    @TypeConverter
    public static Date longToDate(Long dateLong) {
        return Objects.nonNull(dateLong) ? new Date(dateLong) : null;
    }

    @TypeConverter
    public static String enumToString(League league) {
        return Objects.nonNull(league) ? league.toString() : null;
    }

    @TypeConverter
    public static League stringToEnum(String league) {
        return Objects.nonNull(league) ? League.valueOf(league) : null;
    }
}
