package dk.easv.mohammadabd.itunes.GUI.model;

import dk.easv.mohammadabd.itunes.BE.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.util.List;public class Player {
    private List<Song> songs;
    private int currentSongIndex;
    private MediaPlayer mediaPlayer;

    public Player(List<Song> songs) {
        this.songs = songs;
        this.currentSongIndex = 0;
    }

    // Play the current song
    public void playSong() {
        if (currentSongIndex >= 0 && currentSongIndex < songs.size()) {
            Song song = songs.get(currentSongIndex);
            System.out.println("Now playing: " + song.getTitle() + " by " + song.getArtist());
            System.out.println("Duration: " + song.getDuration() + " seconds");

            String songPath = song.getFilePath();
            java.net.URL resource = getClass().getResource("/music/" + songPath);

            if (resource == null) {
                System.out.println("Resource not found: " + songPath);
                return;
            }

            try {
                // Stop the current song if it's playing and create a new MediaPlayer
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }

                Media media = new Media(resource.toString());
                mediaPlayer = new MediaPlayer(media);

                // Set the listener for when the song ends
                mediaPlayer.setOnEndOfMedia(() -> {
                    System.out.println("Song finished.");
                    playNextSong();  // Automatically play the next song
                });

                mediaPlayer.setOnPlaying(() -> {
                    System.out.println("Song is playing...");
                });

                mediaPlayer.setOnError(() -> {
                    System.out.println("Error with MediaPlayer: " + mediaPlayer.getError());
                });

                // Start the song playback
                mediaPlayer.play();
                System.out.println("Song is playing...");
            } catch (Exception e) {
                System.out.println("Error playing song: " + e.getMessage());
            }
        } else {
            System.out.println("No song to play.");
        }
    }

    // Pause the current song
    public void pause() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause(); // Pauses the song
            System.out.println("Song is paused.");
        } else {
            System.out.println("No song is currently playing or already paused.");
        }
    }

    // Skip forward 15 seconds
    public void skipForward() {
        if (mediaPlayer != null) {
            double newPosition = mediaPlayer.getCurrentTime().toSeconds() + 15;
            mediaPlayer.seek(javafx.util.Duration.seconds(newPosition));
            System.out.println("Skipped forward. Current position: " + newPosition + " seconds.");
        }
    }

    // Skip backward 15 seconds
    public void skipBackward() {
        if (mediaPlayer != null) {
            double newPosition = mediaPlayer.getCurrentTime().toSeconds() - 15;
            if (newPosition < 0) newPosition = 0;
            mediaPlayer.seek(javafx.util.Duration.seconds(newPosition));
            System.out.println("Skipped backward. Current position: " + newPosition + " seconds.");
        }
    }

    // Play the next song
    public void playNextSong() {
        if (currentSongIndex < songs.size() - 1) {
            currentSongIndex++;

            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }

            playSong();  // Start playing the next song
        } else {
            System.out.println("This is the last song.");
        }
    }

    // Play the previous song
    public void playPreviousSong() {
        if (currentSongIndex > 0) {
            currentSongIndex--;

            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }


            playSong();  // Start playing the previous song
        } else {
            System.out.println("This is the first song.");
        }
    }
}
