/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.games.football.state;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.ScreenshotAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import java.util.logging.Logger;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class InGameState extends BaseGameScreenState implements ActionListener {

    private static final Logger logger = Logger.getLogger(ScreenshotAppState.class.getName());
    protected boolean gamePause = true;


    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        setEnabled(true);

    }

    void setupInput() {
        inputManager = app.getInputManager();
        // replace default handling of ESC key
        inputManager.deleteMapping(SimpleApplication.INPUT_MAPPING_EXIT);
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_ESCAPE), new KeyTrigger(KeyInput.KEY_P), new KeyTrigger(KeyInput.KEY_PAUSE));
        inputManager.addListener(this, "Pause");
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (enabled) {
            //stageManager.initStage();
            goInGame();
        } else {
            goOutGame();
        }
    }

    public void goInGame() {
        gameGUIManager.goToScreen("InGameFirstScreen");
    }

    public void pauseGame() {
    }

    public void resumeGame() {
    }

    public void goOutGame() {
        //stageManager.goOutGame();
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);

        if (!gamePause) {
        }
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("Pause") && isPressed) {
            if (!gamePause) {
                pauseGame();
            } else {
                resumeGame();
            }
            gamePause = !gamePause;
        }
    }

}
