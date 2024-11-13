package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.model.Subscribe;

import java.util.List;
import java.util.Optional;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    @Transactional
    @Modifying
    @Query("""
            delete from Subscribe sub where sub.id = :id
            """)
    void deleteSubscribeById(@Param("id") Long id);

    @Query("""
            select subscribe from Subscribe subscribe
            where subscribe.userSubscriber.id = :userSubscriberId
            and subscribe.userTo.id = :userTo
            """)
    Optional<Subscribe> findSubscribeByUserSubscriberIdAndIdAndUserToId(@Param("userSubscriber") Long userSubscriberId, @Param("userTo") Long userToId);

    List<Subscribe> findByUserSubscriberId(Long id);
}
