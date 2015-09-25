package sg.games.football.gameplay.ai.states.goalkeeper;

import sg.games.football.entities.GoalKeeper;
import sg.games.football.gameplay.ai.fsm.State;
import sg.games.football.gameplay.ai.event.Telegram;
// STATES OF GOAL KEEPER FOR STATE MACHINE
//------------------------- ReturnHome: ----------------------------------
//
// In this state the goalkeeper simply returns back to the center of
// the goal region before changing state back to TendGoal
//------------------------------------------------------------------------

public class ReturnHome extends State<GoalKeeper> {

    static ReturnHome instance;

    public static ReturnHome Instance() {
        if (instance == null) {
            instance = new ReturnHome();
        }
        return instance;
    }

    public void enter(GoalKeeper keeper) {
        keeper.getSteering().arriveOn();
    }

    public void execute(GoalKeeper keeper) {
        keeper.getSteering().setTarget(keeper.getHomeRegion().getCenter());

        //if close enough to home or the opponents get control over the ball,
        //change state to tend goal
        if (keeper.IngetHomeRegion() || !keeper.getTeam().inControl()) {
            keeper.getFSM().changeState(TendGoal.Instance());
        }
    }

    public void exit(GoalKeeper keeper) {
        keeper.getSteering().arriveOff();
    }

    public boolean onMessage(GoalKeeper keeper, Telegram telegram) {
        return false;
    }
}
