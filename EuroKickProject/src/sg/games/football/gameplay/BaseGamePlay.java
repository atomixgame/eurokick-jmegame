package sg.games.football.gameplay;

/**
 *
 * @author cuong.nguyenmanh2
 */
import com.jme3.input.InputManager;
import com.jme3.asset.AssetManager;
import sg.games.football.FootballGame;
import sg.games.football.managers.FootballGameGUIManager;
import sg.games.football.managers.FootballGameStageManager;
import sg.games.football.managers.FootballSoundManager;

public abstract class BaseGamePlay {

    //FIXME: Convert to AppState!
    // Shortcut
    FootballGamePlayManager gpManager;
    // FIX: Remove short cut
    InputManager inputManager;
    FootballGameStageManager stageManager;
    AssetManager assetManager;
    FootballSoundManager soundManager;
    FootballGameGUIManager gameGUIManager;
    FootballGame app;

    public BaseGamePlay(FootballGame app) {
        this.app = app;
        this.assetManager = app.getAssetManager();
        this.inputManager = app.getInputManager();
        this.gpManager = app.getGamePlayManager();
        // FIX: Remove short cut
        this.stageManager = (FootballGameStageManager) app.getStageManager();
//        this.soundManager = app.getSoundManager();
        this.gameGUIManager = (FootballGameGUIManager) app.getGameGUIManager();
    }

    public abstract void initGamePlay();

    public abstract void loadGamePlay();

    public abstract void startGamePlay();

    public abstract void updateGamePlay(float tpf);

    public abstract void endGamePlay();

    public FootballGame getApp() {
        return app;
    }

}
