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
                    try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) { // PreparedStatement sicuro a sqlinjection
                        pstmt.setString(1, playlistName);
                        pstmt.executeUpdate();
                        System.out.println("Playlist '" + playlistName + "' creata.");
                    } catch (SQLException e) {
                        System.out.println("Errore inserimento playlist: " + e.getMessage());
                    }

                } catch (SQLException e) {
                    System.out.println("Errore accesso al db: " + e.getMessage());
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
                            path TEXT NOT NULL UNIQUE,
                            playlist TEXT NOT NULL
                        );
                    """);

            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS playlists (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            name TEXT NOT NULL UNIQUE
                        );
                    """);

            System.out.println("Tabelle create.");

        } catch (SQLException e) {
            System.out.println("Errore creazione tabelle: " + e.getMessage());
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
                        Playlist PlName = new Playlist(rs.getString("id"), rs.getString("name"));
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


    public static List<Song> fetchSongs(String plName) {
        File dbFile = new File("db/data.db");
        String url = "jdbc:sqlite:db/data.db";
        List<Song> songs = new ArrayList<>();
    
        try {
            if (dbFile.exists()) {
                String sql = "SELECT id, title, path FROM songs WHERE playlist = ?";
    
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

}
