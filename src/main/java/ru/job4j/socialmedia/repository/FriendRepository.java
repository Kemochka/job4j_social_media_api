package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.model.Friend;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Transactional
    @Modifying
    @Query("""
            delete from Friend friend where friend.id = :id
            """)
    void delete(@Param("id") Long id);

    List<Friend> findFriendByUserId(Long id);
}
