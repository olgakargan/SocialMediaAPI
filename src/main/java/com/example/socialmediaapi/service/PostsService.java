package com.example.socialmediaapi.service;

import com.example.socialmediaapi.exceptions.IncorrectIdException;
import com.example.socialmediaapi.exceptions.UserNotFoundException;
import com.example.socialmediaapi.models.Posts;
import com.example.socialmediaapi.models.User;
import org.springframework.web.multipart.MultipartFile;

import javax.management.AttributeNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PostsService {
    List<Posts> getAllByUser(Optional<User> userOptional);

    List<Posts> getAllByUserId(String id) throws IncorrectIdException;

    List<Posts> getAllPostsFromFriends(String id, int offset, int limit) throws IncorrectIdException, UserNotFoundException;

    void savePost(Posts posts);

    void editPost(String header, String text, String pic, String id);

    String getPic(String id) throws AttributeNotFoundException, IncorrectIdException;

    String saveImage(MultipartFile pic) throws IOException;

    boolean deletePost(int postId);
}