package com.tasksmart.notification.services.impls;

import com.tasksmart.notification.models.TokenVerifycation;
import com.tasksmart.notification.repositories.VerifycationRepository;
import com.tasksmart.notification.services.VerifycationService;
import com.tasksmart.sharedLibrary.exceptions.BadRequest;
import com.tasksmart.sharedLibrary.models.UserDetail;
import com.tasksmart.sharedLibrary.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerifycationServiceImpl implements VerifycationService {
    private final AuthenticationUtils authenticationUtils;
    private final VerifycationRepository verificationRepository;

    @Override
    public boolean verify(String code) {
        String userId = authenticationUtils.getUserAuthenticated().getUserId();
        Optional<TokenVerifycation> tokenVerifycation = verificationRepository.findByUserIdAndCode(userId, code);
        if(tokenVerifycation.isPresent()){
            verificationRepository.delete(tokenVerifycation.get());
            return true;
        }else {
            throw new BadRequest("Invalid token");
        }
    }

    @Override
    public TokenVerifycation verifycationRequest() {
        UserDetail user = authenticationUtils.getUserAuthenticated();
        System.out.println("Verification request for user: " + user.getEmail());

        TokenVerifycation token = TokenVerifycation.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .build();

        //send email

        verificationRepository.save(token);

        return token;
    }
}
