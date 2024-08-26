package com.tasksmart.sharedLibrary.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExchangeGithubTokenResponse {
    String access_token;
    Long expires_in;
    String refresh_token;
    String scope;
    Long refresh_token_expires_in;
    String token_type;
}
