package sql;

import com.github.javafaker.Faker;
import config.Constants;

import java.sql.*;
import java.util.Locale;
import java.util.UUID;

public class SqlQuery {
    public String getValidCode() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String queryStr = "SELECT code FROM auth_codes order by created desc limit 1";

            conn = DriverManager.getConnection(Constants.JDBC_URL, Constants.DB_USER, Constants.DB_PASSWORD);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(queryStr);
            while (rs.next()) {
                String getCode = rs.getString(1);
                System.out.println("returnCode = " + getCode);
                return getCode;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
        return null;
    }

    public static String generateLogin() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().username();
    }

    public void addUser() {
        Connection conn = null;
        PreparedStatement prstmt = null;
        try {
            String queryInsertNewUser = "INSERT INTO users VALUES (?,?,?,?);";

            conn = DriverManager.getConnection(Constants.JDBC_URL, Constants.DB_USER, Constants.DB_PASSWORD);
            prstmt = conn.prepareStatement(queryInsertNewUser);
            prstmt.setString(1, String.valueOf(UUID.randomUUID()));
            prstmt.setString(2, generateLogin());
            prstmt.setString(3, Constants.HASH_PASSWORD_DB);
            prstmt.setString(4, Constants.STATUS_USERS_DB);
            prstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (prstmt != null) {
                try {
                    prstmt.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

    public String getRandomLoginUser() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String queryStr = "SELECT login FROM users WHERE login NOT IN ('petya', 'vasya') order by RAND() desc limit 1";

            conn = DriverManager.getConnection(Constants.JDBC_URL, Constants.DB_USER, Constants.DB_PASSWORD);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(queryStr);
            while (rs.next()) {
                String getLogin = rs.getString(1);
                System.out.println("getLogin = " + getLogin);
                return getLogin;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
        return null;
    }

    public void truncateTable() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(Constants.JDBC_URL, Constants.DB_USER, Constants.DB_PASSWORD);
            stmt = conn.createStatement();
            int truncateCode = stmt.executeUpdate("TRUNCATE auth_codes;");
            int truncateCards = stmt.executeUpdate("TRUNCATE cards;");
            int truncateTransactions = stmt.executeUpdate("TRUNCATE card_transactions;");
            int truncateUsers = stmt.executeUpdate("DELETE FROM users;");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
    }

}