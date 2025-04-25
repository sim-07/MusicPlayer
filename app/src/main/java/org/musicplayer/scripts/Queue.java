package org.musicplayer.scripts;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Queue {

    List<Song> queue = new LinkedList<>();
    private int currSongIndex = 0;

    public Queue(String plName, boolean casual) {
        queue.addAll(ManagePlaylist.fetchSongs(plName));
        if (casual) {
            Collections.shuffle(queue);
        }
    }

    public List<Song> getQueue() {
        return queue;
    }

    public Song getCurrentSong() {
        System.out.println(currSongIndex);
        return queue.get(currSongIndex);
    }

    public Song getPreviousSong() {
        if (currSongIndex - 1 >= 0) {
            currSongIndex--;
            return queue.get(currSongIndex);
        }
        return null;
    }

    public Song getNextSong() {
        if (currSongIndex + 1 < queue.size()) {
            currSongIndex++;
            return queue.get(currSongIndex);
        } else {
            currSongIndex = 0;
            return queue.get(currSongIndex);
        }
    }

}
