package sg.games.football.gameplay.ai.states.fieldplayer;

import sg.games.football.entities.FieldPlayer;
import sg.games.football.gameplay.ai.fsm.State;

import sg.games.football.gameplay.ai.event.Telegram;
import sg.games.football.gameplay.ai.info.*;

import sg.games.football.geom.Vector2D;
//*****************************************************************************SUPPORT ATTACKING PLAYER

public class SupportAttacker extends State<FieldPlayer> {

    static SupportAttacker instance;

    public static SupportAttacker Instance() {
        if (instance == null) {
            instance = new SupportAttacker();
        }
        return instance;
    }

    public void enter(FieldPlayer player) {
        player.getSteering().arriveOn();
        player.getSteering().setTarget(player.getTeam().getSupportSpot());
        //FIXME: Change animation
//        player.info.setAnim("Run");
//        player.info.setDebugText("SupportAttacker");
    }

    public void execute(FieldPlayer player) {
        //if his team loses control go back home
        if (!player.getTeam().inControl()) {
            player.getFSM().changeState(ReturnToHomeRegion.Instance());
            return;
        }

        //if the best supporting spot changes, change the steering target
        if (player.getTeam().getSupportSpot() != player.getSteering().getTarget()) {
            player.getSteering().setTarget(player.getTeam().getSupportSpot());

            player.getSteering().arriveOn();
        }

        //if this player has a shot at the goal AND the Attacker can pass
        //the ball to him the Attacker should pass the ball to this player
        if (player.getTeam().canShoot(player.getPos(),
                Params.Instance().MaxShootingForce, null)) {
            player.getTeam().requestPass(player);
        }

        //if this player is located at the support spot and his team still have
        //possession, he should remain still and turn to face the ball
        if (player.isAtTarget()) {
            player.getSteering().arriveOff();

            //the player should keep his eyes on the ball!
            player.trackBall();

            player.setVelocity(new Vector2D(0, 0));

            //if not threatened by another player request a pass
            if (!player.isThreatened()) {
                player.getTeam().requestPass(player);
            }
        }
    }

    public void exit(FieldPlayer player) {
        //set supporting player to null so that the team knows it has to 
        //determine a new one.
        player.getTeam().setSupportingPlayer(null);

        player.getSteering().arriveOff();
    }

    public boolean onMessage(FieldPlayer player, final Telegram t) {
        return false;
    }
}
