package com.example.socialmediaapi.service;

import com.example.socialmediaapi.dto.UserPage;
import com.example.socialmediaapi.exceptions.UserNotFoundException;
import com.example.socialmediaapi.models.Friends;
import com.example.socialmediaapi.models.User;

import java.util.List;

public interface UserService {

    User save(User user);

    List<User> getAll();

    User getUserById(int id) throws UserNotFoundException;

    UserPage getUserPage(int id) throws UserNotFoundException;

    boolean sendRequest(int userIdFrom, int userIdTo) throws UserNotFoundException;

    List<Friends> getFriendsListById(int userId) throws UserNotFoundException;

    List<Friends> getSubscribesListById(int userId) throws UserNotFoundException;

    boolean acceptFriendRequest(int userIdFrom, int userIdTo) throws UserNotFoundException;

    boolean deleteFromFriends(int userIdFrom, int userIdTo) throws UserNotFoundException;
}
