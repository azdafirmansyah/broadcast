package com.azda.broadcast.handler;

import com.azda.broadcast.model.Users;
import com.azda.broadcast.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TokenHandler {
    public static Users tokenValidation(String token, UserRepository userRepository){
        Users usr = userRepository.findByToken(token);
        if (usr == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid Token", null);
        }
        return usr;
    }
}
