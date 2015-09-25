/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.games.football.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.scene.Node;
import sg.games.football.FootballGame;
import sg.games.football.managers.FootballGameGUIManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class MainMenuState extends BaseGameScreenState {

    //private ViewPort viewPort;
    private FootballGameGUIManager gameGUIManager;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        setEnabled(true);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            gameGUIManager.goToScreen("MainMenuScreen");
        } else {
        }
    }
}
