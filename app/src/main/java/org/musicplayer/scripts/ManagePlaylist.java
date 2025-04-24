package org.musicplayer.scripts;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ManagePlaylist {

    public static void createPlaylist(String playlistName) {
        File dbFile = new File("db/data.db");
        String url = "jdbc:sqlite:db/data.db";

        try {
            if (dbFile.exists()) {

                try (Connection conn = DriverManager.getConnection(url)) { // conn si chiude in automatico dopo aver finito

                    String insertSQL = "INSERT INTO playlists (name) VALUES (?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                        pstmt.setString(1, playlistName);
                        pstmt.executeUpdate();
                        System.out.println("Playlist '" + playlistName + "' creata.");
                    } catch (SQLException e) {
                        System.out.println("Errore createPlaylist in: " + e.getMessage());
                    }

                } catch (SQLException e) {
                    System.out.println("Errore db: " + e.getMessage());
                }
            } else {
                createDatabase();
            }

        } catch (Exception e) {
            System.out.println("Errore createPlaylist: " + e.getMessage());
        }
    }

    private static void createDatabase() {
        String url = "jdbc:sqlite:db/data.db";

        new File("db").mkdirs();

        try (Statement stmt = DriverManager.getConnection(url).createStatement()) {

            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS songs (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            title TEXT NOT NULL,
                            path TEXT NOT NULL UNIQUE
                        );
                    """);

            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS playlists (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            name TEXT NOT NULL UNIQUE
                        );
                    """);

            // collego songs e playlists. Se elimino un dato in una si elimina anche nelle altre
            stmt.execute("""
                        CREATE TABLE songs_playlist (
                            song_id INTEGER,
                            playlist_id INTEGER,
                            FOREIGN KEY (song_id) REFERENCES songs(id) ON DELETE CASCADE,
                            FOREIGN KEY (playlist_id) REFERENCES playlists(id) ON DELETE CASCADE,
                            PRIMARY KEY (song_id, playlist_id)
                        );
                    """);

            System.out.println("Tabelle create.");

        } catch (SQLException e) {
            System.out.println("Errore createDatabase: " + e.getMessage());
        }
    }

    public static List<Playlist> fetchAllPlaylist() {
        File dbFile = new File("db/data.db");
        String url = "jdbc:sqlite:db/data.db";
        List<Playlist> playlists = new ArrayList<>();

        try {
            if (dbFile.exists()) {
                String sql = "SELECT * FROM playlists";

                try (Connection conn = DriverManager.getConnection(url);
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(sql)) {

                    while (rs.next()) {
                        Playlist PlName = new Playlist(rs.getInt("id"), rs.getString("name"));
                        playlists.add(PlName);
                    }

                }
            } else {
                createDatabase();
            }

        } catch (Exception e) {
            System.out.println("Errore fetchAllPlaylist: " + e.getMessage());
        }

        return playlists;
    }

    public static void deletePlaylist(int playlistId) {
        File dbFile = new File("db/data.db");
        String url = "jdbc:sqlite:db/data.db";

        try {
            if (dbFile.exists()) {
                try (Connection conn = DriverManager.getConnection(url)) {
                    // per eliminare in automatico anche le righe in songs_playlist
                    try (Statement pragma = conn.createStatement()) {
                        pragma.execute("PRAGMA foreign_keys = ON;");
                    }

                    String deleteSQL = "DELETE FROM playlists WHERE id = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
                        pstmt.setInt(1, playlistId);
                        int affected = pstmt.executeUpdate();
                        System.out.println("Eliminate " + affected + " playlist con id=" + playlistId);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Errore deletePlaylist: " + e.getMessage());
        }
    }

    public static List<Song> fetchSongs(String plName) {
        File dbFile = new File("db/data.db");
        String url = "jdbc:sqlite:db/data.db";
        List<Song> songs = new ArrayList<>();

        try {
            if (dbFile.exists()) {
                // seleziono titolo, path e id in base alla tabella intermedia
                String sql = "SELECT songs.id, songs.title, songs.path FROM songs JOIN songs_playlist ON songs.id = songs_playlist.song_id JOIN playlists ON playlists.id = songs_playlist.playlist_id WHERE playlists.name = ?";

                try (Connection conn = DriverManager.getConnection(url);
                        PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, plName);

                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            String id = rs.getString("id");
                            String title = rs.getString("title");
                            String path = rs.getString("path");

                            songs.add(new Song(id, title, path));
                        }
                    }
                }
            } else {
                createDatabase();
            }

        } catch (Exception e) {
            System.out.println("Errore fetchAllSongs: " + e.getMessage());
        }

        return songs;
    }

    public static void addSongsToPlaylist(String songName, String path, String plName) {
        File dbFile = new File("db/data.db");
        String url = "jdbc:sqlite:db/data.db";

        try {
            if (dbFile.exists()) {

                try (Connection conn = DriverManager.getConnection(url)) {

                    // cerco id canzone
                    String fetchExSongs = "SELECT id FROM songs WHERE title = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(fetchExSongs)) {
                        pstmt.setString(1, songName);

                        try (ResultSet rs = pstmt.executeQuery()) {

                            int songId = 0;
                            if (rs.next()) {
                                songId = rs.getInt("id");
                            } else {
                                // la canzone non c'è ancora, la aggiungo a songs e prendo l'id

                                String insertSongSQL = "INSERT INTO songs (title, path) VALUES (?, ?)";
                                try (PreparedStatement insertSongPstmt = conn.prepareStatement(insertSongSQL,
                                        Statement.RETURN_GENERATED_KEYS)) {
                                    insertSongPstmt.setString(1, songName);
                                    insertSongPstmt.setString(2, path);
                                    insertSongPstmt.executeUpdate();

                                    try (ResultSet generatedKeys = insertSongPstmt.getGeneratedKeys()) {
                                        if (generatedKeys.next()) {
                                            songId = generatedKeys.getInt(1); // id appena generato
                                        }
                                    }
                                }
                            }

                            // cerco id playlist in base al nome passato
                            String fetchPlId = "SELECT id FROM playlists WHERE name = ?";
                            try (PreparedStatement playlistPstmt = conn.prepareStatement(fetchPlId)) {
                                playlistPstmt.setString(1, plName);
                                playlistPstmt.executeQuery();

                                try (ResultSet playlistResult = playlistPstmt.executeQuery()) {
                                    if (playlistResult.next()) {
                                        int plId = playlistResult.getInt("id");

                                        // inserisco playlist id e song id
                                        String addExSong = "INSERT INTO songs_playlist (song_id, playlist_id) VALUES (?, ?)";
                                        try (PreparedStatement addSongPlPstmt = conn.prepareStatement(addExSong)) {
                                            addSongPlPstmt.setInt(1, songId);
                                            addSongPlPstmt.setInt(2, plId);
                                            addSongPlPstmt.executeUpdate();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println("Errore addSongs in1: " + e.getMessage());
                    }

                } catch (SQLException e) {
                    System.out.println("Errore addSongs in2: " + e.getMessage());
                }

            }
        } catch (Exception e) {
            System.out.println("Errore addSongs es: " + e.getMessage());
        }

    }
}
