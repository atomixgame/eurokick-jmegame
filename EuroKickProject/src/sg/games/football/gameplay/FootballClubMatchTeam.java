package sg.games.football.gameplay;

import java.util.ArrayList;
import sg.games.football.gameplay.info.FootballPlayerInfo;
import sg.games.football.entities.SoccerCoach;
import sg.games.football.gameplay.info.FootballClubInfo;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class FootballClubMatchTeam {

    FootballClubInfo club;
    // Players
    ArrayList<FootballPlayerInfo> playersList;
    SoccerCoach coach;
    int numOfMembers = 22;
    SoccerTeam team;
    // Training
//    TrainingCourse trainingCourse;

    // Team possitions for the game
    FootballTeamPositionsPlan startTeamPos;
    FootballTeamPositionsPlan currentTeamPos;
    String shirt;

    public FootballClubMatchTeam(String name) {
        playersList = new ArrayList<FootballPlayerInfo>();
    }

    public SoccerTeam createTeamForMatch(FootballGamePlayManager gamePlayManager, FootballMatch match, int part) {
        this.startTeamPos = arrangeTeamForMatch(gamePlayManager, match, part);
        this.team = new SoccerTeam(gamePlayManager);
        this.coach = new SoccerCoach(club.getCoach().getName(), club);
        for (FootballPlayerInfo playerInfo : startTeamPos.positions.keySet()) {
//            playerInfo.createPlayer(team);
            team.players.add(playerInfo.getPlayerBase());
        }

        return this.team;
    }

    public void arrangeStartPos(int part) {
        team.arrangeStartPos(startTeamPos);
    }

    public FootballTeamPositionsPlan arrangeTeamForMatch(FootballGamePlayManager gamePlayManager, FootballMatch match, int part) {
        // reposition
        //gamePlayManager.getCurrentPlayerMatch()
        return coach.arrangeTeamForMatch(gamePlayManager, match, part);
        // fbCinematic.teamReady(this);
    }

//    public void attachModels(AssetManager assetManager, Node rootNode) {
//        if (team != null) {
//            for (PlayerBase base : team.getMembers()) {
//                FootballPlayerInfo player = base.getInfo();
//                player.loadModel(assetManager);
//                player.attachPlayer(rootNode);
//                //player.setupMatchInfo();
//            }
//        }
//    }
    public FootballClubInfo getClub() {
        return club;
    }

    public SoccerCoach getCoach() {
        return coach;
    }

}
