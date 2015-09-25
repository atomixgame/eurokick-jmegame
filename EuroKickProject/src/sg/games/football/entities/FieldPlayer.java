package sg.games.football.entities;

import sg.games.football.gameplay.ai.fsm.StateMachine;

import sg.games.football.gameplay.ai.fsm.State;
import sg.games.football.gameplay.ai.event.Telegram;
import sg.games.football.gameplay.ai.states.team.*;
import sg.games.football.gameplay.ai.states.fieldplayer.*;
import sg.games.football.gameplay.ai.info.*;
import sg.atom.core.execution.Regulator;

import sg.games.football.geom.Vector2D;
import static sg.games.football.geom.Transformations.*;

import sg.games.football.gameplay.info.FootballPlayerInfo;
import sg.games.football.gameplay.PlayerRole;
import sg.games.football.gameplay.SoccerTeam;

public class FieldPlayer extends PlayerBase {

    //an instance of the state machine class
    StateMachine<FieldPlayer> stateMachine;

    //limits the number of kicks a player may take per second
    Regulator kickLimiter;
    boolean drawBody = false;

    public FieldPlayer(SoccerTeam home_team, PlayerRole role, FootballPlayerInfo info) {
        super(home_team, role, info);
    }

    public void init(State<FieldPlayer> start_state) {
        super.init();
        //set up the state machine
        this.stateMachine = new StateMachine<FieldPlayer>(this);
        if (start_state != null) {
            stateMachine.setCurrentState(start_state);
            stateMachine.setPreviousState(start_state);
            stateMachine.setGlobalState(GlobalPlayerState.Instance());

            stateMachine.getCurrentState().enter(this);
        }
        //set up the kick regulator
        this.kickLimiter = new Regulator(Params.Instance().PlayerKickFrequency);
    }

    public void startMatch() {
        super.startMatch();
        this.steering.separationOn();
    }

    //------------------------------ Update ----------------------------------
    //
    // 
    //------------------------------------------------------------------------
    public void update(float tpf) {

        //run the logic for the current state
        //getTeam().checkPlayerPos("Before FSM! ID= "+this.getID()+" State: "+this.getFSM().getCurrentStateName())
        stateMachine.update();
        //getTeam().checkPlayerPos("Before steering! ID= "+this.getID()+" State: "+this.getFSM().getCurrentStateName())
        //        println ("Update ID= "+this.getID()+" State: "+this.getFSM().getCurrentStateName());
        if (steeringActived) {
            //calculate the combined steering force
            steering.calculate();

            //if no steering force is produced decelerate the player by applying a
            //braking force
            if (steering.getForce().isZero()) {
                double BrakingRate = 0.8;

                velocity = velocity.multiply(BrakingRate);
            }
            //the steering force's side component is a force that rotates the 
            //player about its axis. We must limit the rotation so that a player
            //can only turn by PlayerMaxTurnRate rads per update.
            double TurningForce = steering.getSideComponent();

            //Clamp(TurningForce, -Params.Instance().PlayerMaxTurnRate, Params.Instance().PlayerMaxTurnRate);
            //rotate the heading vector
            vec2DRotateAroundOrigin(heading, TurningForce);

            //make sure the velocity vector points in the same direction as
            //the heading vector
            velocity = heading.multiply(velocity.length());

            //and recreate side
            side = heading.perp();

            //now to calculate the acceleration due to the force exerted by
            //the forward component of the steering force in the direction
            //of the player's heading
            Vector2D accel = heading.multiply(steering.getForwardComponent()).divide(mass);

            velocity = velocity.plus(accel);

            //make sure player does not exceed maximum velocity
            velocity.Truncate(maxSpeed);

            //update the position
            position = position.plus(velocity).multiply(tpf);

            //enforce a non-penetration raint if desired
            if (Params.Instance().bNonPenetrationConstraint) {
                enforceNonPenetrationContraint(this, FootballEntityManager.Instance().getAllEntitiesByClass(PlayerBase.class));
            }
        }

        // update rotation
        // heading
        // update location
        super.update(tpf);
    }

    public void checkPlayerPos() {
        if (position.x < 0 || position.y < 0) {
            //            println "Something wrong (1)!";
        }

        Vector2D oldPos = position.cloneVec();
        if (team.getOpponents().getFSM().isInState(Attacking.Instance())) {
            //            println "A. Pos" + position;
            //            println "A. Dis" + oldPos.Distance(position);
        }
        if (position.x < 0 || position.y < 0) {
            //            println "Something wrong (2)! Pos : " + position.toString() + " Vec:" + velocity.toString();
        }
        if (oldPos.Distance(position) > 20) {
            //            println "Something wrong (3)!";
        }

        if (team.getOpponents().getFSM().isInState(Attacking.Instance())) {
            //            println "B. Pos" + position;
            //            println "B. Dis" + oldPos.Distance(position);
        }
    }

    //-------------------- HandleMessage -------------------------------------
    //
    // routes any messages appropriately
    //------------------------------------------------------------------------
    public boolean handleMessage(Telegram msg) {
        return stateMachine.handleMessage(msg);
    }
    /* 
     * getter setter
     */

    public StateMachine<FieldPlayer> getFSM() {
        return stateMachine;
    }

    public boolean isReadyForNextKick() {
        return kickLimiter.isReady();
    }
}
