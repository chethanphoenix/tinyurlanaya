package store;

import java.sql.*;

public class DatabaseStore {

    private final String url;
    private final String user;
    private final String password;

    public DatabaseStore(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public void save(String shortCode, String longUrl) {
        String sql = "INSERT INTO url_mapping (short_code, long_url) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, shortCode);
            stmt.setString(2, longUrl);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getLongUrl(String shortCode) {
        String sql = "SELECT long_url FROM url_mapping WHERE short_code = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, shortCode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("long_url");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public String getShortCode(String longUrl) {
        String sql = "SELECT short_code FROM url_mapping WHERE long_url = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, longUrl);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("short_code");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public boolean exists(String shortCode) {
        String sql = "SELECT 1 FROM url_mapping WHERE short_code = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, shortCode);
            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
