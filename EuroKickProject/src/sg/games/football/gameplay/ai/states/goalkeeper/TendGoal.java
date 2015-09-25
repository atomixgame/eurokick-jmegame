package sg.games.football.gameplay.ai.states.goalkeeper;

import sg.games.football.entities.GoalKeeper;
import sg.games.football.gameplay.ai.fsm.State;
import sg.games.football.gameplay.ai.event.Telegram;
import sg.games.football.gameplay.ai.info.*;
// STATES OF GOAL KEEPER FOR STATE MACHINE
//--------------------------- TendGoal -----------------------------------
//
// This is the main state for the goalkeeper. When in this state he will
// move left to right across the goalmouth using the 'interpose' steering
// behavior to put himself between the ball and the back of the net.
//
// If the ball comes within the 'goalkeeper range' he moves out of the
// goalmouth to attempt to intercept it. (see next state)
//------------------------------------------------------------------------

public class TendGoal extends State<GoalKeeper> {

    static TendGoal instance;

    public static TendGoal Instance() {
        if (instance == null) {
            instance = new TendGoal();
        }
        return instance;
    }

    public void enter(GoalKeeper keeper) {
        //turn interpose on
        keeper.getSteering().interposeOn(Params.Instance().GoalKeeperTendingDistance);

        //interpose will position the agent between the ball position and a target
        //position situated along the goal mouth. This call sets the target
        keeper.getSteering().setTarget(keeper.getRearInterposeTarget());
    }

    public void execute(GoalKeeper keeper) {
        //the rear interpose target will change as the ball's position changes
        //so it must be updated each update-step 
        keeper.getSteering().setTarget(keeper.getRearInterposeTarget());

        //if the ball comes in range the keeper traps it and then changes state
        //to put the ball back in play
        if (keeper.isBallWithinKeeperRange()) {
            keeper.getBall().trap();

            keeper.getMatchGamePlay().setGoalKeeperHasBall(true);

            keeper.getFSM().changeState(PutBallBackInPlay.Instance());

            return;
        }

        //if ball is within a predefined distance, the keeper moves out from
        //position to try and intercept it.
        if (keeper.isBallWithinRangeForIntercept() && !keeper.getTeam().inControl()) {
            keeper.getFSM().changeState(PutBallBackInPlay.Instance());
        }

        //if the keeper has ventured too far away from the goal-line and there
        //is no threat from the opponents he should move back towards it
        if (keeper.isTooFarFromGoalMouth() && keeper.getTeam().inControl()) {
            keeper.getFSM().changeState(ReturnHome.Instance());

            return;
        }
    }

    public void exit(GoalKeeper keeper) {
        keeper.getSteering().interposeOff();
    }

    public boolean onMessage(GoalKeeper keeper, Telegram telegram) {
        return false;
    }
}
