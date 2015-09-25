package sg.games.football.gameplay.ai.states.team;

import sg.games.football.gameplay.ai.fsm.State;
import sg.games.football.gameplay.ai.event.Telegram;
import sg.games.football.gameplay.SoccerTeam;
/**
 *
 * @author cuong.nguyenmanh2
 */

// KICKOFF
public class PrepareForKickOff extends State<SoccerTeam>{
    static PrepareForKickOff instance;
    public static PrepareForKickOff Instance(){
        if (instance==null){
            instance = new PrepareForKickOff();
        }
        return instance;
    }

    public void enter(SoccerTeam team){
        //reset key player pointers
        team.setControllingPlayer(null);
        team.setSupportingPlayer(null);
        team.setReceiver(null);
        team.setPlayerClosestToBall(null);

        //send Msg_GoHome to each player.
        team.returnAllFieldPlayersToHome();
    }

    public void execute(SoccerTeam team){
        //if both teams in position, start the game
        if (team.allPlayersAtHome() && team.getOpponents().allPlayersAtHome()){
            team.getFSM().changeState(Defending.Instance());
        }
    }

    public void exit(SoccerTeam team){
        team.getGamePlayManager().getMatchGamePlay().setGameOn();
    }
    public boolean onMessage(SoccerTeam aObj,Telegram tele){
        return false;
    }
}

