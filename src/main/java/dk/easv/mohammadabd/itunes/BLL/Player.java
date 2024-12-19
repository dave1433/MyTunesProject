package dk.easv.mohammadabd.itunes.BLL;


import dk.easv.mohammadabd.itunes.BE.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

    public class Player {
        private Song currentSong;
        private MediaPlayer mediaPlayer;

        public void setCurrentSong(Song song){
            this.currentSong=song;
            System.out.println("Set current song to: " + song.getTitle() + " by " + song.getArtist());
        }
        public void play(Song song){
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            this.currentSong = song;
            Media media = new Media(new File(song.getFilePath()).toURI().toString()); // Create Media from file path
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play(); // Start music
            System.out.println("Playing: " + song.getTitle() + " by " + song.getArtist());    }

        public void pause() {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
                System.out.println("Paused: " + currentSong.getTitle());
            } else {
                System.out.println("No song selected");
            }
        }
        public void stop() {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer=null;
                currentSong = null;
            } else {
                System.out.println("No song selected");
            }
        }

        public void playNext() {
            System.out.println(" blablabla");
        }
    }


