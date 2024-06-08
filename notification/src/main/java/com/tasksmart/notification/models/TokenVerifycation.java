package com.tasksmart.notification.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Random;

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
    private String code = String.format("%06d", new Random().nextInt(900000) + 100000);;

    private String email;
    private String userId;

    @Indexed(expireAfter = "60s")
    @Builder.Default
    private Date createAt = new Date() ;
}
