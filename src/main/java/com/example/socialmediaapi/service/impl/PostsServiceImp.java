package com.example.socialmediaapi.service.impl;

import com.example.socialmediaapi.exceptions.IncorrectIdException;
import com.example.socialmediaapi.models.Friends;
import com.example.socialmediaapi.models.Posts;
import com.example.socialmediaapi.models.User;
import com.example.socialmediaapi.repository.FriendsRepository;
import com.example.socialmediaapi.repository.PostsRepository;
import com.example.socialmediaapi.service.PostsService;
import com.example.socialmediaapi.utils.StringUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.AttributeNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Класс PostsServiceImp реализует интерфейс PostsService и предоставляет реализацию методов для работы с постами пользователей.
 <p>Поля:

 <p>- private String filePath - путь к папке для сохранения изображений.
 <p>- private final {@link PostsRepository} postsRepo - репозиторий для работы с постами.
 <p>- private final {@link FriendsRepository} friendsRepo - репозиторий для работы с друзьями пользователей.

 <p>Конструктор:

 <p>- public PostsServiceImp(PostsRepository postsRepo, FriendsRepository friendsRepo)

 <p>Методы:

 <p>- public List<{@link Posts}> getAllByUser(Optional<{@link User}> userOptional) - метод, который возвращает список всех постов пользователя, если пользователь передан в параметре. Если параметр не передан, то возвращается пустой List.
 <p>- public List<{@link Posts}> getAllByUserId(String id) throws IncorrectIdException - метод, который возвращает список всех постов пользователя по его id. Если id не корректный, то выбрасывается исключение {@link IncorrectIdException}.
 <p>- public List<{@link Posts}> getAllPostsFromFriends(String id, int pageNumber, int pageSize) throws IncorrectIdException - метод, который возвращает страницу со всеми постами друзей пользователя. В параметрах передается id пользователя, номер страницы и лимит записей на странице. Если id не корректный, то выбрасывается исключение {@link IncorrectIdException}.
 <p>- public void savePost(Posts posts) - метод, который сохраняет пост в базу данных.
 <p>- public void editPost(String header, String text, String pic, String id) - метод, который изменяет заголовок, текст и изображение поста по его id.
 <p>- public String getPic(String id) throws AttributeNotFoundException, IncorrectIdException - метод, который возвращает путь к изображению поста по его id. Если id не корректный или изображение не найдено, то выбрасывается исключение AttributeNotFoundException или {@link IncorrectIdException} соответственно.
 <p>- public String saveImage(MultipartFile pic) throws IOException - метод, который сохраняет изображение в папку filePath и возвращает путь к сохраненному файлу. Если произошла ошибка при сохранении файла, то выбрасывается исключение IOException.
 <p>- public boolean deletePost(int postId) - метод, который удаляет пост по его id.
 * */
@Service
public class PostsServiceImp implements PostsService {

    @Value("${path.to.file.folder}")
    private String filePath;

    private final PostsRepository postsRepo;

    private final FriendsRepository friendsRepo;

    private final EntityManagerFactory entityManagerFactory;


    public PostsServiceImp(PostsRepository postsRepo, FriendsRepository friendsRepo, EntityManagerFactory entityManagerFactory) {
        this.postsRepo = postsRepo;
        this.friendsRepo = friendsRepo;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<Posts> getAllByUser(Optional<User> userOptional) {
        return userOptional.map(postsRepo::getAllByUserOwner).orElse(Collections.emptyList());
    }
    @Override
    public List<Posts> getAllByUserId(String id) throws IncorrectIdException {
        return postsRepo.getAllByUserOwnerId(StringUtil.ValidationId(id));
    }

    @Override
    public List<Posts> getAllPostsFromFriends(String id, int pageNumber, int pageSize) throws IncorrectIdException {

        int intId = StringUtil.ValidationId(id);

        List<Friends> friendsArrayList = new ArrayList<>();
        friendsArrayList.addAll(friendsRepo.getAllFriends(intId));
        friendsArrayList.addAll(friendsRepo.getAllSubscribes(intId));

        List<Integer> friendsIdArrayList;
        friendsIdArrayList = friendsArrayList.stream()
                .map(Friends::getUserTo)
                .collect(Collectors.toCollection(ArrayList::new));
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {

            return entityManager.createQuery("SELECT posts FROM Posts posts WHERE posts.userOwner.id IN :friendsIds order by posts.creatingTime desc")
                    .setParameter("friendsIds", friendsIdArrayList)
                    .setFirstResult((pageNumber - 1) * pageSize)
                    .setMaxResults(pageSize)
                    .getResultList();
        }
    }

    @Override
    public void savePost(Posts posts) {
        postsRepo.save(posts);
    }

    @Override
    public void editPost(String header, String text, String pic, String id) {
        postsRepo.editPost(
                header,
                text,
                pic,
                String.valueOf(id));
    }

    @Override
    public String getPic(String id) throws AttributeNotFoundException, IncorrectIdException {
        Optional<Posts> postsOptional = postsRepo.findById(StringUtil.ValidationId(id));
        if (postsOptional.isPresent()) {
            return postsOptional.get().getPic();
        } else {
            System.out.println("Изображение не найдено");
            throw new AttributeNotFoundException();
        }
    }

    @Override
    public String saveImage(MultipartFile pic) throws IOException {
        File storageFile = new File(filePath + "/" + pic.getName());
        Path path = Path.of(filePath, pic.getName());
        try (FileOutputStream fos = new FileOutputStream(storageFile)) {
            Files.createFile(path);
            IOUtils.copy(pic.getInputStream(), fos);
        }
        return path.toString();
    }

    @Override
    public boolean deletePost(int postId){
        postsRepo.deleteById(postId);
        return true;
    }
}