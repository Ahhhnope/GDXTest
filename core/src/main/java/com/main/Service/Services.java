package com.main.Service;

import java.sql.*;
import java.time.LocalDate;

public class Services {
    static String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=GameKYS;user=sa;password=123;trustServerCertificate=true";
    static String getAllquery = "select * from matches";
    static String addMatch = "insert into Matches values (?, ?, ?, ?)";
    static String addPlayer = "insert into Player values (?, ?)";

    static String[] matches;
    public boolean getAll() {
        try (Connection con = DriverManager.getConnection(connectionUrl); PreparedStatement stmt = con.prepareStatement(getAllquery)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
//                god no
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean addPlayer(String name) {

        return true;
    }

    public boolean addMatch(int level, float time, int playerID, String date) {
        try (Connection con = DriverManager.getConnection(connectionUrl); PreparedStatement stmt = con.prepareStatement(addMatch)) {

            Time timeBeLike = new Time((long) (time * 1000));
            Date dateBeLike = Date.valueOf(date);

            stmt.setInt(1, level);
            stmt.setTime(2, timeBeLike);
            stmt.setDate(3, dateBeLike);
            stmt.setInt(4, playerID);

            int rs = stmt.executeUpdate();

            if (rs > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public int runStuff() {
        try (Connection con = DriverManager.getConnection(connectionUrl); PreparedStatement stmt = con.prepareStatement("Select * from Matches where MatchID = 1")) {


            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int rss = rs.getInt("PlayerID");
                return rss;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
