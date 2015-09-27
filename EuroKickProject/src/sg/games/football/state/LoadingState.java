/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.games.football.state;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.screen.Screen;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class LoadingState extends BaseGameScreenState {

    private float oldPercent = -1f;
    private Screen loadingScreen;
    private boolean loadComplete = false;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        setEnabled(true);

    }

    @Override
    public void setEnabled(boolean enabled) {
        // Pause and unpause
        super.setEnabled(enabled);
        if (enabled) {
            initPhase();
        } else {
            if (loadComplete) {
                //nextState();
            }

        }
    }

    protected void initPhase() {
//        gameGUIManager.goToScreen("loadingScreen");
//        loadingScreen = gameGUIManager.getNifty().getScreen("loadingScreen");
//        if (loadingScreen == null) {
//            throw new RuntimeException("May be: You didn't add the LoadingScreen in XML yet!");
//        } else {
////            guiController = (UILoadingScreenController) loadingScreen.getScreenController();
//        }
    }

    protected void loadPhase() {
    }

    protected void finishPhase() {
    }

    protected void nextState() {
        //gameStateManager.goInGame();

        boolean detached = stateManager.detach(this);
        stateManager.attach(new MainInGameState());
        //Logger.getLogger(LoadingState.class.getName()).log(Level.INFO, "Detach Loading State");
    }

    protected void watchTask() {
//        // Wait for the GUI controller to finish screen changing
//        if (guiController != null && loadingScreen.isRunning()) {
//            if (stageManager.getProgressInfo().getCurrentProgressName() != null) {
//                float currentProcess = stageManager.getProgressInfo().getCurrentProgressPercent();
//                if (oldPercent != currentProcess) {
//                    guiController.setProgress(currentProcess, stageManager.getProgressInfo().getCurrentProgressName());
//                    oldPercent = currentProcess;
//
//                }
//            }
//        }
    }

    public void updateProgressBar(boolean hasError, String errorMsg) {
        // Wait for the GUI controller to finish screen changing
//        if (guiController != null && gameGUIManager.getNifty().getCurrentScreen().getScreenId().equals("loadingScreen")) {
//            if (hasError) {
//                //
//                guiController.setProgress(0, "Error ! Press Esc to back to main menu :" + errorMsg);
//            } else {
//                if (stageManager.getProgressInfo().getCurrentProgressName() != null) {
//                    float currentProcess = stageManager.getProgressInfo().getCurrentProgressPercent();
//                    if (oldPercent != currentProcess) {
//                        guiController.setProgress(currentProcess, stageManager.getProgressInfo().getCurrentProgressName());
//                        oldPercent = currentProcess;
//
//                    }
//                }
//            }
//        }
    }
}
