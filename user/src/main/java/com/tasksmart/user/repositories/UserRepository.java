package com.tasksmart.user.repositories;

import com.tasksmart.user.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing user data in MongoDB.
 * This interface extends MongoRepository to provide basic CRUD operations for User objects.
 *
 * @author Duy Hoang
 */
public interface UserRepository extends MongoRepository<User, String> {
    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user to find.
     * @return An Optional containing the user if found, otherwise an empty Optional.
     */
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    List<User> findAllByIdIn(List<String> userIds);

    @Query("{$or: [{'name': {$regex: ?0, $options: 'i'}}, {'email': {$regex: ?0, $options: 'i'}}]}")
    List<User> findByNameContainingOrEmailContaining(String keyword);

}
