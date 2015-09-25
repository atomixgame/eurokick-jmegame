package sg.games.football;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import sg.games.football.managers.FootballGameGUIManager;
import sg.games.football.managers.FootballGameStageManager;
import com.jme3.system.AppSettings;
import sg.atom.core.AtomMain;
import sg.atom.world.WorldSettings;
import sg.games.football.gameplay.FootballGamePlayManager;
import sg.games.football.managers.FootballGameWorldManager;

/**
 *
 * @author hungcuong
 */
public class FootballGame extends AtomMain {

    public static void main(String[] args) {
        FootballGame app = FootballGame.getInstance();
        AppSettings settings = new AppSettings(true);
        settings.setWidth(1024);
        settings.setHeight(768);
        app.setSettings(settings);
        app.setShowSettings(false);
        //app.setDisplayStatView(false);
        app.start();
    }
    /**
     * Singleton reference of Object.
     */
    private static FootballGame selfRef;
    private FootballGamePlayManager gamePlayManager;

    /**
     * Constructs singleton instance of Object.
     */
    public FootballGame() {
        selfRef = this;
    }

    /**
     * Provides reference to singleton object of Object.
     *
     * @return Singleton instance of Object.
     */
    public static final FootballGame getInstance() {
        if (selfRef == null) {
            selfRef = new FootballGame();
        }
        return selfRef;
    }

    @Override
    public void initManagers() {
        gameGUIManager = new FootballGameGUIManager(this);
        gameGUIManager.initGUI();
        stageManager = FootballGameStageManager.getInstance();
        stageManager.initStage();
        worldManager = new FootballGameWorldManager(this, rootNode);
        WorldSettings wSettings = new WorldSettings();
        wSettings.useDayLight = false;
        wSettings.useEnviroment = false;
        wSettings.useForestor = false;
        wSettings.useLevel = false;
        wSettings.useWater = false;
        wSettings.useWeather = false;
        wSettings.useTerrainLOD = false;
        wSettings.usePhysics = true;
        worldManager.initWorld(null, wSettings);
        gamePlayManager = new FootballGamePlayManager(this);
//        this.soundManager = new FbSoundManager((FootballGame) app);
        //this.screenEffectManager = new FootballScreenEffectManager(this);
    }

    public void setupKeys() {
        inputManager.addMapping("ToogleStats",
                new KeyTrigger(KeyInput.KEY_I));
        inputManager.addListener(actionListener, "ToogleStats");

    }

    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean pressed, float tpf) {
            if (name.equals("ToogleStats") && pressed) {
//                setDisplayStatView(displayStats);
            }
        }
    };

    @Override
    public void simpleInitApp() {
        super.simpleInitApp();
    }

    public FootballGamePlayManager getGamePlayManager() {
        return gamePlayManager;
    }

    @Override
    public FootballGameWorldManager getWorldManager() {
        return (FootballGameWorldManager) super.getWorldManager(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FootballGameGUIManager getGameGUIManager() {
        return (FootballGameGUIManager) super.getGameGUIManager(); //To change body of generated methods, choose Tools | Templates.
    }

}
