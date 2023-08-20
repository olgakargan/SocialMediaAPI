package com.example.socialmediaapi.controller;


import com.example.socialmediaapi.models.Posts;
import com.example.socialmediaapi.models.Role;
import com.example.socialmediaapi.models.User;
import com.example.socialmediaapi.service.PostsService;
import com.example.socialmediaapi.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
class PostsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostsService postsService;

    @Autowired
    private UserService userService;

    static User userOne;
    static User userTwo;
    static User userThree;
    static User userFour;

    @BeforeAll
    static void setUp() {
        userOne = User.builder().fullName("Olga").city("City").login("Olga").password("Olga").role(Role.USER).build();
        userTwo = User.builder().fullName("Ivan").city("City").login("Ivan").password("Ivan").role(Role.USER).build();
        userThree = User.builder().fullName("Piter").city("City").login("Piter").password("Piter").role(Role.USER).build();
        userFour = User.builder().fullName("Dana").city("City").login("Dana").password("Dana").role(Role.USER).build();

    }

    @Test
    public void addPost() {
        User savedUserOne = userService.save(userOne);
        User savedUserTwo = userService.save(userTwo);
        User savedUserThree = userService.save(userThree);
        User savedUserFour = userService.save(userFour);

        postsService.savePost(Posts.builder().userOwner(savedUserOne).header("a").text("a").creatingTime(LocalDateTime.now()).build());
        postsService.savePost(Posts.builder().userOwner(savedUserTwo).header("aa").text("aa").creatingTime(LocalDateTime.now()).build());
        postsService.savePost(Posts.builder().userOwner(savedUserThree).header("aaa").text("aaa").creatingTime(LocalDateTime.now()).build());
        postsService.savePost(Posts.builder().userOwner(savedUserFour).header("aaaa").text("aaaa").creatingTime(LocalDateTime.now()).build());
    }
    @Test
    void givenValidUserId_whenGetFriendsPosts_thenReturnsPosts() throws Exception {

        // Given
        String userId = "valid_user_id";
        int offset = 0;
        int limit = 10;

        // When
        MvcResult mvcResult = mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/{id}/posts?offset={offset}&limit={limit}", userId, offset, limit))
                .andExpect(status().is4xxClientError())
                .andReturn();

        // Then
        List<Posts> posts = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
        assertNotNull(posts);
        assertFalse(posts.isEmpty());

    }


    @Test
    void getUserPosts() {
    }

    @Test
    void editPost() {
    }

    @Test
    void deletePost() {
    }
}