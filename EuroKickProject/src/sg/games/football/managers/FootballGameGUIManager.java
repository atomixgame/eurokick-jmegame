package sg.games.football.managers;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.dynamic.ImageCreator;
import de.lessvoid.nifty.controls.dynamic.LayerCreator;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.PanelRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;
import de.lessvoid.nifty.tools.SizeValue;
import sg.games.football.FootballGame;
import sg.games.football.ui.MainMenuScreenUI;
import sg.games.football.ui.UIEditPlayer;
import sg.games.football.ui.UIIngameScreenController;
import sg.games.football.ui.UIMainInGameScreenController;
import sg.games.football.ui.UIManagerScreenController;
import sg.games.football.ui.UIPlayerListScreenController;
import sg.atom.ui.GameGUIManager;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class FootballGameGUIManager extends GameGUIManager {

    private Element helpLayer;
    private Element helpCursor;
    private Nifty nifty;

    public FootballGameGUIManager(FootballGame app) {
        super(app);
    }

    @Override
    public void initGUI() {
        super.initGUI();
        setupCommonScreens();
    }

    public void setupCommonScreens() {
        nifty.registerScreenController(new UIIngameScreenController(this),
                new UIMainInGameScreenController(this),
                new UIEditPlayer(this),
                new MainMenuScreenUI(this),
                new UIManagerScreenController(this),
                new UIPlayerListScreenController(this));

        nifty.addXml("Interface/Screens/InGame/Ingame1.xml");
        nifty.addXml("Interface/Screens/InGame/Ingame2.xml");
        nifty.addXml("Interface/Screens/InGame/ManagerScreen.xml");
        nifty.addXml("Interface/Screens/InGame/PlayerListScreen.xml");
        nifty.addXml("Interface/Screens/MainMenu/Loading.xml");
        nifty.addXml("Interface/Screens/EditPlayer/EditPlayer.xml");
        nifty.addXml("Interface/Screens/MainMenu/MainMenu.xml");
        nifty.addXml("Interface/Screens/MainMenu/Options/Options.xml");
        //nifty.addXml("Interface/MainMenu/CreateHost.xml");
    }

    public void simpleUpdate(float tpf) {
    }

    public void testNextScreens() {
    }

    // Help in GUI
    public void setupHelpGUI() {
        // Add another layer
        LayerCreator layerCreator = new LayerCreator("helpLayer");
        //layerCreator.setBackgroundColor("#22222266");
        layerCreator.setChildLayout("absolute");
        helpLayer = layerCreator.create(nifty, nifty.getCurrentScreen(), nifty.getCurrentScreen().getRootElement());
        // And a Hand cursor
        ImageCreator imageCreator = new ImageCreator("HelpCursor");
        imageCreator.setBackgroundImage("Interface/Images/icons/Help/Pointer-Hand.png");
        imageCreator.setFilename("Interface/Images/icons/Help/Pointer-Hand.png");
        imageCreator.setWidth("60px");
        imageCreator.setHeight("60px");
        //imageCreator.setX("100px");
        //imageCreator.setY("100px");
        helpCursor = imageCreator.create(nifty, nifty.getCurrentScreen(), helpLayer);
        //helpLayer.add(helpCursor);
        helpCursor.hide();
    }

    public void helpShowCursorAt(Element el) {
        helpLayer.getRenderer(PanelRenderer.class).setBackgroundColor(new Color("#22222255"));

        helpCursor.setConstraintX(new SizeValue(el.getX() + "px"));
        helpCursor.setConstraintY(new SizeValue(el.getY() + "px"));

        helpCursor.show();

        helpLayer.layoutElements();
    }

    public void helpMoveCursorTo(Element el) {
    }

    public void helpHideCursor() {
        helpCursor.hide();
    }

    public <T extends ScreenController> T getCurrentScreenController(String screenName, Class<T> clazz) {
        Screen currentScreen = nifty.getCurrentScreen();
        if (currentScreen.getScreenId().equals(screenName)) {
            return (T) currentScreen.getScreenController();
        } else {
            return null;
        }
    }
//
//    public ScreenController getCurrentScreenController() {
//        return nifty.getCurrentScreen().getScreenController();
//    }
//
//    public <T extends ScreenController> T getCurrentScreenController(Class<T> clazz) {
//        return (T) getCurrentScreenController();
//    }
//
//    public <T extends Controller> T getQuickUIController(String elementId, Class<T> clazz) {
//        return nifty.getCurrentScreen().findControl(elementId, clazz);
//    }
}
