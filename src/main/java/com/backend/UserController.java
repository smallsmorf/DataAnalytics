package com.backend;

import com.UserExistsException;
import com.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class UserController extends ModelController {

    public boolean addUser(String username, String first_name, String last_name, String password) {
        String sql = "INSERT INTO users(username,first_name,last_name,password) VALUES(?,?,?,?)";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, first_name);
            pstmt.setString(3, last_name);
            pstmt.setString(4, password);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean isUsernameTaken(String username) {
        String sql = "SELECT username FROM users WHERE username = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1, username);
            //
            ResultSet rs  = pstmt.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public int tryLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = this.connect();
            PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1, username);
            //
            ResultSet rs  = pstmt.executeQuery();

            if (rs.next()) {
                String passwordFromDB = rs.getString("password");
                if (passwordFromDB.equals(password)) {
                    User user = new User(username, rs.getString("first_name"), rs.getString("last_name"),
                            password, rs.getInt("is_vip") == 0 ? false : true);
                    SessionManager.getInstance().setUser(user);
                    return 0;
                }
                else {
                    return 1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 2;
    }

    public boolean upgradeToVip(String username){
        String sql = "UPDATE users SET is_vip = ? WHERE username = ?";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 1);
            pstmt.setString(2, username);
            System.out.println(username);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private boolean existsUser(String username){
        if (SessionManager.getInstance().getUser().getUsername().equals(username)) return false;

        String sql = "SELECT * FROM users where username = ?";
        try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
            // set the value
            pstmt.setString(1, username);
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return true;
        }
        return false;
    }
    public boolean updateProfile(String firstname, String lastname, String username, String password)
            throws UserExistsException {
        if (existsUser(username)) throw new UserExistsException();

        String sql = "UPDATE users SET first_name = ?, last_name = ?, username = ?, password = ? WHERE username = ?";
        try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
            // set the value
            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            pstmt.setString(5, SessionManager.getInstance().getUser().getUsername());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?;";
        try (Connection conn = this.connect(); PreparedStatement pstmt  = conn.prepareStatement(sql)){
            // set the value
            pstmt.setString(1, username);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
