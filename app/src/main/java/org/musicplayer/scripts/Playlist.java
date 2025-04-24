package org.musicplayer.scripts;

public class Playlist {
    public int id;
    public String name;

    public Playlist(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

}
