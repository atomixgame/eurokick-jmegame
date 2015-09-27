/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.games.football.ui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import sg.games.football.managers.FootballGameGUIManager;

/**
 *
 * @author cuongnguyen
 */
public class BaseScreenController implements ScreenController {

    protected Nifty nifty;
    protected Screen screen;
    protected final FootballGameGUIManager gameGUIManager;

    public BaseScreenController(FootballGameGUIManager gameGUIManager) {
        this.gameGUIManager = gameGUIManager;
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
    }

    @Override
    public void onStartScreen() {
    }

    @Override
    public void onEndScreen() {
    }

    public FootballGameGUIManager getGameGUIManager() {
        return gameGUIManager;
    }
}
