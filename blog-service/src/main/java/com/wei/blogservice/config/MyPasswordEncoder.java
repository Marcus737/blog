package com.wei.blogservice.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author 凌雪
 */
@Component
public class MyPasswordEncoder implements PasswordEncoder {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String encode(CharSequence rawPassword) {
        return encoder.encode(rawPassword);
    }


    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (!rawPassword.equals(encodedPassword)){
            return encoder.matches(rawPassword, encodedPassword);
        }
        return true;
    }
}
