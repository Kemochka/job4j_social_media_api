package ru.job4j.socialmedia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.model.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long id);

    List<Post> findByCreatedIsGreaterThanEqualAndCreatedIsLessThanEqual(LocalDateTime startAt, LocalDateTime endAt);

    Page<Post> findByOrderByCreatedDesc(Pageable pageable);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("""
            update Post post set post.title = :title, post.description = :description where post.id = :id
            """)
    int updateTitleAndDesc(@Param("title") String title, @Param("description") String description, @Param("id") Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("""
            delete from File file where file.post.id = :id
            """)
    int deleteFileByPostId(@Param("id") Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("""
            delete from Post post where post.id = :id
            """)
    int deletePostById(@Param("id") Long id);

    @Query("""
            select post from Post post
            join User user on user.id = post.user.id
            join Subscribe s on user.id = s.userTo.id
            where s.userSubscriber.id = :id order by post.created desc
            """)
    Page<Post> findAllSubscriberPostsByUserOrderByCreatedDesc(@Param("id") Long id, Pageable pageable);
}
