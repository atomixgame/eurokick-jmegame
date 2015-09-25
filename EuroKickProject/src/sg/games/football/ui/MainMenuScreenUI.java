/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.games.football.ui;

import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.SizeValue;
import sg.games.football.managers.FootballGameGUIManager;
import sg.games.football.state.LoadingState;
import sg.games.football.state.MainMenuState;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class MainMenuScreenUI implements ScreenController {

    FootballGameGUIManager gameGUIManager;
    Screen screen;
    String[] tabNames = {"KeyboardTabPanel", "GraphicsTabPanel", "SoundsTabPanel", "GeneralTabPanel"};
    String currentTab;

    public MainMenuScreenUI(FootballGameGUIManager gameGUIManager) {
        this.gameGUIManager = gameGUIManager;

    }

    public void bind(Nifty nifty, Screen screen) {
        this.screen = screen;
        gameGUIManager.getInputManager().setCursorVisible(true);
    }

    public void onStartScreen() {
        if (screen.getScreenId().equals("Options")) {
            showTab("KeyboardTabPanel");
        }

    }

    public void onEndScreen() {
    }

    public void showOptions() {
        System.out.println(" Go to Options");
        gameGUIManager.goToScreen("Options");
    }

    public void backToMainMenu() {
        gameGUIManager.goToScreen("MainMenuScreen");
    }

    public void showTab(String tabName) {
        if (currentTab != tabName) {
            hideAllTabs();
            Element e = screen.findElementById(tabName);
            e.setVisibleToMouseEvents(true);
            e.setConstraintHeight(new SizeValue("100%"));
            e.setVisible(true);
            e.setFocusable(true);

            currentTab = tabName;
            e.getParent().resetLayout();
            screen.resetLayout();
            screen.layoutLayers();

            System.out.println(" " + e.getId() + " " + e.getConstraintHeight() + " " + e.getY() + " " + e.getConstraintY());
            System.out.println("-------------------------------------------------");
        }
    }

    public void hideAllTabs() {
        for (String name : tabNames) {
            //if (name != currentTab) {
            Element e = screen.findElementById(name);
            System.out.println(" " + e.getId() + " " + e.getConstraintHeight() + " " + e.getY() + " " + e.getConstraintY());
            e.setVisibleToMouseEvents(false);
            e.setConstraintHeight(new SizeValue("0%"));
            e.setFocusable(false);
            e.setVisible(false);
            e.getLayoutPart().getBox().setY(0);

            System.out.println(" " + e.getId() + " " + e.getConstraintHeight() + " " + e.getY() + " " + e.getConstraintY());
            //}
        }

    }

    public void createHost() {
        //FIXME: Go to next State. which is loading
        //gameGUIManager.getApp().getGameStateManager().loadGame();
        AppStateManager stateManager = gameGUIManager.getApp().getStateManager();
        MainMenuState menuState = stateManager.getState(MainMenuState.class);
        boolean detached = stateManager.detach(menuState);
        stateManager.attach(new LoadingState());
    }

    public void singleGame() {
        //gameGUIManager.goToScreen("CreateSingleGameScreen");
        createHost();
    }

    public void quitGame() {
        gameGUIManager.getApp().quitGame();
    }
}
