package dk.easv.mohammadabd.itunes.GUI.model;

import dk.easv.mohammadabd.itunes.BE.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;

public class Player {
    private List<Song> songs;
    private int currentSongIndex;
    private MediaPlayer mediaPlayer;
    private boolean isPaused;
    private PlayerProgressListener progressListener;

    public interface PlayerProgressListener {
        void onProgress(double currentTimeInSeconds);
    }

    public Player(List<Song> songs) {
        this.songs = songs;
        this.currentSongIndex = 0;
        this.isPaused = false;
    }

    public void setProgressListener(PlayerProgressListener listener) {
        this.progressListener = listener;
    }

    public void playSong() {
        if (currentSongIndex >= 0 && currentSongIndex < songs.size()) {
            playSong(songs.get(currentSongIndex));
        } else {
            System.out.println("No song to play.");
        }
    }

    public void playSong(Song song) {
        if (song == null || song.getFilePath() == null || song.getFilePath().isEmpty()) {
            System.out.println("No valid song selected.");
            return;
        }

        String songPath = "music/" + song.getFilePath();
        URL resource = getClass().getClassLoader().getResource(songPath);

        if (resource == null) {
            System.out.println("File not found in resources: " + songPath);
            return;
        }

        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            }

            Media media = new Media(resource.toString());
            mediaPlayer = new MediaPlayer(media);
            isPaused = false;

            System.out.println("Now playing: " + song.getTitle() + " by " + song.getArtist());
            System.out.println("Duration: " + song.getDuration() + " seconds");

            mediaPlayer.setOnEndOfMedia(this::playNextSong);
            mediaPlayer.setOnPlaying(() -> System.out.println("Song is playing..."));
            mediaPlayer.setOnError(() -> {
                System.out.println("Error with MediaPlayer: " + mediaPlayer.getError());
            });

            mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                if (progressListener != null) {
                    progressListener.onProgress(newValue.toSeconds());
                }
            });

            mediaPlayer.play();
        } catch (Exception e) {
            System.out.println("Error playing song: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public double getCurrentSongDuration() {
        if (mediaPlayer != null && mediaPlayer.getMedia() != null) {
            return mediaPlayer.getMedia().getDuration().toSeconds();
        }
        return 0;
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            isPaused = true;
            System.out.println("Song is paused.");
        } else {
            System.out.println("No song is currently playing or already paused.");
        }
    }

    public void resume() {
        if (mediaPlayer != null && isPaused) {
            mediaPlayer.play();
            isPaused = false;
            System.out.println("Resuming playback...");
        } else {
            System.out.println("No paused song to resume.");
        }
    }

    public void skipForward() {
        if (mediaPlayer != null) {
            double newPosition = mediaPlayer.getCurrentTime().toSeconds() + 15;
            mediaPlayer.seek(Duration.seconds(newPosition));
            System.out.println("Skipped forward. Current position: " + newPosition + " seconds.");
        }
    }

    public void skipBackward() {
        if (mediaPlayer != null) {
            double newPosition = mediaPlayer.getCurrentTime().toSeconds() - 15;
            if (newPosition < 0) newPosition = 0;
            mediaPlayer.seek(Duration.seconds(newPosition));
            System.out.println("Skipped backward. Current position: " + newPosition + " seconds.");
        }
    }

    public void playNextSong() {
        if (currentSongIndex < songs.size() - 1) {
            currentSongIndex++;
            playSong();
        } else {
            System.out.println("This is the last song.");
        }
    }

    public void playPreviousSong() {
        if (currentSongIndex > 0) {
            currentSongIndex--;
            playSong();
        } else {
            System.out.println("This is the first song.");
        }
    }

    public void seekTo(double timeInSeconds) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(Duration.seconds(timeInSeconds));
        }
    }
}
