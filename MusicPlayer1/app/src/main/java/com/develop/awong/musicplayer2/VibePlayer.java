package com.develop.awong.musicplayer2;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.Person;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Created by AWong on 3/4/18.
 */

public class VibePlayer implements ModePlayer {
    private Location currentLoc;
    private List<Song> fireBaseSongs;
    private HashMap<String,Song> allSongs;
    HashMap<String,Song> playListMap;
    private PriorityQueue<Song> playlist;
    Calendar currentCalendar;
    private FirebaseManager firebaseManager;
    private GPSTracker gps;
    ListPlayer listPlayer;
    Context cont;


    public VibePlayer(Location loc, Context context) {
        this.playlist = new PriorityQueue<Song>(11, new PriorityComparator());
        this.currentLoc = loc;
        this.currentCalendar = Calendar.getInstance();
        this.firebaseManager = new FirebaseManager();
        this.fireBaseSongs = new ArrayList<>();
        playListMap = new HashMap<>();
        this.cont = context;

    }




    public void populatePlaylist() {
        System.out.println("firebase songs size " + fireBaseSongs.size());




        /* this for-loop would determine what songs in the allSongs list is a
           candidate for the playlist in flashback mode*/

        for (int i = 0; i < fireBaseSongs.size(); i++) {
            Song curSong = fireBaseSongs.get(i);

            System.out.println("songs name " + curSong.getSongTitle());
            int score = 0;


            // location score
            Location curSongLocation =  new Location("");
            curSongLocation.setLatitude(curSong.getLatitude());
            curSongLocation.setLongitude(curSong.getLongitude());

            if (curSongLocation == null)
            {
                System.out.println("cursong location is null" );
                continue;
            }
            if (currentLoc.distanceTo(curSongLocation) <= 300) {
                score += 3001;
                System.out.println("loc score get!");
            }


            // time score
            long diff = Math.abs(currentCalendar.getTimeInMillis() - Long.parseLong(curSong.getTimeInMillis()));
            long days = diff / (24 * 60 * 60 * 1000);
            if (days < 8) {
                score += 3000;
                System.out.println("time score get!");
            }

            // friend score
            for (Person friend: LoginActivity.friends) {
                List<EmailAddress> emails = friend.getEmailAddresses();
                System.out.println("email size here" + emails.size());
                for (EmailAddress email : emails) {
                    String friendEmail = email.getValue();
                    System.out.println("friend email: " + friendEmail);
                    if (curSong.getUserEmail().equalsIgnoreCase(friendEmail)) {
                        score += 2999;
                        System.out.println("time score get!");
                    }
                    else if (curSong.getUserEmail().equalsIgnoreCase(LoginActivity.personEmail)) {
                        curSong.setUser("You");
                    }
                    else {
                        NameGenerator generator = new NameGenerator();
                        String cuteName = generator.generateName(curSong.getUserEmail());
                        curSong.setUser(cuteName);
                    }
                }
            }

            System.out.println("song title " + curSong.getSongTitle() + " scores: "+ score);
            playlist.add(curSong);
            curSong.setScore(score);


            //remove duplicate
            if (playListMap.containsKey(curSong.getSongTitle())) {
                if (curSong.getScore() < playListMap.get(curSong.getSongTitle()).getScore()) {
                    continue;
                }
            }

            if(MenuActivity.songMap.containsKey(curSong.getSongTitle())) {
                byte[] bitmap = MenuActivity.songMap.get(curSong.getSongTitle()).getAlbumArt();
                curSong.setAlbumArt(bitmap);
            }

            playListMap.put(curSong.getSongTitle(), curSong);
            MenuActivity.songMap.put(curSong.getSongTitle(), curSong);
        }

        System.out.println(playListMap.size());

        // just test print
        for (Map.Entry<String,Song> entry : playListMap.entrySet()) {
            String key = entry.getKey();
            int score = entry.getValue().getScore();
            System.out.println("title and score pair: " + key + "  " + score +"  " + entry.getValue().getUserEmail());


        }


        List<String> trackList = new ArrayList<>(playListMap.keySet());
        MenuActivity.currentTrackList = trackList;


        ListPlayer listPlayer = new ListPlayer(trackList, cont);
        listPlayer.playCurrentList();


        System.out.println("Current tracklist in Vibe Player size is " + trackList.size());
        //startPlay();
    }



    public void setFireBaseSongs(List<Song> fireBaseSongs) {
        this.fireBaseSongs = fireBaseSongs;
    }

    //getter for playlist
    public PriorityQueue<Song> getPlaylist(){
        return playlist;
    }

}
