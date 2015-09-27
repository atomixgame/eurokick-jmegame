/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.games.football.ui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.ListBoxSelectionChangedEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.builder.*;
import sg.games.football.gameplay.FootballGamePlayManager;
import sg.games.football.gameplay.GPMatch;
import java.util.List;
import java.util.*;
import de.lessvoid.nifty.tools.SizeValue;
import javax.inject.Inject;
import sg.games.football.FootballGame;
import sg.games.football.entities.PlayerBase;
import sg.games.football.managers.FootballGameGUIManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class UIIngameScreenController extends BaseScreenController {

    // MatchScreen
    private Element timeElement;
    private Element clubATitle, clubBTitle;
    private Element scoreElement;
    private Element commenterTextElement;
    private Element redPlayerEl, bluePlayerEl, fieldEl;
    // MiniView
    private Element miniView;
    private Element ballEl;

    @Inject
    public UIIngameScreenController(FootballGameGUIManager gameGUIManager) {
        super(gameGUIManager);
    }

    public void bind(Nifty nifty, Screen screen) {
        this.screen = screen;
        this.nifty = nifty;
        if (screen.getScreenId().equals("Stragegy")) {
            //fillMyListBox(screen);
        } else if (screen.getScreenId().equals("MatchScreen")) {
            timeElement = screen.findElementById("MatchTime");
            clubATitle = screen.findElementById("ClubATitle");
            clubBTitle = screen.findElementById("ClubBTitle");
            scoreElement = screen.findElementById("Scores");
            commenterTextElement = screen.findElementById("CommenterTextElement");

            fieldEl = screen.findElementById("Field");

        } else {

        }
    }

    public void onStartScreen() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onEndScreen() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Fill the listbox with items. In this case with Strings.
     */
    public void fillMyListBox(Screen screen) {
        ListBox listBox = screen.findNiftyControl("myListBox", ListBox.class);
        FootballGamePlayManager gameplayManager = FootballGame.getInstance().getGamePlayManager();
        GPMatch gameplay = gameplayManager.getMatchGamePlay();

//        for (FootballStragegy st : gameplay.getCurrentPlayer().getCoach().getStragegies()) {
//            listBox.addItem(st.getPosTitle());
//        }
    }

    /**
     * When the selection of the ListBox changes this method is called.
     */
    @NiftyEventSubscriber(id = "myListBox")
    public void onMyListBoxSelectionChanged(final String id, final ListBoxSelectionChangedEvent<String> event) {
        GPMatch gameplay = getMatchGameplay();

        List<String> selection = event.getSelection();
        for (String selectedItem : selection) {
            gameplay.changeStragegy(selectedItem);
            System.out.println("listbox selection [" + selectedItem + "]");
        }
    }
    /* 
     * Match Screen */

    public GPMatch getMatchGameplay() {
        FootballGamePlayManager gamePlayManager = gameGUIManager.getApp().getGamePlayManager();
        GPMatch gameplay = gamePlayManager.getMatchGamePlay();
        return gameplay;
    }

    public void startMatch() {
        GPMatch gameplay = getMatchGameplay();
        gameGUIManager.goToScreen("MatchScreen");
        gameplay.startMatch();

    }

    public void continueMatch() {
        GPMatch gameplay = getMatchGameplay();
        gameGUIManager.goToScreen("MatchScreen");
        gameplay.continueMatch();

    }

    public void setMatchTime(float t) {
        int min = (int) Math.floor(t / 60);
        int sec = Math.round(t - min * 60);
        if (timeElement != null) {
            timeElement.getRenderer(TextRenderer.class).setText(min + ":" + sec);
        }
    }

    public void setCommenterText(String text) {
        if (commenterTextElement != null) {
            commenterTextElement.getRenderer(TextRenderer.class).setText(text);
        }
    }
    HashMap<PlayerBase, Element> playerPosMap = new HashMap<PlayerBase, Element>();

    public void createMiniView() {

        GPMatch gameplay = getMatchGameplay();
        int i = 0;
        for (PlayerBase player : gameplay.redTeam.players) {
            i++;
            final int xPos = (int) Math.round(player.getPos().getX());
            final int yPos = (int) Math.round(player.getPos().getY());
            Element playerEl = new PanelBuilder("RedPlayer_" + i) {
                {
                    childLayoutVertical();
                    x(xPos + "px");
                    y(yPos + "px");
                    width("5px");
                    height("5px");
                    backgroundColor("#a00f");
                    childLayoutHorizontal();
                }
            }.build(nifty, screen, fieldEl);
            playerEl.markForMove(fieldEl);
            playerPosMap.put(player, playerEl);
        }

        i = 0;
        for (PlayerBase player : gameplay.blueTeam.players) {
            i++;
            final int xPos = (int) Math.round(player.getPos().getX());
            final int yPos = (int) Math.round(player.getPos().getY());
            Element playerEl = new PanelBuilder("BluePlayer_" + i) {
                {
                    childLayoutVertical();
                    x(xPos + "px");
                    y(yPos + "px");
                    width("5px");
                    height("5px");
                    backgroundColor("#00af");
                    childLayoutHorizontal();
                }
            }.build(nifty, screen, fieldEl);
            playerEl.markForMove(fieldEl);
            playerPosMap.put(player, playerEl);
        }

        final int xPos = (int) Math.round(gameplay.ballController.getPos().getX());
        final int yPos = (int) Math.round(gameplay.ballController.getPos().getY());
        ballEl = new PanelBuilder("BluePlayer_" + i) {
            {
                childLayoutVertical();
                x(xPos + "px");
                y(yPos + "px");
                width("8px");
                height("8px");
                backgroundColor("#ffff");
                childLayoutHorizontal();
            }
        }.build(nifty, screen, fieldEl);
        ballEl.markForMove(fieldEl);
    }

    public void updatePlayerPos() {
        if (playerPosMap.isEmpty()) {
            createMiniView();
        } else {
            GPMatch gameplay = getMatchGameplay();
            //FIXME: Better transformation
            double cx = gameplay.getPitch().cxClient;
            double cy = gameplay.getPitch().cyClient;
            int panelWidth = fieldEl.getWidth();
            int panelHeight = fieldEl.getHeight();

            for (PlayerBase player : gameplay.redTeam.players) {
                int xPos = (int) Math.round(player.getPos().getX() * panelWidth / cx);
                int yPos = (int) Math.round(panelHeight - player.getPos().getY() * panelHeight / cy);
                Element playerEl = playerPosMap.get(player);
                playerEl.setConstraintX(new SizeValue(xPos + "px"));
                playerEl.setConstraintY(new SizeValue(yPos + "px"));

            }
            for (PlayerBase player : gameplay.blueTeam.players) {
                int xPos = (int) Math.round(player.getPos().getX() * panelWidth / cx);
                int yPos = (int) Math.round(panelHeight - player.getPos().getY() * panelHeight / cy);
                Element playerEl = playerPosMap.get(player);
                playerEl.setConstraintX(new SizeValue(xPos + "px"));
                playerEl.setConstraintY(new SizeValue(yPos + "px"));

            }
            int xPos = (int) Math.round(gameplay.ballController.getPos().getX() * panelWidth / cx);
            int yPos = (int) Math.round(panelHeight - gameplay.ballController.getPos().getY() * panelHeight / cy);
            ballEl.setConstraintX(new SizeValue(xPos + "px"));
            ballEl.setConstraintY(new SizeValue(yPos + "px"));
            fieldEl.layoutElements();
        }
    }

}
