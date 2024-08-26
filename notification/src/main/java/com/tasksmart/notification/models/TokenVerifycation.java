package com.tasksmart.notification.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Random;

/**
 * Represents a TokenVerifycation in the project.
 * A TokenVerifycation is a card that contains a code, email, and other information.
 * @author Duy Hoang
 */
@Document("TokenVerifycation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TokenVerifycation {
    @Id
    private String id;

    @Builder.Default
    private String code = String.format("%06d", new Random().nextInt(900000) + 100000);

    private String email;

    @Indexed(expireAfter = "60s")
    @Builder.Default
    private Date createAt = new Date() ;
}
