package sg.games.football.managers;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import sg.games.football.FootballGame;
import sg.atom.stage.StageManager;

/**
 *
 * @author hungcuong
 */
public class FootballGameStageManager extends StageManager {

    /**
     * Singleton reference of FootballGameStageManager.
     */
    private static FootballGameStageManager selfRef;
    /* Action management */
//    DelayedClosureExecutor actionQueue = new DelayedClosureExecutor(0);

    /**
     * Constructs singleton instance of FootballGameStageManager.
     */
    private FootballGameStageManager(FootballGame app) {
        super(app);
        selfRef = this;
    }

    /**
     * Provides reference to singleton object of FootballGameStageManager.
     *
     * @return Singleton instance of FootballGameStageManager.
     */
    public static FootballGameStageManager getInstance() {
        if (selfRef == null) {
            selfRef = new FootballGameStageManager(FootballGame.getInstance());
        }
        return selfRef;
    }

    /* Custom */
    public void setupCamera() {
        getApp().getFlyByCamera().setMoveSpeed(10f);
        Camera cam = getCurrentCamera();
        cam.setLocation(new Vector3f(0, 20, 0));
        cam.lookAt(((FootballGameWorldManager) getApp().getWorldManager()).getStadiumMaker().getCenter(), Vector3f.UNIT_Y);
    }
    
    public void showCursor(boolean show) {
        if (show) {
            getApp().getFlyByCamera().setEnabled(false);
            getApp().getInputManager().setCursorVisible(true);
        } else {
            getApp().getFlyByCamera().setEnabled(true);
            getApp().getInputManager().setCursorVisible(false);
        }
    }
    
    @Override
    public void update(float tpf) {
        super.update(tpf);
    }
    
    public void pauseGame() {
        gamePaused = true;
    }
    
    public void resumeGame() {
    }
    /* Game specific Short cut */
    
    private FootballScreenEffectManager getScreenEffectManager() {
        return (FootballScreenEffectManager) screenEffectManager;
    }
    
    @Override
    public FootballGame getApp() {
        return (FootballGame) super.getApp(); //To change body of generated methods, choose Tools | Templates.
    }
}
