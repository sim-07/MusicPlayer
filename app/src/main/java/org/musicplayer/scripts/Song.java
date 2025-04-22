package org.musicplayer.scripts;

public class Song {
    public String id;
    public String name;
    public String path;

    public Song(String id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

}
