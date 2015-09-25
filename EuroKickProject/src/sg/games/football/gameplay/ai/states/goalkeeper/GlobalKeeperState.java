package sg.games.football.gameplay.ai.states.goalkeeper;

import sg.games.football.entities.GoalKeeper;

import sg.games.football.gameplay.ai.fsm.State;
import sg.games.football.gameplay.ai.event.Telegram;
import static sg.games.football.gameplay.ai.event.Telegram.MessageType.*;

// STATES OF GOAL KEEPER FOR STATE MACHINE
//--------------------------- GlobalKeeperState -------------------------------

public class GlobalKeeperState extends State<GoalKeeper>{
    static GlobalKeeperState instance;
    public static GlobalKeeperState Instance(){
        if (instance==null){
            instance = new GlobalKeeperState();
        }
        return instance;
    }
    public void enter(GoalKeeper keeper){
    }

    public void execute(GoalKeeper keeper){
    }

    public void exit(GoalKeeper keeper){
    }
 
    public boolean onMessage(GoalKeeper keeper, Telegram telegram){
        switch(telegram.Msg){
        case Msg_GoHome:
            keeper.setDefaultHomeRegion();
            keeper.getFSM().changeState(ReturnHome.Instance());
            break;

        case Msg_ReceiveBall:
            keeper.getFSM().changeState(InterceptBall.Instance());
            break;

        }//end switch

        return false;
    }

}



