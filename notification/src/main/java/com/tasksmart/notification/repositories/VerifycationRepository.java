package com.tasksmart.notification.repositories;

import com.tasksmart.notification.models.TokenVerifycation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * The repository for the TokenVerifycation model.
 * This repository is used to interact with the database.
 * @author Duy Hoang
 */
public interface VerifycationRepository extends MongoRepository<TokenVerifycation, String> {
    /**
     * Find a TokenVerifycation by email and code.
     * @param email The email of the TokenVerifycation
     * @param code The code of the TokenVerifycation
     * @return The TokenVerifycation with the given email and code
     */
    Optional<TokenVerifycation> findByEmailAndCode(String email,String code);

    /**
     * Find a TokenVerifycation by email.
     * @param email The email of the TokenVerifycation
     * @return The TokenVerifycation with the given email
     */
    Optional<TokenVerifycation> findByEmail(String email);
}
