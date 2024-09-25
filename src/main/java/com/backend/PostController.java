package com.backend;

import com.models.Post;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostController extends ModelController {

    public boolean addNewPost(int id, String content, int numLikes, int numShares, String datetime) {
        String sql = "INSERT INTO posts(ID,content,author,num_likes,num_shares,datetime) VALUES(?,?,?,?,?,?)";

        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, content);
            pstmt.setString(3, SessionManager.getInstance().getUser().getUsername());
            pstmt.setInt(4, numLikes);
            pstmt.setInt(5, numShares);
            pstmt.setString(6, datetime);
            pstmt.executeUpdate();
            System.out.println("Post added successfully");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean postIDisUnique(int id){
        String sql = "SELECT * FROM posts WHERE ID = ?";

        try (Connection conn = this.connect();
            PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public Post retrievePost(int id) {
        String sql = "SELECT * FROM posts WHERE ID = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            ResultSet rs  = pstmt.executeQuery();
            if (rs.next()) {
                String content = rs.getString("content");
                String author = rs.getString("author");
                int numLikes = rs.getInt("num_likes");
                int numShares = rs.getInt("num_shares");
                String datetime = rs.getString("datetime");
                Post post = new Post(id, content, author, numLikes, numShares, datetime);
                return post;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean deletePost(int id) {
        String sql = "DELETE FROM posts where ID = " + id;
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean exportPost(int id, File directory) {
        Post targetPost = retrievePost(id);
        if (targetPost != null && directory != null && directory.isDirectory()) {
            try {
                // Construct the file path
                File file = new File(directory + File.separator + "post" + ".csv");

                FileWriter writer = new FileWriter(file);
                // header row
                writer.write("ID,content,author,#likes,#shares,date-time\n");
                // data row
                writer.write(String.join(",", String.valueOf(targetPost.getId()),
                        appendDQ(targetPost.getContent()), appendDQ(targetPost.getAuthor()),
                        appendDQ(String.valueOf(targetPost.getNumLikes())),
                        appendDQ(String.valueOf(targetPost.getNumShares())), appendDQ(targetPost.getDateTime())));
                writer.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Solution to dealing with "," characters when parsing CSV referenced here
    // https://stackoverflow.com/questions/16103224/string-containing-comma-inputting-in-to-the-csv-file
    private String appendDQ(String str) {
        return "\"" + str + "\"";
    }

    public List<Post> getTopNPosts(int n, String author) {
        String sql = "SELECT * from posts ";
        if (author != null) {
            sql += "where author = \"" + author + "\" ";
        }
        sql += "ORDER BY num_likes DESC LIMIT " + n;
        List<Post> posts = new ArrayList<>();
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID");
                String content = rs.getString("content");
                String postAuthor = rs.getString("author");
                int likes = rs.getInt("num_likes");
                int shares = rs.getInt("num_shares");
                String datetime = rs.getString("datetime");
                Post post = new Post(id, content, postAuthor, likes, shares, datetime);
                posts.add(post);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return posts;
    }

    public List<Integer> getSharesDistribution(){
        String sql = "SELECT\n  CASE\n    WHEN num_shares BETWEEN 0 AND 99 THEN '0-99'\n    "+
                "WHEN num_shares BETWEEN 100 AND 999 THEN '100-999'\n    WHEN num_shares >= 1000 THEN '>=1000'\n  " +
                "END AS share_category,\n   COUNT(*) AS share_count\nFROM posts\n GROUP BY share_category";

        List<Integer> sharesArray = new ArrayList<>();
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int count = rs.getInt("share_count");
                sharesArray.add(count);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sharesArray;
    }

    public boolean parseCSV(File file){
        try {
            CSVParser.readPosts(file);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

}
