package com.tasksmart.user.services.impls;

import com.tasksmart.sharedLibrary.dtos.request.ExchangeGitHubTokenRequest;
import com.tasksmart.sharedLibrary.dtos.request.ExchangeGoogleTokenRequest;
import com.tasksmart.sharedLibrary.dtos.responses.*;
import com.tasksmart.sharedLibrary.repositories.httpClients.OAuth.GitHubAuthClient;
import com.tasksmart.sharedLibrary.repositories.httpClients.OAuth.GitHubClient;
import com.tasksmart.sharedLibrary.repositories.httpClients.OAuth.GoogleAuthClient;
import com.tasksmart.sharedLibrary.utils.AuthenticationUtils;
import com.tasksmart.user.dtos.request.UserSignInRequest;
import com.tasksmart.user.dtos.response.AuthResponse;
import com.tasksmart.user.dtos.response.UserGeneralResponse;
import com.tasksmart.user.models.User;
import com.tasksmart.user.repositories.UserRepository;
import com.tasksmart.user.services.AuthService;
import com.tasksmart.sharedLibrary.exceptions.BadRequest;
import com.tasksmart.sharedLibrary.exceptions.ResourceNotFound;
import com.tasksmart.sharedLibrary.utils.JWTUtil;
import com.tasksmart.user.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the AuthService interface.
 * This class provides authentication-related functionalities.
 *
 * @author Duy Hoang
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    /** Repository instance for accessing user data. */
    private final UserRepository userRepository;

    /** Mapper instance for converting data between different representations. */
    private final ModelMapper modelMapper;

    /** Encoder instance for hashing and verifying passwords. */
    private final PasswordEncoder passwordEncoder;

    private final JWTUtil jwtUtil;

    private final AuthenticationUtils authenticationUtils;

    private final GoogleAuthClient googleAuthClient;
    private final GitHubAuthClient gitHubAuthClient;
    private final GitHubClient gitHubClient;
    private final UserService userService;

    @Value("${oauth.google.clientId}")
    private String GOOGLE_CLIENT_ID;

    @Value("${oauth.google.clientSecret}")
    private String GOOGLE_CLIENT_SECRET;

    @Value("${oauth.google.redirectUri}")
    private String GOOGLE_REDIRECT_URI;

    @Value("${oauth.google.grantType}")
    private String GOOGLE_GRANT_TYPE;

    @Value("${oauth.github.clientId}")
    private String GITHUB_CLIENT_ID;

    @Value("${oauth.github.clientSecret}")
    private String GITHUB_CLIENT_SECRET;

    @Value("${oauth.github.redirectUri}")
    private String GITHUB_REDIRECT_URI;

    @Override
    public UserGeneralResponse introspect() {
        String userId = authenticationUtils.getUserIdAuthenticated();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFound("User not found!")
        );
        return modelMapper.map(user, UserGeneralResponse.class);
    }

    public AuthResponse googleAuthenticate(String code){
        ExchangeGoogleTokenResponse exchangeGoogleTokenResponse = googleAuthClient.exchangeToken(ExchangeGoogleTokenRequest
                .builder()
                .code(code)
                        .clientId(GOOGLE_CLIENT_ID)
                        .clientSecret(GOOGLE_CLIENT_SECRET)
                        .redirectUri(GOOGLE_REDIRECT_URI)
                        .grantType(GOOGLE_GRANT_TYPE)
                .build());
        GoogleUserResponse googleUserResponse = googleAuthClient.tokenInfo(exchangeGoogleTokenResponse.getId_token());
        User user;
        if(!userRepository.existsByEmail(googleUserResponse.getEmail())){
            user = userService.createUserOAuth(googleUserResponse.getEmail(), googleUserResponse.getName(), googleUserResponse.getPicture(), "");
        }else{
            user = userRepository.findByEmail(googleUserResponse.getEmail()).orElseThrow(
                    () -> new ResourceNotFound("User not found!")
            );
            if(StringUtils.equals(user.getProfileImagePath(), googleUserResponse.getPicture())){
                user.setProfileImagePath(googleUserResponse.getPicture());
                userRepository.save(user);
            }
        }
        UserGeneralResponse userGeneralResponse = getUserResponse(user);
        return AuthResponse.builder()
                .user(userGeneralResponse)
                .accessToken(jwtUtil.generateToken(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getRole(), 1))
                .refreshToken(jwtUtil.generateToken(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getRole(), 24*5))
                .build();
    }

    public AuthResponse githubAuthenticate(String code){
        ExchangeGithubTokenResponse exchange = gitHubAuthClient.exchangeToken(ExchangeGitHubTokenRequest
                .builder()
                .code(code)
                .client_id(GITHUB_CLIENT_ID)
                .client_secret(GITHUB_CLIENT_SECRET)
                .redirect_uri(GITHUB_REDIRECT_URI)
                .build());
        System.out.println(exchange.getAccess_token());

        GitHubUserResponse gitHubUserResponse = gitHubClient.getUserInfo("Bearer "+ exchange.getAccess_token());
        List<GitHubUserEmailResponse> emails = gitHubClient.getUserEmail("Bearer "+ exchange.getAccess_token());
        String email = getEmailPrimary(emails);

        User user;
        if(!userRepository.existsByEmail(email)){
            user = userService.createUserOAuth(email, gitHubUserResponse.getName(), gitHubUserResponse.getAvatar_url(), gitHubUserResponse.getLogin());
        }else{
            user = userRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFound("User not found!")
            );
            if(StringUtils.equals(user.getProfileImagePath(), gitHubUserResponse.getAvatar_url())){
                user.setProfileImagePath(gitHubUserResponse.getAvatar_url());
                userRepository.save(user);
            }
        }
        return AuthResponse.builder()
                .user(getUserResponse(user))
                .accessToken(jwtUtil.generateToken(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getRole(), 1))
                .refreshToken(jwtUtil.generateToken(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getRole(), 24*5))
                .build();
    }

    /** {@inheritDoc} */
    @Override
    public AuthResponse login(UserSignInRequest userSignInRequest) {
        if(StringUtils.isBlank(userSignInRequest.getEmail()) && StringUtils.isBlank(userSignInRequest.getUsername())){
            throw new BadRequest("Please provide complete login information!");
        }

        User user;

        if(StringUtils.isNotBlank(userSignInRequest.getEmail())){
            user = userRepository.findByEmail(userSignInRequest.getEmail()).orElseThrow(
                    () -> new ResourceNotFound("User not found!")
            );
//            if(!CollectionUtils.isEmpty(user.getAccountType())) {
//                List<EUserType> accountTypes = user.getAccountType().stream().toList();
//                if(!accountTypes.contains(EUserType.Default))
//                    throw new BadRequest("User not found!");
//            }
        } else {
            user = userRepository.findByUsername(userSignInRequest.getUsername()).orElseThrow(
                    () -> new ResourceNotFound("User not found!")
            );
        }

        if(! passwordEncoder.matches(userSignInRequest.getPassword(), user.getPassword())){
            throw new BadRequest("Invalid username/email or password!");
        }

        UserGeneralResponse userGeneralResponse = getUserResponse(user);
        return AuthResponse.builder()
                .user(userGeneralResponse)
                .accessToken(jwtUtil.generateToken(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getRole(), 1))
                .refreshToken(jwtUtil.generateToken(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getRole(), 24*5))
                .build();
    }

    @Override
    public AuthResponse refresh(String refresh){
        if (refresh == null || !refresh.startsWith("Bearer"))
            throw new BadRequest("Invalid authorization header.");

        String refreshToken = refresh.substring(7);
        Jwt jwt = jwtUtil.jwtDecoder().decode(refreshToken);
        String UserId = jwt.getClaim("userId");

        User user = userRepository.findById(UserId).orElseThrow(
                () -> new ResourceNotFound("User not found!")
        );

        UserGeneralResponse userGeneralResponse = getUserResponse(user);
        return AuthResponse.builder()
                .user(userGeneralResponse)
                .accessToken(jwtUtil.generateToken(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getRole(), 1))
                .refreshToken(refresh)
                .build();
    }

    private UserGeneralResponse getUserResponse(User user){
        return modelMapper.map(user, UserGeneralResponse.class);
    }
    private String getEmailPrimary(List<GitHubUserEmailResponse> emails){
        for(GitHubUserEmailResponse email : emails){
            if(email.isPrimary())
                return email.getEmail();
        }
        return null;
    }
}
