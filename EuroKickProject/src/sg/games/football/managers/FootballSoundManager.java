package sg.games.football.managers;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioRenderer;
import java.util.ArrayList;
import java.util.EnumMap;
import sg.atom.stage.sound.SoundClip;
import sg.games.football.FootballGame;
import sg.games.football.stage.sound.SoundClips;

public class FootballSoundManager {

//    private EnumMap<SoundClips, AudioNode> soundMap;
//    private FootballGame app;
//    private AudioRenderer audioRenderer;
//    private AssetManager assetManager;
//
//    public FootballSoundManager(FootballGame app) {
//        this.app = app;
//        audioRenderer = app.getAudioRenderer();
//        assetManager = app.getAssetManager();
//        soundMap = new EnumMap<SoundClips, AudioNode>(SoundClip.class);
//    }
//
//    public void load(int level) {
//        if (level == 1) {
//
//        }
//    }
//
//    // loads all sound effects which will be needed for that level
//    private void loadSoundEffects(SoundClips[] sounds) {
//
//        for (SoundClips s : sounds) {
//            AudioNode soundNode = new AudioNode(assetManager, s.path());
//            soundMap.put(s, soundNode);
//        }
//    }
//
//    // load all music which will be streamed
//    public void loadMusic(SoundClip[] music) {
//
//        for (SoundClips s : music) {
//            if (s != null) {
//                AudioNode musicNode = new AudioNode(assetManager, s.path(), true);
//                musicNode.setLooping(true);
//                musicNode.setPositional(false);
//                musicNode.setDirectional(false);
//
//                soundMap.put(s, musicNode);
//            }
//        }
//    }
//
//    public void unloadMusic(SoundClip[] music) {
//        for (SoundClips s : music) {
//            soundMap.remove(s);
//        }
//    }
//
//    public void unloadAllMusic() {
//        ArrayList<SoundClips> musicList = new ArrayList<SoundClips>();
//
//        for (SoundClip s : soundMap.keySet()) {
//            if (soundMap.get(s).isLooping()) {
//                musicList.add(s);
//            }
//        }
//
//        for (SoundClips soundClip : musicList) {
//            soundMap.get(soundClip).stop();
//            soundMap.remove(soundClip);
//        }
//    }
//
//    public void play(SoundClips sound) {
//        this.play(sound, 1f);
//    }
//
//    public void play(SoundClips sound, float startVolume) {
//        AudioNode toPlay = soundMap.get(sound);
//
//        if (toPlay != null) {
//            if (sound.isMusic()) {
//                /*
//                 if (app.getUserSettings().isMusicMuted()) {
//                 return;
//                 }
//                 */
//                toPlay.setVolume(startVolume);
//                toPlay.play();
//            } else {
//                /*
//                 if (app.getUserSettings().isSoundFXMuted()) {
//                 return;
//                 }
//                 */
//                toPlay.setVolume(startVolume);
//                toPlay.playInstance();
//            }
//        }
//    }
//
//    // pause the music
//    public void pause(SoundClip sound) {
//
//        AudioNode toPause = soundMap.get(sound);
//
//        if (toPause != null) {
//            audioRenderer.pauseSource(toPause);
//        }
//    }
//
//    // if paused it will play, if playing it will be paused
//    public void togglePlayPause(SoundClip sound) {
//        AudioNode toToggle = soundMap.get(sound);
//
//        if (toToggle != null) {
//            if (toToggle.getStatus() == AudioNode.Status.Paused
//                    || toToggle.getStatus() == AudioNode.Status.Stopped) {
//                play(sound);
//            } else {
//                pause(sound);
//            }
//        }
//    }
//
//    // tries to stop a sound, will probably only work for streaming music though
//    void stop(SoundClip sound) {
//        AudioNode toStop = soundMap.get(sound);
//
//        toStop.stop();
//    }
//
//    public void cleanup() {
//        unloadAllMusic();
//        soundMap.clear();
//    }
}
