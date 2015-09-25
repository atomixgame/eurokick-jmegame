package sg.games.football.gameplay.ai.states.fieldplayer;

import sg.games.football.entities.FieldPlayer;
import sg.games.football.gameplay.ai.fsm.State;

import sg.games.football.gameplay.ai.event.Telegram;

import sg.games.football.geom.Vector2D;

//***************************************************************************** WAIT
public class Wait extends State<FieldPlayer> {

    static Wait instance;

    public static Wait Instance() {
        if (instance == null) {
            instance = new Wait();
        }
        return instance;
    }

    public void enter(FieldPlayer player) {
        //if the game is not on make sure the target is the center of the player's
        //home region. This is ensure all the players are in the correct positions
        //ready for kick off
        if (!player.getMatchGamePlay().isGameOn()) {
            player.getSteering().setTarget(player.getHomeRegion().getCenter());
            //FIXME: Change animation
//            player.info.setAnim("Run");
//            player.info.setDebugText("Wait");
        }
    }

    public void execute(FieldPlayer player) {

        //if the player has been jostled out of position, get back in position  
        if (!player.isAtTarget()) {
            player.getSteering().arriveOn();
//            println "Wrong behavior" + player.ID;
            return;
        } else {
            player.getSteering().arriveOff();
            player.setVelocity(new Vector2D(0, 0));
            //the player should keep his eyes on the ball!
            player.trackBall();
        }

        //if this player's team is controlling AND this player is not the Attacker
        //AND is further up the field than the Attacker he should request a pass.
        if (player.getTeam().inControl()
                && (!player.isgetControllingPlayer())
                && player.isAheadOfAttacker()) {
            player.getTeam().requestPass(player);

            return;
        }

        if (player.getMatchGamePlay().isGameOn()) {
//            println "Go Chase ball" + player.ID;
            //if the ball is nearer this player than any other team member  AND
            //there is not an assigned receiver AND neither goalkeeper has
            //the ball, go chase it
            if (player.isClosestTeamMemberToBall()
                    && player.getTeam().getReceiver() == null
                    && !player.getMatchGamePlay().isGoalKeeperHasBall()) {

                player.getFSM().changeState(ChaseBall.Instance());
                return;
            }
        }

    }

    public void exit(FieldPlayer player) {

    }

    public boolean onMessage(FieldPlayer player, final Telegram t) {
        return false;

    }

}
