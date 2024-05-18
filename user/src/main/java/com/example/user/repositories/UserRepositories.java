package com.example.user.repositories;

import com.example.user.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Repository interface for accessing user data in MongoDB.
 * This interface extends MongoRepository to provide basic CRUD operations for User objects.
 *
 * @author Duy Hoang
 */
public interface UserRepositories extends MongoRepository<User, String> {
    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user to find.
     * @return An Optional containing the user if found, otherwise an empty Optional.
     */
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
