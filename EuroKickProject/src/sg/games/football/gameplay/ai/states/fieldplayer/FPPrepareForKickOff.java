package sg.games.football.gameplay.ai.states.fieldplayer;

import sg.games.football.entities.FieldPlayer;
import sg.games.football.gameplay.ai.fsm.State;

import sg.games.football.gameplay.ai.event.Telegram;

//***************************************************************************** WAIT
/**
 *
 * @author cuong.nguyenmanh2
 */
public class FPPrepareForKickOff extends State<FieldPlayer>{
    static FPPrepareForKickOff instance;
    public static FPPrepareForKickOff Instance(){
        if (instance==null){
            instance = new FPPrepareForKickOff();
        }
        return instance;
    }


    public void enter(FieldPlayer player){
        //FIXME: Change animation
//        player.info.setAnim("Run");
//        player.info.setDebugText("PrepareForKickOff");
    }

    public void execute(FieldPlayer player){ 

        
    }

    public void exit(FieldPlayer player){
        
    }

    public boolean onMessage(FieldPlayer player, final Telegram t){
        return false;
        
    }
}

