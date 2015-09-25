package sg.games.football.entities;

import sg.games.football.gameplay.ai.event.Telegram;
import sg.games.football.gameplay.ai.fsm.State;
import sg.games.football.gameplay.ai.fsm.StateMachine;
import sg.games.football.gameplay.ai.states.goalkeeper.*;
import sg.games.football.gameplay.ai.info.*;

import sg.games.football.geom.Vector2D;
import static sg.games.football.geom.Vector2D.*;
import sg.games.football.gameplay.PlayerRole;
import sg.games.football.gameplay.SoccerTeam;
import sg.games.football.gameplay.info.FootballPlayerInfo;

public class GoalKeeper extends PlayerBase {

    //an instance of the state machine class
    private StateMachine<GoalKeeper> stateMachine;

    //this vector is updated to point towards the ball and is used when
    //rendering the goalkeeper (instead of the underlaying vehicle's heading)
    //to ensure he always appears to be watching the ball
    private Vector2D lookAt;

    boolean drawBody = false;

    public GoalKeeper(SoccerTeam home_team, FootballPlayerInfo info) {

        super(home_team, PlayerRole.GoalKeeper, info);

    }

    public void init(State<GoalKeeper> start_state) {
        super.init(); //set up the state machine
        stateMachine = new StateMachine<GoalKeeper>(this);
        stateMachine.setCurrentState(start_state);
        stateMachine.setPreviousState(start_state);
        stateMachine.setGlobalState(GlobalKeeperState.Instance());

        stateMachine.getCurrentState().enter(this);
    }

    public void startMatch() {

    }

    //-------------------------- Update --------------------------------------
    public void update(float tpf) {
        //run the logic for the current state
        stateMachine.update();

        if (steeringActived) {
            //calculate the combined force from each steering behavior 
            Vector2D SteeringForce = steering.calculate();

            //Acceleration = Force/Mass
            Vector2D Acceleration = SteeringForce.divide(mass);

            //update velocity
            velocity = velocity.plus(Acceleration);

            //make sure player does not exceed maximum velocity
            velocity.Truncate(maxSpeed);

            //update the position
            position = position.plus(velocity.multiply(tpf));

            //enforce a non-penetration raint if desired
            if (Params.Instance().bNonPenetrationConstraint) {
                enforceNonPenetrationContraint(this, FootballEntityManager.Instance().getAllEntitiesByClass(PlayerBase.class));
            }

            //update the heading if the player has a non zero velocity
            if (!velocity.isZero()) {
                heading = Vec2DNormalize(velocity);

                side = heading.perp();
            }

            if (getBall() != null) {
                //look-at vector always points toward the ball
                if (!getMatchGamePlay().isGoalKeeperHasBall()) {
                    lookAt = Vec2DNormalize(getBall().getPos().minus(getPos()));
                }
            }
        }
        // update rotation
        // lookAt
        // update location
        super.update(tpf);
    }

    public boolean isBallWithinRangeForIntercept() {
        return (vec2DDistanceSq(getTeam().getHomeGoal().getCenter(), getBall().getPos())
                <= Params.Instance().GoalKeeperInterceptRangeSq);
    }

    public boolean isTooFarFromGoalMouth() {
        return (vec2DDistanceSq(getPos(), getRearInterposeTarget())
                > Params.Instance().GoalKeeperInterceptRangeSq);
    }

    public Vector2D getRearInterposeTarget() {
        double xPosTarget = getTeam().getHomeGoal().getCenter().x;

        double yPosTarget = getPitch().getPlayingArea().getCenter().y
                - Params.Instance().GoalWidth * 0.5 + (getBall().getPos().y * Params.Instance().GoalWidth)
                / getPitch().getPlayingArea().getHeight();

        return new Vector2D(xPosTarget, yPosTarget);
    }

    //-------------------- HandleMessage -------------------------------------
    //
    // routes any messages appropriately
    //------------------------------------------------------------------------
    public boolean handleMessage(Telegram msg) {
        return stateMachine.handleMessage(msg);
    }

    /* getter setter
     */
    public StateMachine<GoalKeeper> getFSM() {
        return stateMachine;
    }

    public Vector2D getLookAt() {
        return lookAt;
    }

    public void setLookAt(Vector2D v) {
        lookAt = v;
    }
}
