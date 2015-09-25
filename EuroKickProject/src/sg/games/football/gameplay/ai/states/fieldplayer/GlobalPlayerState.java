package sg.games.football.gameplay.ai.states.fieldplayer;

import sg.games.football.entities.FieldPlayer;
import sg.games.football.gameplay.ai.fsm.*;
import sg.games.football.gameplay.ai.event.*;
import sg.games.football.gameplay.ai.info.*;
import static sg.games.football.gameplay.ai.event.Telegram.MessageType.*;

import sg.games.football.geom.Vector2D;

//************************************************************************ Global state
public class GlobalPlayerState extends State<FieldPlayer> {

    static GlobalPlayerState instance;

    public static GlobalPlayerState Instance() {
        if (instance == null) {
            instance = new GlobalPlayerState();
        }
        return instance;
    }

    public void enter(FieldPlayer player) {

    }

    public void execute(FieldPlayer player) {
        if (player.getMatchGamePlay().isGameOn()) {
            //if a player is in possession and close to the ball reduce his max speed
            if ((player.isBallWithinReceivingRange()) && (player.isgetControllingPlayer())) {
                player.setMaxSpeed(Params.Instance().PlayerMaxSpeedWithBall);
            } else {
                player.setMaxSpeed(Params.Instance().PlayerMaxSpeedWithoutBall);
            }
        }

    }

    public void exit(FieldPlayer player) {

    }

    public boolean onMessage(FieldPlayer player, Telegram telegram) {
        switch (telegram.Msg) {
            case Msg_ReceiveBall:
                //set the target
                player.getSteering().setTarget((Vector2D) telegram.ExtraInfo);
                //change state 
                player.getFSM().changeState(ReceiveBall.Instance());
                return true;

            case Msg_SupportAttacker:

                //if already supporting just return
                if (player.getFSM().isInState(SupportAttacker.Instance())) {
                    return true;
                }

                //set the target to be the best supporting position
                player.getSteering().setTarget(player.getTeam().getSupportSpot());
                //change the state
                player.getFSM().changeState(SupportAttacker.Instance());
                return true;

            case Msg_Wait:
                //change the state
                player.getFSM().changeState(Wait.Instance());
                return true;

            case Msg_GoHome:

                player.setDefaultHomeRegion();
                player.getFSM().changeState(ReturnToHomeRegion.Instance());
                return true;

            case Msg_PassToMe:

                //get the position of the player requesting the pass 
                FieldPlayer receiver = (FieldPlayer) (telegram.ExtraInfo);

            //if the ball is not within kicking range or their is already a 
                //receiving player, this player cannot pass the ball to the player
                //making the request.
                if (player.getTeam().getReceiver() != null
                        || !player.isBallWithinKickingRange()) {

                    return true;
                }

                //make the pass   
                player.getBall().kick(receiver.getPos().minus(player.getBall().getPos()),
                        Params.Instance().MaxPassingForce);

                //let the receiver know a pass is coming 
                Dispatcher.Instance().dispatchMsg(Dispatcher.Instance().SEND_MSG_IMMEDIATELY,
                        player.getID(),
                        receiver.getID(),
                        Msg_ReceiveBall,
                        receiver.getPos());

                //change state   
                player.getFSM().changeState(Wait.Instance());

                player.findSupport();
                return true;

        }//end switch

        return false;
    }

}
