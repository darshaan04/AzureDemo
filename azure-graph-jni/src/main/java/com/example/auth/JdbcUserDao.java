package com.example.auth;

import java.sql.*;
import java.util.Optional;
import java.util.Properties;
import java.io.InputStream;

public class JdbcUserDao {
private final String url;
private final String user;
private final String pass;

public JdbcUserDao(Properties cfg) {
this.url = cfg.getProperty("db.url");
this.user = cfg.getProperty("db.user");
this.pass = cfg.getProperty("db.pass");
}

private Connection getConn() throws SQLException {
return DriverManager.getConnection(url, user, pass);
}


public Optional<User> findByUsername(String username) throws SQLException {
try (Connection c = getConn();
PreparedStatement ps = c.prepareStatement("SELECT id, username, password_hash, role FROM users WHERE username = ?")) {
ps.setString(1, username);
try (ResultSet rs = ps.executeQuery()) {
if (rs.next()) {
User u = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password_hash"), rs.getString("role"));
return Optional.of(u);
}
}
}
return Optional.empty();
}


public void insert(String username, String passwordHash, String role) throws SQLException {
try (Connection c = getConn();
PreparedStatement ps = c.prepareStatement("INSERT INTO users (username, password_hash, role) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
ps.setString(1, username);
ps.setString(2, passwordHash);
ps.setString(3, role);
ps.executeUpdate();
}
}


public static class User {
public final int id;
public final String username;
public final String passwordHash;
public final String role;


public User(int id, String username, String passwordHash, String role) {
this.id = id; this.username = username; this.passwordHash = passwordHash; this.role = role;
}
}

}
