/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.games.football.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import sg.games.football.FootballGame;
import sg.games.football.managers.FootballGameGUIManager;
import sg.games.football.managers.FootballGameStageManager;

/**
 *
 * @author cuongnguyen
 */
public class BaseGameScreenState extends AbstractAppState {

    protected Node rootNode;
    protected AssetManager assetManager;
    protected AppStateManager stateManager;
    protected InputManager inputManager;
    protected ViewPort viewPort;
    protected FootballGameGUIManager gameGUIManager;
    protected FootballGameStageManager stageManager;
    protected FootballGame app;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

//        this.app = (FootballGame) app; // can cast Application to something more specific
//        this.gameGUIManager = (FootballGUIManager) this.app.getGameGUIManager();
    }

}
