package com.backend;

import com.models.Post;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostControllerTest {
    Post samplePost = new Post(0, "test", "default", 1, 1, "11/11/1111 11:11");
    @Test
    @Order(1)
    void addNewPost() {
        var postController = new PostController();
        boolean status = postController.addNewPost(samplePost.getId(), samplePost.getContent(),
                samplePost.getNumLikes(), samplePost.getNumShares(), samplePost.getDateTime());
        assertEquals(true, status);
    }

    @Test
    @Order(2)
    void postIDisUnique() {
        var postController = new PostController();
        boolean status = postController.postIDisUnique(0);
        assertEquals(false, status);
    }

    @Test
    @Order(2)
    void retrievePost() {
        var postController = new PostController();
        Post post =  postController.retrievePost(samplePost.getId());
        assertNotEquals(post, null);
        assertEquals( samplePost.getContent(), post.getContent());
        assertEquals(samplePost.getNumLikes(), post.getNumLikes());
        assertEquals(samplePost.getNumShares(), post.getNumShares());
        assertEquals(samplePost.getDateTime(), post.getDateTime());
    }

    @Test
    @Order(3)
    void deletePost() {
        var postController = new PostController();
        assertNotEquals(null, postController.retrievePost(samplePost.getId()));
        boolean status = postController.deletePost(samplePost.getId());
        assertEquals(true, status);
        assertEquals(null, postController.retrievePost(samplePost.getId()));
    }

    @Test
    @Order(2)
    void getTopNPosts() {
        var postController = new PostController();
        List<Post> posts = postController.getTopNPosts(1, samplePost.getAuthor());
        assertNotEquals(null, posts);
        assertEquals(1, posts.size());
    }

}