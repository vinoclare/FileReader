package com.example.demo.Tools;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCTest {
    public static void main(String[] args) throws Exception {
        List<Weapons> weapons = queryWeapons();
        weapons.forEach((wp) -> wp.getRDF());
    }

    static List<Weapons> queryWeapons() throws SQLException {
        List<Weapons> weapons = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)) {
            try (PreparedStatement ps = conn
                    .prepareStatement("SELECT * FROM weapons WHERE predict = ?")) {
                ps.setString(1, "类型");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        weapons.add(extractRow(rs));
                    }
                }
            }
        }
        return weapons;
    }

    static Weapons extractRow(ResultSet rs) throws SQLException {
        Weapons wp = new Weapons();
        wp.setSubject(rs.getString("subject"));
        wp.setPredict(rs.getString("predict"));
        wp.setObject(rs.getString("object"));
        return wp;
    }

    //static final String jdbcUrl = "jdbc:mysql://localhost/learnjdbc?useSSL=false&characterEncoding=utf8";
    static final String jdbcUrl = "jdbc:mysql://localhost/military?useSSL=false&allowPublicKeyRetrieval=true&useSSL=false&characterEncoding=utf8";
    static final String jdbcUsername = "root";
    static final String jdbcPassword = "123456";

}
