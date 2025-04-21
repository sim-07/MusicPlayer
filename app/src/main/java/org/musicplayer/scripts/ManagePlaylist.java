package org.musicplayer.scripts;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ManagePlaylist {

    public static void createPlaylist(String playlistName) {
        File dbFile = new File("db/playlists.db");
        String url = "jdbc:sqlite:db/playlists.db";

        try {
            if (dbFile.exists()) {

                try (Connection conn = DriverManager.getConnection(url)) {

                    String insertSQL = "INSERT INTO playlists (name) VALUES (?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) { // PreparedStatement sicuro a
                                                                                       // sqlinjection
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
        String url = "jdbc:sqlite:db/playlists.db";

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

            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS song_playlist (
                            song_id INTEGER,
                            playlist_id INTEGER,
                            FOREIGN KEY (song_id) REFERENCES songs(id) ON DELETE CASCADE,
                            FOREIGN KEY (playlist_id) REFERENCES playlists(id) ON DELETE CASCADE,
                            PRIMARY KEY (song_id, playlist_id)
                        );
                    """);

            System.out.println("Tabelle create.");

        } catch (SQLException e) {
            System.out.println("Errore creazione tabelle: " + e.getMessage());
        }
    }

    public static void fetchPlaylist() {
        try {

        } catch (Exception e) {
            System.out.println("Errore fetchPlaylist: " + e.getMessage());
        }
    }

}
