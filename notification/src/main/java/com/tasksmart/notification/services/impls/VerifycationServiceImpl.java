package com.tasksmart.notification.services.impls;

import com.tasksmart.notification.models.TokenVerifycation;
import com.tasksmart.notification.repositories.VerifycationRepository;
import com.tasksmart.notification.services.EmailSenderService;
import com.tasksmart.notification.services.VerifycationService;
import com.tasksmart.sharedLibrary.exceptions.BadRequest;
import com.tasksmart.sharedLibrary.models.UserDetail;
import com.tasksmart.sharedLibrary.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class VerifycationServiceImpl implements VerifycationService {
    private final VerifycationRepository verificationRepository;
    private final EmailSenderService emailSenderService;

    @Override
    public boolean verify(String email, String code) {
        Optional<TokenVerifycation> tokenVerifycation = verificationRepository.findByEmailAndCode(email, code);
        if(tokenVerifycation.isPresent()){
            TokenVerifycation token = tokenVerifycation.get();
            if(token.getCreateAt().getTime() + 60000*10 < new Date().getTime()){
                throw new BadRequest("Token expired");
            }
            return true;
        }else {
            throw new BadRequest("Invalid token");
        }
    }

    @Override
    public boolean verifyInternal(String email, String code) {
        Optional<TokenVerifycation> tokenVerifycation = verificationRepository.findByEmailAndCode(email, code);
        if(tokenVerifycation.isPresent()){
            TokenVerifycation token = tokenVerifycation.get();
            if(token.getCreateAt().getTime() + 60000*10 < new Date().getTime()){
                return false;
            }
            return true;
        }else {
            return false;
        }
    }

    @Override
    public TokenVerifycation verifycationRequest(String email) {
        Optional<TokenVerifycation> tokenVerifycationOptional = verificationRepository.findByEmail(email);
        if(tokenVerifycationOptional.isPresent()){
            TokenVerifycation tokenVerifycation = tokenVerifycationOptional.get();
            if(tokenVerifycation.getCreateAt().getTime() + 60000 < new Date().getTime()){
                tokenVerifycation.setCode(String.format("%06d", new Random().nextInt(900000) + 100000));
                tokenVerifycation.setCreateAt(new Date());
                verificationRepository.save(tokenVerifycation);

                emailSenderService.sendVerificationEmail(email, tokenVerifycation.getCode());
                return tokenVerifycation;
            }else{
                throw new BadRequest("Please wait for 60s to request new token");
            }
        }else{
            TokenVerifycation token = TokenVerifycation.builder()
                    .email(email)
                    .build();
            verificationRepository.save(token);
            emailSenderService.sendVerificationEmail(email, token.getCode());
            return token;
        }
    }
}
