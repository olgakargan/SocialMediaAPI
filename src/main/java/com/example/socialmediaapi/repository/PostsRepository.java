package com.example.socialmediaapi.repository;

import com.example.socialmediaapi.models.Posts;
import com.example.socialmediaapi.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Репозиторий для записей
 */
public interface PostsRepository extends JpaRepository<Posts, Integer>, PagingAndSortingRepository<Posts, Integer> {

    @Transactional
    List<Posts> getAllByUserOwner(User userOwner);

    @Transactional
    List<Posts> getAllByUserOwnerId(int id);


    @Transactional
    @Modifying
    @Query("update Posts e set e.header = :header, e.text = :text, e.pic = :pic where e.id = :id")
    void editPost(String header, String text, String pic, String id);
}