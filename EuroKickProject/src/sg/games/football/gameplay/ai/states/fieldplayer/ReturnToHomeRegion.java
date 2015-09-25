package sg.games.football.gameplay.ai.states.fieldplayer;

import sg.games.football.entities.FieldPlayer;
import sg.games.football.gameplay.ai.fsm.State;

import sg.games.football.gameplay.ai.event.Telegram;
import sg.games.football.gameplay.Region;


//************************************************************************ RETURN TO HOME REGION
public class ReturnToHomeRegion extends State<FieldPlayer>{
    static ReturnToHomeRegion instance;
    public static ReturnToHomeRegion Instance(){
        if (instance==null){
            instance = new ReturnToHomeRegion();
        }
        return instance;
    }


    public void enter(FieldPlayer player){
        player.getSteering().arriveOn();

        if (!player.getHomeRegion().isInside(player.getSteering().getTarget(), Region.RegionModifier.halfsize)){
            player.getSteering().setTarget(player.getHomeRegion().getCenter());
        }
        
        //FIXME: Change animation
//        player.info.setAnim("Run");
//        player.info.setDebugText("ReturnToHomeRegion");
    }

    public void execute(FieldPlayer player){
        if (player.getMatchGamePlay().isGameOn()){
            //if the ball is nearer this player than any other team member  &&
            //there is not an assigned receiver && the goalkeeper does not gave
            //the ball, go chase it
            if ( player.isClosestTeamMemberToBall() &&
                (player.getTeam().getReceiver() == null) &&
                !player.getMatchGamePlay().isGoalKeeperHasBall()){
                player.getFSM().changeState(ChaseBall.Instance());

                return;
            }
        }

        //if game is on and close enough to home, change state to wait and set the 
        //player target to his current position.(so that if he gets jostled out of 
        //position he can move back to it)
        if (player.getMatchGamePlay().isGameOn() && player.getHomeRegion().isInside(player.getPos(),
                Region.RegionModifier.halfsize)){
            player.getSteering().setTarget(player.getPos());
            player.getFSM().changeState(Wait.Instance());
        }
        //if game is not on the player must return much closer to the center of his
        //home region
        else if(!player.getMatchGamePlay().isGameOn() && player.isAtTarget()){
            player.getFSM().changeState(Wait.Instance());
        }
    }

    public void exit(FieldPlayer player){
        player.getSteering().arriveOff();
    }
    public boolean onMessage(FieldPlayer player, final Telegram t){
        return false;
    }

}

