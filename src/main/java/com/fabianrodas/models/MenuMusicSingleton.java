package com.fabianrodas.models;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

/* This class controls the background menu music */

public class MenuMusicSingleton {

    private static MenuMusicSingleton instance;
    private final MediaPlayer musicPlayer;

    private MenuMusicSingleton() {
        URL resource = getClass().getResource("/com/fabianrodas/music/menuLoop.mp3");

        if (resource == null) {
            throw new IllegalArgumentException("menuLoop.mp3 not found");
        }

        Media music = new Media(resource.toExternalForm());
        musicPlayer = new MediaPlayer(music);

        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public static MenuMusicSingleton getInstance() {
        if (instance == null) {
            instance = new MenuMusicSingleton();
        }
        return instance;
    }

    public void startMusic() {
        musicPlayer.play();
    }

    public void stopMusic() {
        musicPlayer.stop();
    }

    public void setVolume(double volume) {
        musicPlayer.setVolume(volume);
    }

    public void setMute(boolean mute) {
        musicPlayer.setMute(mute);
    }
}