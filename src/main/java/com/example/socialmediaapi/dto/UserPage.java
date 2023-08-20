package com.example.socialmediaapi.dto;

import com.example.socialmediaapi.models.Friends;
import com.example.socialmediaapi.models.Posts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
/**
 * DTO Страница пользователя
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPage {
    private String fullName;
    private String city;
    private List<Posts> posts;
    private List<Friends> friends;

    public <E> UserPage(int userId, String john_doe, String s, String s1, ArrayList<E> es) {

    }
}