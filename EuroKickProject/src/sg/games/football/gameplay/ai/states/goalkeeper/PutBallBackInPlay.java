package sg.games.football.gameplay.ai.states.goalkeeper;

import sg.games.football.entities.GoalKeeper;
import sg.games.football.entities.PlayerBase;
import sg.games.football.gameplay.ai.fsm.State;
import sg.games.football.gameplay.ai.event.Telegram;
import sg.games.football.geom.Vector2D;
import sg.games.football.gameplay.ai.info.*;
import sg.games.football.gameplay.ai.event.*;
import static sg.games.football.geom.Vector2D.*;
// STATES OF GOAL KEEPER FOR STATE MACHINE

//--------------------------- PutBallBackInPlay --------------------------
//
//------------------------------------------------------------------------
class PutBallBackInPlay extends State<GoalKeeper> {

    static PutBallBackInPlay instance;

    public static PutBallBackInPlay Instance() {
        if (instance == null) {
            instance = new PutBallBackInPlay();
        }
        return instance;
    }

    public void enter(GoalKeeper keeper) {
        //let the team know that the keeper is in control
        keeper.getTeam().setControllingPlayer(keeper);

        //send all the players home
        keeper.getTeam().getOpponents().returnAllFieldPlayersToHome();
        keeper.getTeam().returnAllFieldPlayersToHome();
    }

    public void execute(GoalKeeper keeper) {
        PlayerBase receiver = null;
        Vector2D BallTarget = new Vector2D();

        //test if there are players further forward on the field we might
        //be able to pass to. If so, make a pass.
        receiver = keeper.getTeam().findPass(keeper,
                receiver,
                BallTarget,
                Params.Instance().MaxPassingForce,
                Params.Instance().GoalkeeperMinPassDist);
        if (receiver != null) {
            //make the pass 
            keeper.getBall().kick(Vec2DNormalize(BallTarget.minus(keeper.getBall().getPos())),
                    Params.Instance().MaxPassingForce);

            //goalkeeper no longer has ball 
            keeper.getMatchGamePlay().setGoalKeeperHasBall(false);

            //let the receiving player know the ball's comin' at him
            Dispatcher.Instance().dispatchMsg(Dispatcher.SEND_MSG_IMMEDIATELY,
                    keeper.getID(),
                    receiver.getID(),
                    Telegram.MessageType.Msg_ReceiveBall,
                    BallTarget);

            //go back to tending the goal 
            keeper.getFSM().changeState(TendGoal.Instance());

            return;
        }

        keeper.setVelocity(new Vector2D());
    }

    public void exit(GoalKeeper keeper) {
    }

    public boolean onMessage(GoalKeeper keeper, Telegram telegram) {
        return false;
    }
}
