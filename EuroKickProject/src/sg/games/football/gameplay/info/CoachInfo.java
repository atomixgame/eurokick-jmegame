package sg.games.football.gameplay.info;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class CoachInfo {

    String name; // 
    FootballClubInfo club;

    CoachInfo(String name, FootballClubInfo club) {
        this.name = name;
        this.club = club;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FootballClubInfo getClub() {
        return club;
    }

    public void setClub(FootballClubInfo club) {
        this.club = club;
    }

}
