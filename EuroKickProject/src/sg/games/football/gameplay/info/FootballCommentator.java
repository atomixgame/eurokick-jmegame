package sg.games.football.gameplay.info;

import sg.games.football.gameplay.GPMatch;
/**
 *
 * @author cuong.nguyenmanh2
 */
public class FootballCommentator {
    String name;
    GPMatch gameplay;
    int lineNum = 0;
    float EACH_LINE_TIME = 2;
    /*
     * Model the Commentor AI with simple rule base actor
     */
    
    public FootballCommentator(String name, GPMatch gameplay){
        this.name = name;
        this.gameplay = gameplay;
    }
    
    public void sayScript(String topic){
        if (topic.equalsIgnoreCase("MatchStart")){
        }

    }
    void removeALine(){
        lineNum--;
    }
    
    public void say(String text){
        lineNum++;
        // Update the UI

//        gameplay.getStageManager().delayAction((float) (3 + EACH_LINE_TIME*lineNum),{
//                Screen currentScreen = gameplay.getStageManager().getGUIManager().getNifty().getCurrentScreen();
//                if (currentScreen.getScreenId().equals("MatchScreen")){
//                    UIIngameScreenController sc= (UIIngameScreenController) currentScreen.getScreenController();
//                    sc.setCommenterText(text);
//                }
//            },{
//                removeALine();
//            });
    }
    
}

