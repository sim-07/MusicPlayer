package org.musicplayer.scripts;

public class Playlist {
    public String id;
    public String name;

    public Playlist(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
