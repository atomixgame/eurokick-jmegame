package sg.games.football.gameplay.ai.steering;

/**
 *
 * @author cuongnguyen
 */
public enum BehaviorType {

    none(0x0000), seek(0x0001), arrive(0x0002), separation(0x0004), pursuit(0x0008), interpose(0x0010);
    int value;

    BehaviorType(int value) {
        this.value = value;
    }

}
