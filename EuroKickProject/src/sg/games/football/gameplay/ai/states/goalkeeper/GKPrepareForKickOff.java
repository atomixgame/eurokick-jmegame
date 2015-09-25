package sg.games.football.gameplay.ai.states.goalkeeper;

import sg.games.football.entities.GoalKeeper;
import sg.games.football.gameplay.ai.fsm.State;
import sg.games.football.gameplay.ai.event.Telegram;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class GKPrepareForKickOff extends State<GoalKeeper>{
    static GKPrepareForKickOff instance;
    public static GKPrepareForKickOff Instance(){
        if (instance==null){
            instance = new GKPrepareForKickOff();
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
        return false;
    }
}

