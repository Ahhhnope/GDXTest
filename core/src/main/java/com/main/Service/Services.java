package com.main.Service;

import com.main.MatchModel;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.TimeZone;

public class Services {
    static String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=GameKYS;user=sa;password=123;trustServerCertificate=true";
    static String getAllquery = "Select * from Matches order by Score desc";
    static String addMatch = "insert into Matches values (?, ?, ?, ?)";
    static String addPlayer = "insert into Player values (?, ?)";

    static String[] matches;
    public ArrayList<MatchModel> getAllHighScore() {
        ArrayList<MatchModel> list = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(connectionUrl); PreparedStatement stmt = con.prepareStatement(getAllquery)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                list.add(new MatchModel(
                    rs.getTime("TimePlayed").toString(),
                    rs.getDate("DateAchieved").toString(),
                    rs.getFloat("Score")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean addPlayer(String name) {

        return true;
    }

    public boolean addMatch(int level, int score, float time, String date) {
        try (Connection con = DriverManager.getConnection(connectionUrl); PreparedStatement stmt = con.prepareStatement(addMatch)) {

            Time timeBeLike = new Time(((long) (time * 1000)) - TimeZone.getDefault().getRawOffset());

            Date dateBeLike = Date.valueOf(date);

            stmt.setInt(1, level);
            stmt.setInt(2, score);
            stmt.setTime(3, timeBeLike);
            stmt.setDate(4, dateBeLike);

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
                int rss = rs.getInt("Score");
                return rss;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
