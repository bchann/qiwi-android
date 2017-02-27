package com.example.brianchan.qiwi_android;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Brian Chan on 2/5/2017.
 */

public class FirebaseCalls {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference partyRef = database.getReference("parties");
    private int roomid;
    private String history_id, request_list_id, playlist_id;
    private String dj_list_id;
    private int passcode = 1234;
    private boolean requests_paused = false;
    private String spotifyAuth = "tempspotifyauthkey";
    private static final int passcodeLength = 4;
    private List<Song> history_list, request_list, playlist;

    /**
     * Creates a new party in Firebase and links it with songlists, userlists, and users.
     */
    public FirebaseCalls() {
        //passcode = passcodeGen();
        roomid = passcode;

        //Room references for the different database containers
        DatabaseReference roomRef = partyRef.child("" + roomid);
        DatabaseReference songsRef = database.getReference("songlists");
        DatabaseReference djRef = database.getReference("djlists");

        //Set up the three song lists
        history_id = songsRef.push().getKey();
        songsRef.child(history_id).child("songs").setValue("null");
        songsRef.child(history_id).child("party_id").setValue(roomid);
        songsRef.child(history_id).child("list_type").setValue("history");

        request_list_id = songsRef.push().getKey();
        songsRef.child(request_list_id).child("songs").setValue("null");
        songsRef.child(request_list_id).child("party_id").setValue(roomid);
        songsRef.child(request_list_id).child("list_type").setValue("request");

        playlist_id = songsRef.push().getKey();
        songsRef.child(playlist_id).child("songs").setValue("null");
        songsRef.child(playlist_id).child("party_id").setValue(roomid);
        songsRef.child(playlist_id).child("list_type").setValue("playlist");

        //Set up dj list
        dj_list_id = djRef.push().getKey();
        djRef.child(dj_list_id).child("users").child("dj").setValue(spotifyAuth);
        djRef.child(dj_list_id).child("party_id").setValue(roomid);
        djRef.child(dj_list_id).child("list_type").setValue("dj");

        //Set up party members
        roomRef.child("history_id").setValue(history_id);
        roomRef.child("request_list_id").setValue(request_list_id);
        roomRef.child("playlist_id").setValue(playlist_id);
        roomRef.child("dj_list_id").setValue(dj_list_id);
        roomRef.child("passcode").setValue(this.passcode);
        roomRef.child("requests_paused").setValue(requests_paused);
    }

    /**
     * Generates a random unique 4-digit integer passcode for the room.
     * @return Random unique 4-digit integer passcode.
     */
    private int passcodeGen() {
        final int[] pass = new int[1];

        String passcode = "";

        for (int i = 0; i < passcodeLength; i++) {
            int randint = (int) (Math.random() * 9);
            passcode += randint;
        }

        pass[0] = Integer.parseInt(passcode);

        partyRef.child(passcode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    pass[0] = passcodeGen();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        return pass[0];
    }
}

/**
 * A Song in a song list.
 */
class Song {
    private String song_name; //Name of the song
    private String song_id; //Spotify id of the song
    private String requester; //Person who requested the song

    /**
     * Constructor which just assigns the song attributes.
     * @param song_name Name of the song
     * @param song_id Spotify id of the song
     * @param requester Person who requested the song
     */
    public Song(String song_name, String song_id, String requester) {
        this.song_name = song_name;
        this.song_id = song_id;
        this.requester = requester;
    }
}

/**
 * A User in a user list.
 */
class User {
    private String spotify_key;
    private String username;

    public User(String spotify_key, String username) {
        this.spotify_key = spotify_key;
        this.username = username;
    }
}
