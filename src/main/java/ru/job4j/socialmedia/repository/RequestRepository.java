package ru.job4j.socialmedia.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.socialmedia.model.Request;

public interface RequestRepository extends CrudRepository<Request, Long> {
}
