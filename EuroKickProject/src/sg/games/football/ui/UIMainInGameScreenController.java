/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.games.football.ui;

import com.jme3.material.Material;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import javax.inject.Inject;
import sg.games.football.managers.FootballGameGUIManager;

/**
 *
 * @author CuongNguyen
 */
public class UIMainInGameScreenController extends BaseScreenController {

    //Background
    private Material bgMat;
    private Picture backgroundPicture;
    private boolean staticBg = false;
    private Element talkText;

    @Inject
    public UIMainInGameScreenController(FootballGameGUIManager gameGUIManager) {
        super(gameGUIManager);
    }

    public void bind(Nifty nifty, Screen screen) {
        super.bind(nifty, screen);
        if (screen.getScreenId().equals("InGameFirstScreen")) {
            initBackground();
            talkText = screen.findElementById("talkText");
            gameGUIManager.setupHelpGUI();
        }

    }

    public void initBackground() {
        AppSettings settings = gameGUIManager.getApp().getSettings();
        RenderManager renderManager = gameGUIManager.getApp().getRenderManager();
        ViewPort viewPort = gameGUIManager.getApp().getViewPort();
        Camera cam = gameGUIManager.getApp().getCamera();

        bgMat = gameGUIManager.getApp().getAssetManager().loadMaterial("Materials/Background/BgStadium.j3m");
        backgroundPicture = new Picture("background");
        backgroundPicture.setMaterial(bgMat);
        backgroundPicture.setWidth(settings.getWidth());
        backgroundPicture.setHeight(settings.getHeight());
        backgroundPicture.setPosition(0, 0);

        backgroundPicture.updateGeometricState();

        ViewPort pv = renderManager.createPreView("background", cam);
        pv.setClearFlags(true, true, true);
        pv.attachScene(backgroundPicture);

        viewPort.setClearFlags(false, true, true);
        backgroundPicture.updateGeometricState();
    }

    @Override
    public void onStartScreen() {

    }

    @Override
    public void onEndScreen() {

    }

    public void nextLine() {
        talkText.getRenderer(TextRenderer.class).setText("Hi");
        gameGUIManager.helpShowCursorAt(talkText);
    }
}
