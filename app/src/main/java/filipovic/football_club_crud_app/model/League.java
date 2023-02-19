package filipovic.football_club_crud_app.model;

public enum League {

    LA_LIGA("Španjolska La liga"),
    FA_PREMIER_LEAGUE("Engleska FA Premier liga"),
    SERIE_A("Talijanska Serie A"),
    BUNDESLIGA("Njemačka Bundesliga"),
    LIGUE_1("Francuska Ligue 1");

    private final String name;

    League(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
