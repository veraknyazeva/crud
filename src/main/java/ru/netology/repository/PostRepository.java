package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
// Stub
public class PostRepository {
    private final AtomicLong count = new AtomicLong(0);
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            long id = count.incrementAndGet();
            post.setId(id);
            posts.put(id, post);
        } else {
            Optional<Post> optional = getById(post.getId());
            if (optional.isPresent()) {
                posts.put(post.getId(), post);
            } else {
                //назначаем свой id и сохраняем
                long id = count.incrementAndGet();
                post.setId(id);
                posts.put(id, post);
            }
        }
        return post;
    }

    public void removeById(long id) {
        posts.remove(id);
    }
}