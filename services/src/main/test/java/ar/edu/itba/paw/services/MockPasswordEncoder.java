package ar.edu.itba.paw.services;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by daniel on 5/15/17.
 */
public class MockPasswordEncoder implements PasswordEncoder{
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword);
    }
}
