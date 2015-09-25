package sg.games.football.gameplay;

/**
 *
 * @author cuong.nguyenmanh2
 */
public enum PlayerRole {

    GoalKeeper, Attacker, Striker, Middler, Defender;

    public PlayerRole byIndex(int i) {
        return (PlayerRole) values()[i];
    }

    public String toShortString() {
        return "";
    }
}
