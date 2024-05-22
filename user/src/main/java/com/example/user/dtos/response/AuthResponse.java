package com.example.user.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class indicate the dto of authentication response.
 *
 * @author Duy Hoang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthResponse {
    /** This is the storage field for the user response. */
    private UserGeneralResponse user;

    /** This is the storage field for the access token. */
    private String accessToken;

    /** This is the storage field for the refresh token. */
    private String refreshToken;
}
