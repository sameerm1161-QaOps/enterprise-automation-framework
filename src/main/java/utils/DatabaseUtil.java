package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseUtil {

    private static final Logger log = LoggerFactory.getLogger(DatabaseUtil.class);
    private Connection connection;

    public void connect(String url, String username, String password) {
        try {
            connection = DriverManager.getConnection(url, username, password);
            log.info("DB connected: {}", url);
        } catch (SQLException e) {
            throw new RuntimeException("DB connection failed: " + e.getMessage());
        }
    }

    public void connectH2() {
        connect("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
    }

    public void executeUpdate(String sql) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            log.info("Executed: {}", sql);
        } catch (SQLException e) {
            throw new RuntimeException("SQL failed: " + e.getMessage());
        }
    }

    public List<Map<String, Object>> executeQuery(String sql) {
        List<Map<String, Object>> results = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= cols; i++) {
                    row.put(meta.getColumnName(i), rs.getObject(i));
                }
                results.add(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Query failed: " + e.getMessage());
        }
        return results;
    }

    public int getCount(String tableName) {
        List<Map<String, Object>> result = executeQuery(
                "SELECT COUNT(*) as COUNT FROM " + tableName
        );
        return ((Number) result.get(0).get("COUNT")).intValue();
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                log.info("DB disconnected");
            }
        } catch (SQLException e) {
            log.error("Disconnect failed: {}", e.getMessage());
        }
    }
}