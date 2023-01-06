package com.muates.userservice.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.muates.userservice.model.dto.request.UserActivationRequest;
import com.muates.userservice.model.entity.EmailToken;
import com.muates.userservice.model.entity.User;
import com.muates.userservice.repository.EmailTokenRepository;
import com.muates.userservice.repository.UserRepository;
import com.muates.userservice.service.UserActivationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserActivationServiceImpl implements UserActivationService {

    private final Logger log = LoggerFactory.getLogger(UserActivationServiceImpl.class);

    private final UserRepository userRepository;
    private final EmailTokenRepository emailTokenRepository;

    @Override
    public String activateUser(String token) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        String username = decodedJWT.getSubject();
        String email = decodedJWT.getClaim("email").asString();

        Optional<User> optUser = userRepository.findUserByUsername(username);

        if (optUser.isEmpty()) {
            log.error("User does not exist, username: {}", username);
            throw new UsernameNotFoundException("User does not exist");
        }

        User user = optUser.get();

        if (!Objects.equals(user.getEmail(), email)) {
            log.error("Email does not match, email: {}", email);
            throw new RuntimeException("Email does not match");
        }

        Optional<EmailToken> optEmailToken = emailTokenRepository.findByUserId(user.getId());

        if (optEmailToken.isEmpty()) {
            log.error("Token does not exist");
            throw new IllegalStateException("Token does not exist");
        }

        EmailToken emailToken = optEmailToken.get();

        if (!emailToken.getEnable()) {
            log.error("Token already use");
            throw new RuntimeException("Token already use");
        }

        user.setEnabled(true);
        user.setUpdatedDate(new Date());
        userRepository.save(user);

        emailToken.setEnable(false);
        emailToken.setUpdatedDate(new Date());
        emailTokenRepository.save(emailToken);

        log.info("User activation successful, username: {}", username);

        return "User activation successful";
    }

    @Override
    public String createToken(UserActivationRequest request) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String token = JWT.create()
                .withSubject(request.getUsername())
                .withClaim("userId", request.getUserId())
                .withClaim("email", request.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                .sign(algorithm);

        EmailToken emailToken = new EmailToken();
        emailToken.setUserId(request.getUserId());
        emailToken.setToken(token);
        emailToken.setCreatedDate(new Date());
        emailTokenRepository.save(emailToken);

        return token;
    }
}
