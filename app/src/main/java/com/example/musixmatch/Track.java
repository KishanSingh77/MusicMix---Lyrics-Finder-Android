package com.example.musixmatch;

public class Track {

    public Track( ) {

    }

    String  track_name ;
    String  album_name ;
    String   artist_name ;
    String   updated_time ;
    String   track_share_url;

    @Override
    public String toString() {
        return "Track{" +
                "track_name='" + track_name + '\'' +
                ", album_name='" + album_name + '\'' +
                ", artist_name='" + artist_name + '\'' +
                ", updated_time='" + updated_time + '\'' +
                ", track_share_url='" + track_share_url + '\'' +
                '}';
    }


}
