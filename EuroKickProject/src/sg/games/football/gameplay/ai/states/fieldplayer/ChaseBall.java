package sg.games.football.gameplay.ai.states.fieldplayer;

import sg.games.football.gameplay.ai.fsm.State;
import sg.games.football.gameplay.ai.event.Telegram;
import sg.games.football.entities.FieldPlayer;
//***************************************************************************** CHASEBALL

public class ChaseBall extends State<FieldPlayer> {

    static ChaseBall instance;

    public static ChaseBall Instance() {
        if (instance == null) {
            instance = new ChaseBall();
        }
        return instance;
    }

    public void enter(FieldPlayer player) {
        player.getSteering().seekOn();

        //FIXME: Change animation
//        player.info.setAnim("Run");
//        player.info.setDebugText("ChaseBall");
    }

    public void execute(FieldPlayer player) {
        //if the ball is within kicking range the player changes state to KickBall.
        if (player.isBallWithinKickingRange()) {
            //player.getTeam().checkPlayerPos("ChaseBall - Before changeState")
            player.getFSM().changeState(KickBall.Instance());
            //player.getTeam().checkPlayerPos("ChaseBall - After changeState")
            return;
        }

        //if the player is the closest player to the ball then he should keep
        //chasing it
        if (player.isClosestTeamMemberToBall()) {
            player.getSteering().setTarget(player.getBall().getPos());
            return;
        }

        //if the player is not closest to the ball anymore, he should return back
        //to his home region and wait for another opportunity
        player.getFSM().changeState(ReturnToHomeRegion.Instance());
    }

    public void exit(FieldPlayer player) {
        player.getSteering().seekOff();
    }

    public boolean onMessage(FieldPlayer player, final Telegram t) {
        return false;
    }
}
