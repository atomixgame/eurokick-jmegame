package sg.games.football.gameplay;

/**
 *
 * @author hungcuong
 */
public class Rules {

    // Chance
    public static float CHANCE_SCORE = 0.4f;
    static float DISTANCE_NEAR = 4;

    // Feild
    // Range
    boolean isScored() {
        return true;

    }

    boolean isFault() {
        return true;
    }

    boolean isBallInFeild() {
        return true;
    }

    // Luat bi duoi hon 6 cau thu
    boolean continueAfterFault(FootballMatch aMatch) {
        //if (team.)
        return true;
    }
}
