package sg.games.football.entities;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import sg.games.football.gameplay.info.FootballPlayerInfo;
import java.util.ArrayList;
import java.util.List;
import sg.atom.gameplay.player.Player;
import sg.games.football.gameplay.FootballGamePlayManager;
import sg.games.football.gameplay.FootballMatch;
import sg.games.football.gameplay.FootballStragegy;
import sg.games.football.gameplay.FootballTeamPositionsPlan;
import sg.games.football.gameplay.PlayerRole;
import static sg.games.football.gameplay.PlayerRole.GoalKeeper;
import static sg.games.football.gameplay.PlayerRole.Striker;
import sg.games.football.gameplay.PlayerRoleInMatch;
import sg.games.football.gameplay.SoccerPitch;
import sg.games.football.gameplay.info.FootballClubInfo;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class SoccerCoach {

    String name;
    // 
    FootballClubInfo club;
    FootballGamePlayManager gamePlayManager;
    ArrayList<FootballStragegy> stragegies = new ArrayList<FootballStragegy>();
    float[] posArr = {0.2f, 0.4f, 0.6f};
    Player gamePlayer;
    FootballStragegy currentStragegy;
    SoccerPitch pitch;
    // The team stragistic positions which the whole team are trained in train sessions
    FootballTeamPositionsPlan savedTeamPos;
    /*
     * STRAGEGY
     */
    boolean isAI = true;

    public SoccerCoach(String name, FootballClubInfo club) {
        this.name = name;
        this.club = club;
        this.isAI = true;
    }

    public SoccerCoach(Player gamePlayer, FootballClubInfo club) {
        this.gamePlayer = gamePlayer;
//        this.name = currentGamePlayer.getName();
        this.club = club;
        this.isAI = false;
    }

    public void generateStragegies() {
        this.stragegies.add(new FootballStragegy("Attack1", "4-4-2"));
        this.stragegies.add(new FootballStragegy("Defend1", "3-3-4"));
        this.stragegies.add(new FootballStragegy("Attack2", "4-5-1"));
    }

    public FootballStragegy findStragegy(final String title) {
        return Collections2.filter(this.stragegies, new Predicate<FootballStragegy>() {

            @Override
            public boolean apply(FootballStragegy it) {
                return it.posTitle.equals(title);
            }
        }).iterator().next();
    }

    public FootballStragegy findStragegyForMatch(FootballMatch match) {
        return findStragegy("4-4-2");
    }

    public ArrayList<FootballStragegy> getStragegies() {
        return this.stragegies;
    }

    public FootballTeamPositionsPlan arrangeTeamForMatch(FootballGamePlayManager gameplay, FootballMatch match, int part) {
        if (gamePlayer != null) {

        }
        // decide a stragegy
        changeStragegy(findStragegy("4-4-2"));
        // choose player in club player list by calculating training score
        // 
        List teamList = new ArrayList<FootballPlayerInfo>();
        // find best Goal keeper
        //teamList << club.playersList.findAll{player-> player.role == GoalKeeper}.max{player-> player.skillGoalKeep}
        teamList.addAll(club.getPlayersList());
        // find num of best field player
        return arrangeTeam(gameplay, teamList, part);
    }

    public void changeStragegy(FootballStragegy stragegy) {
        currentStragegy = stragegy;
        /*
         // random speed
         int speed = 1 + FastMath.rand.nextInt(9);
         player.setSpeed(speed);
         */

    }

    public FootballTeamPositionsPlan arrangeTeam(FootballGamePlayManager gameplay, List<FootballPlayerInfo> teamList, int part) {
        //StadiumMaker stadium = gamePlayManager.getStageManager().getWorldManager().getStadiumMaker();
        FootballTeamPositionsPlan teamPos = new FootballTeamPositionsPlan();
        pitch = SoccerPitch.getDefault();
        //pitch.init(null)

        for (int num = 0; num < 10; num++) {
            teamPos.setPosFor(teamList.get(num), pitch.regions.get(part * 12 + num).center);
        }
        return teamPos;
    }

    public PlayerRoleInMatch getRoleByNumber(String stragegyPosTitle, int number) {
        if (number == 1) {
            return new PlayerRoleInMatch(GoalKeeper, 1, 1, 0);
        } else {
            String[] strSplit = stragegyPosTitle.split("-");
            int count = 1;
            for (int i = 0; i < strSplit.length; i++) {
                int rowTotal = Integer.parseInt(strSplit[i]);
                if (number > count + rowTotal) {
                    count += rowTotal;
                } else {
                    PlayerRole role = Striker; //byIndex(i + 1);
                    return new PlayerRoleInMatch(role, number - count - 1, rowTotal, i);
                }
            }
        }
        return null;
    }
}
