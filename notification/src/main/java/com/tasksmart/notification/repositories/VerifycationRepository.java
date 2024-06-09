package com.tasksmart.notification.repositories;

import com.tasksmart.notification.models.TokenVerifycation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VerifycationRepository extends MongoRepository<TokenVerifycation, String> {
    Optional<TokenVerifycation> findByEmailAndCode(String email,String code);
    Optional<TokenVerifycation> findByEmail(String email);
}
