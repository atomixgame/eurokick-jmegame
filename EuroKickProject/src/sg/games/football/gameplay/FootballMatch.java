package sg.games.football.gameplay;

import com.jme3.math.FastMath;
import java.util.Arrays;
import java.util.Collections;
import org.joda.time.DateTime;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class FootballMatch {
    /* Constants */

    public static String[] stadiumNames = {"stadiumA", "stadiumB"};
    public static String[] weathers = {"Rainy", "Cloudy", "Sunny"};

    private String matchName;
    int scoreA = 0;
    int scoreB = 0;
    FootballClubMatchTeam clubTeamA, clubTeamB;
    String stadiumName;

    // Infos
    String weather;
    //    def cards = []

    // Timing
    DateTime startTime;
    float matchTime;
    float remainTime;
    float addedTime;
    float halfTime;

    // State
    int status;
    boolean ballIn;

    public FootballMatch(FootballClubMatchTeam clubA, FootballClubMatchTeam clubB) {
        this(clubA, clubB, "");
    }

    public FootballMatch(FootballClubMatchTeam clubA, FootballClubMatchTeam clubB, String matchName) {
        this.clubTeamA = clubA;
        this.clubTeamB = clubB;
        this.matchName = matchName;
        randomInfo();
    }

    public void randomInfo() {
        weather = weathers[FastMath.nextRandomInt(0, weathers.length - 1)];
        stadiumName = stadiumNames[FastMath.nextRandomInt(0, stadiumNames.length - 1)];
        matchName = "Last match of Round 4 . Premier League";
    }

    public void startMatch() {
        matchTime = 0;
        addedTime = 0;
        halfTime = 45 * 60;
        remainTime = halfTime;
    }

    public void endMatch() {
        // Timeout reach
        // Refree whistle
        // Two team out
    }

//    public Vector3f whereGoal(boolean goalA) {
//        if (goalA) {
//            return new Vector3f(1, 1, 1);
//        } else {
//            return new Vector3f(0, 0, 0);
//        }
//    }
    public String getShortIntro() {
        return "final match of the season";
    }

    public FootballClubMatchTeam opponent(FootballClubMatchTeam aClub) {
        if (aClub == clubTeamA) {
            return clubTeamB;
        } else {
            return clubTeamA;
        }
    }

    public String toString() {
        return clubTeamA.getClub().getName() + "vs" + clubTeamB.getClub().getName() + " at " + startTime;
    }
}
