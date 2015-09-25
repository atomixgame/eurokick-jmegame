package sg.games.football.gameplay;

import sg.games.football.gameplay.managergp.GPManager;
import java.util.ArrayList;
import sg.atom.gameplay.GameLevel;
import sg.atom.gameplay.GamePlayManager;
import sg.games.football.FootballGame;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class FootballGamePlayManager extends GamePlayManager {

    // DEFAUT INSTANCE
    private static FootballGamePlayManager _defaultInstance;
    League league;
    BaseGamePlay currentGamePlay;
    GPMatch gpMatch;
    GPManager gpManager;
    GPCoach gpCoach;
    // Debug
    boolean cheat;
    ArrayList<BaseGamePlay> gamePlays;

    public FootballGamePlayManager(FootballGame app) {
        super(app);
        _defaultInstance = this;
    }

    // Just default instance not a singleton!!!!
    public static FootballGamePlayManager getDefault() {
        return _defaultInstance;
    }

    private void setupKeys() {
    }

    @Override
    public void startLevel(GameLevel level) {
    }

    @Override
    public void configGamePlay() {
        //FIXME: Load real data of the leagues!
        createTestLeague();
    }

    public void createTestLeague() {
        league = new League("Champion League");
        league.fillRandomClub();
        league.scheduleSeason();
    }

    @Override
    public void loadGamePlay() {
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        currentGamePlay.updateGamePlay(tpf);
    }

    public void startGamePlay() {
        //FIXME: Change the way gameplay start
        gpMatch = new GPMatch((FootballGame) app);
        gpCoach = new GPCoach((FootballGame) app);
        gpManager = new GPManager((FootballGame) app);
        gpMatch.league = league;
        gamePlays = new ArrayList(3);
        gamePlays.add(gpCoach);
        gamePlays.add(gpManager);
        gamePlays.add(gpMatch);
        currentGamePlay = gpMatch;
        gpMatch.startGamePlay();
        gpCoach.startGamePlay();
        gpManager.startGamePlay();

    }

    public <T extends BaseGamePlay> T getGamePlay(Class<T> clazz) {
        for (int i = 0; i < gamePlays.size(); i++) {
            if (clazz.isInstance(gamePlays.get(i))) {
                return (T) gamePlays.get(i);
            }
        }
        return null;
    }

    public GPMatch getMatchGamePlay() {
        if (currentGamePlay instanceof GPMatch) {
            return (GPMatch) currentGamePlay;
        } else {
            throw new RuntimeException("You can not get GPMacth this time !");
        }
    }

    public void checkCheat() {

    }

    public League getLeague() {
        return league;
    }

}
