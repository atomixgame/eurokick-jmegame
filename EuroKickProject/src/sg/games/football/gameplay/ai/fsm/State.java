package sg.games.football.gameplay.ai.fsm;

import sg.games.football.gameplay.ai.event.Telegram;

public abstract class State<V> {

    public abstract void enter(V keeper);

    public abstract void execute(V keeper);

    public abstract void exit(V keeper);

    public abstract boolean onMessage(V object, final Telegram t);
}
