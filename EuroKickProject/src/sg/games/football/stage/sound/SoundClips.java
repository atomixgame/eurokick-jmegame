package sg.games.football.stage.sound;

public enum SoundClips {

    STADIUM_BACKGROUND("background/background.wav", true),
    MUSIC("Music/EndlessSummerSingleInstrumental.ogg", true);

    private final String path;
    private final boolean music;

    SoundClips(String filename, boolean isMusic) {
        path = "Sounds/" + filename;
        music = isMusic;
    }

    public String path() {
        return path;
    }

    public boolean isMusic() {
        return music;
    }
}
