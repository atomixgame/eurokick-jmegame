package sg.games.football.gameplay.ai.states.fieldplayer;

import sg.games.football.entities.FieldPlayer;
import sg.games.football.gameplay.ai.fsm.State;
import sg.games.football.gameplay.ai.event.Telegram;
import sg.games.football.gameplay.ai.info.*;
import sg.games.football.geom.Vector2D;
import static sg.games.football.geom.Utils.*;

//************************************************************************     RECEIVEBALL
class ReceiveBall extends State<FieldPlayer>{
    static ReceiveBall instance;
    public static ReceiveBall Instance(){
        if (instance==null){
            instance = new ReceiveBall();
        }
        return instance;
    }


    public void enter(FieldPlayer player){
        //let the team know this player is receiving the ball
        player.getTeam().setReceiver(player);
  
        //this player is also now the controlling player
        player.getTeam().setControllingPlayer(player);

        //there are two types of receive behavior. One uses arrive to direct
        //the receiver to the position sent by the passer in its telegram. The
        //other uses the pursuit behavior to pursue the ball. 
        //This statement selects between them dependent on the probability
        //ChanceOfUsingArriveTypeReceiveBehavior, whether or not an opposing
        //player is close to the receiving player, and whether or not the receiving
        //player is in the opponents 'hot region' (the third of the pitch closest
        //to the opponent's goal
        double PassThreatRadius = 70.0;

        if (( player.isInHotRegion() ||
                RandFloat() < Params.Instance().ChanceOfUsingArriveTypeReceiveBehavior) &&
            !player.getTeam().isOpponentWithinRadius(player.getPos(), PassThreatRadius)){
            player.getSteering().arriveOn();
        }
        else{
            player.getSteering().pursuitOn();
        }
        
        //FIXME: Change animation
//        player.info.setAnim("Run");
//        player.info.setDebugText("ReceiveBall");
    }

    public void execute(FieldPlayer player){
        //if the ball comes close enough to the player or if his team lose control
        //he should change state to chase the ball
        if (player.isBallWithinReceivingRange() || !player.getTeam().inControl()){
            player.getFSM().changeState(ChaseBall.Instance());

            return;
        }  

        if (player.getSteering().PursuitIsOn()){
            player.getSteering().setTarget(player.getBall().getPos());
        }

        //if the player has 'arrived' at the steering target he should wait and
        //turn to face the ball
        if (player.isAtTarget()){
            player.getSteering().arriveOff();
            player.getSteering().pursuitOff();
            player.trackBall();    
            player.setVelocity(new Vector2D(0,0));
        } 
    }

    public void exit(FieldPlayer player){
        player.getSteering().arriveOff();
        player.getSteering().pursuitOff();

        player.getTeam().setReceiver(null);
    }
    public boolean onMessage(FieldPlayer player, final Telegram t){
        return false;
    }
}