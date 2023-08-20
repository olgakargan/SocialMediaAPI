package com.example.socialmediaapi.repository;

import com.example.socialmediaapi.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


/**
 * Репозиторий для пользователей
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional
    Optional<User> getUserByLogin(String login);

    @Transactional
    @Query("SELECT id from User")
    List<Integer> getAllUsersId();
}