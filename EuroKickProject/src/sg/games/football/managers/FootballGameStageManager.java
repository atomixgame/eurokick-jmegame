/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.games.football.managers;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import sg.games.football.FootballGame;
import sg.games.football.gameplay.FootballGamePlayManager;
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

    @Override
    public void configStageCustom() {
        getGamePlayManager().configGamePlay();
        //this.getScreenEffectManager().init();
    }

    @Override
    public void finishStageCustom() {
        setupCamera();
//        setupKeys();
        //pauseGame();
        //getGamePlayManager().startGamePlay();
    }

    /* Custom */
    public void setupCamera() {
        getApp().getFlyByCamera().setMoveSpeed(10f);
        Camera cam = getCurrentCamera();
        cam.setLocation(new Vector3f(0, 20, 0));
        cam.lookAt(((FootballGameWorldManager) worldManager).getStadiumMaker().getCenter(), Vector3f.UNIT_Y);
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
    public void updateStageCustom(float tpf) {
        getGamePlayManager().update(tpf);

        //getScreenEffectManager().simpleUpdate(tpf);
//        actionQueue.updateActionQueue(tpf);
    }

    public void pauseGame() {
        gamePaused = true;
    }

    public void resumeGame() {
    }
    /* Game specific Short cut */

    @Override
    public FootballGameWorldManager getWorldManager() {
        return (FootballGameWorldManager) worldManager;
    }

    @Override
    public FootballGamePlayManager getGamePlayManager() {
        return (FootballGamePlayManager) gamePlayManager;
    }

    private FootballScreenEffectManager getScreenEffectManager() {
        return (FootballScreenEffectManager) screenEffectManager;
    }
}
