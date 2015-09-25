package sg.games.football.gameplay.info;

import java.util.ArrayList;
import sg.games.football.gameplay.PlayerRole;
import sg.games.football.gameplay.coachgp.training.*;
import sg.games.football.gameplay.SoccerTeam;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class FootballClubInfo {

    String idClub;
    String name;
    String country;
    String logoPath;
    int rate;
    int leagePos;
    boolean currentChampion;
    // Business
    int cost;
    int budget;
    int income;
    int outcome;
    int reputation;

    String owner;
    String manager;
    // Staffs Infos
    ArrayList<String> staffs;

    String phone;
    String fax;
    String email;

    // Players
    ArrayList<FootballPlayerInfo> playersList;
    CoachInfo coach;
    int numOfMembers = 11;
    SoccerTeam team;
    // Training
    TrainingCourse trainingCourse;

    // Team possitions for the game
    String shirt;
    PlayerCustomizeSystem playerCustomizeSystem;

    public FootballClubInfo(String name) {
        this.name = name;
        this.playersList = new ArrayList<FootballPlayerInfo>();
        this.playerCustomizeSystem = new PlayerCustomizeSystem();
    }

    public void fillPlayerList() {
        for (int i = 0; i < numOfMembers; i++) {
            FootballPlayerInfo player = makePlayerInfo("Player " + i, PlayerRole.Striker);
            playerCustomizeSystem.getRandomPlayerInfo(player);
            playersList.add(player);
            if (i == 0) {
                player.setRole(PlayerRole.GoalKeeper);
            }
        }
        this.shirt = playerCustomizeSystem.getRandomShirt();;
    }

    public FootballPlayerInfo makePlayerInfo(String name, PlayerRole role) {
        FootballPlayerInfo player = new FootballPlayerInfo(this, name, role);
        return player;
    }

    public int getNumOfMembers() {
        return numOfMembers;
    }

    public String getName() {
        return name;
    }

    public String getShirt() {
        return shirt;
    }

    public ArrayList<String> getStaffs() {
        return staffs;
    }

    public ArrayList<FootballPlayerInfo> getPlayersList() {
        return playersList;
    }

    public String getShortIntro() {
        return "" + name;
    }

    public String toString() {
        return getShortIntro();
    }

    public CoachInfo getCoach() {
        return coach;
    }

}
